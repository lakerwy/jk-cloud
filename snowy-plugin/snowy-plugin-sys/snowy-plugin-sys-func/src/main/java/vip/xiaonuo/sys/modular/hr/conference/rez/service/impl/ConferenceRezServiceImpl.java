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
package vip.xiaonuo.sys.modular.hr.conference.rez.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.util.DateUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiV2UserGetbymobileResponse;
import com.taobao.api.ApiException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.pojo.SaBaseLoginUser;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.common.util.CommonTimeFormatUtil;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.dbs.core.util.BizTableCommon;
import vip.xiaonuo.dbs.core.util.CommonUtils;
import vip.xiaonuo.dev.api.IDingDingService;
import vip.xiaonuo.dev.modular.message.config.DingTalkProperties;
import vip.xiaonuo.sys.modular.hr.conference.config.entity.ConferenceConfig;
import vip.xiaonuo.sys.modular.hr.conference.config.param.ConferenceTimeDataParam;
import vip.xiaonuo.sys.modular.hr.conference.config.service.ConferenceConfigService;
import vip.xiaonuo.sys.modular.hr.conference.rez.entity.ConferenceRez;
import vip.xiaonuo.sys.modular.hr.conference.rez.entity.ConferenceRezPeriod;
import vip.xiaonuo.sys.modular.hr.conference.rez.mapper.ConferenceRezMapper;
import vip.xiaonuo.sys.modular.hr.conference.rez.param.*;
import vip.xiaonuo.sys.modular.hr.conference.rez.service.ConferenceRezPeriodService;
import vip.xiaonuo.sys.modular.hr.conference.rez.service.ConferenceRezService;
import vip.xiaonuo.sys.modular.hr.conference.rez.vo.ConferenceMsgEvent;
import vip.xiaonuo.sys.modular.hr.conference.rez.vo.ConferenceTime;
import vip.xiaonuo.sys.modular.hr.conference.rez.vo.ConferenceTimeData;
import vip.xiaonuo.sys.modular.hr.conference.userSign.entity.ConferenceUserSign;
import vip.xiaonuo.sys.modular.hr.conference.userSign.service.ConferenceUserSignService;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.enums.HrUserAttendanceStatEnum;
import vip.xiaonuo.sys.modular.org.entity.SysOrg;
import vip.xiaonuo.sys.modular.org.service.SysOrgService;
import vip.xiaonuo.sys.modular.position.entity.SysPosition;
import vip.xiaonuo.sys.modular.position.service.SysPositionService;
import vip.xiaonuo.sys.modular.user.entity.SysUser;
import vip.xiaonuo.sys.modular.user.service.SysUserService;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 会议模块-预约会议Service接口实现类
 *
 * @author ljy
 * @date  2023/12/01 23:35
 **/
@Service
public class ConferenceRezServiceImpl extends ServiceImpl<ConferenceRezMapper, ConferenceRez> implements ConferenceRezService {

    @Resource
    SysUserService sysUserService;
    @Resource
    ConferenceConfigService conferenceConfigService;
    @Resource
    SysOrgService sysOrgService;
    @Resource
    SysPositionService sysPositionService;
    @Resource
    ConferenceRezPeriodService conferenceRezPeriodService;
    @Resource
    ConferenceUserSignService conferenceUserSignService;
    @Resource
    IDingDingService iDingDingService;
    @Resource
    DingTalkProperties dingTalkProperties;
    @Resource
    private ApplicationEventPublisher eventPublisher;

    @Override
    public Page<ConferenceRez> page(ConferenceRezPageParam conferenceRezPageParam) {
        QueryWrapper<ConferenceRez> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(conferenceRezPageParam.getRezPeriodId())) {
            queryWrapper.lambda().eq(ConferenceRez::getRezPeriodId, conferenceRezPageParam.getRezPeriodId());
        }
        if(ObjectUtil.isNotEmpty(conferenceRezPageParam.getConferenceConfigId())) {
            queryWrapper.lambda().eq(ConferenceRez::getConferenceConfigId, conferenceRezPageParam.getConferenceConfigId());
        }
        if(ObjectUtil.isNotEmpty(conferenceRezPageParam.getRezUser())) {
            queryWrapper.lambda().eq(ConferenceRez::getRezUser, conferenceRezPageParam.getRezUser());
        }
        if(ObjectUtil.isNotEmpty(conferenceRezPageParam.getPresideUser())) {
            queryWrapper.lambda().eq(ConferenceRez::getPresideUser, conferenceRezPageParam.getPresideUser());
        }
        if(ObjectUtil.isNotEmpty(conferenceRezPageParam.getRecordUser())) {
            queryWrapper.lambda().eq(ConferenceRez::getRecordUser, conferenceRezPageParam.getRecordUser());
        }
        if(ObjectUtil.isNotEmpty(conferenceRezPageParam.getVerifyUserId())) {
            queryWrapper.lambda().eq(ConferenceRez::getVerifyUserId, conferenceRezPageParam.getVerifyUserId());
        }
        if(ObjectUtil.isNotEmpty(conferenceRezPageParam.getVerifyStatus())) {
            queryWrapper.lambda().eq(ConferenceRez::getVerifyStatus, conferenceRezPageParam.getVerifyStatus());
        }
        if(ObjectUtil.isNotEmpty(conferenceRezPageParam.getSn())) {
            queryWrapper.lambda().eq(ConferenceRez::getSn, conferenceRezPageParam.getSn());
        }
        if(ObjectUtil.isAllNotEmpty(conferenceRezPageParam.getSortField(), conferenceRezPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(conferenceRezPageParam.getSortOrder());
            queryWrapper.orderBy(true, conferenceRezPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(conferenceRezPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(ConferenceRez::getId);
        }
        Page<ConferenceRez> page = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        userSign(page);
        return page;
    }
    //返回签到列表
    private void userSign(Page<ConferenceRez> page){
        if (CollectionUtil.isEmpty(page.getRecords())){
            return;
        }
        List<String> rezIds = page.getRecords().stream().map(rez -> rez.getId()).collect(Collectors.toList());
        List<ConferenceUserSign> signList  = conferenceUserSignService.list(new LambdaQueryWrapper<ConferenceUserSign>().in(ConferenceUserSign::getRezId,rezIds));
        Map<String,List<ConferenceUserSign>> map = new HashMap<>();
        signList.forEach(sign -> {
            List<ConferenceUserSign> signs = map.get(sign.getRezId()) != null ? map.get(sign.getRezId()) : new ArrayList<>();
            signs.add(sign);
            map.put(sign.getRezId(),signs);
        });
        page.getRecords().forEach(rez -> {
            if(map.get(rez.getId()) != null){
                rez.setConferenceUserSignList(map.get(rez.getId()));
            }
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ConferenceRezAddParam conferenceRezAddParam) {
        SaBaseLoginUser loginUser = StpLoginUserUtil.getLoginUser();
        if(StringUtils.isEmpty(conferenceRezAddParam.getRezUser())){
            conferenceRezAddParam.setRezUser(loginUser.getId());
        }
        if(ObjectUtil.isNotEmpty(conferenceRezAddParam.getWeekTime())  && conferenceRezAddParam.getWeekTime() != 0
                && ObjectUtil.isNotEmpty(conferenceRezAddParam.getDayTime()) && conferenceRezAddParam.getDayTime() != 0){
            log.error("周期预约错误，参数错误：WeekTime："+conferenceRezAddParam.getWeekTime()+",dayTime:"+conferenceRezAddParam.getDayTime());
            throw new CommonException("周期预约错误，请联系管理员");
        }else if((ObjectUtil.isNotEmpty(conferenceRezAddParam.getWeekTime()) && conferenceRezAddParam.getWeekTime() != 0)
                || (ObjectUtil.isNotEmpty(conferenceRezAddParam.getDayTime())  && conferenceRezAddParam.getDayTime() != 0)){
            periodAddRez(conferenceRezAddParam);
            return;
        }
        Integer trueNum = addTrue(conferenceRezAddParam);
        if(trueNum > 0){
            String msg = trueNum == 3 ? "当前的时间已有预约" : "传入时间有误！";
            throw new CommonException(msg);
        }
        ConferenceRez conferenceRez = BeanUtil.toBean(conferenceRezAddParam, ConferenceRez.class);
        String sn = CommonUtils.getTableSn(BizTableCommon.BIZ_CHART_REVIEWS, DbsConstant.SLAVE_1_DATASOURCE_NAME);
        conferenceRez.setSn(sn);
        this.save(conferenceRez);
        //异步推送消息
        Map<String,ConferenceConfig> conferenceConfig = conferenceConfig();
        String location = conferenceConfig.get(conferenceRez.getConferenceConfigId()) != null ? conferenceConfig.get(conferenceRez.getConferenceConfigId()).getName() : "";
        ConferenceMsgEvent conferenceMsgEvent = new ConferenceMsgEvent(conferenceRezAddParam.getJoinUser());
        conferenceMsgEvent.setTheme(conferenceRezAddParam.getTheme());
        String theme = ObjectUtil.isNotEmpty(conferenceRez) ? conferenceRez.getTheme() :  "";
        String confTime = ObjectUtil.isNotEmpty(conferenceRez) && ObjectUtil.isNotEmpty(conferenceRez.getBeginTime()) && ObjectUtil.isNotEmpty(conferenceRez.getEndTime())?
                DateUtil.format(conferenceRez.getBeginTime(), "MM-dd")+" "+DateUtil.format(conferenceRez.getBeginTime(),"HH:mm")+"-"+DateUtil.format(conferenceRez.getEndTime(),"HH:mm") : "";
        conferenceMsgEvent.setConfTime(confTime);
        conferenceMsgEvent.setLocation(location);
        SysUser sysUser = sysUserService.getById(conferenceRez.getRezUser());
        conferenceMsgEvent.setRezUser(sysUser.getName());
        eventPublisher.publishEvent(conferenceMsgEvent);
    }
    private Map<String,ConferenceConfig> conferenceConfig(){
        Map<String,ConferenceConfig> configMap = new HashMap<>();
        List<ConferenceConfig> configList = conferenceConfigService.list();
        configList.forEach(config -> configMap.put(config.getId(),config));
        return configMap;
    }
    private Integer addTrue(ConferenceRezAddParam conferenceRezAddParam){
        //开始时间>=结束时间
        if(conferenceRezAddParam.getBeginTime().compareTo(conferenceRezAddParam.getEndTime()) >= 0){ return 1; }
        Date date = DateUtil.date();
        //结束时间 < 当前时间
        if(conferenceRezAddParam.getEndTime().compareTo(date) < 0 ){ return 2; }
        Integer integer = conferenceRezAddParam.getBeginTime().getHours()-conferenceRezAddParam.getEndTime().getHours();
        if(integer > 12){
            return 2;
        }

        // 已有会议开始时间 <= 预约开始时间  && 已有会议结束时间  >= 预约结束时间  包含
        Long beginNum = this.count(new LambdaQueryWrapper<ConferenceRez>().le(ConferenceRez::getBeginTime,conferenceRezAddParam.getBeginTime())
                .ge(ConferenceRez::getEndTime,conferenceRezAddParam.getEndTime()).eq(ConferenceRez::getConferenceConfigId,conferenceRezAddParam.getConferenceConfigId()));

        // 预约开始时间 <= 已有会议开始时间  && 预约结束时间 >= 已有会议结束时间  包含
        Long endNum = this.count(new LambdaQueryWrapper<ConferenceRez>().ge(ConferenceRez::getBeginTime,conferenceRezAddParam.getBeginTime())
                .le(ConferenceRez::getEndTime,conferenceRezAddParam.getEndTime()).eq(ConferenceRez::getConferenceConfigId,conferenceRezAddParam.getConferenceConfigId()));

        /** 已有预约 */
        if(beginNum > 0 || endNum > 0){ return 3; }
        return 0;
    }

    /** 周期预约，当前只生成当前周或者下周一次标准会议，开一次生成一次，避免数据冗余*/
    private void periodAddRez(ConferenceRezAddParam conferenceRezAddParam){
        ConferenceRezPeriod conferenceRezPeriod = BeanUtil.toBean(conferenceRezAddParam, ConferenceRezPeriod.class);
        conferenceRezPeriodService.save(conferenceRezPeriod);
        String beginTimeParam = CommonTimeFormatUtil.dateToStr(conferenceRezAddParam.getBeginTime(),DatePattern.NORM_TIME_PATTERN);
        String endTimeParam = CommonTimeFormatUtil.dateToStr(conferenceRezAddParam.getEndTime(),DatePattern.NORM_TIME_PATTERN);

        List<ConferenceRez>  conferenceRezList = new ArrayList<>();
        /** 周期生成标准会议：周 有效期三个月，月 有效期一年*/
        if(ObjectUtil.isNotEmpty(conferenceRezAddParam.getWeekTime())){

            Date endTime = conferenceRezAddParam.getBeginTime();
            /** 获取下月和下下月的时间*/
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localTime = LocalDate.parse(CommonTimeFormatUtil.dateToStr(conferenceRezAddParam.getBeginTime(),DatePattern.NORM_DATE_PATTERN), formatter);
            LocalDate endOfMonth = localTime.with(TemporalAdjusters.lastDayOfMonth());//月最后一天
            LocalDate  endLocalDate = endOfMonth.minusMonths(-2);
            try{
                endTime = DateUtils.parseDate(endLocalDate.format(formatter)+HrUserAttendanceStatEnum.BEGIN_TIMNE.getValue());
                List<Date> periodWeekTime = CommonTimeFormatUtil.periodWeekTime(conferenceRezAddParam.getBeginTime(),endTime,conferenceRezAddParam.getWeekTime());
                periodWeekTime.forEach(time ->{
                    String strTime = CommonTimeFormatUtil.dateToStr(time,DatePattern.NORM_DATE_PATTERN);
                    ConferenceRez conferenceRez = BeanUtil.toBean(conferenceRezAddParam, ConferenceRez.class);
                    conferenceRez.setRezPeriodId(conferenceRezPeriod.getId());
                    Date begin = CommonTimeFormatUtil.strToDate(strTime+" "+beginTimeParam,DatePattern.NORM_DATETIME_PATTERN);
                    Date end = CommonTimeFormatUtil.strToDate(strTime+" "+endTimeParam,DatePattern.NORM_DATETIME_PATTERN);
                    ConferenceRezAddParam param = new ConferenceRezAddParam();
                    param.setBeginTime(begin);
                    param.setEndTime(end);
                    Integer trueNum = addTrue(param);
                    if(trueNum > 0){
                        String msg = trueNum == 3 ? "当前的时间已有预约" : "传入时间有误！";
                        throw new CommonException(msg);
                    }
                    conferenceRez.setBeginTime(begin);
                    conferenceRez.setEndTime(end);
                    String sn = CommonUtils.getTableSn(BizTableCommon.BIZ_CHART_REVIEWS, DbsConstant.SLAVE_1_DATASOURCE_NAME);
                    conferenceRez.setSn(sn);
                    this.save(conferenceRez);
                });
            }catch (Exception e){
                log.error("时间生成错误！");
            }
           return;
        }
        if(ObjectUtil.isNotEmpty(conferenceRezAddParam.getDayTime())){

        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ConferenceRezEditParam conferenceRezEditParam) {
       /* if(StringUtils.isNotEmpty(conferenceRezEditParam.getRezPeriodId())){
            editPeriod(conferenceRezEditParam);
            return;
        }*/
        ConferenceRez conferenceRez = this.queryEntity(conferenceRezEditParam.getId());
        BeanUtil.copyProperties(conferenceRezEditParam, conferenceRez);
        this.updateById(conferenceRez);
    }
    private void editPeriod(ConferenceRezEditParam conferenceRezEditParam){
        ConferenceRezPeriod conferenceRezPeriod = conferenceRezPeriodService.getById(conferenceRezEditParam.getRezPeriodId());
        BeanUtil.copyProperties(conferenceRezEditParam, conferenceRezPeriod);
        List<ConferenceRez> conferenceRezList = this.list(new LambdaQueryWrapper<ConferenceRez>()
                .eq(ConferenceRez::getRezPeriodId,conferenceRezEditParam.getRezPeriodId())
                .lt(ConferenceRez::getEndTime,DateUtil.date()));
        List<String> rezIds = conferenceRezList.stream().map(rez -> rez.getId()).collect(Collectors.toList());
        this.removeBatchByIds(rezIds);
        ConferenceRezAddParam conferenceRezAddParam = BeanUtil.toBean(conferenceRezEditParam,ConferenceRezAddParam.class);
        periodAddRez(conferenceRezAddParam);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ConferenceRezIdParam> conferenceRezIdParamList) {
        if(CollectionUtil.isNotEmpty(conferenceRezIdParamList) && StringUtils.isNotEmpty(conferenceRezIdParamList.get(0).getRezPeriodId())){
            List<ConferenceRez> conferenceRezList = this.list(new LambdaQueryWrapper<ConferenceRez>()
                    .eq(ConferenceRez::getRezPeriodId,conferenceRezIdParamList.get(0).getRezPeriodId())
                    .ge(ConferenceRez::getEndTime,DateUtil.date()));
            List<String> rezIds = conferenceRezList.stream().map(rez -> rez.getId()).collect(Collectors.toList());
            this.removeBatchByIds(rezIds);
            conferenceRezPeriodService.removeById(conferenceRezIdParamList.get(0).getRezPeriodId());
            return;
        }
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(conferenceRezIdParamList, ConferenceRezIdParam::getId));
    }

    @Override
    public ConferenceRez detail(ConferenceRezIdParam conferenceRezIdParam) {
        return this.queryEntity(conferenceRezIdParam.getId());
    }

    @Override
    public ConferenceRez queryEntity(String id) {
        ConferenceRez conferenceRez = this.getById(id);
        if(ObjectUtil.isEmpty(conferenceRez)) {
            throw new CommonException("会议模块-预约会议不存在，id值为：{}", id);
        }
        return conferenceRez;
    }

    @Override
    public List<ConferenceTimeData> conferenceList(ConferenceTimeDataParam conferenceTimeDataParam){
        if(StringUtils.isEmpty(conferenceTimeDataParam.getDay())){
            conferenceTimeDataParam.setDay(DateUtil.format(new Date(),DatePattern.NORM_DATE_PATTERN));
        }
        Map<String,String> userMap = getUserData();//获取人员数据
        List<ConferenceTimeData> conferenceTimeDataList = new ArrayList<>();
        String beginTime = conferenceTimeDataParam.getDay()+ HrUserAttendanceStatEnum.BEGIN_TIMNE.getValue();
        //所有会议室数据
        List<ConferenceConfig> conferenceConfigList = conferenceConfigService.list();
        //非周期预约数据
        List<ConferenceRez> conferenceRezList = this.list(new LambdaQueryWrapper<ConferenceRez>()
                .ge(ConferenceRez::getBeginTime,conferenceTimeDataParam.getDay()+ HrUserAttendanceStatEnum.BEGIN_TIMNE.getValue())
                .le(ConferenceRez::getEndTime,conferenceTimeDataParam.getDay()+ HrUserAttendanceStatEnum.END_TIME.getValue()));

        conferenceRezList.forEach(rez -> {
            if(ObjectUtil.isNotEmpty(rez.getBeginTime())){
                rez.setBeginTime(countMinute(rez.getBeginTime()));
            }
            if(ObjectUtil.isNotEmpty(rez.getEndTime())){
                rez.setEndTime(countMinute(rez.getEndTime()));
            }
        });
        Map<String,List<ConferenceRez>> map = new HashMap<>();//key conferenceConfigId ,value ConferenceRez
        conferenceRezList.forEach(rez -> {
            List<ConferenceRez> conferenceRezs = map.get(rez.getConferenceConfigId()) != null ?  map.get(rez.getConferenceConfigId()) : new ArrayList<>();
            conferenceRezs.add(rez);
            map.put(rez.getConferenceConfigId(),conferenceRezs);
        });

        conferenceConfigList.forEach(config -> {
            List<ConferenceTime> conferenceTimes = conferenceTimes(conferenceTimeDataParam.getDay());
            ConferenceTimeData conferenceTimeData = new ConferenceTimeData();
            conferenceTimeData.setConferenceTimes(conferenceTimes);
            conferenceTimeData.setConferencename(config.getName());
            conferenceTimeData.setConferenceId(config.getId());
            conferenceTimeData.setContainMax(config.getContainMax());
            conferenceTimeData.setFileId(config.getFileId());
            conferenceTimeData.setDevice(config.getDevice());
            List<ConferenceRez> rezList = map.get(config.getId()) == null ? new ArrayList<>() :  map.get(config.getId());
            if(CollectionUtil.isEmpty(rezList)){
                conferenceTimeData.setConferenceTimes(conferenceTimes);
            }else{
                conferenceRezTime(rezList,conferenceTimeData,userMap);
            }
            conferenceTimeDataList.add(conferenceTimeData);
        });
        return conferenceTimeDataList;
    }

    private void conferenceRezTime(List<ConferenceRez> rezList,ConferenceTimeData conferenceTimeData,Map<String,String> userMap){
        if(CollectionUtil.isNotEmpty(rezList)){
            Map<String,ConferenceRez> map = new HashMap<>();//key time,value rez
            rezList.forEach(rez -> {
                while (rez.getBeginTime().compareTo(rez.getEndTime()) < 0){
                    Date time1 = rez.getBeginTime();
                    Date time2 = DateUtil.offsetMinute(rez.getBeginTime(),30);
                    map.put(CommonTimeFormatUtil.dateToStr(time1,"HH:mm")+"-"+CommonTimeFormatUtil.dateToStr(time2,"HH:mm"),rez);
                    rez.setBeginTime(time2);
                }
            });
            conferenceTimeData.getConferenceTimes().forEach(conferenceTime -> {
                if(map.get(conferenceTime.getTime()) != null){
                    ConferenceRez rez = map.get(conferenceTime.getTime());
                    conferenceTime.setStatus(2);
                    conferenceTime.setRezUserID(rez.getRezUser());
                    conferenceTime.setRezUserName(userMap.get(rez.getRezUser()));
                    conferenceTime.setRezId(rez.getId());
                }
            });
        }
    }

    private List<ConferenceTime> conferenceTimes(String day){
        /**7:30:00 - 23:00:00 */
        Date beginTime = CommonTimeFormatUtil.strToDate(day+" 8:00:00", DatePattern.NORM_DATETIME_PATTERN);
        Date endTime = CommonTimeFormatUtil.strToDate(day+" 18:00:00",DatePattern.NORM_DATETIME_PATTERN);
        List<ConferenceTime> conferenceTimeList = new ArrayList<>();
        while (beginTime.compareTo(endTime) < 0){
            Date time1 = beginTime;
            ConferenceTime conferenceTime = new ConferenceTime();
            conferenceTime.setStatus(1);
            conferenceTime.setBeginTime(beginTime);
            Date time2 = DateUtil.offsetMinute(beginTime,30);
            conferenceTime.setEndTime(time2);
            beginTime = time2;
            conferenceTime.setTime(CommonTimeFormatUtil.dateToStr(time1,"HH:mm")+"-"+CommonTimeFormatUtil.dateToStr(time2,"HH:mm"));
            conferenceTimeList.add(conferenceTime);
        }
        return conferenceTimeList;
    }

    private Date countMinute(Date time){
        Integer  minute = Integer.parseInt(DateUtil.format(time,"mm"));
        if(minute == 0 || minute == 30){
            return time;
        }
        String timeStr = DateUtil.format(time,"yyyy-MM-dd HH");
        String minuteStr = "";
        if(minute < 30){
            minuteStr = ":00:00";
        }else{
            minuteStr = ":30:00";
        }
        return CommonTimeFormatUtil.strToDate(timeStr+minuteStr,DatePattern.NORM_DATETIME_PATTERN);
    }

    @Override
    public  Map<String,String> getUserData(){
        Map<String,String> map = new HashMap<>();
        List<SysUser> sysUserList = sysUserService.list();
        sysUserList.forEach(user -> map.put(user.getId(),user.getName()));
        return map;
    }

    @Override
    public Map<String,String> getOrgData(){
        Map<String,String> map = new HashMap<>();
        List<SysOrg> orgList = sysOrgService.list();
        orgList.forEach(org -> map.put(org.getId(),org.getName()));
        return map;
    }
    @Override
    public Map<String,String> getPostionData(){
        Map<String,String> map = new HashMap<>();
        List<SysPosition> positionList = sysPositionService.list();
        positionList.forEach(position -> map.put(position.getId(),position.getName()));
        return map;
    }

    @EventListener
    @Async
    public void handleConferenceMsgEvent(ConferenceMsgEvent conferenceMsgEvent){
        // TODO 发送消息业务逻辑
        //ConferenceMsgEvent event = conferenceMsgEvent;
        if(StringUtils.isNotEmpty(conferenceMsgEvent.getUserIds())){
            List<String> userIds = Arrays.asList(conferenceMsgEvent.getUserIds().split(","));
            List<SysUser> sysUserList = sysUserService.list(new LambdaQueryWrapper<SysUser>().in(SysUser::getId,userIds));
            sysUserList.forEach(user -> {
                conferenceMsgEvent.setPhone(user.getAccount());
                sendConfMsg(conferenceMsgEvent, "【会议通知】",0);
            });
        }
    }
    /**
     * 发送 会议通知
     */
    @Override
    public OapiMessageCorpconversationAsyncsendV2Response sendConfMsg(ConferenceMsgEvent conferenceMsgEvent, String title, Integer type) {
        OapiV2UserGetbymobileResponse oapiV2UserGetbymobileResponse = iDingDingService.getByMobile(conferenceMsgEvent.getPhone());
        if (!oapiV2UserGetbymobileResponse.isSuccess()) {
            log.error(oapiV2UserGetbymobileResponse.getErrmsg());
            return null;
        }
        OapiV2UserGetbymobileResponse.UserGetByMobileResponse result = oapiV2UserGetbymobileResponse.getResult();

        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        // yourAgentId
        request.setAgentId(dingTalkProperties.getAgentId());
        request.setUseridList(result.getUserid());
        request.setToAllUser(false);
        OapiMessageCorpconversationAsyncsendV2Request.Body body = new OapiMessageCorpconversationAsyncsendV2Request.Body();

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype("oa");
        msg.setOa(buildOa(conferenceMsgEvent,title,type));
        request.setMsg(msg);

        OapiGettokenResponse oapiGettokenResponse = iDingDingService.getToken();
        String accessToken = oapiGettokenResponse.getAccessToken();
        try {
            return client.execute(request, accessToken);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    private OapiMessageCorpconversationAsyncsendV2Request.OA buildOa(ConferenceMsgEvent conferenceMsgEvent,String title,Integer type){
        OapiMessageCorpconversationAsyncsendV2Request.OA oa = new OapiMessageCorpconversationAsyncsendV2Request.OA();
        OapiMessageCorpconversationAsyncsendV2Request.Head head = new OapiMessageCorpconversationAsyncsendV2Request.Head();
        head.setText("会议通知");
        head.setBgcolor("FFBBBBBB");
        oa.setHead(head);

        OapiMessageCorpconversationAsyncsendV2Request.Body body = new OapiMessageCorpconversationAsyncsendV2Request.Body();
        body.setTitle(title);
        String theme = conferenceMsgEvent.getTheme();
        String confTime = conferenceMsgEvent.getConfTime();
        String location = conferenceMsgEvent.getLocation();
        String rezUser = conferenceMsgEvent.getRezUser();
        String presideUser = conferenceMsgEvent.getPresideUser();

        OapiMessageCorpconversationAsyncsendV2Request.Form form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        List<OapiMessageCorpconversationAsyncsendV2Request.Form> forms = new ArrayList<>();

        form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        form.setKey("会议主题：");
        form.setValue(theme);
        forms.add(form);

        form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        form.setKey("会议时间：");
        form.setValue(confTime);
        forms.add(form);

        form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        form.setKey("会议地点：");
        form.setValue(location);
        forms.add(form);

        form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        form.setKey("预约人：");
        form.setValue(rezUser);
        forms.add(form);

        body.setForm(forms);
        oa.setBody(body);

        return oa;
    }

    @Override
    public void recordNotice(String rezId){

    }
}
