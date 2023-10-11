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
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.pojo.SaBaseLoginUser;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.common.util.CommonTimeFormatUtil;
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
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.service.HrUserAttendanceConfigService;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfigGroup.entity.HrUserAttendanceConfigGroup;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfigGroup.service.HrUserAttendanceConfigGroupService;
import vip.xiaonuo.sys.modular.hr.userAttendanceRelation.entity.HrUserAttendanceRelation;
import vip.xiaonuo.sys.modular.hr.userAttendanceRelation.service.HrUserAttendanceRelationService;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.entity.HrUserAttendanceStatEvent;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.service.HrUserAttendanceStatService;
import vip.xiaonuo.sys.modular.org.entity.SysOrg;
import vip.xiaonuo.sys.modular.org.service.SysOrgService;
import vip.xiaonuo.sys.modular.user.entity.SysUser;
import vip.xiaonuo.sys.modular.user.service.SysUserService;

import javax.annotation.Resource;
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

    private static final String HR_ATTENDANCE_CONFIG_DEFAULT_KEY = "HR_ATTENDANCE_CONFIG_DEFAULT_KEY";
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
    @Resource
    private ApplicationEventPublisher eventPublisher;
    @Resource
    private HrUserAttendanceStatService hrUserAttendanceStatService;
    @Resource
    private SysOrgService sysOrgService;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private HrUserAttendanceConfigGroupService hrUserAttendanceConfigGroupService;

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
        if(StringUtils.isEmpty(hrUserAttendanceAddParam.getUserId())){
            SaBaseLoginUser loginUser = StpLoginUserUtil.getLoginUser();
            hrUserAttendanceAddParam.setUserId(loginUser.getId());
        }
        Date dateNow = DateUtil.date();/** 当前打卡时间*/
        if(ObjectUtil.isEmpty(hrUserAttendanceAddParam.getCheckTime())){
            hrUserAttendanceAddParam.setCheckTime(dateNow);
        }
        HrUserAttendance hrUserAttendance = BeanUtil.toBean(hrUserAttendanceAddParam, HrUserAttendance.class);
        hrUserAttendance.setAbnormalFlag(-1);/** 初始化默认值*/

        /** 计算有效打卡时间区间*/
        List<AttendanceTimeSlot> attendanceTimeSlotList =  getAttendanceTimeSlotList(hrUserAttendanceAddParam.getUserId(),hrUserAttendance.getCheckTime());//getAttendanceConfigValueByKey(dateNow);

        /** 上下班休息标识；OnDuty：上班；OffDuty：下班；休息 REST */
        attendanceTimeSlotList.forEach(slot -> {
            if(hrUserAttendance.getCheckTime().after(slot.getBeginTime()) && hrUserAttendance.getCheckTime().before(slot.getEndTime())){
                hrUserAttendance.setAbnormalFlag(1);/** 打卡正常*/
                hrUserAttendance.setCheckType(slot.getDutyType());
                hrUserAttendance.setConfigOrderNumber(slot.getOrderNumber());
            }
            if(hrUserAttendance.getCheckTime().after(slot.getWorkBeginTime()) && hrUserAttendance.getCheckTime().before(slot.getWorkEndTime())){
                hrUserAttendance.setAbnormalFlag(2);/** 打卡异常*/
                hrUserAttendance.setCheckType(slot.getDutyType());
                hrUserAttendance.setConfigOrderNumber(slot.getOrderNumber());
            }
        });
        this.save(hrUserAttendance);

        /** 异步处理打卡同步考勤统计表 */
        HrUserAttendanceStatEvent attendanceStatEvent = BeanUtil.toBean(hrUserAttendance, HrUserAttendanceStatEvent.class);
        eventPublisher.publishEvent(attendanceStatEvent);
        return  HrUserAttendanceEnum.ATTENDANCE_NORMAL.getValue();
    }

    private  List<AttendanceTimeSlot> getAttendanceTimeSlotList(String userId,Date dateNow){
        List<HrUserAttendanceConfig> configList = new ArrayList<>();
        String cache = String.valueOf(commonCacheOperator.get(HR_ATTENDANCE_CONFIG_KEY));//取考勤缓存数据
        if(StringUtils.isNotEmpty(cache)){
            List<AttendanceConfigCacheEntity> configCacheEntityList = JSONArray.parseArray(cache, AttendanceConfigCacheEntity.class);
            for(AttendanceConfigCacheEntity entity : configCacheEntityList){
                //todo string contains 有问题
                if(entity.getUserIds().contains(userId)){
                    List<UserAttendanceConfigEntity> userAttendanceConfigEntityList = entity.getUserAttendanceConfigEntityList();
                    configList = userAttendanceConfigEntityList.stream().map(config -> BeanUtil.toBean(config, HrUserAttendanceConfig.class)).collect(Collectors.toList());
                    return getAttendanceTimeSlotData(configList,dateNow);
                }
            }
        }
        //取默认考勤配置
        List<HrUserAttendanceConfigGroup>  hrUserAttendanceConfigGroupList = hrUserAttendanceConfigGroupService.list(new QueryWrapper<HrUserAttendanceConfigGroup>()
                .lambda().eq(HrUserAttendanceConfigGroup::getIsDefaultConfig,"0"));
        if(ObjectUtil.isEmpty(hrUserAttendanceConfigGroupList)){
            return new ArrayList<>();
        }
        configList = hrUserAttendanceConfigService.list(new QueryWrapper<HrUserAttendanceConfig>().lambda()
                .eq(HrUserAttendanceConfig::getAttendanceTypeId,hrUserAttendanceConfigGroupList.get(0).getId()));
        return getAttendanceTimeSlotData(configList,dateNow);
    }

    private List<AttendanceTimeSlot> getAttendanceTimeSlotData(List<HrUserAttendanceConfig> configList,Date dateNow){
        List<AttendanceTimeSlot>  attendanceTimeSlotList = new ArrayList<>();
        configList = configList.stream().sorted(Comparator.comparing(HrUserAttendanceConfig::getOrderNumber)).collect(Collectors.toList());
        for(HrUserAttendanceConfig attendanceConfig : configList){
            AttendanceTimeSlot attendanceTimeSlot = new AttendanceTimeSlot();
            attendanceTimeSlot.setDutyType(attendanceConfig.getDutyType());
            Date time = CommonTimeFormatUtil.yearAndHourConnect(CommonTimeFormatUtil.dateToStr(dateNow,DATE_FORMAT),attendanceConfig.getCheckTime().toString());
            attendanceTimeSlot.setBeginTime(CommonTimeFormatUtil.addOrUpMinute(time,Integer.parseInt(attendanceConfig.getBeginMinTime()),2));
            attendanceTimeSlot.setEndTime(CommonTimeFormatUtil.addOrUpMinute(time,Integer.parseInt(attendanceConfig.getEndMinTime()),1));
            attendanceTimeSlot.setCompareTime(time);
            attendanceTimeSlot.setWorkHours(StringUtils.isEmpty(attendanceConfig.getWorkHours()) ? "0" : attendanceConfig.getWorkHours());
            attendanceTimeSlot.setOrderNumber(attendanceConfig.getOrderNumber());
            attendanceTimeSlot.setWorkEndTime(time);//初始化默认值
            attendanceTimeSlot.setWorkBeginTime(time);//初始化默认值
            //获取工作区间
            double minute =  Double.parseDouble(attendanceConfig.getWorkHours())*60;
            if(attendanceConfig.getDutyType().equals(HrUserAttendanceEnum.ATTENDANCE_ON_DUTY.getValue()) && minute > 0){
                attendanceTimeSlot.setWorkBeginTime(time);
                attendanceTimeSlot.setWorkEndTime(CommonTimeFormatUtil.addOrUpMinute(time, (int) minute,1));
            }
            if(attendanceConfig.getDutyType().equals(HrUserAttendanceEnum.ATTENDANCE_OFF_DUTY.getValue()) && minute > 0){
                attendanceTimeSlot.setWorkBeginTime(CommonTimeFormatUtil.addOrUpMinute(time, (int) minute,2));
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

    public  void attendanceConfigCache() {
        List<HrUserAttendanceConfigGroup> hrUserAttendanceConfigGroupList = hrUserAttendanceConfigGroupService.list();//考勤组全表查询
        List<HrUserAttendanceConfig> hrUserAttendanceConfigList = hrUserAttendanceConfigService.list();//考勤配置全表查询

        List<AttendanceConfigCacheEntity> attendanceConfigCacheEntityList = hrUserAttendanceConfigGroupList.stream().map(group -> {
            AttendanceConfigCacheEntity attendanceConfigCacheEntity = new AttendanceConfigCacheEntity();
            attendanceConfigCacheEntity.setAttendanceTypeId(group.getId());
            List<UserAttendanceConfigEntity> userAttendanceConfigEntityList = hrUserAttendanceConfigList.stream().map(config -> {
                UserAttendanceConfigEntity userAttendanceConfigEntity = new UserAttendanceConfigEntity();
                if(config.getAttendanceTypeId().equals(group.getId())){
                    return BeanUtil.toBean(config, UserAttendanceConfigEntity.class);
                }
                return userAttendanceConfigEntity;
            }).collect(Collectors.toList());
            attendanceConfigCacheEntity.setUserAttendanceConfigEntityList(userAttendanceConfigEntityList);
            return attendanceConfigCacheEntity;
        }).collect(Collectors.toList());

        List<HrUserAttendanceRelation> hrUserAttendanceRelationList = hrUserAttendanceRelationService.list();//考勤关联全表查询
        Map<String,String> mapRelation = new HashMap<>();
        //todo 用字符串拼接存会有问题----
        StringBuffer relationStr = new StringBuffer();
        hrUserAttendanceRelationList.forEach(relation -> {
            String key = relation.getAttendanceTypeId();
            relationStr.append(key).append(",");
            if(relation.getEmployType().equals(HrUserAttendanceEnum.ORG_TYPE.getValue())){ //部门
                mapRelation.put(key,getOrgAndUserId(relation.getEmployId()));
            }
            if(relation.getEmployType().equals(HrUserAttendanceEnum.USER_TYPE.getValue())){ //人员
                mapRelation.put(key,relation.getEmployId());
            }
        });
        attendanceConfigCacheEntityList.forEach(cache -> cache.setUserIds(mapRelation.get(cache.getAttendanceTypeId())));
        commonCacheOperator.put(HR_ATTENDANCE_CONFIG_KEY,JSON.toJSONString(attendanceConfigCacheEntityList)); //缓存
    }

    @Override
    public List<UserAttendanceCountEntity> attendanceMonthCount(HrUserAttendancePageParam hrUserAttendancePageParam) {
        SaBaseLoginUser loginUser = StpLoginUserUtil.getLoginUser();

        if(StringUtils.isEmpty(hrUserAttendancePageParam.getStartTime()) || StringUtils.isEmpty(hrUserAttendancePageParam.getEndTime())){
            return new ArrayList<>();
        }
        /** 获取当月有效工作日时间列表 */
        /*Page<HrUserAttendance> page = page(hrUserAttendancePageParam);
        List<HrUserAttendance> hrUserAttendanceList = page.getRecords();*/

//        LambdaQueryWrapper<HrUserAttendanceStat> lambdaQueryWrapperStat = new LambdaQueryWrapper();
//        lambdaQueryWrapperStat.eq(HrUserAttendanceStat::getUserId,loginUser.getId())
//                .between(HrUserAttendanceStat::getCheckTime,hrUserAttendancePageParam.getStartTime(),hrUserAttendancePageParam.getEndTime());
//        List<HrUserAttendanceStat> hrUserAttendanceStatList = hrUserAttendanceStatService.list(lambdaQueryWrapperStat);
        List<HrUserAttendance> hrUserAttendanceList = this.list(new LambdaQueryWrapper<HrUserAttendance>().eq(HrUserAttendance::getUserId,loginUser.getId())
                .between(HrUserAttendance::getCheckTime,hrUserAttendancePageParam.getStartTime(),hrUserAttendancePageParam.getEndTime()));

        /**  list循环一次，获取-1  循环二次获取 1*/
        Map<String,UserAttendanceCountEntity> map = new HashMap<>();
        for(HrUserAttendance hrUserAttendance : hrUserAttendanceList){
            String day = CommonTimeFormatUtil.dateToStr(hrUserAttendance.getCheckTime(),DATE_FORMAT);
            map.put(day,new UserAttendanceCountEntity("",hrUserAttendance.getAbnormalFlag() != null ? "0" : "1",day));
        }

        List<HrUserAttendance> userAttendances = hrUserAttendanceList.stream().filter(attendance ->
                attendance.getAbnormalFlag() != null && attendance.getAbnormalFlag() == 2).collect(Collectors.toList());/**  筛选打卡异常的数据*/

        /** 打卡异常的数据 */
        userAttendances.forEach(attendance ->  {
            String day = CommonTimeFormatUtil.dateToStr(attendance.getCheckTime(),DATE_FORMAT);
            if(map.get(day) != null){
                map.get(day).setDayAttendanceStatus("1");
            }
        });

        return map.keySet().stream().map(map::get).collect(Collectors.toList());
    }

    @Override
    public Map<String,List<HrUserAttendance>> attendanceDayInfo(HrUserAttendancePageParam hrUserAttendancePageParam){
        SaBaseLoginUser loginUser = StpLoginUserUtil.getLoginUser();
        hrUserAttendancePageParam.setUserId(loginUser.getId());
        if(StringUtils.isEmpty(hrUserAttendancePageParam.getStartTime()) || StringUtils.isEmpty(hrUserAttendancePageParam.getEndTime())){
            return new HashMap<>();
        }
        QueryWrapper<HrUserAttendance> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().between(HrUserAttendance::getCheckTime,hrUserAttendancePageParam.getStartTime(),hrUserAttendancePageParam.getEndTime())
                .eq(HrUserAttendance::getUserId,loginUser.getId());

        List<HrUserAttendance> hrUserAttendanceList = this.list(queryWrapper);
        Map<String,List<HrUserAttendance>> map = new HashMap<>();
        if(hrUserAttendanceList.isEmpty()){
            map.put("0",new ArrayList<>());
            map.put("1",new ArrayList<>());
            map.put("2",new ArrayList<>());
            return map;
        }
        /** 暂时取固定班次  -- 上班 OnDuty，休息 REST，下班 OffDuty   返回数据：0 有效且唯一，1 无效  ，2 早退或迟到 */
        List<HrUserAttendance> onDutyAttendances = new ArrayList<>();
        List<HrUserAttendance> restAttendances = new ArrayList<>();
        List<HrUserAttendance> offDutyAttendances = new ArrayList<>();
        //减少遍历的次数
        for (HrUserAttendance hrUserAttendance : hrUserAttendanceList) {
            String checkType = hrUserAttendance.getCheckType();
            if(HrUserAttendanceEnum.ATTENDANCE_ON_DUTY.getValue().equals(checkType)){
                onDutyAttendances.add(hrUserAttendance);
            }else if(HrUserAttendanceEnum.ATTENDANCE_REST.getValue().equals(checkType)){
                restAttendances.add(hrUserAttendance);
            }else {
                offDutyAttendances.add(hrUserAttendance);
            }
        }
        onDutyAttendances.sort(Comparator.comparing(HrUserAttendance::getCheckTime));
        if(CollUtil.isNotEmpty(onDutyAttendances) && onDutyAttendances.get(0).getAbnormalFlag() == 1){
            onDutyAttendances.get(0).setAbnormalFlag(0);
        }
        map.put("0",onDutyAttendances);

        if(CollUtil.isNotEmpty(restAttendances)){
            restAttendances.get(restAttendances.size() -1).setAbnormalFlag(0);
        }
        map.put("1",restAttendances);

        if(CollUtil.isNotEmpty(offDutyAttendances) && offDutyAttendances.get(offDutyAttendances.size() - 1).getAbnormalFlag() == 1){
            offDutyAttendances.get(offDutyAttendances.size() -1).setAbnormalFlag(0);
        }
        map.put("2",offDutyAttendances);

        return  map;


//        List<HrUserAttendance>  hrUserAttendanceOnDutyList = hrUserAttendanceList.stream()
//                .filter(attendance -> attendance.getCheckType().equals(HrUserAttendanceEnum.ATTENDANCE_ON_DUTY.getValue()))
//                .sorted(Comparator.comparing(HrUserAttendance::getCheckTime)).collect(Collectors.toList());
//        if(!hrUserAttendanceOnDutyList.isEmpty() && hrUserAttendanceOnDutyList.get(0).getAbnormalFlag() == 1){
//            hrUserAttendanceOnDutyList.get(0).setAbnormalFlag(0);
//        }
//        map.put("0",hrUserAttendanceOnDutyList);
//
//        List<HrUserAttendance>  hrUserAttendanceRestList = hrUserAttendanceList.stream()
//                .filter(attendance -> attendance.getCheckType().equals(HrUserAttendanceEnum.ATTENDANCE_REST.getValue()))
//                .sorted(Comparator.comparing(HrUserAttendance::getCheckTime)).collect(Collectors.toList());
//        if(!hrUserAttendanceRestList.isEmpty()){
//            hrUserAttendanceRestList.get(hrUserAttendanceRestList.size() -1).setAbnormalFlag(0);
//        }
//        map.put("1",hrUserAttendanceRestList);
//
//        List<HrUserAttendance>  hrUserAttendanceOffDutyList = hrUserAttendanceList.stream()
//                .filter(attendance -> attendance.getCheckType().equals(HrUserAttendanceEnum.ATTENDANCE_OFF_DUTY.getValue()))
//                .sorted(Comparator.comparing(HrUserAttendance::getCheckTime)).collect(Collectors.toList());
//        if(!hrUserAttendanceOffDutyList.isEmpty() && hrUserAttendanceOffDutyList.get(hrUserAttendanceOffDutyList.size() - 1).getAbnormalFlag() == 1){
//            hrUserAttendanceOffDutyList.get(hrUserAttendanceOffDutyList.size()-1).setAbnormalFlag(0);
//        }
//        map.put("2",hrUserAttendanceOffDutyList);
//        return map;
    }


    /**
     * 申请补卡通过之后的流程
     *
     * */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void resubmitAttendance(String processInstanceId, String applyType){
        HrUserApplyAttendance hrUserApplyAttendance = hrUserApplyAttendanceService.getOne(new QueryWrapper<HrUserApplyAttendance>().lambda().eq(HrUserApplyAttendance::getProcessInstanceId,processInstanceId));
        HrUserAttendanceAddParam hrUserAttendanceAddParam = new HrUserAttendanceAddParam();
        hrUserAttendanceAddParam.setUserId(hrUserApplyAttendance.getApplyUserId());
        hrUserAttendanceAddParam.setCheckTime(CommonTimeFormatUtil.strToDate(hrUserApplyAttendance.getApplyAttendanceTime()));
        hrUserAttendanceAddParam.setIsResubmitAttendanceFlag(1);
        hrUserAttendanceAddParam.setApplyResubmitAttendanceId(hrUserApplyAttendance.getId());
        attendance(hrUserAttendanceAddParam);
    }

    /**
     * 打卡记录同步考勤统计表
     * @author ljy
     * @date  2023/09/26 14:24
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void attendanceStatJobHandle(){
        //System.out.println("接口调用成功！");
    }

    /**
     * 递归该部门下面有多少人员
     * author ljy
     * @date  2023/10/09 16:24
     * */
    public String getOrgAndUserId(String orgId){
        StringBuffer userIds = new StringBuffer();
        if(StringUtils.isEmpty(orgId)){
            return userIds.toString();
        }
        List<SysOrg> bizOrgList = sysOrgService.list();//全表查询
        List<SysOrg> bizOrgListNew = recursionOrg(new ArrayList<>(),orgId,bizOrgList);
        List<String> orgList = new ArrayList<>();
        bizOrgListNew.forEach(org -> orgList.add(org.getId()));
        if(ObjectUtil.isEmpty(bizOrgListNew)){
            return userIds.toString();
        }
        QueryWrapper<SysUser> wrapper = new QueryWrapper<SysUser>().select("id");
        wrapper.lambda().in(SysUser::getOrgId,orgList);
        List<SysUser> userList = sysUserService.list(wrapper);
        if(ObjectUtil.isEmpty(userList)){
            return userIds.toString();
        }
        userList.forEach(user -> userIds.append(user.getId()).append(","));
        return userIds.toString();
    }

    private List<SysOrg> recursionOrg(List<SysOrg> bizOrgListNew,String orgId,List<SysOrg> bizOrgList){
        bizOrgList.forEach(org -> {if(org.getParentId().equals(orgId)){
            bizOrgListNew.add(org);
            recursionOrg(bizOrgListNew,org.getId(),bizOrgList);
        }});
        return bizOrgListNew;
    }
}
