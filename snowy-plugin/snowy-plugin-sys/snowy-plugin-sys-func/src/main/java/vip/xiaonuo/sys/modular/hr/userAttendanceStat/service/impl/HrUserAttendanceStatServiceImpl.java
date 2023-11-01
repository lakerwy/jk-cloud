/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.sys.modular.hr.userAttendanceStat.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.util.DateUtils;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.pojo.SaBaseLoginUser;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.common.util.CommonTimeFormatUtil;
import vip.xiaonuo.sys.modular.hr.applyWorkOt.entity.HrApplyWorkOt;
import vip.xiaonuo.sys.modular.hr.applyWorkOt.service.HrApplyWorkOtService;
import vip.xiaonuo.sys.modular.hr.festivalData.entity.HrFestivalData;
import vip.xiaonuo.sys.modular.hr.festivalData.service.HrFestivalDataService;
import vip.xiaonuo.sys.modular.hr.userApplyHoliday.entity.HrUserApplyHoliday;
import vip.xiaonuo.sys.modular.hr.userApplyHoliday.enums.HrUserApplyHolidayEnum;
import vip.xiaonuo.sys.modular.hr.userApplyHoliday.service.HrUserApplyHolidayService;
import vip.xiaonuo.sys.modular.hr.userAttendance.entity.HrUserAttendance;
import vip.xiaonuo.sys.modular.hr.userAttendance.enums.HrUserAttendanceEnum;
import vip.xiaonuo.sys.modular.hr.userAttendance.param.HrUserAttendancePageParam;
import vip.xiaonuo.sys.modular.hr.userAttendance.service.HrUserAttendanceService;
import vip.xiaonuo.sys.modular.hr.userAttendance.vo.AttendanceConfigCacheEntity;
import vip.xiaonuo.sys.modular.hr.userAttendance.vo.AttendanceTimeSlot;
import vip.xiaonuo.sys.modular.hr.userAttendance.vo.UserAttendanceConfigEntity;
import vip.xiaonuo.sys.modular.hr.userAttendance.vo.UserAttendanceCountEntity;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.entity.HrUserAttendanceConfig;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.service.HrUserAttendanceConfigService;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfigGroup.service.HrUserAttendanceConfigGroupService;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.entity.HrUserAttendanceStat;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.enums.HrUserAttendanceStatEnum;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.param.*;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.vo.*;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.mapper.HrUserAttendanceStatMapper;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.service.HrUserAttendanceStatService;
import vip.xiaonuo.sys.modular.user.entity.SysUser;
import vip.xiaonuo.sys.modular.user.service.SysUserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 考勤统计表Service接口实现类
 *
 * @author ljy
 * @date  2023/10/07 10:28
 **/
@Service
public class HrUserAttendanceStatServiceImpl extends ServiceImpl<HrUserAttendanceStatMapper, HrUserAttendanceStat> implements HrUserAttendanceStatService {

    private final String ON_DUTY = "OnDuty";
    private final String OFF_DUTY = "OffDuty";
    private static final String HR_ATTENDANCE_CONFIG_KEY = "HR_ATTENDANCE_CONFIG_KEY";
    private static final String HR_ATTENDANCE_CONFIG_DEFAULT_KEY = "HR_ATTENDANCE_CONFIG_DEFAULT_KEY";  //默认配置
    List<HrFestivalData> FESTIVAL_DATA_LIST = new ArrayList<>();//暂时，后续改为redis

    @Resource
    HrUserAttendanceStatService hrUserAttendanceStatService;
    @Resource
    private CommonCacheOperator commonCacheOperator;
    @Resource
    private HrUserAttendanceConfigService hrUserAttendanceConfigService;
    @Resource
    private HrUserAttendanceConfigGroupService hrUserAttendanceConfigGroupService;
    @Resource
    private HrUserAttendanceService hrUserAttendanceService;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private HrUserApplyHolidayService hrUserApplyHolidayService;
    @Resource
    private HrFestivalDataService hrFestivalDataService;
    @Resource
    private HrApplyWorkOtService hrApplyWorkOtService;

    @Override
    public Page<HrUserAttendanceStat> page(HrUserAttendanceStatPageParam hrUserAttendanceStatPageParam) {
        QueryWrapper<HrUserAttendanceStat> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(hrUserAttendanceStatPageParam.getUserId())) {
            queryWrapper.lambda().eq(HrUserAttendanceStat::getUserId, hrUserAttendanceStatPageParam.getUserId());
        }
        if(ObjectUtil.isNotEmpty(hrUserAttendanceStatPageParam.getMachineCode())) {
            queryWrapper.lambda().eq(HrUserAttendanceStat::getMachineCode, hrUserAttendanceStatPageParam.getMachineCode());
        }
        if(ObjectUtil.isNotEmpty(hrUserAttendanceStatPageParam.getCheckType())) {
            queryWrapper.lambda().eq(HrUserAttendanceStat::getCheckType, hrUserAttendanceStatPageParam.getCheckType());
        }
        if(ObjectUtil.isNotEmpty(hrUserAttendanceStatPageParam.getIsResubmitAttendanceFlag())) {
            queryWrapper.lambda().eq(HrUserAttendanceStat::getIsResubmitAttendanceFlag, hrUserAttendanceStatPageParam.getIsResubmitAttendanceFlag());
        }
        if(ObjectUtil.isAllNotEmpty(hrUserAttendanceStatPageParam.getSortField(), hrUserAttendanceStatPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(hrUserAttendanceStatPageParam.getSortOrder());
            queryWrapper.orderBy(true, hrUserAttendanceStatPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(hrUserAttendanceStatPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(HrUserAttendanceStat::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(HrUserAttendanceStatAddParam hrUserAttendanceStatAddParam) {
        HrUserAttendanceStat hrUserAttendanceStat = BeanUtil.toBean(hrUserAttendanceStatAddParam, HrUserAttendanceStat.class);
        this.save(hrUserAttendanceStat);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(HrUserAttendanceStatEditParam hrUserAttendanceStatEditParam) {
        HrUserAttendanceStat hrUserAttendanceStat = this.queryEntity(hrUserAttendanceStatEditParam.getId());
        BeanUtil.copyProperties(hrUserAttendanceStatEditParam, hrUserAttendanceStat);
        this.updateById(hrUserAttendanceStat);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<HrUserAttendanceStatIdParam> hrUserAttendanceStatIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(hrUserAttendanceStatIdParamList, HrUserAttendanceStatIdParam::getId));
    }

    @Override
    public HrUserAttendanceStat detail(HrUserAttendanceStatIdParam hrUserAttendanceStatIdParam) {
        return this.queryEntity(hrUserAttendanceStatIdParam.getId());
    }

    @Override
    public HrUserAttendanceStat queryEntity(String id) {
        HrUserAttendanceStat hrUserAttendanceStat = this.getById(id);
        if(ObjectUtil.isEmpty(hrUserAttendanceStat)) {
            throw new CommonException("考勤统计表不存在，id值为：{}", id);
        }
        return hrUserAttendanceStat;
    }

    /**
     * 异步处理打卡记录到考勤统计表，每人每天存在统计表的数据不能超过配置次数
     * */
    @EventListener
    @Async
    public void handleAttendanceEvent(HrUserAttendanceStatEvent hrUserAttendanceStatEvent){
        HrUserAttendanceStat hrUserAttendanceStat = hrUserAttendanceStatService.getOne(new LambdaQueryWrapper<HrUserAttendanceStat>()
                .eq(HrUserAttendanceStat::getUserId, hrUserAttendanceStatEvent.getUserId()).
                eq(HrUserAttendanceStat::getCheckType, hrUserAttendanceStatEvent.getCheckType())
                .between(HrUserAttendanceStat::getCheckTime, DateUtil.beginOfDay(hrUserAttendanceStatEvent.getCheckTime()), DateUtil.endOfDay(hrUserAttendanceStatEvent.getCheckTime())));

        /** 同步数据时，判断异常数据是否为迟到还是早退  3 迟到，4 早退 */
        HrUserAttendanceStat stat = BeanUtil.toBean(hrUserAttendanceStatEvent, HrUserAttendanceStat.class);
        Integer abnormalFlag = stat.getAbnormalFlag() == 1 ?
                1 : stat.getCheckType().equals(HrUserAttendanceEnum.ATTENDANCE_ON_DUTY.getValue())? 3 : 4;
        stat.setAbnormalFlag(abnormalFlag);
        /**
         * 查询stat统计表中是否存在当前时段的打卡记录，
         * 若不存在，则直接存stat表
         * 若存在，判断是属于上班(取最早数据)，还是属于下班(取最晚数据)
         * */
        if(ObjectUtil.isEmpty(hrUserAttendanceStat)){
            this.save(stat);
            return;
        }
        if(ON_DUTY.equals(hrUserAttendanceStatEvent.getCheckType())){
            if(hrUserAttendanceStatEvent.getCheckTime().before(hrUserAttendanceStat.getCheckTime())){
                this.removeById(hrUserAttendanceStat.getId());
                this.save(stat);
            }
            return;
        }
        if(hrUserAttendanceStatEvent.getCheckTime().after(hrUserAttendanceStat.getCheckTime())){
            this.removeById(hrUserAttendanceStat.getId());
            this.save(stat);
            return;
        }
    }

    @Override
    public Map<String,Object> dayStatInfo(){
        SaBaseLoginUser loginUser = StpLoginUserUtil.getLoginUser();
        Map<String,Object> map = new HashMap<>();
        Date beginDate = DateUtil.beginOfDay(DateUtil.date());
        Date endDate = DateUtil.endOfDay(DateUtil.date());
        List<HrUserAttendanceStat> hrUserAttendanceStatList = this.list(new QueryWrapper<HrUserAttendanceStat>().lambda()
                .eq(HrUserAttendanceStat::getUserId,loginUser.getId())
                .between(HrUserAttendanceStat::getCheckTime,beginDate,endDate));
        List<UserAttendanceConfigEntity> userAttendanceConfigEntityList = getUserConfigCachData(loginUser.getId());
        List<Map<String,Object>> mapList = new ArrayList<>();
        for(UserAttendanceConfigEntity entity : userAttendanceConfigEntityList){
            Map<String,Object> attendanceMap = new HashMap<>();
            attendanceMap.put("DutyType",entity.getDutyType());
            attendanceMap.put("checkTime",entity.getCheckTime());
            attendanceMap.put("attendanceTime","");
            attendanceMap.put("status","0");//状态
            for(HrUserAttendanceStat stat : hrUserAttendanceStatList){
                if(entity.getDutyType().equals(stat.getCheckType())){
                    attendanceMap.put("attendanceTime",stat.getCheckTime());
                    if(stat.getAbnormalFlag() == 2){
                        attendanceMap.put("status",stat.equals("OnDuty") ? "1" : "2");
                    }
                }
            }
            mapList.add(attendanceMap);
        }
        map.put("dayAttendanceNum",hrUserAttendanceStatList.size());//已经打卡次数
        map.put("attendance",mapList);
        return map;
    }

    @Override
    public List<UserAttendanceConfigEntity> getUserConfigCachData(String userId){ //获取打卡次数
        String cache = String.valueOf(commonCacheOperator.get(HR_ATTENDANCE_CONFIG_KEY));
        if(StringUtils.isNotEmpty(cache)){
            List<AttendanceConfigCacheEntity> configCacheEntityList = JSONArray.parseArray(cache, AttendanceConfigCacheEntity.class);
            for(AttendanceConfigCacheEntity entity : configCacheEntityList){
                if(entity.getUserIds().contains(userId)){
                    return entity.getUserAttendanceConfigEntityList();
                }
            }
        }
        //取默认配置
        String defaultConfig = String.valueOf(commonCacheOperator.get(HR_ATTENDANCE_CONFIG_DEFAULT_KEY));
        List<HrUserAttendanceConfig> configList = JSONArray.parseArray(defaultConfig, HrUserAttendanceConfig.class);
        return configList.stream().map(config -> BeanUtil.toBean(config, UserAttendanceConfigEntity.class)).collect(Collectors.toList());
    }

    @Override
    public Map<String,List<HrUserAttendance>> attendanceDayInfo(HrUserAttendancePageParam hrUserAttendancePageParam){
        SaBaseLoginUser loginUser = StpLoginUserUtil.getLoginUser();
        String userId = StringUtils.isNotEmpty(hrUserAttendancePageParam.getUserId()) ? hrUserAttendancePageParam.getUserId() : loginUser.getId();
        hrUserAttendancePageParam.setUserId(userId);
        if(StringUtils.isEmpty(hrUserAttendancePageParam.getStartTime()) || StringUtils.isEmpty(hrUserAttendancePageParam.getEndTime())){
            return new HashMap<>();
        }
        List<HrUserAttendance> hrUserAttendanceList = hrUserAttendanceService.list(new LambdaQueryWrapper<HrUserAttendance>()
                .eq(HrUserAttendance::getUserId,loginUser.getId())
                .between(HrUserAttendance::getCheckTime,hrUserAttendancePageParam.getStartTime(),hrUserAttendancePageParam.getEndTime())
                .orderByAsc(HrUserAttendance::getCheckTime));

        List<HrUserAttendanceStat> hrUserAttendanceStatList = hrUserAttendanceStatService.list(new LambdaQueryWrapper<HrUserAttendanceStat>()
                .eq(HrUserAttendanceStat::getUserId,loginUser.getId())
                .between(HrUserAttendanceStat::getCheckTime,hrUserAttendancePageParam.getStartTime(),hrUserAttendancePageParam.getEndTime())
                .orderByAsc(HrUserAttendanceStat::getCheckTime));
        /** 把记录表和统计表合并 -- 合并迟到早退数据 */
        List<HrUserAttendance> mergeList = new ArrayList<>();
        hrUserAttendanceList.forEach(attendance ->{
            attendance.setAbnormalFlag(2);//默认值
            hrUserAttendanceStatList.forEach(stat -> {
                if(stat.getCheckTime().compareTo(attendance.getCheckTime()) == 0){
                    attendance.setAbnormalFlag(stat.getAbnormalFlag());
                }
            });
            mergeList.add(attendance);
        });

        List<UserAttendanceConfigEntity> userAttendanceConfigEntityList = hrUserAttendanceStatService.getUserConfigCachData(loginUser.getId());/** 获取打卡配置*/
        Date startTime =  CommonTimeFormatUtil.strToDate(hrUserAttendancePageParam.getStartTime(),DatePattern.NORM_DATE_PATTERN);
        Date nowDate = DateUtil.date();
        try{
            nowDate = DateUtils.parseDate(DateUtil.now(),DateUtils.DATE_FORMAT_10);
        }catch (Exception e){
            log.error("格式化时间失败！e:"+e.toString());
        }
        return attendanceDayInfoCore(userAttendanceConfigEntityList,mergeList,startTime,nowDate,userId);
    }

    private Map<String,List<HrUserAttendance>> attendanceDayInfoCore(List<UserAttendanceConfigEntity> userAttendanceConfigEntityList,
            List<HrUserAttendance> hrUserAttendanceList,Date startTime,Date nowDate,String userId){
        Map<String,List<HrUserAttendance>> map = new HashMap<>();
        for(int i=0;i<userAttendanceConfigEntityList.size();i++){
            String dutyType = userAttendanceConfigEntityList.get(i).getDutyType();
            String dayTime = DateUtil.format(DateUtil.date(),DatePattern.NORM_DATE_PATTERN);
            Date checkTime = CommonTimeFormatUtil.strToDate(dayTime+" "+userAttendanceConfigEntityList.get(i).getCheckTime(),DatePattern.NORM_DATETIME_PATTERN);
            List<HrUserAttendance> list = hrUserAttendanceList.stream().filter(attendance -> attendance.getCheckType().equals(dutyType)).collect(Collectors.toList());
            if(CollectionUtil.isEmpty(list)){
                HrUserAttendance hrUserAttendance = new HrUserAttendance();
                /**
                 * 先判断这个dutyType对应的checkTime与当前时间
                 * 若checkTime <= 当前时间，已过了dutyType对应的打卡时间，无数据则标识缺卡
                 * */
                if(nowDate.compareTo(startTime) < 0 || (nowDate.compareTo(startTime) == 0 && checkTime.compareTo(DateUtil.date()) > 0)){
                    //查询未来数据，未来数据不存在缺卡的情况，直接空即可
                    map.put(String.valueOf(i),list);
                    continue;
                }
                /**  预留：后续要加请假，调休，年假的情况 */

                hrUserAttendance.setUserId(userId);
                hrUserAttendance.setAbnormalFlag(-1);
                hrUserAttendance.setCheckTime(userAttendanceConfigEntityList.get(i).getCheckTime());
                hrUserAttendance.setCheckType(userAttendanceConfigEntityList.get(i).getDutyType());
                hrUserAttendance.setConfigOrderNumber(userAttendanceConfigEntityList.get(i).getOrderNumber());
                list.add(hrUserAttendance);
                map.put(String.valueOf(i),list);
                continue;
            }
            map.put(String.valueOf(i),list);
        }
        return map;
    }



    @Override
    public List<UserAttendanceCountEntity> attendanceMonthCount(HrUserAttendancePageParam hrUserAttendancePageParam) {
        SaBaseLoginUser loginUser = StpLoginUserUtil.getLoginUser();

        if(StringUtils.isEmpty(hrUserAttendancePageParam.getStartTime()) || StringUtils.isEmpty(hrUserAttendancePageParam.getEndTime())){
            return new ArrayList<>();
        }
        /** 获取当月有效工作日时间列表 */
        List<HrUserAttendance> hrUserAttendanceList = hrUserAttendanceService.list(new LambdaQueryWrapper<HrUserAttendance>().eq(HrUserAttendance::getUserId,loginUser.getId())
                .between(HrUserAttendance::getCheckTime,hrUserAttendancePageParam.getStartTime(),hrUserAttendancePageParam.getEndTime()));

        /**  list循环一次，获取-1  循环二次获取 1*/
        Map<String,UserAttendanceCountEntity> map = new HashMap<>();
        for(HrUserAttendance hrUserAttendance : hrUserAttendanceList){
            String day = CommonTimeFormatUtil.dateToStr(hrUserAttendance.getCheckTime(),DatePattern.NORM_DATE_PATTERN);
            map.put(day,new UserAttendanceCountEntity("",hrUserAttendance.getAbnormalFlag() != null ? "0" : "1",day,hrUserAttendance.getCheckTime()));
        }
        List<HrUserAttendance> userAttendances = hrUserAttendanceList.stream().filter(attendance ->
                attendance.getAbnormalFlag() != null && attendance.getAbnormalFlag() == 2).collect(Collectors.toList());/**  筛选打卡异常的数据*/
        /** 打卡异常的数据 */
        userAttendances.forEach(attendance ->  {
            String day = CommonTimeFormatUtil.dateToStr(attendance.getCheckTime(),DatePattern.NORM_DATE_PATTERN);
            if(map.get(day) != null){
                map.get(day).setDayAttendanceStatus("1");
            }
        });
        return map.keySet().stream().map(map::get).collect(Collectors.toList());
    }

    @Override
    public AttendanceStatNumData attendanceDayPreview(){
        /** 获取一天的开始时间，结束时间 */
        Date beginDate = DateUtil.beginOfDay(DateUtil.date());
        Date endDate = DateUtil.endOfDay(DateUtil.date());
        AttendanceStatNumData attendanceStatNumData = new AttendanceStatNumData();
        Long attendanceNum = this.count(new LambdaQueryWrapper<HrUserAttendanceStat>()
                .between(HrUserAttendanceStat::getCheckTime,beginDate,endDate)
                .groupBy(HrUserAttendanceStat::getUserId));
        attendanceStatNumData.setAttendanceNum(attendanceNum);//打卡人数

        Long userNum = sysUserService.count();
        attendanceStatNumData.setUserNum(userNum); //应到人数
        attendanceStatNumData.setNoAttendanceNum(userNum-attendanceNum); //未打卡人数

        List<HrUserAttendanceStat> hrUserAttendanceStats = this.list(new LambdaQueryWrapper<HrUserAttendanceStat>()
                .between(HrUserAttendanceStat::getCheckTime,beginDate,endDate));
        attendanceStatNumData.setLateUserNum(0l);
        hrUserAttendanceStats.forEach(stat -> {
            if(stat.getAbnormalFlag() == 3){
                Long lateUserNum = attendanceStatNumData.getLateUserNum();
                attendanceStatNumData.setLateUserNum(lateUserNum++);//迟到人数
            }
        });

        //外勤人数  -- 暂时不确定外勤人数
        attendanceStatNumData.setEgressNum(0l);
        return attendanceStatNumData;
    }

    @Override
    public LinkedHashMap<Integer, String> statPreviewDateList(AttendanceDayStatParam attendanceDayStatParam){
        Date beginTime = CommonTimeFormatUtil.strToDate(attendanceDayStatParam.getBeginTime(),DatePattern.NORM_DATE_PATTERN);
        Date endTime = CommonTimeFormatUtil.strToDate(attendanceDayStatParam.getEndTime(),DatePattern.NORM_DATE_PATTERN);
        LinkedHashMap<Integer, String> resultMap = new LinkedHashMap<>();
        handDayList(beginTime,endTime,resultMap);
        return resultMap;
    }

    private void handDayList(Date beginTime,Date endTime,LinkedHashMap<Integer, String> map){
        // 遍历时间段中的每一天
        Date current = beginTime;
        while (current.compareTo(endTime) <= 0) {
            // 获取当前日期的周几
            Integer dayOfWeek = DateUtil.dayOfWeek(current);
            Integer datDate = DateUtil.dayOfMonth(current);
            String dayStr = dayOfWeek.toString();
            if(dayOfWeek == 6){
                dayStr = "六";
            }
            if(dayOfWeek == 7){
                dayStr = "日";
            }
            map.put(datDate,dayStr);
            // 将日期加1天
            current = DateUtil.offset(current, DateField.DAY_OF_MONTH, 1);
        }
    }

    @Override
    public LinkedHashMap<Integer, String> statPreviewMonthList(AttendanceDayStatParam attendanceDayStatParam){
        Date beginTime = CommonTimeFormatUtil.strToDate(attendanceDayStatParam.getBeginTime(),DatePattern.NORM_DATE_PATTERN);
        Date endTime = CommonTimeFormatUtil.strToDate(attendanceDayStatParam.getEndTime(),DatePattern.NORM_DATE_PATTERN);
        LinkedHashMap<Integer, String> resultMap = new LinkedHashMap<>();
        handMonthList(beginTime,endTime,resultMap);
        return resultMap;
    }
    private void handMonthList(Date beginTime,Date endTime,LinkedHashMap<Integer, String> map){
        // 遍历时间段中的每一天
        Date current = beginTime;
        while (current.compareTo(endTime) <= 0) {
            // 获取当前日期的周几
            Integer dayOfWeek = DateUtil.dayOfWeek(current);
            Integer datDate = DateUtil.dayOfMonth(current);
            String dayStr = datDate.toString();
            if(dayOfWeek == 6){
                dayStr = "六";
            }
            if(dayOfWeek == 7){
                dayStr = "日";
            }
            map.put(datDate,dayStr);
            // 将日期加1天
            current = DateUtil.offset(current, DateField.DAY_OF_MONTH, 1);
        }
    }

    @Override
    public Page<AttendanceDayStatParam> dayStatPreview(AttendanceDayStatParam attendanceDayStatParam){
        Date beginTime = CommonTimeFormatUtil.strToDate(attendanceDayStatParam.getBeginTime(),DatePattern.NORM_DATETIME_PATTERN);
        Date endTime = CommonTimeFormatUtil.strToDate(attendanceDayStatParam.getEndTime(),DatePattern.NORM_DATETIME_PATTERN);
        List<AttendanceDayStatParam> attendanceDayStatList = new ArrayList<>();
        List<String> dayList = CommonTimeFormatUtil.getPerStrDayList(beginTime,endTime);//时间处理

        /** 人员查询分页*/
        LambdaQueryWrapper<SysUser> sysUserLambdaQueryWrapper = new LambdaQueryWrapper();
        if(ObjectUtil.isNotEmpty(attendanceDayStatParam.getUserList())){
            sysUserLambdaQueryWrapper.notIn(SysUser::getId,attendanceDayStatParam.getUserList());
        }
        if(ObjectUtil.isNotEmpty(attendanceDayStatParam.getOrgList())){
            sysUserLambdaQueryWrapper.notIn(SysUser::getOrgId,attendanceDayStatParam.getOrgList());
        }
        Page<SysUser> userList = sysUserService.page(CommonPageRequest.defaultPage(),sysUserLambdaQueryWrapper);// 查所有人，并去除不需要统计的人员或部门
        List<String> userIds = userList.getRecords().stream().map(user -> user.getId()).collect(Collectors.toList());
        /** 打卡记录只查询当前返回的人员*/
        List<HrUserAttendance> hrUserAttendanceList = hrUserAttendanceService.list(new LambdaQueryWrapper<HrUserAttendance>()
                .between(HrUserAttendance::getCheckTime,beginTime,endTime)
                .in(HrUserAttendance::getUserId,userIds)); // 查询该时间段内所有人打卡数据


        /**  开始统计 */
        userList.getRecords().forEach(user -> {
            AttendanceDayStatParam attendanceDayStat = new AttendanceDayStatParam();
            attendanceDayStat.setUserId(user.getId());
            attendanceDayStat.setUserName(user.getName());
            attendanceDayStat.setPosition(user.getPositionName());
            attendanceDayStat.setEmpNo(user.getEmpNo());
            attendanceDayStat.setOrgId(user.getOrgId());
            List<Map<Integer,List<AttendanceDayStatData>>> dayCheckTimeMapList = new ArrayList<>();
            Map<String,List<AttendanceDayStatData>> statTempMap = new HashMap<>();//key - day , value - List<AttendanceDayStatData>
            hrUserAttendanceList.forEach(attendance -> {
                String day = CommonTimeFormatUtil.dateToStr(attendance.getCheckTime(),DatePattern.NORM_DATE_PATTERN);
                String userId = attendance.getUserId();
                if(user.getId().equals(userId)){
                    List<AttendanceDayStatData> attendanceDayStatDataList = statTempMap.get(day) == null ? new ArrayList<>() :  statTempMap.get(day);
                    attendanceDayStatDataList.add(new AttendanceDayStatData(attendance.getCheckTime(),attendance.getAbnormalFlag(),day));
                    statTempMap.put(day,attendanceDayStatDataList);
                }
            });
            /**  打卡时间数据和传入时间合并 */
            TreeMap<Integer,List<AttendanceDayStatData>> statMap = new TreeMap<>();//key - day , value - List<AttendanceDayStatData>
            dayList.forEach(time ->{
                List<AttendanceDayStatData> attendanceDayStatDataList = statTempMap.get(time) == null ? new ArrayList<>() : statTempMap.get(time);
                if(CollectionUtil.isEmpty(attendanceDayStatDataList)){
                    attendanceDayStatDataList.add(new AttendanceDayStatData(null,-1,CommonTimeFormatUtil.dayAndSunday(time)));
                }
                SimpleDateFormat sdf = new SimpleDateFormat("dd");
                Integer key = Integer.parseInt(sdf.format(CommonTimeFormatUtil.strToDate(time,DatePattern.NORM_DATE_PATTERN)));
                statMap.put(key,attendanceDayStatDataList);
            });

            /** 一次轮询结束，开始保存数据 */
            dayCheckTimeMapList.add(statMap);
            List<AttendanceTimeSlot> attendanceTimeSlotList = hrUserAttendanceService.getAttendanceTimeSlotList(user.getId(),beginTime);
            attendanceDayStat.setAttendanceTypeName(attendanceTimeSlotList.get(0).getAttendanceTypeName());
            attendanceDayStat.setAttendanceTypeId(attendanceTimeSlotList.get(0).getAttendanceTypeId());
            attendanceDayStat.setDayCheckTimeMapList(dayCheckTimeMapList);
            attendanceDayStatList.add(attendanceDayStat);
        });

        //做分页处理
        Page<AttendanceDayStatParam> attendanceDayStatParamPage = new Page<>();
        attendanceDayStatParamPage.setTotal(sysUserService.count(sysUserLambdaQueryWrapper));
        attendanceDayStatParamPage.setRecords(attendanceDayStatList);
        return attendanceDayStatParamPage;
    }
    @Override
    public Page<AttendanceMonthStatParam> monthStatPreview(AttendanceMonthStatParam attendanceMonthStatParam){
        Date beginTime = CommonTimeFormatUtil.strToDate(attendanceMonthStatParam.getBeginTime(),DatePattern.NORM_DATETIME_PATTERN);
        Date endTime = CommonTimeFormatUtil.strToDate(attendanceMonthStatParam.getEndTime(),DatePattern.NORM_DATETIME_PATTERN);
        List<String> dayList = CommonTimeFormatUtil.getPerStrDayList(beginTime,endTime);//时间处理

       /** 查询人员数据，并去掉不需要统计的人员*/
        LambdaQueryWrapper<SysUser> sysUserLambdaQueryWrapper = new LambdaQueryWrapper();
        if(ObjectUtil.isNotEmpty(attendanceMonthStatParam.getUserList())){
            sysUserLambdaQueryWrapper.notIn(SysUser::getId,attendanceMonthStatParam.getUserList());
        }
        if(ObjectUtil.isNotEmpty(attendanceMonthStatParam.getOrgList())){
            sysUserLambdaQueryWrapper.notIn(SysUser::getOrgId,attendanceMonthStatParam.getOrgList());
        }
        Page<SysUser> userList = sysUserService.page(CommonPageRequest.defaultPage(),sysUserLambdaQueryWrapper);
        /** 查询请假数据*/
        List<HrUserApplyHoliday> hrUserApplyHolidayList = hrUserApplyHolidayService.list(new LambdaQueryWrapper<>());

        /** 打卡记录只查询当前返回的人员*/
        List<HrUserAttendanceStat> hrUserAttendanceStatList = hrUserAttendanceStatService.list(new LambdaQueryWrapper<HrUserAttendanceStat>()
                .between(HrUserAttendanceStat::getCheckTime,beginTime,endTime)
                .in(HrUserAttendanceStat::getUserId,userList.getRecords().stream().map(user -> user.getId()).collect(Collectors.toList()))); // 查询该时间段内所有人打卡数据

        List<AttendanceMonthStatParam> attendanceMonthStatList = new ArrayList<>();
        String timeStr = " 8:30:00";
        /**  待优化 */
        userList.getRecords().forEach(user -> { AttendanceMonthStatParam statParam = new AttendanceMonthStatParam();
            try{
                statParam = countWorkData(hrUserAttendanceStatList,timeStr,statParam,user);//计算基础数据
                attendanceMonthStatList.add(dayListMonth(hrUserAttendanceStatList,statParam,dayList,user,hrUserApplyHolidayList));
            }catch (Exception e){
                log.error("统计数据失败！"+e.toString()); }
        });

        Page<AttendanceMonthStatParam> page = new Page<>();
        page.setRecords(attendanceMonthStatList);
        page.setTotal(sysUserService.count(sysUserLambdaQueryWrapper));
        return page;
    }

    /** 计算 出勤天数 迟到次数 迟到时长 上班缺卡次数 下班缺卡次数 旷工天数*/
    private AttendanceMonthStatParam countWorkData(List<HrUserAttendanceStat> hrUserAttendanceStatList,String timeStr,AttendanceMonthStatParam attendanceMonthStatParam,SysUser sysUser) {
        Integer lateNum = 0;//迟到次数
        Long lateTime = 0l;//迟到时长
        Integer onDutyLackNum = 0;//上班缺卡次数
        Integer offDutyLackNum = 0;//下班缺卡次数
        Integer absenteeismNum = 0;//旷工天数
        Integer onDutyNum = 0;//上班打卡次数
        Integer offDutyNum = 0;//下班打卡次数
        Map<String,Integer> dayMap = new HashMap<>();//出勤的天时间
        for(HrUserAttendanceStat hrUserAttendanceStat : hrUserAttendanceStatList){
            if(!hrUserAttendanceStat.getUserId().equals(sysUser.getId())){
                continue; }
            if(hrUserAttendanceStat.getAbnormalFlag() == 3){
                lateNum++;
                try{
                    Date onDutyTime = DateUtils.parseDate(DateUtil.format(hrUserAttendanceStat.getCheckTime(),DatePattern.NORM_DATE_PATTERN)+timeStr,DatePattern.NORM_DATETIME_PATTERN);
                    lateTime = lateTime + DateUtil.between(hrUserAttendanceStat.getCheckTime(),onDutyTime,DateUnit.MINUTE);
                }catch (ParseException e){
                    log.error("时间转换失败！"+e.toString()); } }
            if(hrUserAttendanceStat.getCheckType().equals("OnDuty")){
                onDutyNum++; }
            if(hrUserAttendanceStat.getCheckType().equals("OffDuty")){
                offDutyNum++; }
        }
        attendanceMonthStatParam.setWorkDays(dayMap.keySet().size());
        attendanceMonthStatParam.setLateNum(lateNum);
        attendanceMonthStatParam.setLateTime(lateTime);
        attendanceMonthStatParam.setOffDutyLackNum(offDutyLackNum);
        attendanceMonthStatParam.setOnDutyLackNum(onDutyLackNum);
        attendanceMonthStatParam.setAbsenteeismNum(absenteeismNum);
        attendanceMonthStatParam.setUserId(sysUser.getId());
        attendanceMonthStatParam.setUserName(sysUser.getName());
        return attendanceMonthStatParam;
    }

    /**
     *
     * */
    private AttendanceMonthStatParam dayListMonth(List<HrUserAttendanceStat> hrUserAttendanceStatList,AttendanceMonthStatParam statParam,List<String> dayList,SysUser sysUser,List<HrUserApplyHoliday> hrUserApplyHolidayList){

        List<Map<Integer,AttendanceMonthStatData>> mapList = new ArrayList<>();
        Map<Integer,List<HrUserAttendanceStat>> statMap = new HashMap<>();
        hrUserAttendanceStatList.forEach(stat -> {
            if(stat.getUserId().equals(sysUser.getId())){
                String day = DateUtil.format(stat.getCheckTime(),DatePattern.NORM_DATE_PATTERN);
                List<HrUserAttendanceStat> statList = statMap.get(day) == null ? new ArrayList<>() : statMap.get(day);
                SimpleDateFormat sdf = new SimpleDateFormat("dd");
                Integer key = Integer.parseInt(sdf.format(CommonTimeFormatUtil.strToDate(day,DatePattern.NORM_DATE_PATTERN)));
                statMap.put(key,statList);
            }
        });
        dayList.forEach(day -> {
            Map<Integer,AttendanceMonthStatData> monthStatDataMap = new HashMap<>();
            if(CommonTimeFormatUtil.dayAndSunday(day).equals("六") || CommonTimeFormatUtil.dayAndSunday(day).equals("日")){
                AttendanceMonthStatData attendanceMonthStatData = new AttendanceMonthStatData();
                attendanceMonthStatData.setDay(CommonTimeFormatUtil.dayAndSunday(day));
                attendanceMonthStatData.setStatus("休息");

                SimpleDateFormat sdf = new SimpleDateFormat("dd");
                Integer key = Integer.parseInt(sdf.format(CommonTimeFormatUtil.strToDate(day,DatePattern.NORM_DATE_PATTERN)));
                monthStatDataMap.put(key,attendanceMonthStatData);
                mapList.add(monthStatDataMap);
            }else{
                List<HrUserAttendanceStat> statList = statMap.get(day);
                AttendanceMonthStatData attendanceMonthStatData = countStatData(statList,new AttendanceMonthStatData(),day);
                SimpleDateFormat sdf = new SimpleDateFormat("dd");
                Integer key = Integer.parseInt(sdf.format(CommonTimeFormatUtil.strToDate(day,DatePattern.NORM_DATE_PATTERN)));
                monthStatDataMap.put(key,attendanceMonthStatData);
                mapList.add(monthStatDataMap);
            }

        });
        statParam.setMonthStatList(mapList);
        return statParam;
    }


    private AttendanceMonthStatData countStatData(List<HrUserAttendanceStat> statList,AttendanceMonthStatData attendanceMonthStatData,String day){
        if(CollectionUtil.isEmpty(statList)){
            attendanceMonthStatData.setStatus("旷工");
            attendanceMonthStatData.setDay(CommonTimeFormatUtil.dayAndSunday(day));
            return attendanceMonthStatData;
        }
        if(statList.size() < 3){
            attendanceMonthStatData.setStatus("缺卡");
            attendanceMonthStatData.setDay(CommonTimeFormatUtil.dayAndSunday(day));
            return attendanceMonthStatData;
        }

        Date date = DateUtil.parse(day+" 8:30:00",DatePattern.NORM_DATETIME_PATTERN);
        for(HrUserAttendanceStat stat : statList){
            if(stat.getCheckType().equals("OnDuty") && stat.getCheckTime().compareTo(date) >0){
                attendanceMonthStatData.setStatus("迟到"+DateUtil.between(stat.getCheckTime(),date,DateUnit.MINUTE)+"分钟");
                attendanceMonthStatData.setDay(CommonTimeFormatUtil.dayAndSunday(day));
                return attendanceMonthStatData;
            }
        }
        attendanceMonthStatData.setStatus("正常");
        attendanceMonthStatData.setDay(CommonTimeFormatUtil.dayAndSunday(day));
        return attendanceMonthStatData;
    }

    /*private void countData(List<String> days,List<HrUserAttendanceStat> statList,List<HrUserApplyHoliday> hrUserApplyHolidayList){
        Map<String,String> dayMap = new HashMap<>();
        days.forEach(day -> {
            if(isFestival(day+HrUserAttendanceStatEnum.BEGIN_TIMNE.getValue(),1)){

            }else{

            }
        });

    }*/

    @Override
    public  Boolean isFestival(String date,Integer type){//type类别：1 判断是法定节假日，2 判断是法定加班日
        Boolean isTrue = false;
        try{
            Date isTrueDate = DateUtils.parseDate(date,DateUtils.DATE_FORMAT_19);
            Date holidayTime = DateUtils.parseDate(HrUserAttendanceStatEnum.NOW_YEAR.getValue(),DateUtils.DATE_FORMAT_19);
            if(CollectionUtil.isEmpty(FESTIVAL_DATA_LIST)){
                FESTIVAL_DATA_LIST = hrFestivalDataService.list();  //后续改为缓存
            }
            List<HrFestivalData> festivalDataList = FESTIVAL_DATA_LIST;

            if(CollectionUtil.isEmpty(festivalDataList)){
                return false; }
            HrFestivalData hrFestivalData = new HrFestivalData();
            for(HrFestivalData hrFestival : festivalDataList){
                if(hrFestival.getHolidayType() == type){
                    hrFestivalData  = hrFestival; }
            }
            List<String> dayList = Arrays.asList(hrFestivalData.getExtJson().split(","));
            for(String day:dayList){
                try{
                    List<String> list = Arrays.asList(day.split("#"));
                    Date beginTime = DateUtils.parseDate(list.get(0)+HrUserAttendanceStatEnum.BEGIN_TIMNE.getValue(),DateUtils.DATE_FORMAT_19);
                    Date endTime = DateUtils.parseDate(list.get(1)+HrUserAttendanceStatEnum.BEGIN_TIMNE.getValue(),DateUtils.DATE_FORMAT_19);
                    if((beginTime.compareTo(isTrueDate) <= 0  && endTime.compareTo(isTrueDate) >=0)){
                        isTrue = true; }
                }catch (Exception e){
                    log.error("时间格式化失败！e:"+e.toString());}}
        }catch (Exception e){
            log.error("判断是否为节假日失败！e:"+e.toString());}
        return isTrue;
    }

    @Override
    public Page<DayStatExcel> monthPreDayStat(DayStatExcelParam dayStatExcelParam){
        Date beginTime = CommonTimeFormatUtil.strToDate(dayStatExcelParam.getBeginTime(),DatePattern.NORM_DATETIME_PATTERN);
        Date endTime = CommonTimeFormatUtil.strToDate(dayStatExcelParam.getEndTime(),DatePattern.NORM_DATETIME_PATTERN);
        List<String> dayList = CommonTimeFormatUtil.getPerStrDayList(beginTime,endTime);


        List<DayStatExcelParam>  dayStatExcelParamList = dayAttendanceList(dayStatExcelParam);
        List<DayStatExcel> dayStatExcelList = new ArrayList<>();
        dayStatExcelParamList.forEach(param -> {
            DayStatExcel dayStatExcel = BeanUtil.toBean(param,DayStatExcel.class);
            isNeedStyle(param,dayStatExcel);
            dayStatExcelList.add(dayStatExcel);
        });

        /** 计算总数 total*/
        Long total = 0l;
        LambdaQueryWrapper<SysUser> userQuery = new LambdaQueryWrapper<>();
        if(CollectionUtil.isNotEmpty(dayStatExcelParam.getUserList())){ userQuery.in(SysUser::getOrgId,dayStatExcelParam.getOrgList());}
        List<SysUser> userList = sysUserService.list(userQuery);

        if(CollectionUtil.isNotEmpty(userList)){
            total = total + userList.size();
        }
        if(CollectionUtil.isNotEmpty(dayStatExcelParam.getUserList())){
            total = total + dayStatExcelParam.getUserList().size();
        }
        if(total == 0){
            total = sysUserService.count();
        }


        /** 分页 */
        Page<DayStatExcel> page = new Page<>();
        page.setRecords(dayStatExcelList);
        page.setTotal(total*dayList.size());
        return page;
    }

    @Override
    public void downloadExcel(DayStatExcelParam dayStatExcelParam, HttpServletResponse response) throws Exception{
        List<DayStatExcelParam>  dayStatExcelList = dayAttendanceList(dayStatExcelParam);
        workbookExcelDay(dayStatExcelList,response);
    }

    public List<DayStatExcelParam> dayAttendanceList(DayStatExcelParam dayStatExcelParam){
        Date beginTime = CommonTimeFormatUtil.strToDate(dayStatExcelParam.getBeginTime(),DatePattern.NORM_DATETIME_PATTERN);
        Date endTime = CommonTimeFormatUtil.strToDate(dayStatExcelParam.getEndTime(),DatePattern.NORM_DATETIME_PATTERN);

        /**  筛选部门*/
        LambdaQueryWrapper<SysUser> userQuery = new LambdaQueryWrapper<>();
        if(CollectionUtil.isNotEmpty(dayStatExcelParam.getUserList())){ userQuery.in(SysUser::getOrgId,dayStatExcelParam.getOrgList());}
        List<SysUser> userList = sysUserService.list(userQuery);
        List<String> userIds = userList.stream().map(user -> user.getId()).collect(Collectors.toList());


        /** 获取这段时间的每一天*/
        List<String> dayList = CommonTimeFormatUtil.getPerStrDayList(beginTime,endTime);

        /** 筛选人员打卡数据 */
        List<String> listUserIds = new ArrayList<>();
        LambdaQueryWrapper<HrUserAttendanceStat> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.between(HrUserAttendanceStat::getCheckTime,beginTime,endTime);
        if(CollectionUtil.isNotEmpty(dayStatExcelParam.getUserList())){
            listUserIds.addAll(dayStatExcelParam.getUserList());
        }
        if(CollectionUtil.isNotEmpty(userIds)){
            listUserIds.addAll(userIds);
        }
        if(CollectionUtil.isNotEmpty(listUserIds)){
            lambdaQueryWrapper.in(HrUserAttendanceStat::getUserId,listUserIds);
        }
        List<HrUserAttendanceStat> userAttendanceStatList = this.list(lambdaQueryWrapper);
        Map<String,Map<String,List<HrUserAttendanceStat>>> attendanceDayMap = new HashMap<>();/**  打卡数据预处理：key -userId，value(key-年月日，value-打卡记录)*/
        userAttendanceStatList.forEach(stat -> {
            String userId = stat.getUserId();
            Map<String,List<HrUserAttendanceStat>> attendanceMap = attendanceDayMap.get(userId) == null ? new HashMap<>() : attendanceDayMap.get(userId);
            String date = DateUtil.format(stat.getCheckTime(), DatePattern.NORM_DATE_PATTERN);
            List<HrUserAttendanceStat> statList = attendanceMap.get(date) == null ? new ArrayList<>() : attendanceMap.get(date);
            statList.add(stat);
            attendanceMap.put(date,statList);
            attendanceDayMap.put(userId,attendanceMap);
        });

        /** 查询加班数据 */
        LambdaQueryWrapper<HrApplyWorkOt> workOtQuery = new LambdaQueryWrapper<>();
        workOtQuery.between(HrApplyWorkOt::getApplyBeginTime,beginTime,endTime);
        workOtQuery.between(HrApplyWorkOt::getApplyEndTime,beginTime,endTime);
        if(CollectionUtil.isNotEmpty(listUserIds)){ workOtQuery.in(HrApplyWorkOt::getApplyUserId,listUserIds);}
        List<HrApplyWorkOt> hrApplyWorkOtList = hrApplyWorkOtService.list(workOtQuery);
        Map<String,List<HrApplyWorkOt>> workOtMap = new HashMap<>();
        hrApplyWorkOtList.forEach(workOt -> {
            List<HrApplyWorkOt> workOtList = workOtMap.get(workOt.getApplyUserId()) != null ? workOtMap.get(workOt.getApplyUserId()) : new ArrayList<>();
            workOtList.add(workOt);
            workOtMap.put(workOt.getApplyUserId(),workOtList);
        });
        Map<String,List<DayStatExcelParam>> dayStatMap = dayStat(attendanceDayMap,dayList,userList);
        List<DayStatExcelParam> dayStatExcelParamList = new ArrayList<>();
        dayStatMap.entrySet().forEach(entry -> dayStatExcelParamList.addAll(entry.getValue()));

        return dayStatExcelParamList;
    }

    /**
     * 计算这段时间的打卡记录
     * user - 循环
     * userAttendanceStatList - 循环
     * */
    private Map<String,List<DayStatExcelParam>> dayStat(Map<String,Map<String,List<HrUserAttendanceStat>>> attendanceDayMap,List<String> dayList,
                                                        List<SysUser> userList){

        Map<String,List<DayStatExcelParam>> listMap = new HashMap<>();
        userList.forEach(user ->{
            List<DayStatExcelParam> dayStatExcelParamList = listMap.get(user.getId()) == null ? new ArrayList<>() : listMap.get(user.getId());
            String userId = user.getId();
            Map<String,List<HrUserAttendanceStat>>  attendanceMap = attendanceDayMap.get(userId) == null ? new HashMap<>() : attendanceDayMap.get(userId);// key - day,value - 打卡记录
            dayList.forEach(day -> {
                /** 当前这天的打卡数据 */
                List<HrUserAttendanceStat> statList = attendanceMap.get(day) == null ? new ArrayList<>() : attendanceMap.get(day);
                Boolean isWork = false;
                dayStatExcelParamList.add(hrUserAttendanceStat(user,statList,isWork,day));
                listMap.put(userId,dayStatExcelParamList);
            });
        });

        return listMap;
    }

    /** 开始赋值 */
    private DayStatExcelParam hrUserAttendanceStat(SysUser sysUser,List<HrUserAttendanceStat> statList,Boolean isWork,String day){
        DayStatExcelParam dayStatExcelParam = new DayStatExcelParam();
        dayStatExcelParam.setUserId(sysUser.getId());  //userId
        dayStatExcelParam.setUserName(sysUser.getName()); //姓名
        dayStatExcelParam.setEmpNo(sysUser.getEmpNo());//编号
        dayStatExcelParam.setOrgId(sysUser.getOrgId());//岗位ID
        dayStatExcelParam.setOrgName(sysUser.getOrgName());//岗位
        dayStatExcelParam.setPosition(sysUser.getPositionName());//职位
        /** 数据暂时固定 */
        dayStatExcelParam.setAttendanceTypeId("1671734716242948621");//修改SysUser表的字段，增加考勤字段，可以直接拿来用
        dayStatExcelParam.setAttendanceTypeDetails("标准班次 08:30-12:00 14:00-17:30");
        Date dayDate = CommonTimeFormatUtil.strToDate(day+ HrUserApplyHolidayEnum.TIMNE.getValue(),DatePattern.NORM_DATETIME_PATTERN);
        String dateStr = day+ HrUserApplyHolidayEnum.TIMNE.getValue();
        if(DateUtil.isWeekend(dayDate) && !isFestival(day+ HrUserApplyHolidayEnum.TIMNE.getValue(), 2)){//是周末,且非法定加班日
            dayStatExcelParam.setAttendanceTypeDetails("休息");
        }
        if(isFestival(day+ HrUserApplyHolidayEnum.TIMNE.getValue(), 1)){//是法定节假日
            dayStatExcelParam.setAttendanceTypeDetails("休息");
        }

        dayStatExcelParam.setAttendanceTypeName("建科标准考勤");
        dayStatExcelParam.setDate(CommonTimeFormatUtil.dayAndWeek(day));
        dayStatExcelParam.setDay(day);


        if(dayStatExcelParam.getAttendanceTypeDetails().equals("休息")){
            if(CollectionUtil.isNotEmpty(statList)){ //存在周末上班打卡的情况
                dayStatExcelParam.setUserAttendanceStatList(statList);
            }
            dayStatExcelParam.setRestDays(1.0);
        }else{
            statAttendanceDeal(statList,dayStatExcelParam);
            dayStatExcelParam.setAttendanceDays(1.0);
        }
        return dayStatExcelParam;
    }

    /** 请假以及外出数据会在审核通过之后做后置处理，数据会到打卡统计表*/
    private void statAttendanceDeal(List<HrUserAttendanceStat> statList,DayStatExcelParam dayStatExcelParam){
        Integer lackAttendance = dayStatExcelParam.getLackAttendance() == null ? 0 : dayStatExcelParam.getLackAttendance(); //缺卡次数
        Integer lateNum = dayStatExcelParam.getLateNum() == null ? 0 : dayStatExcelParam.getLateNum(); //迟到次数
        Integer lateMinute = dayStatExcelParam.getLateMinute() == null ? 0 : dayStatExcelParam.getLateMinute(); //迟到分钟
        Integer leaveEarlyNum = dayStatExcelParam.getLeaveEarlyNum() == null ? 0: dayStatExcelParam.getLeaveEarlyNum();//早退
        Map<String,HrUserAttendanceStat> map = new HashMap<>();
        statList.forEach(stat -> map.put(stat.getCheckType(),stat));
        HrUserAttendanceStat hrUserAttendanceStat = new HrUserAttendanceStat();
        hrUserAttendanceStat.setAbnormalFlag(-1);
        hrUserAttendanceStat.setUserId(dayStatExcelParam.getUserId());

        List<HrUserAttendanceStat> list = new ArrayList<>();

        if(ObjectUtil.isEmpty(map.get("OnDuty"))){//上下班标识；OnDuty：上班；OffDuty：下班；休息 REST
            HrUserAttendanceStat stat = BeanUtil.toBean(hrUserAttendanceStat,HrUserAttendanceStat.class);
            stat.setCheckType("OnDuty");
            dayStatExcelParam.setLackAttendance(++lackAttendance);//缺卡
            list.add(stat);
        }else {
            if(map.get("OnDuty").getAbnormalFlag() == 3){
                dayStatExcelParam.setLateNum(lateNum+1);
                dayStatExcelParam.setLateMinute(0);
            }
            list.add(map.get("OnDuty"));
        }

        if(ObjectUtil.isEmpty(map.get("REST"))){//上下班标识；OnDuty：上班；OffDuty：下班；休息 REST
            HrUserAttendanceStat stat = BeanUtil.toBean(hrUserAttendanceStat,HrUserAttendanceStat.class);
            stat.setCheckType("REST");
            dayStatExcelParam.setLackAttendance(++lackAttendance);//缺卡
            list.add(stat);
        }else {
            list.add(map.get("REST"));
        }

        if(ObjectUtil.isEmpty(map.get("OffDuty"))){//上下班标识；OnDuty：上班；OffDuty：下班；休息 REST
            HrUserAttendanceStat stat = BeanUtil.toBean(hrUserAttendanceStat,HrUserAttendanceStat.class);
            stat.setCheckType("OffDuty");
            dayStatExcelParam.setLackAttendance(++lackAttendance);//缺卡
            list.add(stat);
        }else {
            if(map.get("OffDuty").getAbnormalFlag() == 4){
                dayStatExcelParam.setLeaveEarlyNum(leaveEarlyNum+1);//早退
                dayStatExcelParam.setLeaveEarlyNum(0);
            }
            list.add(map.get("OffDuty"));
        }
        dayStatExcelParam.setUserAttendanceStatList(list);
    }

    /**
    * 每日统计数据下载
    * */
    @Override
    public void workbookExcelDay(List<DayStatExcelParam> dayStatExcelList,HttpServletResponse response) throws Exception{
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");
        setHerd(sheet,workbook,"");
        setContent(workbook,sheet,dayStatExcelList);

        OutputStream outputStream = new FileOutputStream("output.xlsx");
        try{
            workbook.write(outputStream);
        }catch (Exception e){
            System.out.println("");
        }finally {
            workbook.close();
            outputStream.close();
        }
    }
    /** 设置内容 */
    private void setContent(Workbook workbook,Sheet sheet,List<DayStatExcelParam> dayStatExcelList){
        Integer rowSize = dayStatExcelList.size();
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);// 设置水平居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);// 设置垂直居中
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 12); //字体
        style.setFont(font);//增加字体样式
        for (int i = 1; i <= rowSize; i++) {//i=1,表头占一行
            Row row = sheet.createRow(i); // 创建行
            Short height = (short) 360;
            row.setHeight(height);

            //打卡是否异常：-1 缺卡，1 正常,3 迟到，4 早退，5 请假，6 管理员修改
            DayStatExcelParam dayStatExcelParam = dayStatExcelList.get(i-1);
            DayStatExcel dayStatExcel = BeanUtil.toBean(dayStatExcelParam,DayStatExcel.class);
            isNeedStyle(dayStatExcelParam,dayStatExcel);
            int j=0;
            for (Field field : DayStatExcel.class.getDeclaredFields()) {
                field.setAccessible(true); // 确保可以访问私有属性
                try {
                    Object value = field.get(dayStatExcel);
                    Cell cell = row.createCell(j); // 创建单元格
                    String cellValue = ObjectUtil.isNotEmpty(value) ? value.toString() : "";
                    cell.setCellValue(cellValue); // 设置单元格的值
                    setColorStyle(workbook,cell,dayStatExcelParam,dayStatExcel,cellValue);
                    j++;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void isNeedStyle(DayStatExcelParam dayStatExcelParam,DayStatExcel dayStatExcel){
        List<HrUserAttendanceStat> hrUserAttendanceStatList =  dayStatExcelParam.getUserAttendanceStatList();
        if(dayStatExcelParam.getAttendanceTypeDetails().equals("休息") && CollectionUtil.isEmpty(hrUserAttendanceStatList)){
            return;
        }
        /*if(dayStatExcelParam.getAttendanceTypeDetails().equals("休息")){
            return;
        }*/
        Map<String,HrUserAttendanceStat> map = new HashMap<>();
        hrUserAttendanceStatList.forEach(stat -> map.put(stat.getCheckType(),stat));
        //OnDuty：上班；OffDuty：下班；休息 REST
        //打卡是否异常：-1 缺卡，0 管理员修改,1 正常,3 迟到，4 早退，5 请假，6 外出，7 出差
        HrUserAttendanceStat onDutyStat = map.get("OnDuty");
        if(onDutyStat.getAbnormalFlag() == -1){//缺卡
            dayStatExcel.setOnDutyStatus("缺卡");
        }else if(onDutyStat.getAbnormalFlag() == 0){//管理员修改
            String admin = "管理员"+onDutyStat.getUpdateUser()+"改为正常";
            dayStatExcel.setOnDutyStatus(admin);
            dayStatExcel.setOffDutyTime(DateUtil.formatDateTime(onDutyStat.getCheckTime()));
        }else if(onDutyStat.getAbnormalFlag() == 1){//正常
            dayStatExcel.setOnDutyStatus("正常");
            dayStatExcel.setOffDutyTime(DateUtil.formatDateTime(onDutyStat.getCheckTime()));
        }else if(onDutyStat.getAbnormalFlag() == 3){//迟到
            dayStatExcel.setOnDutyStatus("迟到");
            dayStatExcel.setOffDutyTime(DateUtil.formatDateTime(onDutyStat.getCheckTime()));
        }else if(onDutyStat.getAbnormalFlag() == 4){//早退
            dayStatExcel.setOnDutyStatus("早退");
            dayStatExcel.setOffDutyTime(DateUtil.formatDateTime(onDutyStat.getCheckTime()));
        }else if(ObjectUtil.isNotEmpty(onDutyStat.getApplyResubmitAttendanceId())){
            dayStatExcel.setOnDutyStatus("补卡审批通过");
            dayStatExcel.setOffDutyTime(DateUtil.formatDateTime(onDutyStat.getCheckTime()));
        }

        HrUserAttendanceStat restStat = map.get("REST");
        if(restStat.getAbnormalFlag() == -1){//缺卡
            dayStatExcel.setRestStatus("缺卡");
        }else if(onDutyStat.getAbnormalFlag() == 0){//管理员修改
            String admin = "管理员"+onDutyStat.getUpdateUser()+"改为正常";
            dayStatExcel.setRestStatus(admin);
            dayStatExcel.setRestTime(DateUtil.formatDateTime(restStat.getCheckTime()));
        }else if(ObjectUtil.isNotEmpty(restStat.getApplyResubmitAttendanceId())){
            dayStatExcel.setRestStatus("补卡审批通过");
            dayStatExcel.setRestTime(DateUtil.formatDateTime(onDutyStat.getCheckTime()));
        }

        HrUserAttendanceStat offDutyStat = map.get("OffDuty");
        if(offDutyStat.getAbnormalFlag() == -1){//缺卡
            dayStatExcel.setOffDutyStatus("缺卡");
        }else if(onDutyStat.getAbnormalFlag() == 0){//管理员修改
            String admin = "管理员"+onDutyStat.getUpdateUser()+"改为正常";
            dayStatExcel.setOffDutyStatus(admin);
            dayStatExcel.setOffDutyTime(DateUtil.formatDateTime(offDutyStat.getCheckTime()));
        }else if(offDutyStat.getAbnormalFlag() == 1){//正常
            dayStatExcel.setOffDutyStatus("正常");
            dayStatExcel.setOffDutyTime(DateUtil.formatDateTime(offDutyStat.getCheckTime()));
        }else if(offDutyStat.getAbnormalFlag() == 3){//迟到
            dayStatExcel.setOffDutyStatus("迟到");
            dayStatExcel.setOffDutyTime(DateUtil.formatDateTime(offDutyStat.getCheckTime()));
        }else if(offDutyStat.getAbnormalFlag() == 4){//早退
            dayStatExcel.setOffDutyStatus("早退");
            dayStatExcel.setOffDutyTime(DateUtil.formatDateTime(offDutyStat.getCheckTime()));
        }else if(ObjectUtil.isNotEmpty(offDutyStat.getApplyResubmitAttendanceId())){
            dayStatExcel.setOffDutyStatus("补卡审批通过");
            dayStatExcel.setOffDutyTime(DateUtil.formatDateTime(onDutyStat.getCheckTime()));
        }
    }

    private void setColorStyle(Workbook workbook,Cell cell,DayStatExcelParam dayStatExcelParam,DayStatExcel dayStatExcel,String status){
        if (StringUtils.isEmpty(status)){
            return;
        }
        if("正常".equals(status)){
            return;
        }
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);// 设置水平居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);// 设置垂直居中
        Font font = workbook.createFont();
        font.setBold(true);//设置字体加粗
        style.setFont(font);//增加字体样式
        if("缺卡".equals(status)){
            style.setFillForegroundColor(IndexedColors.RED.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(style);
            return;
        }

        if(status.contains("管理员")){
            style.setFillForegroundColor(IndexedColors.RED.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(style);
            return;
        }

        if("迟到".equals(status)){
            style.setFillForegroundColor(IndexedColors.RED.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(style);
            return;
        }

        if("早退".equals(status)){
            style.setFillForegroundColor(IndexedColors.RED.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(style);
            return;
        }

        if("请假".equals(status)){
            style.setFillForegroundColor(IndexedColors.RED.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(style);
            return;
        }

        if("补卡审批通过".equals(status)){
            style.setFillForegroundColor(IndexedColors.RED.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(style);
            return;
        }

    }

    /** 设置表头,暂时先固定 */
    private void setHerd(Sheet sheet,Workbook workbook,String headStr){
        headStr = "姓名,考勤组,部门,工号,职位,日期,班次,上班打卡时间,上班打卡结果,休息打卡时间,休息打卡结果,下班打卡时间,下班打卡结果,"+
                "关联的审批单,出勤天数,休息天数,工作时长,迟到次数,迟到时长,严重迟到次数,严重迟到时长,早退次数,早退时长,缺卡次数,"+
                "旷工天数,出差时长,外出时长,事假(天),病假(天),婚假(天),产假(天),丧假(天),年假(天),调休(小时),加班总时长";
        List<String> list = Arrays.asList(headStr.split(","));

        Row row = sheet.createRow(0); // 创建第一行：当作标题
        Short height = (short) 500;
        row.setHeight(height);

        /** 设置背景颜色 */
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        /** 设置设置边框 */
        style.setBorderBottom(BorderStyle.THIN);//下边框
        style.setBorderLeft(BorderStyle.THIN);//左边框
        style.setBorderTop(BorderStyle.THIN);//上边框
        style.setBorderRight(BorderStyle.THIN);//右边框

        /** 字体剧中 */
        style.setAlignment(HorizontalAlignment.CENTER);// 设置水平居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);// 设置垂直居中

        /** 字体大小和样式*/
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 15);
        font.setFontName("仿宋_GB2312");
        font.setBold(true);//设置字体加粗
        style.setFont(font);//增加字体样式

        for(int i=0;i<list.size();i++){
            Cell cell = row.createCell(i); // 创建单元格
            cell.setCellValue(list.get(i)); // 设置单元格的值
            /** 设置宽度*/
            sheet.setColumnWidth(i, 256 * 25); // 设置第一列的列宽为30个字符宽度
            /** 应用样式*/
            cell.setCellStyle(style);
        }
    }

    /** 月统计考勤概览-假勤分析*/
    @Override
    public AttendanceAnalysisData attendanceAnalysis(AttendanceAnalysisParam attendanceAnalysisParam){
        /**
         * 时间计算
         * */
        List<String> dayList = new ArrayList<>();
        if(attendanceAnalysisParam.getTimeType() == 1){ //日

        }

        if(attendanceAnalysisParam.getTimeType() == 2){//周

        }

        if(attendanceAnalysisParam.getTimeType() == 3){//月

        }

        return new AttendanceAnalysisData();
    }

    private AttendanceAnalysisData countAnalysisData(AttendanceAnalysisParam attendanceAnalysisParam,List<String> dayList){


        return new AttendanceAnalysisData();
    }
}
