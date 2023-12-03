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
import vip.xiaonuo.sys.modular.hr.conference.rez.entity.ConferenceRezPeriod;
import vip.xiaonuo.sys.modular.hr.conference.rez.mapper.ConferenceRezMapper;
import vip.xiaonuo.sys.modular.hr.conference.rez.param.*;
import vip.xiaonuo.sys.modular.hr.conference.rez.service.ConferenceRezPeriodService;
import vip.xiaonuo.sys.modular.hr.conference.rez.service.ConferenceRezService;
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
        if(ObjectUtil.isNotEmpty(conferenceRezAddParam.getWeekTime()) && ObjectUtil.isNotEmpty(conferenceRezAddParam.getDayTime())){
            log.error("周期预约错误，参数错误：WeekTime："+conferenceRezAddParam.getWeekTime()+",dayTime:"+conferenceRezAddParam.getDayTime());
            throw new CommonException("周期预约错误，请联系管理员");
        }else if(ObjectUtil.isNotEmpty(conferenceRezAddParam.getWeekTime()) && ObjectUtil.isNotEmpty(conferenceRezAddParam.getDayTime())){
            periodAddRez(conferenceRezAddParam);
        }
        Integer trueNum = addTrue(conferenceRezAddParam);
        if(trueNum > 0){
            String msg = trueNum == 3 ? "当前的时间已有预约" : "传入时间有误！";
            throw new CommonException(msg);
        }
        SaBaseLoginUser loginUser = StpLoginUserUtil.getLoginUser();
        if(StringUtils.isEmpty(conferenceRezAddParam.getRezUser())){
            conferenceRezAddParam.setRezUser(loginUser.getId());
        }
        ConferenceRez conferenceRez = BeanUtil.toBean(conferenceRezAddParam, ConferenceRez.class);
        this.save(conferenceRez);
    }
    private Integer addTrue(ConferenceRezAddParam conferenceRezAddParam){
        //开始时间>=结束时间
        if(conferenceRezAddParam.getBeginTime().compareTo(conferenceRezAddParam.getEndTime()) >= 0){ return 1; }
        Date date = DateUtil.date();
        //开始时间或者结束时间 < 当前时间
        if(conferenceRezAddParam.getBeginTime().compareTo(date) < 0 || conferenceRezAddParam.getEndTime().compareTo(date) < 0 ){
            return 2;
        }
        /** 已有开始时间和结束时间*/
        Long perNum = this.count(new LambdaQueryWrapper<ConferenceRez>().gt(ConferenceRez::getBeginTime,conferenceRezAddParam.getBeginTime())
                .lt(ConferenceRez::getEndTime,conferenceRezAddParam.getEndTime()));
        Long beginNum = this.count(new LambdaQueryWrapper<ConferenceRez>().lt(ConferenceRez::getBeginTime,conferenceRezAddParam.getBeginTime())
                .gt(ConferenceRez::getEndTime,conferenceRezAddParam.getBeginTime()));
        Long endNum = this.count(new LambdaQueryWrapper<ConferenceRez>().lt(ConferenceRez::getBeginTime,conferenceRezAddParam.getEndTime())
                .gt(ConferenceRez::getEndTime,conferenceRezAddParam.getEndTime()));
        if(beginNum > 0 || endNum > 0 || perNum > 0){ return 3; }
        return 0;
    }

    /** 周期预约，当前只生成当前周或者下周一次标准会议，开一次生成一次，避免数据冗余*/
    private void periodAddRez(ConferenceRezAddParam conferenceRezAddParam){
        ConferenceRezPeriod conferenceRezPeriod = BeanUtil.toBean(conferenceRezAddParam, ConferenceRezPeriod.class);
        conferenceRezPeriodService.save(conferenceRezPeriod);
        List<ConferenceRez>  conferenceRezList = new ArrayList<>();
        /** 周期生成标准会议：周 有效期三个月，月 有效期一年*/
        if(ObjectUtil.isNotEmpty(conferenceRezAddParam.getWeekTime())){
            Calendar instance = Calendar.getInstance();
            instance.setTime(new Date());// 根据指定日期获取周几
            int nowWeek = instance.get(Calendar.DAY_OF_WEEK);
            instance.setTime(conferenceRezAddParam.getBeginTime());
            int periodWeek = instance.get(Calendar.DAY_OF_WEEK);
            if(nowWeek < periodWeek){  // 预约时间 > 当前时间
                ConferenceRez conferenceRez = BeanUtil.toBean(conferenceRezAddParam, ConferenceRez.class);
                conferenceRez.setRezPeriodId(conferenceRezPeriod.getId());
                this.save(conferenceRez);
                return;
            }
            LocalDate today = LocalDate.now();
            LocalDate nextMonday = today.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        }

        if(ObjectUtil.isNotEmpty(conferenceRezAddParam.getWeekTime())){

        }
    }
    //获取三月内每一周的某一天时间



    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ConferenceRezEditParam conferenceRezEditParam) {
        ConferenceRez conferenceRez = this.queryEntity(conferenceRezEditParam.getId());
        BeanUtil.copyProperties(conferenceRezEditParam, conferenceRez);
        this.updateById(conferenceRez);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ConferenceRezIdParam> conferenceRezIdParamList) {
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
