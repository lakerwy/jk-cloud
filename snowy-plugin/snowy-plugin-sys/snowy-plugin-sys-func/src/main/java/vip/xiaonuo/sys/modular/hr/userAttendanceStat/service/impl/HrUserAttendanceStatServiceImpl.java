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
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
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
import vip.xiaonuo.sys.modular.hr.attendanceAnalStat.entity.HrAttendanceAnalStat;
import vip.xiaonuo.sys.modular.hr.attendanceAnalStat.service.HrAttendanceAnalStatService;
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
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.util.ExcelUtil;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.vo.*;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.mapper.HrUserAttendanceStatMapper;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.service.HrUserAttendanceStatService;
import vip.xiaonuo.sys.modular.org.entity.SysOrg;
import vip.xiaonuo.sys.modular.org.service.SysOrgService;
import vip.xiaonuo.sys.modular.user.entity.SysUser;
import vip.xiaonuo.sys.modular.user.service.SysUserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
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
    private HrUserAttendanceService hrUserAttendanceService;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private HrUserApplyHolidayService hrUserApplyHolidayService;
    @Resource
    private HrFestivalDataService hrFestivalDataService;
    @Resource
    private HrApplyWorkOtService hrApplyWorkOtService;
    @Resource
    private SysOrgService sysOrgService;
    @Resource
    private HrAttendanceAnalStatService hrAttendanceAnalStatService;

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
        hrUserAttendanceStatEvent.setAbnormalFlag(abnormalFlag);
        countAnalStat(hrUserAttendanceStatEvent);
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
    //数据统计
    private void countAnalStat(HrUserAttendanceStatEvent hrUserAttendanceStatEvent){
        if(hrUserAttendanceStatEvent.getCheckType().equals("REST")){
            return;
        }
        SysUser sysUser = sysUserService.queryEntity(hrUserAttendanceStatEvent.getUserId());
        List<AttendanceTimeSlot> slotList = hrUserAttendanceService.getAttendanceTimeSlotList(sysUser.getAttendanceGroupId(),hrUserAttendanceStatEvent.getCheckTime());
        Map<String,Date> mapTime = new HashMap<>();
        slotList.forEach(slot -> {
            mapTime.put(slot.getDutyType(),slot.getCompareTime());
        });

        String day = DateUtil.format(hrUserAttendanceStatEvent.getCheckTime(),DatePattern.NORM_DATE_PATTERN);
        List<HrAttendanceAnalStat> hrAttendanceAnalStatList = hrAttendanceAnalStatService.list(new LambdaQueryWrapper<HrAttendanceAnalStat>()
                .eq(HrAttendanceAnalStat::getUserId,hrUserAttendanceStatEvent.getUserId())
                .between(HrAttendanceAnalStat::getCheckTime,day+HrUserAttendanceStatEnum.BEGIN_TIMNE.getValue(),day+HrUserAttendanceStatEnum.END_TIME.getValue()));
        HrAttendanceAnalStat hrAttendanceAnalStat = new HrAttendanceAnalStat();
        if(CollectionUtil.isNotEmpty(hrAttendanceAnalStatList)){
            hrAttendanceAnalStat = hrAttendanceAnalStatList.get(0);
        }
        hrAttendanceAnalStat.setOrgId(sysUser.getOrgId());
        hrAttendanceAnalStat.setUserId(hrUserAttendanceStatEvent.getUserId());
        hrAttendanceAnalStat.setUserName(hrUserAttendanceStatEvent.getUserName());
        hrAttendanceAnalStat.setCheckTime(hrUserAttendanceStatEvent.getCheckTime());
        hrAttendanceAnalStat.setResubmitFlag(StringUtils.isNotEmpty(hrUserAttendanceStatEvent.getIsResubmitAttendanceFlag()) ? 1 : 0);
        hrAttendanceAnalStat.setAttendanceFlag(1);
        hrAttendanceAnalStat.setLateFlag(hrUserAttendanceStatEvent.getAbnormalFlag() == 3 ? 1 : 0);
        if(hrUserAttendanceStatEvent.getAbnormalFlag() == 3){
            Long lateMinutes = DateUtil.between(hrUserAttendanceStatEvent.getCheckTime(),mapTime.get(hrUserAttendanceStatEvent.getCheckType()),DateUnit.MINUTE);
            hrAttendanceAnalStat.setLateMinutes(Integer.parseInt(lateMinutes.toString()));
        }
        hrAttendanceAnalStat.setLeaveEarlyFlag(hrUserAttendanceStatEvent.getAbnormalFlag() == 4 ? 1 : 0);
        if(hrUserAttendanceStatEvent.getAbnormalFlag() == 4){
            Long lateMinutes = DateUtil.between(mapTime.get(hrUserAttendanceStatEvent.getCheckType()),hrUserAttendanceStatEvent.getCheckTime(),DateUnit.MINUTE);
            hrAttendanceAnalStat.setLateMinutes(Integer.parseInt(lateMinutes.toString()));
        }
        if(hrUserAttendanceStatEvent.getCheckType().equals("OffDuty")){//工时
            Long workHours = DateUtil.between(mapTime.get("OnDuty"),hrUserAttendanceStatEvent.getCheckTime(),DateUnit.MINUTE);
            hrAttendanceAnalStat.setWorkotHours(Double.parseDouble(workHours.toString()));
        }
        hrAttendanceAnalStatService.saveOrUpdate(hrAttendanceAnalStat);
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
        List<UserAttendanceConfigEntity> userAttendanceConfigEntityList = getUserConfigCachData(loginUser.getId(),beginDate);
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
    public List<UserAttendanceConfigEntity> getUserConfigCachData(String userId,Date beginDate){ //获取打卡次数
        String cache = String.valueOf(commonCacheOperator.get(HR_ATTENDANCE_CONFIG_KEY));
        List<AttendanceTimeSlot> timeSlots = new ArrayList<>();

        SysUser sysUser = sysUserService.queryEntity(userId);
        List<AttendanceTimeSlot> slotList = new ArrayList<>();
        Map<String,String> configMap = new HashMap<>();
        if(StringUtils.isNotEmpty(cache) && !cache.equals("null")){
            configMap = (Map) JSON.parse(cache);
        }
        if(StringUtils.isNotEmpty(sysUser.getAttendanceGroupId()) && ObjectUtil.isNotEmpty(configMap.get(sysUser.getAttendanceGroupId()))){
            slotList = JSONArray.parseArray(configMap.get(sysUser.getAttendanceGroupId()), AttendanceTimeSlot.class);
            timeSlots = timeSlots(slotList,beginDate);
        }

        //取默认配置
        String defaultConfig = String.valueOf(commonCacheOperator.get(HR_ATTENDANCE_CONFIG_DEFAULT_KEY));
        List<AttendanceTimeSlot> defaSlots = JSONArray.parseArray(defaultConfig, AttendanceTimeSlot.class);
        defaSlots = timeSlots(defaSlots,beginDate);
        List<AttendanceTimeSlot> slots = CollectionUtil.isNotEmpty(timeSlots) ? timeSlots : defaSlots;
        return slots.stream().map(slot -> {
            UserAttendanceConfigEntity userAttendanceConfigEntity = BeanUtil.toBean(slot, UserAttendanceConfigEntity.class);
            userAttendanceConfigEntity.setBeginMinTime(DateUtil.format(slot.getBeginTime(),DatePattern.NORM_DATETIME_PATTERN));
            userAttendanceConfigEntity.setCheckTime(slot.getCompareTime());
            userAttendanceConfigEntity.setEndMinTime(DateUtil.format(slot.getEndTime(),DatePattern.NORM_DATETIME_PATTERN));
            return userAttendanceConfigEntity;
        }).collect(Collectors.toList());
    }

    private List<AttendanceTimeSlot> timeSlots(List<AttendanceTimeSlot> slotList,Date date){
        slotList.forEach(slot -> {
            String day = DateUtil.format(date,DatePattern.NORM_DATE_PATTERN);
            String beginTime = day+" "+DateUtil.format(slot.getBeginTime(),DatePattern.NORM_TIME_PATTERN);
            slot.setBeginTime(CommonTimeFormatUtil.strToDate(beginTime,DatePattern.NORM_DATETIME_PATTERN));

            String endTime = day+" "+DateUtil.format(slot.getEndTime(),DatePattern.NORM_TIME_PATTERN);
            slot.setEndTime(CommonTimeFormatUtil.strToDate(endTime,DatePattern.NORM_DATETIME_PATTERN));

            if(ObjectUtil.isNotEmpty(slot.getCompareTime())){
                String  compareTime= day+" "+DateUtil.format(slot.getCompareTime(),DatePattern.NORM_TIME_PATTERN);
                slot.setCompareTime(CommonTimeFormatUtil.strToDate(compareTime,DatePattern.NORM_DATETIME_PATTERN));
            }

            if(ObjectUtil.isNotEmpty(slot.getWorkBeginTime())){
                String  workBeginTime= day+" "+DateUtil.format(slot.getWorkBeginTime(),DatePattern.NORM_TIME_PATTERN);
                slot.setWorkBeginTime(CommonTimeFormatUtil.strToDate(workBeginTime,DatePattern.NORM_DATETIME_PATTERN));
            }

            if(ObjectUtil.isNotEmpty(slot.getWorkEndTime())){
                String  workEndTime= day+" "+DateUtil.format(slot.getWorkEndTime(),DatePattern.NORM_TIME_PATTERN);
                slot.setWorkEndTime(CommonTimeFormatUtil.strToDate(workEndTime,DatePattern.NORM_DATETIME_PATTERN));
            }
        });
        return slotList;
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

        //去重：年月日-时分相同的数据只取一条
        if(CollectionUtil.isNotEmpty(hrUserAttendanceList)){
            Map<String,HrUserAttendance> map = new HashMap<>();
            hrUserAttendanceList.forEach(attendance -> {
                String time = DateUtil.format(attendance.getCheckTime(),DatePattern.NORM_DATETIME_MINUTE_PATTERN);
                map.put(time,attendance);
            });
            hrUserAttendanceList = map.entrySet().stream().map(at -> at.getValue()).collect(Collectors.toList());
        }

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
                }else{
                    HrUserAttendance hrUserAttendance = BeanUtil.toBean(stat,HrUserAttendance.class);
                    if(ObjectUtil.isNotEmpty(hrUserAttendance)){
                        mergeList.add(hrUserAttendance);
                    }
                }
            });
            mergeList.add(attendance);
        });

        Date startTime =  CommonTimeFormatUtil.strToDate(hrUserAttendancePageParam.getStartTime(),DatePattern.NORM_DATE_PATTERN);
        List<UserAttendanceConfigEntity> userAttendanceConfigEntityList = hrUserAttendanceStatService.getUserConfigCachData(loginUser.getId(),startTime);/** 获取打卡配置*/
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
            Date checkTime = userAttendanceConfigEntityList.get(i).getCheckTime();
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
                //判断是否存在 请假，外出，出差
                Long holidayNum = hrUserApplyHolidayService.count(new LambdaQueryWrapper<HrUserApplyHoliday>());


                hrUserAttendance.setAbnormalFlag(-1);
                hrUserAttendance.setUserId(userId);
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

        Long attendanceNum = hrAttendanceAnalStatService.count(new LambdaQueryWrapper<HrAttendanceAnalStat>()
                .between(HrAttendanceAnalStat::getCheckTime,beginDate,endDate));
        attendanceStatNumData.setAttendanceNum(attendanceNum);//打卡人数

        Long userNum = sysUserService.count(new LambdaQueryWrapper<SysUser>().isNotNull(SysUser::getAttendanceGroupId));
        attendanceStatNumData.setUserNum(userNum); //应到人数
        attendanceStatNumData.setNoAttendanceNum(userNum-attendanceNum); //未打卡人数

        //迟到人数
        Map<String, Object> map = hrAttendanceAnalStatService.getMap(new QueryWrapper<HrAttendanceAnalStat>().select("IFNULL(sum(LATE_FLAG),0) as lateNum")
                .lambda().between(HrAttendanceAnalStat::getCheckTime,beginDate,endDate));
        attendanceStatNumData.setLateUserNum(map.get("lateNum") == null ? 0l : Long.parseLong(map.get("lateNum").toString()));

        //外勤人数  -- 暂时不确定外勤人数
        attendanceStatNumData.setEgressNum(0l);
        return attendanceStatNumData;
    }

    @Override
    public LinkedHashMap<Integer, String> statPreviewDateList(AttendanceDayStatParam attendanceDayStatParam){
        Date beginTime = CommonTimeFormatUtil.strToDate(attendanceDayStatParam.getBeginTime(),DatePattern.NORM_DATETIME_PATTERN);
        Date endTime = CommonTimeFormatUtil.strToDate(attendanceDayStatParam.getEndTime(),DatePattern.NORM_DATETIME_PATTERN);
        LinkedHashMap<Integer, String> resultMap = new LinkedHashMap<>();
        handDayList(beginTime,endTime,resultMap);
        return resultMap;
    }

    private void handDayList(Date beginTime,Date endTime,LinkedHashMap<Integer, String> map){
        // 遍历时间段中的每一天
        Date current = beginTime;
        while (current.compareTo(endTime) <= 0) {
            // 获取当前日期的周几
            Integer dayOfWeek = DateUtil.dayOfWeek(current)-1;
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
            List<AttendanceTimeSlot> attendanceTimeSlotList = hrUserAttendanceService.getAttendanceTimeSlotList(user.getAttendanceGroupId(),beginTime);
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
    public Page<MonthStatData> monthStatPreview(AttendanceMonthStatParam attendanceMonthStatParam){
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
        LambdaQueryWrapper<HrUserApplyHoliday> holidayQuery = new LambdaQueryWrapper();
        if(ObjectUtil.isNotEmpty(userList.getRecords())){ holidayQuery.in(HrUserApplyHoliday::getApplyUserId,userList.getRecords().stream().map(user -> user.getId()).collect(Collectors.toList()));}
        List<HrUserApplyHoliday> hrUserApplyHolidayList = hrUserApplyHolidayService.list(holidayQuery
                .between(HrUserApplyHoliday::getApplyBeginTime,beginTime,endTime)
                .between(HrUserApplyHoliday::getApplyEndTime,beginTime,endTime));
        Map<String,List<HrUserApplyHoliday>> holidayMap = new HashMap<>();
        hrUserApplyHolidayList.forEach(holiday -> {
            List<HrUserApplyHoliday> list = holidayMap.get(holiday.getApplyUserId()) == null ? new ArrayList<>() : holidayMap.get(holiday.getApplyUserId());
            list.add(holiday);
            holidayMap.put(holiday.getApplyUserId(),list);
        });

        /** 打卡记录只查询当前返回的人员*/
        List<HrUserAttendanceStat> hrUserAttendanceStatList = hrUserAttendanceStatService.list(new LambdaQueryWrapper<HrUserAttendanceStat>()
                .between(HrUserAttendanceStat::getCheckTime,beginTime,endTime)
                .in(HrUserAttendanceStat::getUserId,userList.getRecords().stream().map(user -> user.getId()).collect(Collectors.toList()))); // 查询该时间段内所有人打卡数据
        /** 转换打卡数据格式：key - userId，value( key -day,value -stat)*/
        Map<String,Map<String,List<HrUserAttendanceStat>>> attendanceDayMap = statToMap(hrUserAttendanceStatList);

        //List<AttendanceMonthStatParam> attendanceMonthStatList = new ArrayList<>();
        List<MonthStatData> monthStatDataList = new ArrayList<>();
        String timeStr = " 8:30:00";
        /**  待优化 */
        userList.getRecords().forEach(user -> {
            MonthStatData monthStatData = new MonthStatData();
            monthStatData.setUserName(user.getName());
            monthStatData.setAttendanceTypeName("建科标准考勤");
            monthStatData.setOrgName(user.getOrgName());
            monthStatData.setPosition(user.getPositionName());
            monthStatData.setEmpNo(user.getEmpNo());
            Map<String,List<HrUserAttendanceStat>> listMap = attendanceDayMap.get(user.getId()) == null ? new HashMap<>() : attendanceDayMap.get(user.getId());
            List<HrUserApplyHoliday> holidayList = holidayMap.get(user.getId()) == null ? new ArrayList<>() : holidayMap.get(user.getId());
            List<HrApplyWorkOt> hrApplyWorkOts = new ArrayList<>();//预留加班
            countMonth(user,monthStatData,listMap,dayList,holidayList,hrApplyWorkOts);
            monthStatDataList.add(monthStatData);
            /*try{
                statParam = countWorkData(hrUserAttendanceStatList,timeStr,statParam,user);//计算基础数据
                attendanceMonthStatList.add(dayListMonth(hrUserAttendanceStatList,statParam,dayList,user,hrUserApplyHolidayList));
            }catch (Exception e){
                log.error("统计数据失败！"+e.toString());
            }*/
        });
        monthStatDataList.forEach(month -> {
            TreeMap<Integer,MonthStatDataStatus> map = new TreeMap<>();
            List<MonthStatDataStatus> statusList = month.getMonthStatDataStatusList();
            statusList.forEach(status -> {
                SimpleDateFormat sdf = new SimpleDateFormat("dd");
                Integer key = Integer.parseInt(sdf.format(CommonTimeFormatUtil.strToDate(status.getTime(),DatePattern.NORM_DATE_PATTERN)));
                map.put(key,status);
            });
            month.setTreeMap(map);
        });

        Page<MonthStatData> page = new Page<>();
        page.setRecords(monthStatDataList);
        page.setTotal(sysUserService.count(sysUserLambdaQueryWrapper));
        return page;
    }
    /** 计算*/
    private void countMonth(SysUser sysUser,MonthStatData monthStatData,Map<String,List<HrUserAttendanceStat>> statMap,// key -day,value -打卡数据
                            List<String> dayList,List<HrUserApplyHoliday> holidayList,List<HrApplyWorkOt> hrApplyWorkOtList){
        Integer lateNum = 0;//迟到次数
        Long lateTime = 0l;//迟到时长
        Integer onDutyLackNum = 0;//上班缺卡次数
        Integer offDutyLackNum = 0;//下班缺卡次数
        Integer absenteeismNum = 0;//旷工天数
        Integer onDutyNum = 0;//上班打卡次数
        Integer offDutyNum = 0;//下班打卡次数

        List<MonthStatDataStatus> monthStatDataStatusList = new ArrayList<>();
        dayList.forEach(day -> {
            MonthStatDataStatus monthStatDataStatus = new MonthStatDataStatus();
            monthStatDataStatus.setWeekTime(CommonTimeFormatUtil.dayAndSunday(day));
            monthStatDataStatus.setTime(day);
            Boolean isFestival = isFestival(day+HrUserAttendanceStatEnum.BEGIN_TIMNE.getValue(),1);//1 判断是法定节假日，2 判断是法定加班日
            Boolean isWork = isFestival(day+HrUserAttendanceStatEnum.BEGIN_TIMNE.getValue(),2);//1 判断是法定节假日，2 判断是法定加班日
            Boolean isWeek = DateUtil.isWeekend(CommonTimeFormatUtil.strToDate(day+HrUserAttendanceStatEnum.BEGIN_TIMNE.getValue(),DatePattern.NORM_DATETIME_PATTERN));
            Boolean isAttendance = (!isWeek && !isFestival) || (isWeek && isWork) ? true : false; //非周末+非节假日，周末+法定加班日都需要打卡
            if(isAttendance){
                countMonthStat(monthStatDataStatus,statMap.get(sysUser.getId()),sysUser);
            }else{
                monthStatDataStatus.setStatus("休息");
            }
            monthStatDataStatusList.add(monthStatDataStatus);
        });
        monthStatData.setMonthStatDataStatusList(monthStatDataStatusList);

    }

    private void countMonthStat(MonthStatDataStatus monthStatDataStatus,List<HrUserAttendanceStat> statList,SysUser sysUser){
        if(CollectionUtil.isEmpty(statList)){
            monthStatDataStatus.setStatus("旷工");
            return;
        }
        String status = "";
        Map<String,HrUserAttendanceStat> map = new HashMap<>();
        statList.forEach(stat -> map.put(stat.getCheckType(),stat));
        //上班OnDuty，下班 OffDuty，休息 REST
        if(map.get("OnDuty") == null){
            status = status+"上班缺卡;";
        }{
            HrUserAttendanceStat stat = map.get("OnDuty");
            status = attendanceStatus(stat,monthStatDataStatus.getTime());
        }
        if(map.get("REST") == null){
            status = status+"中午缺卡;";
        }{
            HrUserAttendanceStat stat = map.get("REST");
            status = attendanceStatus(stat,monthStatDataStatus.getTime());
        }
        if(map.get("OffDuty") == null){
            status = status+"下班缺卡;";
        }{
            HrUserAttendanceStat stat = map.get("OffDuty");
            status = attendanceStatus(stat,monthStatDataStatus.getTime());
        }
        if(StringUtils.isEmpty(status)){
            status = "正常";
        }{

        }
        monthStatDataStatus.setStatus(status);
    }

    private String attendanceStatus(HrUserAttendanceStat hrUserAttendanceStat,String day){
        if(hrUserAttendanceStat.getAbnormalFlag() == 3){ //-1 缺卡，0 管理员修改,1 正常,3 迟到，4 早退，5 请假，6 外出，7 出差
            Date date = DateUtil.parse(day+" 8:30:00",DatePattern.NORM_DATETIME_PATTERN);
            return "迟到"+DateUtil.between(hrUserAttendanceStat.getCheckTime(),date,DateUnit.MINUTE)+"分钟";
        }

        if(hrUserAttendanceStat.getAbnormalFlag() == 4){
            Date date = DateUtil.parse(day+" 5:30:00",DatePattern.NORM_DATETIME_PATTERN);
            return "早退"+DateUtil.between(hrUserAttendanceStat.getCheckTime(),date,DateUnit.MINUTE)+"分钟";
        }
        return "";
    }

    @Override
    public void downloadMonthExcel(AttendanceMonthStatParam attendanceMonthStatParam, HttpServletResponse response) throws Exception{
        Page<MonthStatData>  page = monthStatPreview(attendanceMonthStatParam);
        List<MonthStatData> monthStatDataList = page.getRecords();
        ExcelUtil.workbookExcelMonth(monthStatDataList,response);
    }


    private Map<String,Map<String,List<HrUserAttendanceStat>>> statToMap(List<HrUserAttendanceStat> userAttendanceStatList){
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
        return attendanceDayMap;
    }

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
        ExcelUtil.workbookExcelDay(dayStatExcelList,response);
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
        Map<String,Map<String,List<HrUserAttendanceStat>>> attendanceDayMap = statToMap(userAttendanceStatList);/**  打卡数据预处理：key -userId，value(key-年月日，value-打卡记录)*/
        /*userAttendanceStatList.forEach(stat -> {
            String userId = stat.getUserId();
            Map<String,List<HrUserAttendanceStat>> attendanceMap = attendanceDayMap.get(userId) == null ? new HashMap<>() : attendanceDayMap.get(userId);
            String date = DateUtil.format(stat.getCheckTime(), DatePattern.NORM_DATE_PATTERN);
            List<HrUserAttendanceStat> statList = attendanceMap.get(date) == null ? new ArrayList<>() : attendanceMap.get(date);
            statList.add(stat);
            attendanceMap.put(date,statList);
            attendanceDayMap.put(userId,attendanceMap);
        });*/

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


    /** 月统计考勤概览-假勤分析
     * 若不传部门ID，固定返回一级部门：子公司，直属事业部，直属设计院，职能中心，研究院
     *
     * */
    @Override
    public AttendanceAnalysisData attendanceAnalysis(AttendanceAnalysisParam attendanceAnalysisParam){
        List<String> orgList = new ArrayList<>();
        List<String> userList = new ArrayList<>();
        Long shouldAtNumL = sysUserService.count(new LambdaQueryWrapper<SysUser>().isNotNull(SysUser::getAttendanceGroupId).ne(SysUser::getAttendanceGroupId,""));
        Integer shouldAtNum = Integer.parseInt(shouldAtNumL.toString());
        if(StringUtils.isEmpty(attendanceAnalysisParam.getOrgId())){
            List<SysOrg> sysOrgList = sysOrgService.list(new LambdaQueryWrapper<SysOrg>().eq(SysOrg::getParentId,HrUserAttendanceStatEnum.PARENT_CODE.getValue()));
            sysOrgList.forEach(org -> orgList.add(org.getId()));
        }else{
            orgList.add(attendanceAnalysisParam.getOrgId());
            String userStr = hrUserAttendanceService.getOrgAndUserId(attendanceAnalysisParam.getOrgId());
            userList = Arrays.asList(userStr.split(","));
        }
        AttendanceAnalysisData attendanceAnalysisData = new AttendanceAnalysisData();
        VactionTimeAnalysis vactionTimeAnalysis = dateAnalysis(attendanceAnalysisParam);
        Boolean isAllOrg = true;
        attendanceBaseData(attendanceAnalysisData,vactionTimeAnalysis,isAllOrg,userList);
        Map<Integer,Map<String, Object>> analStatMap = countLineAnalysis(attendanceAnalysisParam.getTimeType(),orgList,attendanceAnalysisData,vactionTimeAnalysis);
        int size= analStatMap.entrySet().size();
        attendanceAnalysisParam.getType().forEach(type -> {
            String[] yAxis = new String[size];
            switch(type){
                case 1://应到人数  shouldAtNum
                    for(int i=0;i<size;i++){
                        yAxis[i] = shouldAtNum.toString();//暂时用固定值
                    }
                    break;
                case 2: //打卡人数
                    for(int i=0;i<size;i++){
                        String attendanceNum = analStatMap.get("attendanceNum") == null ? "0" : analStatMap.get("attendanceNum").toString();
                        yAxis[i] = attendanceNum;
                    }
                    break;
                case 3: //出勤率
                    for(int i=0;i<size;i++){
                        Integer attendanceNum = analStatMap.get("attendanceNum") == null ? 0 : Integer.parseInt(analStatMap.get("attendanceNum").toString());
                        //Integer shouldAtNum = 308;
                        Double attendanceRate = shouldAtNum != 0l ? attendanceNum/shouldAtNum : 0.0;
                        yAxis[i] = attendanceRate.toString();//暂时用固定值
                    }
                    break;
                case 4: //平均工时
                    for(int i=0;i<size;i++){
                        Integer attendanceNum = analStatMap.get("attendanceNum") == null ? 0 : Integer.parseInt(analStatMap.get("attendanceNum").toString());
                        Double totalWorkHours = analStatMap.get("totalWorkHours") == null ? 0.0 : Double.parseDouble(analStatMap.get("totalWorkHours").toString());
                        Double averageWkHour = attendanceNum == 0 ? 0.0 : totalWorkHours.doubleValue()/attendanceNum;
                        yAxis[i] = averageWkHour.toString();
                    }
                    break;
                case 5: //请假人数
                    for(int i=0;i<size;i++){
                        String leaveNum = analStatMap.get("leaveNum") == null ? "0" : analStatMap.get("leaveNum").toString();
                        yAxis[i] = leaveNum;
                    }
                    break;
                case 6: //加班人数
                    for(int i=0;i<size;i++){
                        String workOtNum = analStatMap.get("workOtNum") == null ? "0" : analStatMap.get("workOtNum").toString();
                        yAxis[i] = workOtNum;
                    }
                    break;
                case 7: //迟到人数
                    for(int i=0;i<size;i++){
                        String lateNum = analStatMap.get("lateNum") == null ? "0" : analStatMap.get("lateNum").toString();
                        yAxis[i] = lateNum;
                    }
                    break;
                default:
                    break;
            }
            if(attendanceAnalysisData.getFirstData() == null){
                attendanceAnalysisData.setFirstData(yAxis);
            }else{
                attendanceAnalysisData.setSecondData(yAxis);
            }
        });
        orgDataStat( attendanceAnalysisData, attendanceAnalysisParam.getOrgId(), attendanceAnalysisParam, vactionTimeAnalysis);
        return attendanceAnalysisData;
    }

    private void attendanceBaseData(AttendanceAnalysisData attendanceAnalysisData, VactionTimeAnalysis vactionTimeAnalysis, Boolean isAllOrg, List<String> userList){

        Long shouldAtNum = isAllOrg ? sysUserService.count(new LambdaQueryWrapper<SysUser>().isNotNull(SysUser::getAttendanceGroupId)) : userList.size();
        attendanceAnalysisData.setShouldAtNum(shouldAtNum);
        attendanceAnalysisData.setShouldAtCompare("0");

        Long attendanceNum = isAllOrg ? hrAttendanceAnalStatService.count(new LambdaQueryWrapper<HrAttendanceAnalStat>()
                .between(HrAttendanceAnalStat::getCheckTime,vactionTimeAnalysis.getFirstBeginTime(),vactionTimeAnalysis.getFirstEndTime()))
                : hrAttendanceAnalStatService.count(new LambdaQueryWrapper<HrAttendanceAnalStat>()
                        .between(HrAttendanceAnalStat::getCheckTime,vactionTimeAnalysis.getFirstBeginTime(),vactionTimeAnalysis.getFirstEndTime())
                        .in(HrAttendanceAnalStat::getUserId,userList));
        attendanceAnalysisData.setAttendanceNum(attendanceNum);
        Long oldAttendanceNum = isAllOrg ? hrAttendanceAnalStatService.count(new LambdaQueryWrapper<HrAttendanceAnalStat>()
                .between(HrAttendanceAnalStat::getCheckTime,vactionTimeAnalysis.getSecondBeginTime(),vactionTimeAnalysis.getSecondEndTime()))
                : hrAttendanceAnalStatService.count(new LambdaQueryWrapper<HrAttendanceAnalStat>()
                        .between(HrAttendanceAnalStat::getCheckTime,vactionTimeAnalysis.getSecondBeginTime(),vactionTimeAnalysis.getSecondEndTime())
                        .in(HrAttendanceAnalStat::getUserId,userList));
        Long  attendanceCompare = attendanceNum-oldAttendanceNum;
        attendanceAnalysisData.setAttendanceCompare(attendanceCompare.toString());

        double attendanceRate = shouldAtNum != 0l ? attendanceNum/shouldAtNum : 0.0;
        double oldDttendanceRate = shouldAtNum != 0l ? oldAttendanceNum/shouldAtNum : 0.0;
        attendanceAnalysisData.setAttendanceRate(decimalFormat(attendanceRate));//出勤率
        attendanceAnalysisData.setAttendanceRateCompare(decimalFormat(attendanceRate-oldDttendanceRate));


        /** 平均工时，请假人数 HOLIDAY_FLAG，加班人数 WORKOT_FLAG，迟到人数 LATE_FLAG*/
        QueryWrapper<HrAttendanceAnalStat> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("IFNULL(sum(WORK_HOURS),0) as totalWorkHours,IFNULL(sum(HOLIDAY_FLAG),0) as leaveNum,IFNULL(sum(WORKOT_FLAG),0) as workOtNum,IFNULL(sum(LATE_FLAG),0) as lateNum");
        Map<String, Object> map = isAllOrg ? hrAttendanceAnalStatService.getMap(queryWrapper.lambda()
                .between(HrAttendanceAnalStat::getCheckTime,vactionTimeAnalysis.getFirstBeginTime(),vactionTimeAnalysis.getFirstEndTime()))
                : hrAttendanceAnalStatService.getMap(queryWrapper.lambda()
                .between(HrAttendanceAnalStat::getCheckTime,vactionTimeAnalysis.getFirstBeginTime(),vactionTimeAnalysis.getFirstEndTime())
                .in(HrAttendanceAnalStat::getUserId,userList));
        Map<String, Object> oldMap = isAllOrg ? hrAttendanceAnalStatService.getMap(queryWrapper.lambda()
                .between(HrAttendanceAnalStat::getCheckTime,vactionTimeAnalysis.getSecondBeginTime(),vactionTimeAnalysis.getSecondEndTime()))
                : hrAttendanceAnalStatService.getMap(queryWrapper.lambda()
                .between(HrAttendanceAnalStat::getCheckTime,vactionTimeAnalysis.getSecondBeginTime(),vactionTimeAnalysis.getSecondEndTime())
                .in(HrAttendanceAnalStat::getUserId,userList));

        //平均工时
        Double totalWorkHours = map.get("totalWorkHours") == null ? 0.0 : Double.parseDouble(map.get("totalWorkHours").toString());
        double averageWkHour = attendanceNum == 0 ? 0.0 : totalWorkHours.doubleValue()/attendanceNum;
        Double oldTotalWorkHours =  oldMap.get("totalWorkHours") == null ? 0.0 : Double.parseDouble(oldMap.get("totalWorkHours").toString());
        double oldAverageWkHour = oldAttendanceNum == 0 ? 0.0 : oldTotalWorkHours.doubleValue()/oldAttendanceNum;
        Double averageWkHourCompare = averageWkHour - oldAverageWkHour;
        attendanceAnalysisData.setAverageWkHourCompare(averageWkHourCompare.toString());
        //请假人数
        Integer leaveNum =  map.get("leaveNum") == null ? 0 : Integer.parseInt(map.get("leaveNum").toString());
        attendanceAnalysisData.setLeaveNum(leaveNum.intValue());
        Integer oldLeaveNum =  oldMap.get("leaveNum") == null ? 0 : Integer.parseInt(oldMap.get("leaveNum").toString());
        Integer leaveNumCompare = leaveNum.intValue() - oldLeaveNum.intValue();
        attendanceAnalysisData.setLeaveNumCompare(leaveNumCompare.toString());
        //加班人数
        BigDecimal workOtNum = (BigDecimal) map.get("workOtNum") == null ? new BigDecimal(0) : (BigDecimal) map.get("workOtNum") ;
        attendanceAnalysisData.setWorkOtNum(workOtNum.intValue());
        BigDecimal oldWorkOtNum =  (BigDecimal) oldMap.get("workOtNum") == null ? new BigDecimal(0) : (BigDecimal) oldMap.get("workOtNum") ;
        Integer workOtNumCompare = workOtNum.intValue() - oldWorkOtNum.intValue();
        attendanceAnalysisData.setWorkOtNumCompare(workOtNumCompare.toString());
        //迟到人数
        BigDecimal lateNum = (BigDecimal) map.get("lateNum") == null ? new BigDecimal(0) : (BigDecimal) map.get("lateNum");
        attendanceAnalysisData.setLateNum(lateNum.intValue());
        BigDecimal oldLateNum = (BigDecimal) oldMap.get("lateNum") == null ? new BigDecimal(0) : (BigDecimal) oldMap.get("lateNum");
        Integer lateNumCompare = lateNum.intValue() - oldLateNum.intValue();
        attendanceAnalysisData.setLateNumCompare(workOtNumCompare.toString());
        if(vactionTimeAnalysis.getTimeType() == null && vactionTimeAnalysis.getTimeType() != 1){
            attendanceAnalysisData.setShouldAtNum(null);
            attendanceAnalysisData.setShouldAtCompare("");
            attendanceAnalysisData.setAttendanceNum(null);
            attendanceAnalysisData.setAttendanceCompare("");
        }

    }

    /** 时间处理 */
    private VactionTimeAnalysis dateAnalysis(AttendanceAnalysisParam attendanceAnalysisParam){
        VactionTimeAnalysis vactionTimeAnalysis = new VactionTimeAnalysis();
        vactionTimeAnalysis.setTimeType(attendanceAnalysisParam.getTimeType());
        List<String> dayList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if(attendanceAnalysisParam.getTimeType() == 1){ //日 - 获取前七天的数据
            Date firstBeginTime = CommonTimeFormatUtil.strToDate(attendanceAnalysisParam.getTime()+HrUserAttendanceStatEnum.BEGIN_TIMNE.getValue(),DatePattern.NORM_DATETIME_PATTERN);
            Date firstEndBeginTime = CommonTimeFormatUtil.strToDate(attendanceAnalysisParam.getTime()+HrUserAttendanceStatEnum.END_TIME.getValue(),DatePattern.NORM_DATETIME_PATTERN);
            Date secondBeginTime = DateUtil.offsetDay(firstBeginTime,-1);
            Date secondEndTime = DateUtil.offsetDay(firstEndBeginTime,-1);
            vactionTimeAnalysis.setFirstBeginTime(firstBeginTime);
            vactionTimeAnalysis.setFirstEndTime(firstEndBeginTime);
            vactionTimeAnalysis.setSecondBeginTime(secondBeginTime);
            vactionTimeAnalysis.setSecondEndTime(secondEndTime);
            List<TimeDataVo> timeDataVoList = new ArrayList<>();
            timeDataVoList.add(new TimeDataVo(firstBeginTime,firstEndBeginTime));
            timeDataVoList.add(new TimeDataVo(secondBeginTime,secondEndTime));
            for(int i=2;i<7;i++){
                timeDataVoList.add(new TimeDataVo(DateUtil.offsetDay(firstBeginTime,-i),DateUtil.offsetDay(firstEndBeginTime,-i)));
            }
            vactionTimeAnalysis.setTimeDataVoList(timeDataVoList);

        }
        LocalDate localTime = LocalDate.parse(attendanceAnalysisParam.getTime(), formatter);
        if(attendanceAnalysisParam.getTimeType() == 2){ //周 - 获取前七周数据
            try{
                LocalDate startOfWeek = localTime.with(DayOfWeek.MONDAY);
                LocalDate endOfWeek = localTime.with(DayOfWeek.SUNDAY);
                Date firstBeginTime =  DateUtils.parseDate(startOfWeek.format(formatter)+HrUserAttendanceStatEnum.BEGIN_TIMNE.getValue());
                Date firstEndBeginTime = DateUtils.parseDate(endOfWeek.format(formatter)+HrUserAttendanceStatEnum.END_TIME.getValue());

                LocalDate startOfPreviousWeek = startOfWeek.minusWeeks(1);
                LocalDate endOfPreviousWeek = endOfWeek.minusWeeks(1);
                Date secondBeginTime = DateUtils.parseDate(startOfPreviousWeek.format(formatter)+HrUserAttendanceStatEnum.BEGIN_TIMNE.getValue());
                Date secondEndTime = DateUtils.parseDate(endOfPreviousWeek.format(formatter)+HrUserAttendanceStatEnum.END_TIME.getValue());

                vactionTimeAnalysis.setFirstBeginTime(firstBeginTime);
                vactionTimeAnalysis.setFirstEndTime(firstEndBeginTime);
                vactionTimeAnalysis.setSecondBeginTime(secondBeginTime);
                vactionTimeAnalysis.setSecondEndTime(secondEndTime);
                List<TimeDataVo> timeDataVoList = new ArrayList<>();
                timeDataVoList.add(new TimeDataVo(firstBeginTime,firstEndBeginTime));
                timeDataVoList.add(new TimeDataVo(secondBeginTime,secondEndTime));
                for(int i=2;i<6;i++){
                    LocalDate localDate1 = startOfWeek.minusWeeks(i);
                    LocalDate localDate2 = endOfWeek.minusWeeks(i);
                    Date date1 = DateUtils.parseDate(localDate1.format(formatter)+HrUserAttendanceStatEnum.BEGIN_TIMNE.getValue());
                    Date date2 = DateUtils.parseDate(localDate2.format(formatter)+HrUserAttendanceStatEnum.END_TIME.getValue());
                    timeDataVoList.add(new TimeDataVo(date1,date2));
                }
                vactionTimeAnalysis.setTimeDataVoList(timeDataVoList);
            }catch (Exception e){
                log.error("时间转换错误！e:"+e.toString());
            }
        }
        if(attendanceAnalysisParam.getTimeType() == 3){ //月
            try{
                LocalDate startOfMonth = localTime.with(TemporalAdjusters.firstDayOfMonth());//月第一天
                LocalDate endOfMonth = localTime.with(TemporalAdjusters.lastDayOfMonth());//月最后一天
                Date firstBeginTime =  DateUtils.parseDate(startOfMonth.format(formatter)+HrUserAttendanceStatEnum.BEGIN_TIMNE.getValue());
                Date firstEndBeginTime = DateUtils.parseDate(endOfMonth.format(formatter)+HrUserAttendanceStatEnum.END_TIME.getValue());

                LocalDate  startOfPreviousMonth = startOfMonth.minusMonths(1);
                LocalDate  endOfPreviousMonth = startOfMonth.minusMonths(1);
                Date secondBeginTime = DateUtils.parseDate(startOfPreviousMonth.format(formatter)+HrUserAttendanceStatEnum.BEGIN_TIMNE.getValue());
                Date secondEndTime = DateUtils.parseDate(endOfPreviousMonth.format(formatter)+HrUserAttendanceStatEnum.END_TIME.getValue());

                vactionTimeAnalysis.setFirstBeginTime(firstBeginTime);
                vactionTimeAnalysis.setFirstEndTime(firstEndBeginTime);
                vactionTimeAnalysis.setSecondBeginTime(secondBeginTime);
                vactionTimeAnalysis.setSecondEndTime(secondEndTime);

                List<TimeDataVo> timeDataVoList = new ArrayList<>();
                timeDataVoList.add(new TimeDataVo(firstBeginTime,firstEndBeginTime));
                timeDataVoList.add(new TimeDataVo(secondBeginTime,secondEndTime));
                for(int i=2;i<6;i++){
                    LocalDate localDate1 = startOfMonth.minusMonths(i);
                    LocalDate localDate2 = endOfMonth.minusMonths(i);
                    Date date1 = DateUtils.parseDate(localDate1.format(formatter)+HrUserAttendanceStatEnum.BEGIN_TIMNE.getValue());
                    Date date2 = DateUtils.parseDate(localDate2.format(formatter)+HrUserAttendanceStatEnum.END_TIME.getValue());
                    timeDataVoList.add(new TimeDataVo(date1,date2));
                }
                vactionTimeAnalysis.setTimeDataVoList(timeDataVoList);
            }catch (Exception e){
                log.error("时间处理失败！e:"+e.toString());
            }
        }
        return vactionTimeAnalysis;
    }

    private Map<Integer,Map<String, Object>> countLineAnalysis(Integer type,List<String> orgList,AttendanceAnalysisData attendanceAnalysisData,VactionTimeAnalysis timeAnalysis){
        QueryWrapper<HrAttendanceAnalStat> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("IFNULL(sum(WORK_HOURS),0) as totalWorkHours,IFNULL(sum(HOLIDAY_FLAG),0) as leaveNum," +
                "IFNULL(sum(WORKOT_FLAG),0) as workOtNum,IFNULL(sum(LATE_FLAG),0) as lateNum,IFNULL(count(*),0) as attendanceNum");
        List<TimeDataVo> timeDataVoList = timeAnalysis.getTimeDataVoList();
        Map<Integer,Map<String, Object>> analStatMap = new HashMap<>();
        Integer i = 0;
        String[] xAxis = xAxis(timeDataVoList,timeAnalysis.getTimeType());
        attendanceAnalysisData.setXAxis(xAxis);//横坐标
        for(TimeDataVo timeDataVo : timeDataVoList){
            Map<String, Object> map = hrAttendanceAnalStatService.getMap(queryWrapper.lambda()
                    .between(HrAttendanceAnalStat::getCheckTime,timeDataVo.getBeginTime(),timeDataVo.getEndTime()));
            analStatMap.put(i,map);
            i++;
        }
        return analStatMap;
    }

    private String[] xAxis(List<TimeDataVo> timeDataVoList,Integer timeType){
        String[] xAxis = new String[timeDataVoList.size()];
        switch(timeType){
            case 1:
                 for(int i =0;i<timeDataVoList.size();i++){
                     String date = CommonTimeFormatUtil.dateToStr(timeDataVoList.get(i).getBeginTime(),"MM.dd");
                     xAxis[i] = date;
                 }
                break;
            default:
                for(int i =0;i<timeDataVoList.size();i++){
                    String date1 = CommonTimeFormatUtil.dateToStr(timeDataVoList.get(i).getBeginTime(),"MM.dd");
                    String date2 = CommonTimeFormatUtil.dateToStr(timeDataVoList.get(i).getEndTime(),"MM.dd");
                    xAxis[i] = date1+"-"+date2;
                }
                break;
        }
        return xAxis;
    }

    private String decimalFormat(Double value){
        DecimalFormat df = new DecimalFormat("#.####");
        String result = df.format(value);
        return result;
    }

    private void orgDataStat(AttendanceAnalysisData attendanceAnalysisData,String orgId,AttendanceAnalysisParam attendanceAnalysisParam,VactionTimeAnalysis vactionTimeAnalysis){
        List<Map<String,Object>> list = new ArrayList<>();
        //当未传部门参数,传部门参数
        if(StringUtils.isEmpty(orgId)){
            List<SysOrg> orgList = sysOrgService.list(new LambdaQueryWrapper<SysOrg>().eq(SysOrg::getParentId,HrUserAttendanceStatEnum.PARENT_CODE.getValue()));
            List<List<String>> userList = new ArrayList<>();
            int  i = 1 ;
            orgList.forEach(org -> {
                List<String> userIds = hrUserAttendanceService.getOrgAndUserList(org.getId()).stream().map(user -> user.getId()).collect(Collectors.toList());
                list.add(orgData(attendanceAnalysisParam,userIds,org.getName(),vactionTimeAnalysis));
            });

        }else{
            List<String> userIds =  hrUserAttendanceService.getOrgAndUserList(orgId).stream().map(user -> user.getId()).collect(Collectors.toList());
        }
        attendanceAnalysisData.setOrgDataMap(list);
    }

    private Map<String,Object> orgData(AttendanceAnalysisParam attendanceAnalysisParam,List<String> userIds,String orgName,VactionTimeAnalysis vactionTimeAnalysis){
        QueryWrapper<HrAttendanceAnalStat> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("IFNULL(sum(WORK_HOURS),0) as totalWorkHours,IFNULL(sum(HOLIDAY_FLAG),0) as leaveNum,IFNULL(sum(WORKOT_FLAG),0) as workOtNum,IFNULL(sum(LATE_FLAG),0) as lateNum");

        Map<String,Object> map = new HashMap<>();
        attendanceAnalysisParam.getType().forEach(type -> {
            switch(type){
                case 1://应到人数  shouldAtNum
                    Long shouldAtNum = sysUserService.count(new LambdaQueryWrapper<SysUser>().in(SysUser::getId,userIds).isNotNull(SysUser::getAttendanceGroupId).ne(SysUser::getAttendanceGroupId,""));
                    map.put("orgName",orgName);
                    map.put(type.toString(),shouldAtNum);
                    break;
                case 2: //打卡人数 attendanceNum
                    Long attendanceNum = hrAttendanceAnalStatService.count(new LambdaQueryWrapper<HrAttendanceAnalStat>().in(HrAttendanceAnalStat::getUserId,userIds)
                            .between(HrAttendanceAnalStat::getCheckTime,vactionTimeAnalysis.getFirstBeginTime(),vactionTimeAnalysis.getFirstEndTime()));
                    map.put("orgName",orgName);
                    map.put(type.toString(),attendanceNum);
                    break;
                case 3: //出勤率
                    attendanceNum = hrAttendanceAnalStatService.count(new LambdaQueryWrapper<HrAttendanceAnalStat>().in(HrAttendanceAnalStat::getUserId,userIds)
                            .between(HrAttendanceAnalStat::getCheckTime,vactionTimeAnalysis.getFirstBeginTime(),vactionTimeAnalysis.getFirstEndTime()));
                    shouldAtNum = sysUserService.count(new LambdaQueryWrapper<SysUser>().in(SysUser::getId,userIds).isNotNull(SysUser::getAttendanceGroupId).ne(SysUser::getAttendanceGroupId,""));
                    Double atdRate = shouldAtNum == 0 ? 0.0 : attendanceNum/shouldAtNum;
                    map.put("orgName",orgName);
                    map.put(type.toString(),decimalFormat(atdRate));
                    break;
                case 4: //平均工时
                    Map<String, Object> workMap = hrAttendanceAnalStatService.getMap(queryWrapper.lambda()
                            .between(HrAttendanceAnalStat::getCheckTime,vactionTimeAnalysis.getFirstBeginTime(),vactionTimeAnalysis.getFirstEndTime()));
                    attendanceNum = hrAttendanceAnalStatService.count(new LambdaQueryWrapper<HrAttendanceAnalStat>().in(HrAttendanceAnalStat::getUserId,userIds)
                            .between(HrAttendanceAnalStat::getCheckTime,vactionTimeAnalysis.getFirstBeginTime(),vactionTimeAnalysis.getFirstEndTime()));
                    Double totalWorkHours = map.get("totalWorkHours") == null ? 0.0 : Double.parseDouble(map.get("totalWorkHours").toString());
                    double averageWkHour = attendanceNum == 0 ? 0.0 : totalWorkHours.doubleValue()/attendanceNum;
                    map.put("orgName",orgName);
                    map.put(type.toString(),decimalFormat(averageWkHour));
                    break;
                case 5: //请假人数
                    workMap = hrAttendanceAnalStatService.getMap(queryWrapper.lambda()
                            .between(HrAttendanceAnalStat::getCheckTime,vactionTimeAnalysis.getFirstBeginTime(),vactionTimeAnalysis.getFirstEndTime()));
                    Integer leaveNum =  workMap.get("leaveNum") == null ? 0 : Integer.parseInt(workMap.get("leaveNum").toString());
                    map.put("orgName",orgName);
                    map.put(type.toString(),leaveNum.toString());
                    break;
                case 6: //加班人数
                    workMap = hrAttendanceAnalStatService.getMap(queryWrapper.lambda()
                            .between(HrAttendanceAnalStat::getCheckTime,vactionTimeAnalysis.getFirstBeginTime(),vactionTimeAnalysis.getFirstEndTime()));
                    BigDecimal workOtNum = (BigDecimal) map.get("workOtNum") == null ? new BigDecimal(0) : (BigDecimal) map.get("workOtNum") ;
                    map.put("orgName",orgName);
                    map.put(type.toString(),workOtNum.toString());
                    break;
                case 7: //迟到人数
                    workMap = hrAttendanceAnalStatService.getMap(queryWrapper.lambda()
                            .between(HrAttendanceAnalStat::getCheckTime,vactionTimeAnalysis.getFirstBeginTime(),vactionTimeAnalysis.getFirstEndTime()));
                    BigDecimal lateNum = (BigDecimal) map.get("lateNum") == null ? new BigDecimal(0) : (BigDecimal) map.get("lateNum");
                    map.put("orgName",orgName);
                    map.put(type.toString(),lateNum.toString());
                    break;
                default:
                    break;
            }
        });
        return map;
    }

}
