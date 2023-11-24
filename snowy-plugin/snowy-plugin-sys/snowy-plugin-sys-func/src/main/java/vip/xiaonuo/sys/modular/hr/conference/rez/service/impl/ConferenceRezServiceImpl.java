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
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
import vip.xiaonuo.sys.modular.hr.conference.config.entity.ConferenceConfig;
import vip.xiaonuo.sys.modular.hr.conference.config.param.ConferenceTimeDataParam;
import vip.xiaonuo.sys.modular.hr.conference.config.service.ConferenceConfigService;
import vip.xiaonuo.sys.modular.hr.conference.rez.entity.ConferenceRez;
import vip.xiaonuo.sys.modular.hr.conference.rez.mapper.ConferenceRezMapper;
import vip.xiaonuo.sys.modular.hr.conference.rez.param.*;
import vip.xiaonuo.sys.modular.hr.conference.rez.service.ConferenceRezService;
import vip.xiaonuo.sys.modular.hr.conference.rez.vo.ConferenceMsgEvent;
import vip.xiaonuo.sys.modular.hr.conference.rez.vo.ConferenceTime;
import vip.xiaonuo.sys.modular.hr.conference.rez.vo.ConferenceTimeData;
import vip.xiaonuo.sys.modular.hr.conference.rezPeriod.entity.ConferenceRezPeriod;
import vip.xiaonuo.sys.modular.hr.conference.rezPeriod.service.ConferenceRezPeriodService;
import vip.xiaonuo.sys.modular.hr.userApplyHoliday.enums.HrUserApplyHolidayEnum;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.enums.HrUserAttendanceStatEnum;
import vip.xiaonuo.sys.modular.org.entity.SysOrg;
import vip.xiaonuo.sys.modular.org.service.SysOrgService;
import vip.xiaonuo.sys.modular.position.entity.SysPosition;
import vip.xiaonuo.sys.modular.position.service.SysPositionService;
import vip.xiaonuo.sys.modular.user.entity.SysUser;
import vip.xiaonuo.sys.modular.user.service.SysUserService;


import javax.annotation.Resource;
import java.sql.Time;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 会议预约-预约会议表数据Service接口实现类
 *
 * @author ljy
 * @date  2023/11/05 23:13
 **/
@Service
public class ConferenceRezServiceImpl extends ServiceImpl<ConferenceRezMapper, ConferenceRez> implements ConferenceRezService {

    @Resource
    private ApplicationEventPublisher eventPublisher;
    @Resource
    private ConferenceConfigService conferenceConfigService;
    @Resource
    private ConferenceRezPeriodService conferenceRezPeriodService;
    @Resource
    private SysUserService sysUserService;
    @Resource
    SysOrgService sysOrgService;
    @Resource
    SysPositionService sysPositionService;

    @Override
    public Page<ConferenceRez> page(ConferenceRezPageParam conferenceRezPageParam) {
        QueryWrapper<ConferenceRez> queryWrapper = new QueryWrapper<>();
        QueryWrapper<ConferenceRezPeriod> queryPeriod = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(conferenceRezPageParam.getConferenceConfigId())) {
            queryWrapper.lambda().eq(ConferenceRez::getConferenceConfigId, conferenceRezPageParam.getConferenceConfigId());
            queryPeriod.lambda().eq(ConferenceRezPeriod::getConferenceConfigId, conferenceRezPageParam.getConferenceConfigId());
        }
        if(ObjectUtil.isNotEmpty(conferenceRezPageParam.getRezUser())) {
            queryWrapper.lambda().eq(ConferenceRez::getRezUser, conferenceRezPageParam.getRezUser());
            queryPeriod.lambda().eq(ConferenceRezPeriod::getRezUser, conferenceRezPageParam.getRezUser());
        }
        if(ObjectUtil.isAllNotEmpty(conferenceRezPageParam.getSortField(), conferenceRezPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(conferenceRezPageParam.getSortOrder());
            queryWrapper.orderBy(true, conferenceRezPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(conferenceRezPageParam.getSortField()));
            queryPeriod.orderBy(true, conferenceRezPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(conferenceRezPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(ConferenceRez::getId);
            queryPeriod.lambda().orderByAsc(ConferenceRezPeriod::getId);
        }
        if(StringUtils.isNotEmpty(conferenceRezPageParam.getBeginTime()) && StringUtils.isNotEmpty(conferenceRezPageParam.getEndTime())){
            queryWrapper.lambda().ge(ConferenceRez::getBeginTime,conferenceRezPageParam.getBeginTime()).le(ConferenceRez::getEndTime,conferenceRezPageParam.getEndTime());//会议时间区间在开始时间和结束时间之间
            queryPeriod.lambda().ge(ConferenceRezPeriod::getBeginTime,conferenceRezPageParam.getBeginTime()).le(ConferenceRezPeriod::getEndTime,conferenceRezPageParam.getEndTime());
        }
        Page<ConferenceRez> page = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        List<ConferenceRez> conferenceRezList = page.getRecords();
        conferenceRezPeriodService.list(queryPeriod).forEach(period -> {
            ConferenceRez conferenceRez = BeanUtil.toBean(period,ConferenceRez.class);
            conferenceRez.setConferenceType("1");
            conferenceRezList.add(conferenceRez);
        });
        if(CollectionUtil.isNotEmpty(conferenceRezList)){
            conferenceRezList.forEach(rez -> rez.setShowTime(caltTime(rez)));
            page.setRecords(conferenceRezList);
            page.setTotal(conferenceRezList.size());
        }
        page.getRecords().forEach(rez ->{
            List<String> rezAgendas = StringUtils.isNotEmpty(rez.getRezAgenda()) ? Arrays.asList(rez.getRezAgenda().trim().split(",")) : new ArrayList<>();
            rez.setRezAgendas(rezAgendas);
        });
        if(StringUtils.isNotEmpty(conferenceRezPageParam.getJoinUserId())){
            filterJoinUser(page, conferenceRezPageParam.getJoinUserId());
        }
        return page;
    }

    private void filterJoinUser(Page<ConferenceRez> page,String filterUserId){
        if(page.getRecords().size() > 0){
            List<ConferenceRez> rezList = new ArrayList<>();
            for(ConferenceRez rez : page.getRecords()){
                List<String> userIds = StringUtils.isNotEmpty(rez.getJoinUser()) ? Arrays.asList(rez.getJoinUser().trim().split(",")) : new ArrayList<>();
                if(userIds.stream().filter(id -> id.equals(filterUserId)).collect(Collectors.toList()).size() > 0){
                    rezList.add(rez);
                }
            }
            page.setRecords(rezList);
        }
    }

    private String caltTime(ConferenceRez rez){
        String caltTime = "";
        String day = DateUtil.format(rez.getBeginTime(),DatePattern.NORM_DATE_PATTERN);
        if(day.equals(DateUtil.format(new Date(),DatePattern.NORM_DATE_PATTERN))){
            caltTime =  "今天 "+DateUtil.format(rez.getBeginTime(),"HH:mm")+"~"+DateUtil.format(rez.getEndTime(),"HH:mm");
        }else{
            caltTime =  DateUtil.format(rez.getBeginTime(),DatePattern.NORM_DATE_PATTERN)+" "+DateUtil.format(rez.getBeginTime(),"HH:mm")+"~"+DateUtil.format(rez.getEndTime(),"HH:mm");
        }
        return caltTime;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ConferenceRezAddParam conferenceRezAddParam) {
        SaBaseLoginUser loginUser = StpLoginUserUtil.getLoginUser();
        if(StringUtils.isEmpty(conferenceRezAddParam.getRezUser())){
            conferenceRezAddParam.setRezUser(loginUser.getId());
        }
        Boolean isTrue = ObjectUtil.isNotEmpty(conferenceRezAddParam.getIsOnLine()) && conferenceRezAddParam.getIsOnLine() == 1? true : false;
        if(isTrue &&  StringUtils.isEmpty(conferenceRezAddParam.getOnLineCode())){
            throw new CommonException("勾选在线会议后，会议号不能为空！");
        }
        StringBuffer conAffUsers = new StringBuffer();
        if(CollectionUtil.isNotEmpty(conferenceRezAddParam.getConAffUsers())){
            conferenceRezAddParam.getConAffUsers().forEach(aff -> {
                conAffUsers.append(aff).append(",");
            });
        }
        if(ObjectUtil.isNotEmpty(conferenceRezAddParam.getStatus())){
            if(ObjectUtil.isEmpty(conferenceRezAddParam.getWeekTime()) && ObjectUtil.isEmpty(conferenceRezAddParam.getDayTime())){
                throw new CommonException("周期预约时，时间类型不能都为空");
            }
            updateConferenceRezPeriod(conferenceRezAddParam,conAffUsers.toString());
        }else{
            ConferenceRez conferenceRez = BeanUtil.toBean(conferenceRezAddParam, ConferenceRez.class);
            StringBuffer  rezAgenda = new StringBuffer();
            if(CollectionUtil.isNotEmpty(conferenceRezAddParam.getRezAgendas())){
                conferenceRezAddParam.getRezAgendas().forEach(agenda -> rezAgenda.append(agenda).append(","));
            }
            conferenceRez.setRezAgenda(ObjectUtil.isNotEmpty(rezAgenda) ? rezAgenda.toString() : "");
            this.save(conferenceRez);
        }
        //异步推送消息
        ConferenceMsgEvent conferenceMsgEvent = new ConferenceMsgEvent("","");
        eventPublisher.publishEvent(conferenceMsgEvent);
    }
    private void updateConferenceRezPeriod(ConferenceRezAddParam conferenceRezAddParam,String affUsers){
        ConferenceRezPeriod conferenceRezPeriod = BeanUtil.toBean(conferenceRezAddParam, ConferenceRezPeriod.class);
        conferenceRezPeriod.setConferenceConfigId(conferenceRezAddParam.getConferenceConfigId());
        //时间处理
        Time beginTime = Time.valueOf(CommonTimeFormatUtil.dateToStr(conferenceRezAddParam.getBeginTime(), DatePattern.NORM_TIME_PATTERN));
        Time endTime = Time.valueOf(CommonTimeFormatUtil.dateToStr(conferenceRezAddParam.getEndTime(), DatePattern.NORM_TIME_PATTERN));
        conferenceRezPeriod.setBeginTime(beginTime);
        conferenceRezPeriod.setEndTime(endTime);
        conferenceRezPeriod.setWeekTime(conferenceRezAddParam.getWeekTime());
        conferenceRezPeriod.setDayTime(conferenceRezAddParam.getDayTime());
        conferenceRezPeriod.setConAffUsers(StringUtils.isNotEmpty(affUsers) ? affUsers : "");
        //会议议程处理
        StringBuffer  rezAgenda = new StringBuffer();
        if(CollectionUtil.isNotEmpty(conferenceRezAddParam.getRezAgendas())){
            conferenceRezAddParam.getRezAgendas().forEach(agenda -> rezAgenda.append(agenda).append(","));
        }
        conferenceRezPeriod.setRezAgenda(ObjectUtil.isNotEmpty(rezAgenda) ? rezAgenda.toString() : "");
        conferenceRezPeriodService.save(conferenceRezPeriod);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ConferenceRezEditParam conferenceRezEditParam) {
        if(StringUtils.isNotEmpty(conferenceRezEditParam.getStatus()) && conferenceRezEditParam.getStatus().equals("1")){
            editPeriod(conferenceRezEditParam);
            return;
        }
        ConferenceRez conferenceRez = this.queryEntity(conferenceRezEditParam.getId());
        BeanUtil.copyProperties(conferenceRezEditParam, conferenceRez);
        this.updateById(conferenceRez);
    }
    private void editPeriod(ConferenceRezEditParam conferenceRezEditParam){
        ConferenceRezPeriod conferenceRezPeriod = conferenceRezPeriodService.queryEntity(conferenceRezEditParam.getId());
        BeanUtil.copyProperties(conferenceRezEditParam, conferenceRezPeriod);
        conferenceRezPeriodService.updateById(conferenceRezPeriod);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ConferenceRezIdParam> conferenceRezIdParamList) {
        List<ConferenceRezIdParam> rezList = new ArrayList<>();
        List<ConferenceRezIdParam> periodList = new ArrayList<>();
        conferenceRezIdParamList.forEach(param -> {
            if(StringUtils.isNotEmpty(param.getStatus()) && param.getStatus().equals("1")){
                periodList.add(param);
            }else {
                rezList.add(param);
            }
        });
        // 执行删除
        conferenceRezPeriodService.removeByIds(CollStreamUtil.toList(periodList, ConferenceRezIdParam::getId));
        this.removeByIds(CollStreamUtil.toList(rezList, ConferenceRezIdParam::getId));
    }

    @Override
    public ConferenceRez detail(ConferenceRezIdParam conferenceRezIdParam) {
        if(StringUtils.isNotEmpty(conferenceRezIdParam.getStatus()) && conferenceRezIdParam.getStatus().equals("1")){
            ConferenceRezPeriod conferenceRezPeriod = conferenceRezPeriodService.queryEntity(conferenceRezIdParam.getId());
            if(ObjectUtil.isNotEmpty(conferenceRezPeriod)){
                return BeanUtil.toBean(conferenceRezPeriod,ConferenceRez.class);
            }
        }
        return this.queryEntity(conferenceRezIdParam.getId());
    }

    @Override
    public ConferenceRez queryEntity(String id) {
        ConferenceRez conferenceRez = this.getById(id);
        if(ObjectUtil.isEmpty(conferenceRez)) {
            ConferenceRezPeriod conferenceRezPeriod = conferenceRezPeriodService.getById(id);
            if(ObjectUtil.isEmpty(conferenceRezPeriod)) {
                throw new CommonException("会议预约-预约会议表数据不存在，id值为：{}", id);
            }
            conferenceRez = BeanUtil.toBean(conferenceRezPeriod,ConferenceRez.class);
        }
        List<String> rezAgendas = StringUtils.isNotEmpty(conferenceRez.getRezAgenda()) ? Arrays.asList(conferenceRez.getRezAgenda().trim().split(",")) : new ArrayList<>();
        conferenceRez.setRezAgendas(rezAgendas);
        return conferenceRez;
    }

    @EventListener
    @Async
    public void handleConferenceMsgEvent(ConferenceMsgEvent conferenceMsgEvent){
        // TODO 发送消息业务逻辑
        ConferenceMsgEvent event = conferenceMsgEvent;
    }


    @Override
    public List<ConferenceConfig> conferenceRoom(ConferenceRoomParam conferenceRoomParam){
        if(ObjectUtil.isEmpty(conferenceRoomParam.getFreeFlag())){
            conferenceRoomParam.setFreeFlag(1);
        }
        List<ConferenceRez> conferenceRezList = this.list(new LambdaQueryWrapper<ConferenceRez>()
                .between(ConferenceRez::getBeginTime,conferenceRoomParam.getBeginTime(),conferenceRoomParam.getEndTime())
                .between(ConferenceRez::getEndTime,conferenceRoomParam.getBeginTime(),conferenceRoomParam.getEndTime()));
        Map<String,String> map = new HashMap<>();
        conferenceRezList.forEach(rez -> map.put(rez.getConferenceConfigId(),rez.getConferenceConfigId()));

        List<ConferenceRezPeriod> conferenceRezPeriodList = conferenceRezPeriodService.list(new LambdaQueryWrapper<ConferenceRezPeriod>()
                .between(ConferenceRezPeriod::getBeginTime,conferenceRoomParam.getBeginTime(),conferenceRoomParam.getEndTime())
                .between(ConferenceRezPeriod::getEndTime,conferenceRoomParam.getBeginTime(),conferenceRoomParam.getEndTime()));
        conferenceRezPeriodList.forEach(rezPeriod -> map.put(rezPeriod.getConferenceConfigId(),rezPeriod.getConferenceConfigId()));
        List<ConferenceConfig> conferenceConfigList = conferenceConfigService.list();


        return conferenceRoomParam.getFreeFlag() == 1 ?
                conferenceConfigList.stream().filter(config -> map.get(config.getId()) == null).collect(Collectors.toList()) :
                conferenceConfigList.stream().filter(config -> map.get(config.getId()) != null).collect(Collectors.toList());
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
        Map<String,List<ConferenceRez>> map = new HashMap<>();//key conferenceConfigId ,value ConferenceRez
        conferenceRezList.forEach(rez -> {
            List<ConferenceRez> conferenceRezs = map.get(rez.getConferenceConfigId()) != null ?  map.get(rez.getConferenceConfigId()) : new ArrayList<>();
            conferenceRezs.add(rez);
            map.put(rez.getConferenceConfigId(),conferenceRezs);
        });

        //周期预约数据
        int week = DateUtil.dayOfWeek(CommonTimeFormatUtil.strToDate(conferenceTimeDataParam.getDay()+HrUserAttendanceStatEnum.BEGIN_TIMNE.getValue(),
                DatePattern.NORM_DATETIME_PATTERN));
        List<ConferenceRezPeriod> periodList = conferenceRezPeriodService.list(new LambdaQueryWrapper<ConferenceRezPeriod>()
                .eq(ConferenceRezPeriod::getWeekTime,week-1 ==0 ? 7 : week-1)
                .le(ConferenceRezPeriod::getBeginTime,conferenceTimeDataParam.getDay()+ HrUserAttendanceStatEnum.BEGIN_TIMNE.getValue())
                .ge(ConferenceRezPeriod::getEndTime,conferenceTimeDataParam.getDay()+ HrUserAttendanceStatEnum.END_TIME.getValue()));
        Map<String,List<ConferenceRezPeriod>> periodMap = new HashMap<>();//key conferenceConfigId ,value ConferenceRez
        periodList.forEach(periodRez -> {
            List<ConferenceRezPeriod> periodRezList = periodMap.get(periodRez.getConferenceConfigId()) != null ?  periodMap.get(periodRez.getConferenceConfigId()) : new ArrayList<>();
            periodRezList.add(periodRez);
            periodMap.put(periodRez.getConferenceConfigId(),periodRezList);
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
            List<ConferenceRezPeriod> periodRezList = periodMap.get(config.getId()) == null ? new ArrayList<>() :  periodMap.get(config.getId());
            if(CollectionUtil.isEmpty(rezList) && CollectionUtil.isEmpty(periodRezList)){
                conferenceTimeData.setConferenceTimes(conferenceTimes);
            }else{
                conferenceTimeData(rezList,periodRezList,conferenceTimeData,userMap);
            }
            conferenceTimeDataList.add(conferenceTimeData);
        });
        return conferenceTimeDataList;
    }

    private void conferenceTimeData(List<ConferenceRez> rezList,List<ConferenceRezPeriod> periodRezList,ConferenceTimeData conferenceTimeData,Map<String,String> userMap){
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
                }
            });
        }

        if(CollectionUtil.isNotEmpty(periodRezList)){
            Map<String,ConferenceRezPeriod> map = new HashMap<>();//key time,value rez
            periodRezList.forEach(period -> {
                while (period.getBeginTime().compareTo(period.getEndTime()) < 0){
                    Date time1 = period.getBeginTime();
                    Date time2 = DateUtil.offsetMinute(time1,30);
                    map.put(CommonTimeFormatUtil.dateToStr(time1,"HH:mm")+"-"+CommonTimeFormatUtil.dateToStr(time2,"HH:mm"),period);
                    period.setBeginTime(time2);
                }
            });
            conferenceTimeData.getConferenceTimes().forEach(conferenceTime -> {
                if(map.get(conferenceTime.getTime()) != null){
                    ConferenceRezPeriod rez = map.get(conferenceTime.getTime());
                    conferenceTime.setStatus(2);
                    conferenceTime.setRezUserID(rez.getRezUser());
                    conferenceTime.setRezUserName(userMap.get(rez.getRezUser()));
                }
            });
        }
    }

    private List<ConferenceTime> conferenceTimes(String day){
        /**7:30:00 - 23:00:00 */
        Date beginTime = CommonTimeFormatUtil.strToDate(day+" 7:30:00",DatePattern.NORM_DATETIME_PATTERN);
        Date endTime = CommonTimeFormatUtil.strToDate(day+" 23:00:00",DatePattern.NORM_DATETIME_PATTERN);
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

}
