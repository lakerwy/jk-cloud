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
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
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
import vip.xiaonuo.sys.modular.hr.userAttendance.entity.HrUserAttendance;
import vip.xiaonuo.sys.modular.hr.userAttendance.enums.HrUserAttendanceEnum;
import vip.xiaonuo.sys.modular.hr.userAttendance.param.ConfigCacheListParam;
import vip.xiaonuo.sys.modular.hr.userAttendance.param.HrUserAttendancePageParam;
import vip.xiaonuo.sys.modular.hr.userAttendance.service.HrUserAttendanceService;
import vip.xiaonuo.sys.modular.hr.userAttendance.vo.AttendanceConfigCacheEntity;
import vip.xiaonuo.sys.modular.hr.userAttendance.vo.AttendanceTimeSlot;
import vip.xiaonuo.sys.modular.hr.userAttendance.vo.UserAttendanceConfigEntity;
import vip.xiaonuo.sys.modular.hr.userAttendance.vo.UserAttendanceCountEntity;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.entity.HrUserAttendanceConfig;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.service.HrUserAttendanceConfigService;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfigGroup.entity.HrUserAttendanceConfigGroup;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfigGroup.service.HrUserAttendanceConfigGroupService;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.entity.HrUserAttendanceStat;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.vo.*;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.mapper.HrUserAttendanceStatMapper;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.param.HrUserAttendanceStatAddParam;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.param.HrUserAttendanceStatEditParam;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.param.HrUserAttendanceStatIdParam;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.param.HrUserAttendanceStatPageParam;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.service.HrUserAttendanceStatService;
import vip.xiaonuo.sys.modular.user.entity.SysUser;
import vip.xiaonuo.sys.modular.user.service.SysUserService;

import javax.annotation.Resource;
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
    public List<String> statPreviewDateList(AttendanceDayStatParam attendanceDayStatParam){
        Date beginTime = CommonTimeFormatUtil.strToDate(attendanceDayStatParam.getBeginTime(),DatePattern.NORM_DATETIME_PATTERN);
        Date endTime = CommonTimeFormatUtil.strToDate(attendanceDayStatParam.getEndTime(),DatePattern.NORM_DATETIME_PATTERN);
        List<String> dayList = CommonTimeFormatUtil.getPerStrDayList(beginTime,endTime);//时间处理
        return dayList.stream().map(day -> CommonTimeFormatUtil.dayAndSunday(day)).collect(Collectors.toList());
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
        /** 打卡记录只查询当前返回的人员*/
        List<HrUserAttendance> hrUserAttendanceList = hrUserAttendanceService.list(new LambdaQueryWrapper<HrUserAttendance>()
                .between(HrUserAttendance::getCheckTime,beginTime,endTime)
                .in(HrUserAttendance::getUserId,userList.getRecords().stream().map(user -> user.getId()).collect(Collectors.toList()))); // 查询该时间段内所有人打卡数据


        /**  开始统计 */
        userList.getRecords().forEach(user -> {
            AttendanceDayStatParam attendanceDayStat = new AttendanceDayStatParam();
            attendanceDayStat.setUserId(user.getId());
            attendanceDayStat.setUserName(user.getName());
            attendanceDayStat.setOrgId(user.getOrgId());
            List<Map<String,List<AttendanceDayStatData>>> dayCheckTimeMapList = new ArrayList<>();
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
            TreeMap<String,List<AttendanceDayStatData>> statMap = new TreeMap<>();//key - day , value - List<AttendanceDayStatData>
            dayList.forEach(time ->{
                List<AttendanceDayStatData> attendanceDayStatDataList = statTempMap.get(time) == null ? new ArrayList<>() : statTempMap.get(time);
                if(CollectionUtil.isEmpty(attendanceDayStatDataList)){
                    attendanceDayStatDataList.add(new AttendanceDayStatData(null,-1,CommonTimeFormatUtil.dayAndSunday(time)));
                }
                SimpleDateFormat sdf = new SimpleDateFormat("dd");
                statMap.put(sdf.format(CommonTimeFormatUtil.strToDate(time,DatePattern.NORM_DATE_PATTERN)),attendanceDayStatDataList);
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
        attendanceDayStatParamPage.setRecords(attendanceDayStatList);
        return attendanceDayStatParamPage;
    }
    @Override
    public List<AttendanceMonthStatParam> monthStatPreview(AttendanceMonthStatParam attendanceMonthStatParam){

        return new ArrayList<>();
    }
}
