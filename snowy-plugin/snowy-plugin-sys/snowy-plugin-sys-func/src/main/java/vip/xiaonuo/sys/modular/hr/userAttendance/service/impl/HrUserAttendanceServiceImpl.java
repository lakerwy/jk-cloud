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
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUnit;
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
import vip.xiaonuo.sys.modular.hr.atdData.entity.OriginalAtdData;
import vip.xiaonuo.sys.modular.hr.atdData.service.OriginalAtdDataService;
import vip.xiaonuo.sys.modular.hr.userApplyAttendance.entity.HrUserApplyAttendance;
import vip.xiaonuo.sys.modular.hr.userApplyAttendance.service.HrUserApplyAttendanceService;
import vip.xiaonuo.sys.modular.hr.userAttendance.entity.*;
import vip.xiaonuo.sys.modular.hr.userAttendance.enums.HrUserAttendanceEnum;
import vip.xiaonuo.sys.modular.hr.userAttendance.mapper.HrUserAttendanceMapper;
import vip.xiaonuo.sys.modular.hr.userAttendance.param.*;
import vip.xiaonuo.sys.modular.hr.userAttendance.service.HrUserAttendanceService;
import vip.xiaonuo.sys.modular.hr.userAttendance.vo.AttendanceTimeSlot;
import vip.xiaonuo.sys.modular.hr.userAttendance.vo.ErrorAttendanceData;
import vip.xiaonuo.sys.modular.hr.userAttendance.vo.OriginalAtdDataVo;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.entity.HrUserAttendanceConfig;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.service.HrUserAttendanceConfigService;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfigGroup.entity.HrUserAttendanceConfigGroup;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfigGroup.service.HrUserAttendanceConfigGroupService;
import vip.xiaonuo.sys.modular.hr.userAttendanceRelation.service.HrUserAttendanceRelationService;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.entity.HrUserAttendanceStat;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.enums.HrUserAttendanceStatEnum;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.vo.HrUserAttendanceStatEvent;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.service.HrUserAttendanceStatService;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.vo.TimeDataVo;
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
    private static final String HR_ATTENDANCE_CONFIG_VALID_TIME_KEY = "HR_ATTENDANCE_CONFIG_VALID_TIME_KEY";//考勤配置有效时间
    private static final String HR_ATTENDANCE_CONFIG_KEY = "HR_ATTENDANCE_CONFIG_KEY";

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @Autowired
    private HrUserAttendanceConfigService hrUserAttendanceConfigService;
    @Resource
    private CommonCacheOperator commonCacheOperator;
    @Resource
    private HrUserApplyAttendanceService hrUserApplyAttendanceService;
    @Resource
    private ApplicationEventPublisher eventPublisher;
    @Resource
    private SysOrgService sysOrgService;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private HrUserAttendanceConfigGroupService hrUserAttendanceConfigGroupService;
    @Resource
    private HrUserAttendanceStatService hrUserAttendanceStatService;
    @Resource
    OriginalAtdDataService originalAtdDataService;

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
        //去重：年月日-时分相同的数据只取一条
        Map<String,HrUserAttendance> map = new HashMap<>();
        Page<HrUserAttendance> page = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        page.getRecords().forEach(attendance -> {
            String time = DateUtil.format(attendance.getCheckTime(),DatePattern.NORM_DATETIME_MINUTE_PATTERN);
            map.put(time,attendance);
        });
        page.setRecords(map.entrySet().stream().map(attendance -> BeanUtil.toBean(attendance,HrUserAttendance.class)).collect(Collectors.toList()));
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
        SysUser sysUser = sysUserService.queryEntity(hrUserAttendanceAddParam.getUserId());
        hrUserAttendanceAddParam.setCreateUser(hrUserAttendanceAddParam.getUserId());
        Date dateNow = DateUtil.date();/** 当前打卡时间*/
        if(ObjectUtil.isEmpty(hrUserAttendanceAddParam.getCheckTime())){
            hrUserAttendanceAddParam.setCheckTime(dateNow);
        }
        HrUserAttendance hrUserAttendance = BeanUtil.toBean(hrUserAttendanceAddParam, HrUserAttendance.class);
        hrUserAttendance.setAbnormalFlag(-1);/** 初始化默认值*/

        /** 计算有效打卡时间区间*/
        List<AttendanceTimeSlot> attendanceTimeSlotList =  getAttendanceTimeSlotList(sysUser.getAttendanceGroupId(),hrUserAttendance.getCheckTime());
        String attendanceTypeId = CollectionUtil.isNotEmpty(attendanceTimeSlotList) && StringUtils.isNotEmpty(attendanceTimeSlotList.get(0).getAttendanceTypeId()) ?
                attendanceTimeSlotList.get(0).getAttendanceTypeId() : "" ;
        String attendanceTypeName = CollectionUtil.isNotEmpty(attendanceTimeSlotList) && StringUtils.isNotEmpty(attendanceTimeSlotList.get(0).getAttendanceTypeName()) ?
                attendanceTimeSlotList.get(0).getAttendanceTypeName() : "" ;

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
        attendanceStatEvent.setAttendanceTypeId(attendanceTypeId);
        attendanceStatEvent.setAttendanceTypeName(attendanceTypeName);
        eventPublisher.publishEvent(attendanceStatEvent);
        return  HrUserAttendanceEnum.ATTENDANCE_NORMAL.getValue();
    }
    @Override
    public List<AttendanceTimeSlot> userConfigCache(ConfigCacheListParam configCacheListParam){
        String  userId = ObjectUtil.isEmpty(configCacheListParam.getUserId()) ? StpLoginUserUtil.getLoginUser().getId() : configCacheListParam.getUserId();

        String cache = String.valueOf(commonCacheOperator.get(HR_ATTENDANCE_CONFIG_KEY));//取考勤缓存数据
        List<AttendanceTimeSlot> slotList = new ArrayList<>();
        Map<String,String> configMap = new HashMap<>();
        if(StringUtils.isNotEmpty(cache) && !cache.equals("null")){
            configMap = (Map)JSON.parse(cache);
        }
        SysUser sysUser = sysUserService.queryEntity(configCacheListParam.getUserId());
        if(StringUtils.isNotEmpty(sysUser.getAttendanceGroupId()) && ObjectUtil.isNotEmpty(configMap.get(sysUser.getAttendanceGroupId()))){
            slotList = JSONArray.parseArray(configMap.get(sysUser.getAttendanceGroupId()), AttendanceTimeSlot.class);
            return timeSlots(slotList,new Date());
        }
        //取默认配置
        String defaultConfigCache = String.valueOf(commonCacheOperator.get(HR_ATTENDANCE_CONFIG_DEFAULT_KEY));
        if(StringUtils.isNotEmpty(defaultConfigCache)){
            slotList = JSONArray.parseArray(defaultConfigCache, AttendanceTimeSlot.class);
        }
        return timeSlots(slotList,new Date());
    }

    @Override
    public List<AttendanceTimeSlot> getAttendanceTimeSlotList(String attendanceGroupId,Date dateNow){
        String cache = String.valueOf(commonCacheOperator.get(HR_ATTENDANCE_CONFIG_KEY));//取考勤缓存数据
        List<AttendanceTimeSlot> slotList = new ArrayList<>();
        Map<String,String> configMap = new HashMap<>();
        if(StringUtils.isNotEmpty(cache) && !cache.equals("null")){
            configMap = (Map)JSON.parse(cache);
        }
        if(StringUtils.isNotEmpty(attendanceGroupId) && ObjectUtil.isNotEmpty(configMap) && ObjectUtil.isNotEmpty(configMap.get(attendanceGroupId))){
            slotList = JSONArray.parseArray(configMap.get(attendanceGroupId), AttendanceTimeSlot.class);
            return timeSlots(slotList,dateNow);
        }
        //取默认配置
        String defaultConfigCache = String.valueOf(commonCacheOperator.get(HR_ATTENDANCE_CONFIG_DEFAULT_KEY));
        if(!defaultConfigCache.equals("null")){
            slotList = JSONArray.parseArray(defaultConfigCache, AttendanceTimeSlot.class);
            return timeSlots(slotList,dateNow);
        }
        return new ArrayList<>();
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

    /**  缓存默认考勤配置*/
    @Override
    public void cacheDefaultConfig(){
        List<HrUserAttendanceConfigGroup>  hrUserAttendanceConfigGroupList = hrUserAttendanceConfigGroupService.list(new QueryWrapper<HrUserAttendanceConfigGroup>()
                .lambda().eq(HrUserAttendanceConfigGroup::getIsDefaultConfig,"0"));
        List<HrUserAttendanceConfig> configList = hrUserAttendanceConfigService.list(new QueryWrapper<HrUserAttendanceConfig>().lambda()
                .eq(HrUserAttendanceConfig::getAttendanceTypeId,hrUserAttendanceConfigGroupList.get(0).getId()));

        commonCacheOperator.put(HR_ATTENDANCE_CONFIG_DEFAULT_KEY,JSON.toJSONString(getAttendanceTimeSlotData(configList,new Date(),new ArrayList<>()))); //缓存
        //commonCacheOperator.put(HR_ATTENDANCE_CONFIG_DEFAULT_KEY,JSON.toJSONString(configList)); //缓存
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

    private boolean isOrigFestival(String day){
        Boolean isFestival =  hrUserAttendanceStatService.isFestival(day+HrUserAttendanceStatEnum.BEGIN_TIMNE.getValue(),1);//type类别：1 判断是法定节假日，2 判断是法定加班日
        Boolean isWork =  hrUserAttendanceStatService.isFestival(day+HrUserAttendanceStatEnum.BEGIN_TIMNE.getValue(),2);
        Boolean isWeek = DateUtil.isWeekend(DateUtil.parseDate(day+HrUserAttendanceStatEnum.BEGIN_TIMNE.getValue()));
        if(!isWeek&&!isFestival){//非周末，且非法定节假日
            return false;
        }
        if(isWeek&&isWork){//周末，且未法定加班日
            return false;
        }
        return true;
    }

    /**
     * 打卡记录同步考勤统计表
     * @author ljy
     * @date  2023/09/26 14:24
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void attendanceStatJobHandle(){
        //休息日无需统计，若存在打卡数据，直接迁移打卡数据
        String day = DateUtil.format(new Date(),DatePattern.NORM_DATE_PATTERN);
        if(isOrigFestival(day)){
            return;
        }
        /**
         * 1.打卡数据定时统计到原始数据表：ORIGINAL_ATD_DATA
         * 2.统计缺卡数据到月统计表：HR_ATTENDANCE_ANAL_STAT
         * */
        String atdTime = CommonTimeFormatUtil.dayAndWeek(day);
        Date beginDate = DateUtil.beginOfDay(DateUtil.date());
        Date endDate = DateUtil.date();
        List<SysUser> userList = sysUserService.list(new LambdaQueryWrapper<SysUser>().isNotNull(SysUser::getAttendanceGroupId).ne(SysUser::getAttendanceGroupId,""));
        List<HrUserAttendance> hrUserAttendanceList = this.list(new LambdaQueryWrapper<HrUserAttendance>().between(HrUserAttendance::getCheckTime,beginDate,endDate));
        Map<String, Map<String, HrUserAttendanceConfig>> map = this.getAtdConfig();//key - 考勤组ID，value - [key 考勤type，value  考勤配置]
        //String day = DateUtil.format(new Date(),DatePattern.NORM_DATE_PATTERN);
        List<HrUserAttendanceConfig> defaultConfigList = defaultConfig();
        /** 历史统计数据 */
        List<OriginalAtdData> originalAtdDataList = originalAtdDataService.list(new LambdaQueryWrapper<OriginalAtdData>().between(OriginalAtdData::getCheckTime,beginDate,endDate));
        Map<String,Map<String,OriginalAtdData>> originalMap = new HashMap<>();//key - userId (value [key - type,value - OriginalAtdData])
        originalAtdDataList.forEach(original -> {
            Map<String,OriginalAtdData> atdDataMap = originalMap.get(original.getUserId()) == null ? new HashMap<>() : originalMap.get(original.getUserId());
            atdDataMap.put(original.getCheckType(),original);
            originalMap.put(original.getUserId(),atdDataMap);
        });

        //每个人的打卡数据聚类处理
        //List<OriginalAtdDataVo> originalAtdDataVoList = new ArrayList<>();
        List<HrUserAttendanceStat> hrUserAttendanceStatList = new ArrayList<>();
        /** 统计的数据*/
        List<OriginalAtdData> originalList = new ArrayList<>();
        userList.forEach(user -> {
            List<HrUserAttendance> attendanceList = new ArrayList<>();
            Map<String,List<HrUserAttendance>> attendanceMap = new HashMap<>();//key type value List<HrUserAttendance>
            hrUserAttendanceList.forEach(attendance -> {
                List<HrUserAttendance> attendances = attendanceMap.get(attendance.getCheckType()) == null ? new ArrayList<>() : attendanceMap.get(attendance.getCheckType());
            attendances.add(attendance);
            attendanceMap.put(attendance.getCheckType(),attendances);
        });
            //开始计算考勤有哪些类别，需要打几次卡，OnDuty，REST，OffDuty
            HrUserAttendanceConfig configParam = new HrUserAttendanceConfig();//用来传递考勤组ID和名称
            Map<String, HrUserAttendanceConfig> mapType = getMapType( map, user,defaultConfigList, day, beginDate, endDate,configParam);//new HashMap<>();

            /**
             * 开始统计数据
             * 1.判断是否存在历史数据，若存在，则跳过，[包含之前请假，出差，外出，历史统计数据]
             * 2.不存在，按照类别获取数据，若获取不到相应类别数据，则直接是缺卡；若获取了一次，后续同类型则未无效打卡数据
             * */
            mapType.entrySet().forEach(type ->{
                HrUserAttendanceConfig config = type.getValue();
                String dutyType = type.getKey();
                Map<String,OriginalAtdData> atdOrigDataMap = originalMap.get(user.getId()) != null ? originalMap.get(user.getId()) : new HashMap<>();
                if(atdOrigDataMap.get(dutyType) == null){//不存在历史数据,若存在直接跳过
                    List<HrUserAttendance> attendances = attendanceMap.get(type) == null ? new ArrayList<>() : attendanceMap.get(type);
                    if(attendances.size() == 0){//缺卡
                        OriginalAtdData originalAtdData = new OriginalAtdData();
                        originalAtdData.setAtdGroupId(config.getAttendanceTypeId());
                        originalAtdData.setAtdGroupName(config.getAttendanceTypeName());
                        originalAtdData.setAbnormalFlag(-1);
                        originalAtdData.setUserId(user.getId());
                        originalAtdData.setUserName(user.getName());
                        originalAtdData.setOrgId(user.getOrgId());
                        originalAtdData.setOrgName(user.getOrgName());
                        originalAtdData.setPostion(user.getPositionName());
                        originalAtdData.setEmpNo(user.getEmpNo());
                        originalAtdData.setAtdResult("缺卡");
                        String checkTime = DateUtil.format(new Date(),DatePattern.NORM_DATE_PATTERN)+ " "+config.getCheckTime();
                        originalAtdData.setCheckTime(CommonTimeFormatUtil.strToDate(checkTime,DatePattern.NORM_DATETIME_PATTERN));
                        originalAtdData.setCheckType(config.getDutyType());
                        originalAtdData.setAtdDate(atdTime);
                        originalList.add(originalAtdData);
                        hrUserAttendanceStatList.add(saveOrUpdate(config,originalAtdData,CommonTimeFormatUtil.strToDate(checkTime,DatePattern.NORM_DATETIME_PATTERN)));
                    }
                }
            });
        });
        if(CollectionUtil.isNotEmpty(originalList)){
            originalAtdDataService.saveBatch(originalList);
        }
        if(CollectionUtil.isNotEmpty(hrUserAttendanceStatList)){
            hrUserAttendanceStatService.saveBatch(hrUserAttendanceStatList);
        }
    }

    private HrUserAttendanceStat saveOrUpdate(HrUserAttendanceConfig config,OriginalAtdData originalAtdData,Date checkTime){
        HrUserAttendanceStat hrUserAttendanceStat = new HrUserAttendanceStat();
        hrUserAttendanceStat.setUserId(originalAtdData.getUserId());
        hrUserAttendanceStat.setUserName(originalAtdData.getUserName());
        hrUserAttendanceStat.setCheckType(originalAtdData.getCheckType());
        hrUserAttendanceStat.setAbnormalFlag(-1);
        hrUserAttendanceStat.setAttendanceTypeId(config.getAttendanceTypeId());
        hrUserAttendanceStat.setAttendanceTypeName(config.getAttendanceTypeName());
        return hrUserAttendanceStat;
    }

    private Map<String, HrUserAttendanceConfig> getMapType(Map<String, Map<String, HrUserAttendanceConfig>> map,SysUser user,List<HrUserAttendanceConfig> defaultConfigList,
                                                           String day,Date beginDate,Date endDate,HrUserAttendanceConfig configParam){
        Map<String, HrUserAttendanceConfig> mapType = new HashMap<>();
        Map<String, HrUserAttendanceConfig> configMap = map.get(user.getId()) != null ? map.get(user.getId()) : new HashMap<>();
        //HrUserAttendanceConfig configParam = new HrUserAttendanceConfig();//用来传递考勤组ID和名称
        if(ObjectUtil.isEmpty(configMap)){
            defaultConfigList.forEach(config -> {
                configParam.setAttendanceTypeId(config.getAttendanceTypeId());
                configParam.setAttendanceTypeName(config.getAttendanceTypeName());
                Date date = CommonTimeFormatUtil.strToDate(day+" "+config.getCheckTime().toString(),DatePattern.NORM_DATETIME_PATTERN);
                Boolean isTrue = date.compareTo(beginDate) > 0 && date.compareTo(endDate) < 0  ? true : false;
                if(isTrue){ mapType.put(config.getDutyType(),config); }
            });
        }else{
            configMap.entrySet().forEach(data -> {
                configParam.setAttendanceTypeId(data.getValue().getAttendanceTypeId());
                configParam.setAttendanceTypeName(data.getValue().getAttendanceTypeName());
                Date date = CommonTimeFormatUtil.strToDate(day+" "+data.getValue().getCheckTime().toString(),DatePattern.NORM_DATETIME_PATTERN);
                Boolean isTrue = date.compareTo(beginDate) > 0 && date.compareTo(endDate) < 0  ? true : false;
                if(isTrue){ mapType.put(data.getValue().getDutyType(),data.getValue()); }
            });
        }
        return mapType;
    };

    private OriginalAtdData saveOriginalAtdData(HrUserAttendance attendance,Integer i,HrUserAttendanceConfig configParam,SysUser user,String atdTime){
        OriginalAtdData originalAtdData = BeanUtil.toBean(attendance,OriginalAtdData.class);
        originalAtdData.setAtdLocation(attendance.getMachineCode());
        originalAtdData.setAtdGroupId(configParam.getAttendanceTypeId());
        originalAtdData.setAtdGroupName(configParam.getAttendanceTypeName());
        Integer abnormalFlag = attendance.getAbnormalFlag() == 1 ?
                1 : attendance.getCheckType().equals(HrUserAttendanceEnum.ATTENDANCE_ON_DUTY.getValue())? 3 : 4;
        originalAtdData.setAbnormalFlag(abnormalFlag);
        originalAtdData.setOrgId(user.getOrgId());
        originalAtdData.setOrgName(user.getOrgName());
        originalAtdData.setUserId(user.getId());
        originalAtdData.setUserName(user.getName());
        originalAtdData.setEmpNo(user.getEmpNo());
        originalAtdData.setPostion(user.getPositionName());
        originalAtdData.setAtdDate(atdTime);
        originalAtdData.setCheckTime(attendance.getCheckTime());
        originalAtdData.setCheckType(attendance.getCheckType());
        originalAtdData.setAtdResult("打卡无效:此记录已被更新");
        if(i==0){
            originalAtdData.setAtdResult(attendance.getAbnormalFlag() == 1 ?
                    "正常" : attendance.getCheckType().equals(HrUserAttendanceEnum.ATTENDANCE_ON_DUTY.getValue())? "迟到" : "早退");
        }
        return originalAtdData;
    }

    private List<HrUserAttendanceConfig> defaultConfig(){
        List<HrUserAttendanceConfigGroup>  hrUserAttendanceConfigGroupList = hrUserAttendanceConfigGroupService.list(new QueryWrapper<HrUserAttendanceConfigGroup>()
                .lambda().eq(HrUserAttendanceConfigGroup::getIsDefaultConfig,"0"));
        List<HrUserAttendanceConfig> configList = hrUserAttendanceConfigService.list(new QueryWrapper<HrUserAttendanceConfig>().lambda()
                .eq(HrUserAttendanceConfig::getAttendanceTypeId,hrUserAttendanceConfigGroupList.get(0).getId()));
        return configList;
    }

    @Override
    public List<SysUser> getOrgAndUserList(String orgId){
        List<SysUser> sysUsers = new ArrayList<>();
        if(StringUtils.isEmpty(orgId)){
            return sysUsers;
        }
        List<SysOrg> bizOrgList = sysOrgService.list();//全表查询
        List<SysOrg> bizOrgListNew = recursionOrg(new ArrayList<>(),orgId,bizOrgList);
        List<String> orgList = new ArrayList<>();
        orgList.add(orgId);//包含本身
        bizOrgListNew.forEach(org -> orgList.add(org.getId()));
        if(ObjectUtil.isEmpty(orgList)){
            return sysUsers;
        }
        sysUsers = sysUserService.list(new LambdaQueryWrapper<SysUser>().in(SysUser::getOrgId,orgList));
        return sysUsers;
    }
    /**
     * 递归该部门下面有多少人员
     * author ljy
     * @date  2023/10/09 16:24
     * */
    @Override
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

    @Override
    public void  attendanceConfigCache(){
        List<HrUserAttendanceConfigGroup> hrUserAttendanceConfigGroupList = hrUserAttendanceConfigGroupService.list();
        List<HrUserAttendanceConfig> hrUserAttendanceConfigList = hrUserAttendanceConfigService.list();
        // List<Map>    map - key:groupId ,value list<Config>
        //Map<String,TimeDataVo>  key - groupId
        Map<String,String> configMap = new HashMap<>();
        Map<String, TimeDataVo> timeMap = new HashMap<>();
        hrUserAttendanceConfigGroupList.forEach(group -> {
            if(StringUtils.isNotEmpty(group.getValidBeginTime()) || StringUtils.isNotEmpty(group.getValidEndTime())){
                timeMap.put(group.getId(),new TimeDataVo(CommonTimeFormatUtil.strToDate(group.getValidBeginTime(),DatePattern.NORM_DATETIME_PATTERN)
                        ,CommonTimeFormatUtil.strToDate(group.getValidEndTime(),DatePattern.NORM_DATETIME_PATTERN)));
            }
            String groupId = group.getId();
            List<HrUserAttendanceConfig> configList = new ArrayList<>();
            hrUserAttendanceConfigList.forEach(config -> {
                if(groupId.equals(config.getAttendanceTypeId())){
                    configList.add(config);
                }
            });
            if(CollectionUtil.isNotEmpty(configList)){
                List<AttendanceTimeSlot> configToSlot = getAttendanceTimeSlotData(configList,new Date(),new ArrayList<>());
                String slotJson = JSON.toJSONString(configToSlot);
                configMap.put(groupId,slotJson);
            }
        });
        //String configJson = JSON.toJSONString(configMap);
        commonCacheOperator.put(HR_ATTENDANCE_CONFIG_KEY,JSON.toJSONString(configMap)); //考勤配置
        //String configTimeJosn = JSON.toJSONString(timeMap);
        commonCacheOperator.put(HR_ATTENDANCE_CONFIG_VALID_TIME_KEY,JSON.toJSONString(timeMap)); //考勤配置有效时间
        cacheDefaultConfig();  //缓存默认配置
    }

    private List<AttendanceTimeSlot> getAttendanceTimeSlotData(List<HrUserAttendanceConfig> configList,Date dateNow,List<AttendanceTimeSlot>  attendanceTimeSlotList){
        configList = configList.stream().sorted(Comparator.comparing(HrUserAttendanceConfig::getId)).collect(Collectors.toList());//按照ID排序，防止顺序错乱
        for(HrUserAttendanceConfig attendanceConfig : configList){
            AttendanceTimeSlot attendanceTimeSlot = new AttendanceTimeSlot();
            attendanceTimeSlot.setAttendanceTypeId(attendanceConfig.getAttendanceTypeId());
            attendanceTimeSlot.setDutyType(attendanceConfig.getDutyType());
            Date time = CommonTimeFormatUtil.yearAndHourConnect(DateUtil.format(dateNow,DATE_FORMAT),ObjectUtil.isNotEmpty(attendanceConfig.getCheckTime()) ?attendanceConfig.getCheckTime().toString() : " 00:00:00");

            attendanceTimeSlot.setBeginTime(CommonTimeFormatUtil.addOrUpMinute(time,Integer.parseInt(attendanceConfig.getBeginMinTime() == null ? "0" : attendanceConfig.getBeginMinTime()),2));
            attendanceTimeSlot.setEndTime(CommonTimeFormatUtil.addOrUpMinute(time,Integer.parseInt(attendanceConfig.getEndMinTime() == null ? "0" : attendanceConfig.getEndMinTime()),1));
            attendanceTimeSlot.setCompareTime(time);//存打卡点时间
            attendanceTimeSlot.setWorkHours(StringUtils.isEmpty(attendanceConfig.getWorkHours()) ? "0" : attendanceConfig.getWorkHours());
            attendanceTimeSlot.setOrderNumber(attendanceConfig.getOrderNumber());
            attendanceTimeSlot.setWorkEndTime(time);//初始化默认值
            attendanceTimeSlot.setWorkBeginTime(time);//初始化默认值
            attendanceTimeSlot.setAttendanceTypeName(attendanceConfig.getAttendanceTypeName());
            //获取工作区间
            double minute =  attendanceConfig.getWorkHours() != null ? Double.parseDouble(attendanceConfig.getWorkHours())*60 : 0.0;
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

    private List<ErrorAttendanceData> setAtdErrorStat(List<HrUserAttendanceStat> statList,Map<String,Map<String,String>> map){
        List<ErrorAttendanceData> errorList = new ArrayList<>();
        for(HrUserAttendanceStat stat : statList){
            ErrorAttendanceData errorAttendanceData = new ErrorAttendanceData();
            errorAttendanceData.setAttendanceGroupName(stat.getAttendanceTypeName());
            errorAttendanceData.setUserId(stat.getUserId());
            errorAttendanceData.setUserName(stat.getUserName());

            Map<String,String> mapAtdConf = map.get(stat.getAttendanceTypeId()) != null ? map.get(stat.getAttendanceTypeId()) : new HashMap<>();
            String checkTime = mapAtdConf.get(stat.getCheckType()) == null ? "" : mapAtdConf.get(stat.getCheckType());
            /*if(StringUtils.isEmpty(checkTime)){
                continue;
            }*/
            Date ondutyTime = CommonTimeFormatUtil.strToDate(DateUtil.format(stat.getCheckTime(),DatePattern.NORM_DATE_PATTERN)+" "+checkTime,DatePattern.NORM_DATETIME_PATTERN);

            String ondutyMinute = stat.getAbnormalFlag() == 3  ? String.valueOf(DateUtil.between(stat.getCheckTime(),ondutyTime, DateUnit.MINUTE)) : "";
            String offdutyMinute = stat.getAbnormalFlag() == 4 ? String.valueOf(DateUtil.between(stat.getCheckTime(),ondutyTime, DateUnit.MINUTE)) : "";
            String errorStatus = stat.getAbnormalFlag() == null ? "打卡异常" : stat.getAbnormalFlag() == -1 ? " 缺卡" :
                    stat.getAbnormalFlag() == 3 ? "迟到"+ondutyMinute+"分钟" :
                            stat.getAbnormalFlag() == 4 ?  "早退"+offdutyMinute+"分钟" : "";
            errorAttendanceData.setErrorStatus(errorStatus);
            //OnDuty：上班；OffDuty：下班；休息 REST
            String timeStr = stat.getCheckType().equals("OnDuty") ? "上班 "+checkTime : stat.getCheckType().equals("OffDuty") ? "下班 "+checkTime:"休息";
            errorAttendanceData.setTime(timeStr);
            errorAttendanceData.setCheckTime(DateUtil.format(stat.getCheckTime(),"HH:mm"));
            errorAttendanceData.setAttendanceId(stat.getId());
            errorAttendanceData.setUpdateTime(stat.getUpdateTime());
            errorAttendanceData.setUpdateUser(stat.getUpdateUser());
            errorList.add(errorAttendanceData);
        }
        return errorList;
    }
    @Override
    public Page<ErrorAttendanceData> errorPage(ErrorAttendancePageParam errorAttendancePageParam){
        Page<ErrorAttendanceData> page = new Page<>();
        String day = StringUtils.isEmpty(errorAttendancePageParam.getDay()) ? DateUtil.format(new Date(),DatePattern.NORM_DATE_PATTERN) : errorAttendancePageParam.getDay();
        String beginTime  = day+ HrUserAttendanceStatEnum.BEGIN_TIMNE.getValue();
        String endTime = day+HrUserAttendanceStatEnum.END_TIME.getValue();
        List<HrUserAttendanceConfig> configList = hrUserAttendanceConfigService.list();
        Map<String,Map<String,String>> map = new HashMap<>();
        configList.forEach(config -> {
            Map<String,String> mapAtdConf = map.get(config.getAttendanceTypeId()) == null ? new HashMap<>() : map.get(config.getAttendanceTypeId());
            mapAtdConf.put(config.getDutyType(),ObjectUtil.isEmpty(config.getCheckTime()) ? "" :config.getCheckTime().toString());
            map.put(config.getAttendanceTypeId(),mapAtdConf);
        });
        LambdaQueryWrapper<HrUserAttendanceStat> lambdaQueryWrapper = new LambdaQueryWrapper<HrUserAttendanceStat>().between(HrUserAttendanceStat::getCreateTime,beginTime,endTime);

        if(ObjectUtil.isEmpty(errorAttendancePageParam.getDealFlag())){  //返回全部
            if(errorAttendancePageParam.getAbnormalFlag() != null){
                lambdaQueryWrapper.eq(HrUserAttendanceStat::getAbnormalFlag,errorAttendancePageParam.getAbnormalFlag());
            }
            Page<HrUserAttendanceStat> statPage = hrUserAttendanceStatService.page(CommonPageRequest.defaultPage(),
                    lambdaQueryWrapper.ne(HrUserAttendanceStat::getAbnormalFlag,1)
                            .or()
                            .ne(HrUserAttendanceStat::getEditAbnormalFlag,1));
            page.setTotal(statPage.getTotal());
            page.setRecords(setAtdErrorStat(statPage.getRecords(),map));
            return page;
        }
        if( errorAttendancePageParam.getDealFlag() == 1){ //已经处理
            if(errorAttendancePageParam.getAbnormalFlag() != null){
                lambdaQueryWrapper.eq(HrUserAttendanceStat::getEditAbnormalFlag,errorAttendancePageParam.getAbnormalFlag());
            }
            Page<HrUserAttendanceStat> statPage = hrUserAttendanceStatService.page(CommonPageRequest.defaultPage(),
                    lambdaQueryWrapper.isNotNull(HrUserAttendanceStat::getEditAbnormalFlag).ne(HrUserAttendanceStat::getEditAbnormalFlag,""));
            page.setTotal(statPage.getTotal());
            return page.setRecords(setAtdErrorStat(statPage.getRecords(),map));
        }
        //未处理
        if(errorAttendancePageParam.getAbnormalFlag() != null){
            lambdaQueryWrapper.eq(HrUserAttendanceStat::getAbnormalFlag,errorAttendancePageParam.getAbnormalFlag());
        }
        Page<HrUserAttendanceStat> statPage = hrUserAttendanceStatService.page(CommonPageRequest.defaultPage(),
                lambdaQueryWrapper.isNull(HrUserAttendanceStat::getEditAbnormalFlag)
                        .or()
                        .eq(HrUserAttendanceStat::getEditAbnormalFlag,""));
        page.setTotal(statPage.getTotal());
        page.setRecords(setAtdErrorStat(statPage.getRecords(),map));
        return page;
    }

    @Override
    public void errorEdit(EditErrorAtdParam editErrorAtdParam){
        HrUserAttendanceStat hrUserAttendanceStat = hrUserAttendanceStatService.queryEntity(editErrorAtdParam.getAttendanceId());
        if(ObjectUtil.isEmpty(hrUserAttendanceStat)){
            throw new CommonException("该打卡记录不存在！");
        }
        hrUserAttendanceStat.setEditAbnormalFlag(hrUserAttendanceStat.getAbnormalFlag());
        String day = DateUtil.format(hrUserAttendanceStat.getCheckTime(),DatePattern.NORM_DATE_PATTERN);
        //OnDuty-上班，REST-休息，OffDuty-下班
        Date checkTime = hrUserAttendanceStat.getCheckType().equals("OnDuty")? CommonTimeFormatUtil.strToDate(day+" 08:30:00") :
                hrUserAttendanceStat.getCheckType().equals("OffDuty")? CommonTimeFormatUtil.strToDate(day+" 17:30:00") : hrUserAttendanceStat.getCheckTime();
        hrUserAttendanceStat.setCheckTime(checkTime);
        hrUserAttendanceStat.setEditCheckTime(DateUtil.format(hrUserAttendanceStat.getCheckTime(),DatePattern.NORM_DATETIME_PATTERN));
        hrUserAttendanceStat.setAbnormalFlag(Integer.parseInt(editErrorAtdParam.getEditStatus()));
        hrUserAttendanceStatService.updateById(hrUserAttendanceStat);
    }

    @Override
    public void revokeErrorEdit(EditErrorAtdParam editErrorAtdParam){
        HrUserAttendanceStat hrUserAttendanceStat = hrUserAttendanceStatService.queryEntity(editErrorAtdParam.getAttendanceId());
        if(ObjectUtil.isEmpty(hrUserAttendanceStat)){
            throw new CommonException("该打卡记录不存在");
        }
        hrUserAttendanceStat.setCheckTime(CommonTimeFormatUtil.strToDate(hrUserAttendanceStat.getEditCheckTime(),DatePattern.NORM_DATETIME_PATTERN));
        hrUserAttendanceStat.setAbnormalFlag(hrUserAttendanceStat.getEditAbnormalFlag());
        hrUserAttendanceStat.setEditAbnormalFlag(null);
        hrUserAttendanceStat.setEditCheckTime(null);
        hrUserAttendanceStatService.saveOrUpdate(hrUserAttendanceStat);
    }

    @Override
    public Map<String, Map<String, HrUserAttendanceConfig>> getAtdConfig(){
        Map<String, Map<String, HrUserAttendanceConfig>> map = new HashMap<>();
        List<HrUserAttendanceConfigGroup> groupList = hrUserAttendanceConfigGroupService.list();
        List<HrUserAttendanceConfig> configList = hrUserAttendanceConfigService.list();

        groupList.forEach(group -> {
            String groupId = group.getId();
            Map<String, HrUserAttendanceConfig> configMap = map.get(group.getId()) == null ? new HashMap<>() : map.get(group.getId());
            configList.forEach(config -> {
                if(config.getAttendanceTypeId().equals(group.getId())){
                    configMap.put(config.getDutyType(),config);
                }
            });
            map.put(group.getId(),configMap);
        });
        return map;
    }
}
