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
import vip.xiaonuo.sys.modular.hr.userAttendance.param.*;
import vip.xiaonuo.sys.modular.hr.userAttendance.service.HrUserAttendanceService;
import vip.xiaonuo.sys.modular.hr.userAttendance.vo.AttendanceConfigCacheEntity;
import vip.xiaonuo.sys.modular.hr.userAttendance.vo.AttendanceTimeSlot;
import vip.xiaonuo.sys.modular.hr.userAttendance.vo.UserAttendanceConfigEntity;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.entity.HrUserAttendanceConfig;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.service.HrUserAttendanceConfigService;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfigGroup.entity.HrUserAttendanceConfigGroup;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfigGroup.service.HrUserAttendanceConfigGroupService;
import vip.xiaonuo.sys.modular.hr.userAttendanceRelation.entity.HrUserAttendanceRelation;
import vip.xiaonuo.sys.modular.hr.userAttendanceRelation.service.HrUserAttendanceRelationService;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.entity.HrUserAttendanceStat;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.vo.HrUserAttendanceStatEvent;
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

    private static final String HR_ATTENDANCE_CONFIG_DEFAULT_KEY = "HR_ATTENDANCE_CONFIG_DEFAULT_KEY";  //默认配置
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
        hrUserAttendanceAddParam.setCreateUser(hrUserAttendanceAddParam.getUserId());
        Date dateNow = DateUtil.date();/** 当前打卡时间*/
        if(ObjectUtil.isEmpty(hrUserAttendanceAddParam.getCheckTime())){
            hrUserAttendanceAddParam.setCheckTime(dateNow);
        }
        HrUserAttendance hrUserAttendance = BeanUtil.toBean(hrUserAttendanceAddParam, HrUserAttendance.class);
        hrUserAttendance.setAbnormalFlag(-1);/** 初始化默认值*/

        /** 计算有效打卡时间区间*/
        List<AttendanceTimeSlot> attendanceTimeSlotList =  getAttendanceTimeSlotList(hrUserAttendanceAddParam.getUserId(),hrUserAttendance.getCheckTime());
        /**
         * 上下班休息标识；OnDuty：上班；OffDuty：下班；休息 REST
         * 根据区间判断，在打卡时间区间里，打卡正常
         * 在工作时间区间里，打卡异常
         * 根据区间属性：打卡类型-dutyType，判断是属于迟到还是早退
         * */
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
    @Override
    public List<HrUserAttendanceConfig> userConfigCache(ConfigCacheListParam configCacheListParam){
        String  userId = ObjectUtil.isEmpty(configCacheListParam.getUserId()) ? StpLoginUserUtil.getLoginUser().getId() : configCacheListParam.getUserId();

        String cache = String.valueOf(commonCacheOperator.get(HR_ATTENDANCE_CONFIG_KEY));//取考勤缓存数据
        if(StringUtils.isNotEmpty(cache)){
            List<AttendanceConfigCacheEntity> configCacheEntityList = JSONArray.parseArray(cache, AttendanceConfigCacheEntity.class);
            for(AttendanceConfigCacheEntity entity : configCacheEntityList){
                List<String> userIds = Arrays.asList(entity.getUserIds().split(","));
                for(String userIdSTr : userIds){
                    if(userIdSTr.equals(userId)){
                        return hrUserAttendanceConfigService.list(new LambdaQueryWrapper<HrUserAttendanceConfig>()
                                .eq(HrUserAttendanceConfig::getAttendanceTypeId,configCacheEntityList.get(0).getAttendanceTypeId()));
                    }
                }
            }
        }
        //取默认配置
        String defaultConfig = String.valueOf(commonCacheOperator.get(HR_ATTENDANCE_CONFIG_DEFAULT_KEY));

        return JSONArray.parseArray(defaultConfig, HrUserAttendanceConfig.class);
    }

    @Override
    public   List<AttendanceTimeSlot> getAttendanceTimeSlotList(String userId,Date dateNow){
        List<HrUserAttendanceConfig> configList = new ArrayList<>();
        String cache = String.valueOf(commonCacheOperator.get(HR_ATTENDANCE_CONFIG_KEY));//取考勤缓存数据
        if(StringUtils.isNotEmpty(cache)){
            List<AttendanceConfigCacheEntity> configCacheEntityList = JSONArray.parseArray(cache, AttendanceConfigCacheEntity.class);
            for(AttendanceConfigCacheEntity entity : configCacheEntityList){
                //todo string contains 有问题     -- 已修改，2023-10-11 22:10:20
                List<String> userIds = Arrays.asList(entity.getUserIds().split(","));
                for(String userIdSTr : userIds){  //双层for，待优化
                    if(userIdSTr.equals(userId)){
                    //if(entity.getUserIds().contains(userId)){
                        List<UserAttendanceConfigEntity> userAttendanceConfigEntityList = entity.getUserAttendanceConfigEntityList();
                        configList = userAttendanceConfigEntityList.stream().map(config -> BeanUtil.toBean(config, HrUserAttendanceConfig.class)).collect(Collectors.toList());
                        return getAttendanceTimeSlotData(configList,dateNow);
                    }
                }
            }
        }
        /** 缓存没有，则取默认配置 */
        String defaultConfig = String.valueOf(commonCacheOperator.get(HR_ATTENDANCE_CONFIG_DEFAULT_KEY));
        return getAttendanceTimeSlotData(JSONArray.parseArray(defaultConfig, HrUserAttendanceConfig.class),dateNow);
    }

    private List<AttendanceTimeSlot> getAttendanceTimeSlotData(List<HrUserAttendanceConfig> configList,Date dateNow){
        List<AttendanceTimeSlot>  attendanceTimeSlotList = new ArrayList<>();
        configList = configList.stream().sorted(Comparator.comparing(HrUserAttendanceConfig::getOrderNumber)).collect(Collectors.toList());//按照num排序，防止顺序错乱
        /**
         * 根据考勤配置，获取用户打卡时间区间和工作时间区间，分别用来判断正常打卡,迟到,早退的情况
         * beginTime - endTime 打卡时间区间
         * workBeginTime - workEndTime 工作时间区间
         * */
        for(HrUserAttendanceConfig attendanceConfig : configList){
            AttendanceTimeSlot attendanceTimeSlot = new AttendanceTimeSlot();
            attendanceTimeSlot.setAttendanceTypeId(attendanceConfig.getAttendanceTypeId());
            attendanceTimeSlot.setDutyType(attendanceConfig.getDutyType());
            Date time = CommonTimeFormatUtil.yearAndHourConnect(CommonTimeFormatUtil.dateToStr(dateNow,DATE_FORMAT),attendanceConfig.getCheckTime().toString());
            attendanceTimeSlot.setBeginTime(CommonTimeFormatUtil.addOrUpMinute(time,Integer.parseInt(attendanceConfig.getBeginMinTime()),2));
            attendanceTimeSlot.setEndTime(CommonTimeFormatUtil.addOrUpMinute(time,Integer.parseInt(attendanceConfig.getEndMinTime()),1));
            attendanceTimeSlot.setCompareTime(time);//存打卡点时间
            attendanceTimeSlot.setWorkHours(StringUtils.isEmpty(attendanceConfig.getWorkHours()) ? "0" : attendanceConfig.getWorkHours());
            attendanceTimeSlot.setOrderNumber(attendanceConfig.getOrderNumber());
            attendanceTimeSlot.setWorkEndTime(time);//初始化默认值
            attendanceTimeSlot.setWorkBeginTime(time);//初始化默认值
            attendanceTimeSlot.setAttendanceTypeName(attendanceConfig.getAttendanceTypeName());
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

    /**
     * 缓存考勤配置数据
     * */
    @Override
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
        //todo 用字符串拼接存会有问题----  -- 已修改，2023-10-11 22:10:20
        hrUserAttendanceRelationList.forEach(relation -> {
            String key = relation.getAttendanceTypeId();
            String userIds = ObjectUtil.isNotEmpty(mapRelation.get(key)) ? mapRelation.get(key):"";
            if(relation.getEmployType().equals(HrUserAttendanceEnum.ORG_TYPE.getValue())){ //部门
                mapRelation.put(key,userIds+getOrgAndUserId(relation.getEmployId()));
            }
            if(relation.getEmployType().equals(HrUserAttendanceEnum.USER_TYPE.getValue())){ //人员
                mapRelation.put(key,userIds+relation.getEmployId());
            }
        });
        attendanceConfigCacheEntityList.forEach(cache -> cache.setUserIds(mapRelation.get(cache.getAttendanceTypeId())));
        commonCacheOperator.put(HR_ATTENDANCE_CONFIG_KEY,JSON.toJSONString(attendanceConfigCacheEntityList)); //缓存
        cacheDefaultConfig();
    }

    /**  缓存默认考勤配置*/
    @Override
    public void cacheDefaultConfig(){
        List<HrUserAttendanceConfigGroup>  hrUserAttendanceConfigGroupList = hrUserAttendanceConfigGroupService.list(new QueryWrapper<HrUserAttendanceConfigGroup>()
                .lambda().eq(HrUserAttendanceConfigGroup::getIsDefaultConfig,"0"));
        List<HrUserAttendanceConfig> configList = hrUserAttendanceConfigService.list(new QueryWrapper<HrUserAttendanceConfig>().lambda()
                .eq(HrUserAttendanceConfig::getAttendanceTypeId,hrUserAttendanceConfigGroupList.get(0).getId()));
        commonCacheOperator.put(HR_ATTENDANCE_CONFIG_DEFAULT_KEY,JSON.toJSONString(configList)); //缓存
    }

    /**
     * 申请补卡通过之后的流程
     * 补卡或是异常打卡(早退，迟到)申请
     * 传入新打卡时间，直接调打卡attendance接口，走打卡流程
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
        orgList.add(orgId);//包含本身
        bizOrgListNew.forEach(org -> orgList.add(org.getId()));
        if(ObjectUtil.isEmpty(orgList)){
            return userIds.toString();
        }
        List<SysUser> userList = sysUserService.list(new LambdaQueryWrapper<SysUser>().in(SysUser::getOrgId,orgList));
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
