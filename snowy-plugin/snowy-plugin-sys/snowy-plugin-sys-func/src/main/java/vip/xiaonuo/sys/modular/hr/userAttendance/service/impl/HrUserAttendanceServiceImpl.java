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
package vip.xiaonuo.sys.modular.hr.userAttendance.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.pojo.SaBaseLoginUser;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.common.util.CommonTimeFormatUtil;
import vip.xiaonuo.sys.modular.hr.attendanceMachine.entity.HrAttendanceMachine;
import vip.xiaonuo.sys.modular.hr.attendanceMachine.enums.HrAttendanceMachineEnum;
import vip.xiaonuo.sys.modular.hr.userApplyAttendance.entity.HrUserApplyAttendance;
import vip.xiaonuo.sys.modular.hr.userApplyAttendance.service.HrUserApplyAttendanceService;
import vip.xiaonuo.sys.modular.hr.userAttendance.entity.*;
import vip.xiaonuo.sys.modular.hr.userAttendance.enums.HrUserAttendanceEnum;
import vip.xiaonuo.sys.modular.hr.userAttendance.mapper.HrUserAttendanceMapper;
import vip.xiaonuo.sys.modular.hr.userAttendance.param.HrUserAttendanceAddParam;
import vip.xiaonuo.sys.modular.hr.userAttendance.param.HrUserAttendanceEditParam;
import vip.xiaonuo.sys.modular.hr.userAttendance.param.HrUserAttendanceIdParam;
import vip.xiaonuo.sys.modular.hr.userAttendance.param.HrUserAttendancePageParam;
import vip.xiaonuo.sys.modular.hr.userAttendance.service.HrUserAttendanceService;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.entity.HrUserAttendanceConfig;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.param.HrUserAttendanceConfigPageParam;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.service.HrUserAttendanceConfigService;
import vip.xiaonuo.sys.modular.hr.userAttendanceRelation.entity.HrUserAttendanceRelation;
import vip.xiaonuo.sys.modular.hr.userAttendanceRelation.service.HrUserAttendanceRelationService;

import javax.annotation.Resource;
import java.sql.Time;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 打卡记录表Service接口实现类
 *
 * @author ljy
 * @date  2023/09/07 14:24
 **/
@Service
public class HrUserAttendanceServiceImpl extends ServiceImpl<HrUserAttendanceMapper, HrUserAttendance> implements HrUserAttendanceService {

    private static final String HR_ATTENDANCE_CONFIG_KEY = "HR_ATTENDANCE_CONFIG_KEY";
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @Autowired
    private HrUserAttendanceConfigService hrUserAttendanceConfigService;
    @Autowired
    private HrUserAttendanceRelationService hrUserAttendanceRelationService;
    @Resource
    private CommonCacheOperator commonCacheOperator;
    @Resource
    private HrUserApplyAttendanceService hrUserApplyAttendanceService;

    @Override
    public Page<HrUserAttendance> page(HrUserAttendancePageParam hrUserAttendancePageParam) {
        QueryWrapper<HrUserAttendance> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(hrUserAttendancePageParam.getUserId())) {
            queryWrapper.lambda().eq(HrUserAttendance::getUserId, hrUserAttendancePageParam.getUserId());
        }
        //时间
        if(ObjectUtil.isNotEmpty(hrUserAttendancePageParam.getStartTime()) && ObjectUtil.isNotEmpty(hrUserAttendancePageParam.getEndTime())) {
            queryWrapper.lambda().between(HrUserAttendance::getCheckTime, hrUserAttendancePageParam.getStartTime(), hrUserAttendancePageParam.getEndTime());
        }
        if(ObjectUtil.isNotEmpty(hrUserAttendancePageParam.getMachineId())) {
            queryWrapper.lambda().eq(HrUserAttendance::getMachineCode, hrUserAttendancePageParam.getMachineId());
        }
        if(ObjectUtil.isNotEmpty(hrUserAttendancePageParam.getCheckType())) {
            queryWrapper.lambda().eq(HrUserAttendance::getCheckType, hrUserAttendancePageParam.getCheckType());
        }
        if(ObjectUtil.isNotEmpty(hrUserAttendancePageParam.getAbnormalFlag())) {
            queryWrapper.lambda().eq(HrUserAttendance::getAbnormalFlag, hrUserAttendancePageParam.getAbnormalFlag());
        }
        if(ObjectUtil.isAllNotEmpty(hrUserAttendancePageParam.getSortField(), hrUserAttendancePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(hrUserAttendancePageParam.getSortOrder());
            queryWrapper.orderBy(true, hrUserAttendancePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(hrUserAttendancePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(HrUserAttendance::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(HrUserAttendanceAddParam hrUserAttendanceAddParam) {
        HrUserAttendance hrUserAttendance = BeanUtil.toBean(hrUserAttendanceAddParam, HrUserAttendance.class);
        this.save(hrUserAttendance);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String attendance(HrUserAttendanceAddParam hrUserAttendanceAddParam){
        SaBaseLoginUser loginUser = StpLoginUserUtil.getLoginUser();
        hrUserAttendanceAddParam.setUserId(loginUser.getId());
        Date dateNow =DateUtil.date();/** 当前打卡时间*/
        if(ObjectUtil.isEmpty(hrUserAttendanceAddParam.getCheckTime())){
            hrUserAttendanceAddParam.setCheckTime(dateNow);
        }
        HrUserAttendance hrUserAttendance = BeanUtil.toBean(hrUserAttendanceAddParam, HrUserAttendance.class);
        hrUserAttendance.setAbnormalFlag(-1);/** 初始化默认值*/

        /** 计算有效打卡时间区间*/
        List<AttendanceTimeSlot> attendanceTimeSlotList =  getAttendanceTimeSlotList(new HrUserAttendanceConfigPageParam(),dateNow);//getAttendanceConfigValueByKey(dateNow);

        /** 上下班休息标识；OnDuty：上班；OffDuty：下班；休息 REST */
        attendanceTimeSlotList.stream().forEach(slot -> {
            if(dateNow.after(slot.getBeginTime()) && dateNow.before(slot.getEndTime())){
                hrUserAttendance.setAbnormalFlag(1);/** 打卡正常*/
                hrUserAttendance.setCheckType(slot.getDutyType());
                hrUserAttendance.setConfigOrderNumber(slot.getOrderNumber());
            }
            if(dateNow.after(slot.getWorkBeginTime()) && dateNow.before(slot.getWorkEndTime())){
                hrUserAttendance.setAbnormalFlag(2);/** 打卡正常*/
                hrUserAttendance.setCheckType(slot.getDutyType());
                hrUserAttendance.setConfigOrderNumber(slot.getOrderNumber());
            }
        });

        this.save(hrUserAttendance);
        return  HrUserAttendanceEnum.ATTENDANCE_NORMAL.getValue();
    }
    private  List<AttendanceTimeSlot> getAttendanceTimeSlotList(HrUserAttendanceConfigPageParam hrUserAttendanceConfigPageParam,Date dateNow){
        Page<HrUserAttendanceConfig> pageList = hrUserAttendanceConfigService.page(hrUserAttendanceConfigPageParam);//一般用list查询
        List<HrUserAttendanceConfig> configList = pageList.getRecords(); /**  该数据后续取缓存*/

        List<AttendanceTimeSlot>  attendanceTimeSlotList = new ArrayList<>();
        configList = configList.stream().sorted(Comparator.comparing(HrUserAttendanceConfig::getOrderNumber)).collect(Collectors.toList());
        for(HrUserAttendanceConfig attendanceConfig : configList){
            AttendanceTimeSlot attendanceTimeSlot = new AttendanceTimeSlot();
            attendanceTimeSlot.setDutyType(attendanceConfig.getDutyType());
            Date time = CommonTimeFormatUtil.yearAndHourConnect(CommonTimeFormatUtil.dateToStr(dateNow,DATE_FORMAT),attendanceConfig.getCheckTime().toString());
            attendanceTimeSlot.setBeginTime(CommonTimeFormatUtil.addOrUpMinute(time,Integer.valueOf(attendanceConfig.getBeginMinTime()),2));
            attendanceTimeSlot.setEndTime(CommonTimeFormatUtil.addOrUpMinute(time,Integer.valueOf(attendanceConfig.getEndMinTime()),1));
            attendanceTimeSlot.setCompareTime(time);
            attendanceTimeSlot.setWorkHours(StringUtils.isEmpty(attendanceConfig.getWorkHours()) ? "0" : attendanceConfig.getWorkHours());
            attendanceTimeSlot.setOrderNumber(attendanceConfig.getOrderNumber());
            attendanceTimeSlot.setWorkEndTime(time);//初始化默认值
            attendanceTimeSlot.setWorkBeginTime(time);//初始化默认值
            //获取工作区间
            Double minute =  Double.valueOf(attendanceConfig.getWorkHours())*60;
            if(attendanceConfig.getDutyType().equals(HrUserAttendanceEnum.ATTENDANCE_ON_DUTY.getValue()) && minute > 0){
                attendanceTimeSlot.setWorkBeginTime(time);
                attendanceTimeSlot.setWorkEndTime(CommonTimeFormatUtil.addOrUpMinute(time,minute.intValue(),1));
            }
            if(attendanceConfig.getDutyType().equals(HrUserAttendanceEnum.ATTENDANCE_OFF_DUTY.getValue()) && minute > 0){
                attendanceTimeSlot.setWorkBeginTime(CommonTimeFormatUtil.addOrUpMinute(time,minute.intValue(),2));
                attendanceTimeSlot.setWorkEndTime(time);
            }
            attendanceTimeSlotList.add(attendanceTimeSlot);
        }
        return attendanceTimeSlotList;
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(HrUserAttendanceEditParam hrUserAttendanceEditParam) {
        HrUserAttendance hrUserAttendance = this.queryEntity(hrUserAttendanceEditParam.getId());
        BeanUtil.copyProperties(hrUserAttendanceEditParam, hrUserAttendance);
        this.updateById(hrUserAttendance);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<HrUserAttendanceIdParam> hrUserAttendanceIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(hrUserAttendanceIdParamList, HrUserAttendanceIdParam::getId));
    }

    @Override
    public HrUserAttendance detail(HrUserAttendanceIdParam hrUserAttendanceIdParam) {
        return this.queryEntity(hrUserAttendanceIdParam.getId());
    }

    @Override
    public HrUserAttendance queryEntity(String id) {
        HrUserAttendance hrUserAttendance = this.getById(id);
        if(ObjectUtil.isEmpty(hrUserAttendance)) {
            throw new CommonException("打卡记录表不存在，id值为：{}", id);
        }
        return hrUserAttendance;
    }

    @Override
    public List<AttendanceTimeSlot> getAttendanceConfigValueByKey(Date dateNow){
        SaBaseLoginUser loginUser = StpLoginUserUtil.getLoginUser();
        //HrUserAttendanceRelation hrUserAttendanceRelationServiceOne = hrUserAttendanceRelationService.getOne(new LambdaQueryWrapper<HrUserAttendanceRelation>().eq(HrUserAttendanceRelation::getUserId,loginUser.getId()));
        HrUserAttendanceRelation hrUserAttendanceRelationServiceOne = new HrUserAttendanceRelation();
        if(ObjectUtil.isEmpty(hrUserAttendanceRelationServiceOne) || StringUtils.isEmpty(hrUserAttendanceRelationServiceOne.getAttendanceType())){
            return new ArrayList<>();
        }
        /** 开始取缓存的数据 */
        if(ObjectUtil.isEmpty(commonCacheOperator.get(HR_ATTENDANCE_CONFIG_KEY))){
            Page<HrUserAttendanceConfig> pageList = hrUserAttendanceConfigService.page(new HrUserAttendanceConfigPageParam());
            List<HrUserAttendanceConfig> configList = pageList.getRecords();
            commonCacheOperator.put(HR_ATTENDANCE_CONFIG_KEY, configList.stream().map(config -> BeanUtil.toBean(config, UserAttendanceConfigEntity.class)).collect(Collectors.toList())
            );
        }
        List<LinkedHashMap> configEntityMapList= (List<LinkedHashMap>) commonCacheOperator.get(HR_ATTENDANCE_CONFIG_KEY);

        /** 计算考勤时间区间*/
        List<AttendanceTimeSlot>  attendanceTimeSlotList = new ArrayList<>();
        for(LinkedHashMap map : configEntityMapList){
            if(!hrUserAttendanceRelationServiceOne.getAttendanceType().equals(map.get(HrUserAttendanceEnum.ATTENDANCE_TYPE_NAME.getValue()))){
                continue;
            }
            AttendanceTimeSlot attendanceTimeSlot = new AttendanceTimeSlot();
            attendanceTimeSlot.setDutyType(map.get(HrUserAttendanceEnum.ATTENDANCE_DUTY_TYPE.getValue()).toString());
            Date time = CommonTimeFormatUtil.yearAndHourConnect(CommonTimeFormatUtil.dateToStr(dateNow,DATE_FORMAT),map.get(HrUserAttendanceEnum.ATTENDANCE_CHECK_TIME.getValue()).toString());
            attendanceTimeSlot.setBeginTime(CommonTimeFormatUtil.addOrUpMinute(time,Integer.valueOf(map.get(HrUserAttendanceEnum.ATTENDANCE_BEGINMINTIME.getValue()).toString()),2));
            attendanceTimeSlot.setEndTime(CommonTimeFormatUtil.addOrUpMinute(time,Integer.valueOf(map.get(HrUserAttendanceEnum.ATTENDANCE_ENDMINTIME.getValue()).toString()),1));
            //attendanceTimeSlot.setWorkHours(StringUtils.isEmpty(attendanceConfig.getWorkHours()) ? "0" : attendanceConfig.getWorkHours());
            attendanceTimeSlotList.add(attendanceTimeSlot);
        }
        return attendanceTimeSlotList;
    }

    @Override
    public List<UserAttendanceCountEntity> attendanceMonthCount(HrUserAttendancePageParam hrUserAttendancePageParam) {
        SaBaseLoginUser loginUser = StpLoginUserUtil.getLoginUser();

        if(StringUtils.isEmpty(hrUserAttendancePageParam.getStartTime()) || StringUtils.isEmpty(hrUserAttendancePageParam.getEndTime())){
            return new ArrayList<>();
        }
        /** 获取当月有效工作日时间列表 */
        Page<HrUserAttendance> page = page(hrUserAttendancePageParam);
        List<HrUserAttendance> hrUserAttendanceList = page.getRecords();

        LambdaQueryWrapper<HrUserAttendance> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(HrUserAttendance::getUserId, hrUserAttendancePageParam.getUserId())
                .between(HrUserAttendance::getCheckTime,hrUserAttendancePageParam.getStartTime(),hrUserAttendancePageParam.getEndTime())
                .eq(HrUserAttendance::getUserId,loginUser.getId());
        List<HrUserAttendance> hrUserAttendanceListCount = this.list(lambdaQueryWrapper);

        /**  list循环一次，获取-1  循环二次获取 1*/
        Map<String,UserAttendanceCountEntity> map = new HashMap<>();
        for(HrUserAttendance hrUserAttendance : hrUserAttendanceList){
            String day = CommonTimeFormatUtil.dateToStr(hrUserAttendance.getCheckTime(),DATE_FORMAT);
            map.put(day,new UserAttendanceCountEntity("",hrUserAttendance.getAbnormalFlag() != null ? "0" : "1",day));
        }

        List<HrUserAttendance> userAttendances = hrUserAttendanceList.stream().filter(attendance ->
                attendance.getAbnormalFlag() != null && attendance.getAbnormalFlag() == 2).collect(Collectors.toList());/**  筛选打卡异常的数据*/

        /** 打卡异常的数据 */
        userAttendances.stream().forEach(attendance ->  {
            String day = CommonTimeFormatUtil.dateToStr(attendance.getCheckTime(),DATE_FORMAT);
            if(map.get(day) != null){
                map.get(day).setDayAttendanceStatus("1");
            }
        });

        List<UserAttendanceCountEntity> userAttendanceCountEntities  = map.keySet().stream().map(key -> map.get(key)).collect(Collectors.toList());
        return userAttendanceCountEntities;
    }

    @Override
    public Map<String,List<HrUserAttendance>> attendanceDayInfo(HrUserAttendancePageParam hrUserAttendancePageParam){
        SaBaseLoginUser loginUser = StpLoginUserUtil.getLoginUser();
        hrUserAttendancePageParam.setUserId(loginUser.getId());
        if(StringUtils.isEmpty(hrUserAttendancePageParam.getStartTime()) || StringUtils.isEmpty(hrUserAttendancePageParam.getEndTime())){
            return new HashMap<>();
        }
        Page<HrUserAttendance> page = page(hrUserAttendancePageParam);
        List<HrUserAttendance> hrUserAttendanceList = page.getRecords();
        Map<String,List<HrUserAttendance>> map = new HashMap<>();
        if(hrUserAttendanceList.size() == 0){
            map.put("0",new ArrayList<>());
            map.put("1",new ArrayList<>());
            map.put("2",new ArrayList<>());
            return map;
        }
        /** 暂时取固定班次  -- 上班 OnDuty，休息 REST，下班 OffDuty   返回数据：0 有效且唯一，1 无效  ，2 早退或迟到 */
        List<HrUserAttendance>  hrUserAttendanceOnDutyList = hrUserAttendanceList.stream()
                .filter(attendance -> attendance.getCheckType().equals(HrUserAttendanceEnum.ATTENDANCE_ON_DUTY.getValue()))
                .sorted(Comparator.comparing(HrUserAttendance::getCheckTime)).collect(Collectors.toList());
        if(hrUserAttendanceOnDutyList.size() > 0 && hrUserAttendanceOnDutyList.get(0).getAbnormalFlag() == 1){
            hrUserAttendanceOnDutyList.get(0).setAbnormalFlag(0);
        }
        map.put("0",hrUserAttendanceOnDutyList);

        List<HrUserAttendance>  hrUserAttendanceRestList = hrUserAttendanceList.stream()
                .filter(attendance -> attendance.getCheckType().equals(HrUserAttendanceEnum.ATTENDANCE_REST.getValue()))
                .sorted(Comparator.comparing(HrUserAttendance::getCheckTime)).collect(Collectors.toList());
        if(hrUserAttendanceRestList.size() > 0 ){
            hrUserAttendanceRestList.get(hrUserAttendanceRestList.size() -1).setAbnormalFlag(0);
        }
        map.put("1",hrUserAttendanceRestList);

        List<HrUserAttendance>  hrUserAttendanceOffDutyList = hrUserAttendanceList.stream()
                .filter(attendance -> attendance.getCheckType().equals(HrUserAttendanceEnum.ATTENDANCE_OFF_DUTY.getValue()))
                .sorted(Comparator.comparing(HrUserAttendance::getCheckTime)).collect(Collectors.toList());
        if(hrUserAttendanceOffDutyList.size() > 0 && hrUserAttendanceOffDutyList.get(hrUserAttendanceOffDutyList.size() - 1).getAbnormalFlag() == 1){
            hrUserAttendanceOffDutyList.get(hrUserAttendanceOffDutyList.size()-1).setAbnormalFlag(0);
        }
        map.put("2",hrUserAttendanceOffDutyList);
        return map;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void resubmitAttendance(String processInstanceId, String applyType){
        HrUserApplyAttendance hrUserApplyAttendance = hrUserApplyAttendanceService.getOne(new QueryWrapper<HrUserApplyAttendance>().lambda().eq(HrUserApplyAttendance::getProcessInstanceId,processInstanceId));
        HrUserAttendance hrUserAttendance = new HrUserAttendance();
        hrUserAttendance.setAbnormalFlag(1);
        hrUserAttendance.setCheckType("OnDuty");
        hrUserAttendance.setCheckTime(new Date());
        hrUserAttendance.setIsResubmitAttendanceFlag(1);
        hrUserAttendance.setApplyResubmitAttendanceId(hrUserApplyAttendance.getId());
        this.save(hrUserAttendance);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void attendanceStatJobHandle(){
        //System.out.println("接口调用成功！");
    }
}
