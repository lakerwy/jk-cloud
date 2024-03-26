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
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
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
import org.springframework.beans.factory.annotation.Value;
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
import vip.xiaonuo.sys.modular.hr.conference.DingDingRec.DingdingCalendarUtil;
import vip.xiaonuo.sys.modular.hr.conference.config.entity.ConferenceConfig;
import vip.xiaonuo.sys.modular.hr.conference.config.param.ConferenceTimeDataParam;
import vip.xiaonuo.sys.modular.hr.conference.config.service.ConferenceConfigService;
import vip.xiaonuo.sys.modular.hr.conference.rez.entity.ConferenceRez;
import vip.xiaonuo.sys.modular.hr.conference.rez.entity.ConferenceRezPeriod;
import vip.xiaonuo.sys.modular.hr.conference.rez.mapper.ConferenceRezMapper;
import vip.xiaonuo.sys.modular.hr.conference.rez.param.*;
import vip.xiaonuo.sys.modular.hr.conference.rez.service.ConferenceRezPeriodService;
import vip.xiaonuo.sys.modular.hr.conference.rez.service.ConferenceRezService;
import vip.xiaonuo.sys.modular.hr.conference.rez.service.RuanLianApiService;
import vip.xiaonuo.sys.modular.hr.conference.rez.vo.*;
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

    private static final String BOOK_SUCCESS = "bookSuccess";
    private static final String BOOK_CANCEL = "bookCancel";
    private static final String EVENT_SUB_TYPE = "eventSubType";

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
    @Resource
    RuanLianApiService ruanLianApiService;

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
        if(ObjectUtil.isNotEmpty(conferenceRezPageParam.getBeginTime())) {
            queryWrapper.lambda().gt(ConferenceRez::getBeginTime, conferenceRezPageParam.getBeginTime());
        }
        if(ObjectUtil.isNotEmpty(conferenceRezPageParam.getEndTime())) {
            queryWrapper.lambda().lt(ConferenceRez::getSn, conferenceRezPageParam.getSn());
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
        outSideUserSign(page);
        return page;
    }
    //返回公司人员签到列表
    private void userSign(Page<ConferenceRez> page){
        if (CollectionUtil.isEmpty(page.getRecords())){
            return;
        }
        List<String> rezIds = page.getRecords().stream().map(rez -> rez.getId()).collect(Collectors.toList());
        List<ConferenceUserSign> signList  = conferenceUserSignService.list(new LambdaQueryWrapper<ConferenceUserSign>().in(ConferenceUserSign::getRezId,rezIds));
        Map<String,List<ConferenceUserSign>> map = new HashMap<>();
        signList.forEach(sign -> {
            if(ObjectUtil.isEmpty(sign.getIsOutside()) || sign.getIsOutside() != 1){
                List<ConferenceUserSign> signs = map.get(sign.getRezId()) != null ? map.get(sign.getRezId()) : new ArrayList<>();
                signs.add(sign);
                map.put(sign.getRezId(),signs);
            }
        });
        page.getRecords().forEach(rez -> {
            if(map.get(rez.getId()) != null){
                rez.setConferenceUserSignList(map.get(rez.getId()));
            }
        });
    }

    //返回外部人员签到列表
    private void outSideUserSign(Page<ConferenceRez> page){
        if (CollectionUtil.isEmpty(page.getRecords())){
            return;
        }
        List<String> rezIds = page.getRecords().stream().map(rez -> rez.getId()).collect(Collectors.toList());
        List<ConferenceUserSign> outsideSignList  = conferenceUserSignService.list(new LambdaQueryWrapper<ConferenceUserSign>().isNotNull(ConferenceUserSign::getIsOutside)
                .eq(ConferenceUserSign::getIsOutside,1).in(ConferenceUserSign::getRezId,rezIds));
        Map<String,List<ConferenceUserSign>> map = new HashMap<>();
        outsideSignList.forEach(sign -> {
            List<ConferenceUserSign> signs = map.get(sign.getRezId()) != null ? map.get(sign.getRezId()) : new ArrayList<>();
            signs.add(sign);
            map.put(sign.getRezId(),signs);
        });
        page.getRecords().forEach(rez -> {
            if(map.get(rez.getId()) != null){
                rez.setOutsideSignList(map.get(rez.getId()));
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
        Long configSize = conferenceConfigService.count(new LambdaQueryWrapper<ConferenceConfig>()
                .eq(ConferenceConfig::getId,conferenceRezAddParam.getConferenceConfigId())
                .eq(ConferenceConfig::getStatus,2)
                .gt(ConferenceConfig::getEndTime,conferenceRezAddParam.getBeginTime()));
        if(configSize > 0){
            throw new CommonException("会议室已经停用，无法被预约！");
        }
        if(ObjectUtil.isNotEmpty(conferenceRezAddParam.getWeekTime())  && conferenceRezAddParam.getWeekTime() != 0
                && ObjectUtil.isNotEmpty(conferenceRezAddParam.getDayTime()) && conferenceRezAddParam.getDayTime() != 0){
            log.error("周期预约错误，参数错误：WeekTime："+conferenceRezAddParam.getWeekTime()+",dayTime:"+conferenceRezAddParam.getDayTime());
            throw new CommonException("周期预约错误，请联系管理员");
        }else if((ObjectUtil.isNotEmpty(conferenceRezAddParam.getWeekTime()) && conferenceRezAddParam.getWeekTime() != 0)
                || (ObjectUtil.isNotEmpty(conferenceRezAddParam.getDayTime())  && conferenceRezAddParam.getDayTime() != 0)){
            AddConfPeriodEvent addConfPeriodEvent = new AddConfPeriodEvent(conferenceRezAddParam);
            eventPublisher.publishEvent(addConfPeriodEvent);
            //periodAddRez(conferenceRezAddParam);
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
        pushMsgEvent(conferenceRez,conferenceRezAddParam);
    }
    private void pushMsgEvent(ConferenceRez conferenceRez,ConferenceRezAddParam conferenceRezAddParam){
        //异步推送消息
        Map<String,ConferenceConfig> conferenceConfig = conferenceConfig();
        String location = conferenceConfig.get(conferenceRez.getConferenceConfigId()) != null ? conferenceConfig.get(conferenceRez.getConferenceConfigId()).getName() : "";
        String conferenceConfigId = conferenceConfig.get(conferenceRez.getConferenceConfigId()) != null ? conferenceConfig.get(conferenceRez.getConferenceConfigId()).getId() : "";
        ConferenceMsgEvent conferenceMsgEvent = new ConferenceMsgEvent(conferenceRezAddParam.getJoinUser());
        conferenceMsgEvent.setTheme(conferenceRezAddParam.getTheme());
        String confTime = ObjectUtil.isNotEmpty(conferenceRez) && ObjectUtil.isNotEmpty(conferenceRez.getBeginTime()) && ObjectUtil.isNotEmpty(conferenceRez.getEndTime())?
                DateUtil.format(conferenceRez.getBeginTime(), "MM-dd")+" "+DateUtil.format(conferenceRez.getBeginTime(),"HH:mm")+"-"+DateUtil.format(conferenceRez.getEndTime(),"HH:mm") : "";
        conferenceMsgEvent.setConfTime(confTime);
        conferenceMsgEvent.setConferenceConfigId(conferenceConfigId);
        conferenceMsgEvent.setLocation(location);
        SysUser sysUser = sysUserService.getById(conferenceRez.getRezUser());
        conferenceMsgEvent.setRezUser(sysUser.getName());
        conferenceMsgEvent.setRezId(conferenceRez.getId());
        conferenceMsgEvent.setOutsideUsers(conferenceRezAddParam.getOutsideUsers());
        eventPublisher.publishEvent(conferenceMsgEvent);
    }
    private Map<String,ConferenceConfig> conferenceConfig(){
        Map<String,ConferenceConfig> configMap = new HashMap<>();
        List<ConferenceConfig> configList = conferenceConfigService.list();
        configList.forEach(config -> configMap.put(config.getId(),config));
        return configMap;
    }

    @Override
    public Integer addTrue(ConferenceRezAddParam conferenceRezAddParam){
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ConferenceRezEditParam conferenceRezEditParam) {
        if(StringUtils.isNotEmpty(conferenceRezEditParam.getRezPeriodId())){
            editPeriod(conferenceRezEditParam);
            return;
        }
        ConferenceRez conferenceRez = this.queryEntity(conferenceRezEditParam.getId());
        BeanUtil.copyProperties(conferenceRezEditParam, conferenceRez);
        this.updateById(conferenceRez);
        //editJoinUser(conferenceRezEditParam,conferenceRez);
    }

    private void editJoinUser(ConferenceRezEditParam conferenceRezEditParam,ConferenceRez conferenceRez){
        List<ConferenceUserSign> signList = conferenceUserSignService.list(new LambdaQueryWrapper<ConferenceUserSign>()
                .eq(ConferenceUserSign::getRezId,conferenceRez.getId()));
        List<String> signIds = signList.stream().map(sign -> sign.getId()).collect(Collectors.toList());
        conferenceUserSignService.removeBatchByIds(signIds);
        Map<String,ConferenceUserSign> signMap = new HashMap<>();
        signList.forEach(sign -> signMap.put(sign.getUserId(),sign));
        List<ConferenceUserSign> conferenceUserSignList = new ArrayList();
        List<String> userIds = Arrays.asList(conferenceRezEditParam.getJoinUser().split(","));
        List<SysUser> sysUserList = sysUserService.list(new LambdaQueryWrapper<SysUser>().in(SysUser::getId,userIds));
        sysUserList.forEach(user -> {
            ConferenceUserSign conferenceUserSign = signMap.get(user.getId()) !=null?signMap.get(user.getId()) : new ConferenceUserSign();
            conferenceUserSign.setId(null);
            conferenceUserSign.setUserId(user.getId());
            conferenceUserSign.setUserSignName(user.getName());
            conferenceUserSign.setPhone(user.getAccount());
            conferenceUserSign.setConferenceConfigId(conferenceRez.getConferenceConfigId());
            conferenceUserSign.setConferenceConfigName(conferenceRez.getTheme());
            conferenceUserSign.setRezId(conferenceRez.getId());
            conferenceUserSign.setIsOutside(0);
            conferenceUserSignList.add(conferenceUserSign);
        });
        if(CollectionUtil.isNotEmpty(conferenceRezEditParam.getOutsideSignList())){
            conferenceRezEditParam.getOutsideSignList().forEach(outSideSign -> {
                if(StringUtils.isNotEmpty(outSideSign.getPhone())){
                    outSideSign.setId(null);
                    outSideSign.setRezId(conferenceRez.getId());
                    outSideSign.setConferenceConfigId(conferenceRez.getConferenceConfigId());
                    outSideSign.setConferenceConfigName(conferenceRez.getTheme());
                    outSideSign.setRezId(conferenceRez.getId());
                    conferenceUserSignList.add(outSideSign);
                }
            });
        }
        conferenceUserSignService.saveBatch(conferenceUserSignList);
    }

    private void editPeriod(ConferenceRezEditParam conferenceRezEditParam){
        ConferenceRezPeriod conferenceRezPeriod = conferenceRezPeriodService.getById(conferenceRezEditParam.getRezPeriodId());
        List<ConferenceRez> conferenceRezList = this.list(new LambdaQueryWrapper<ConferenceRez>()
                .eq(ConferenceRez::getRezPeriodId,conferenceRezEditParam.getRezPeriodId())
                .lt(ConferenceRez::getEndTime,DateUtil.date()));
        conferenceRezList.forEach(rez -> {
            BeanUtil.copyProperties(conferenceRezEditParam, rez);
            conferenceRezEditParam.setId(rez.getId());
            //editJoinUser(conferenceRezEditParam,rez);
        });
        if(CollectionUtil.isNotEmpty(conferenceRezList)){
            this.saveOrUpdateBatch(conferenceRezList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ConferenceRezIdParam> conferenceRezIdParamList) {
        List<ConferenceRez> conferenceRezList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(conferenceRezIdParamList) && StringUtils.isNotEmpty(conferenceRezIdParamList.get(0).getRezPeriodId())){
            conferenceRezList = this.list(new LambdaQueryWrapper<ConferenceRez>()
                    .eq(ConferenceRez::getRezPeriodId,conferenceRezIdParamList.get(0).getRezPeriodId())
                    .ge(ConferenceRez::getEndTime,DateUtil.date()));
            List<String> rezIds = conferenceRezList.stream().map(rez -> rez.getId()).collect(Collectors.toList());
            conferenceRezList.forEach(rez -> rez.setConferenceStatus(1));
            this.saveOrUpdateBatch(conferenceRezList);
            conferenceRezList.forEach(rez -> {
                JSONObject jsonObject = ruanLianApiService.delmeeting(rez.getYyid());
                if(jsonObject == null || !jsonObject.get("code").equals("1")){
                    log.error("取消会议失败！rezId："+rez.getId());
                }
            });//this.removeBatchByIds(rezIds);
            conferenceRezPeriodService.removeById(conferenceRezIdParamList.get(0).getRezPeriodId());
            return;
        }
        // 执行删除
        //this.removeByIds(CollStreamUtil.toList(conferenceRezIdParamList, ConferenceRezIdParam::getId));
        List<String> ids = conferenceRezIdParamList.stream().map(param -> param.getId()).collect(Collectors.toList());
        conferenceRezList = this.list(new LambdaQueryWrapper<ConferenceRez>().in(ConferenceRez::getId,ids));
        conferenceRezList.forEach(rez -> rez.setConferenceStatus(1));
        this.saveOrUpdateBatch(conferenceRezList);
        conferenceRezList.forEach(rez -> {
            JSONObject jsonObject =ruanLianApiService.delmeeting(rez.getYyid());
            if(jsonObject == null || !jsonObject.get("code").equals(1)){
                log.error("取消会议失败！rezId："+rez.getId());
            }
        });
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
        userDetailSign(conferenceRez);
        return conferenceRez;
    }
    /** 签到列表 */
    private void userDetailSign(ConferenceRez conferenceRez){
        if (ObjectUtil.isEmpty(conferenceRez)){
            return;
        }
        List<ConferenceUserSign> signList  = conferenceUserSignService.list(new LambdaQueryWrapper<ConferenceUserSign>().eq(ConferenceUserSign::getRezId,conferenceRez.getId()));
        List<ConferenceUserSign> conferenceUserSignList = new ArrayList<>();
        List<ConferenceUserSign> outsideSignList = new ArrayList<>();
        signList.forEach(sign -> {
            if(ObjectUtil.isEmpty(sign.getIsOutside()) || sign.getIsOutside() != 1){
                conferenceUserSignList.add(sign);
            }else{
                outsideSignList.add(sign);
            }
        });
        conferenceRez.setConferenceUserSignList(conferenceUserSignList);
        conferenceRez.setOutsideSignList(outsideSignList);
    }

    @Override
    public List<ConferenceTimeData> conferenceList(ConferenceTimeDataParam conferenceTimeDataParam){
        if(StringUtils.isEmpty(conferenceTimeDataParam.getDay())){
            conferenceTimeDataParam.setDay(DateUtil.format(new Date(),DatePattern.NORM_DATE_PATTERN));
        }
        Map<String,String> userMap = getUserData();//获取人员数据
        List<ConferenceTimeData> conferenceTimeDataList = new ArrayList<>();
        //String beginTime = conferenceTimeDataParam.getDay()+ HrUserAttendanceStatEnum.BEGIN_TIMNE.getValue();
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
    public void handleConferenceMsgEvent(ConferenceMsgEvent conferenceMsgEvent) throws Exception{
        // TODO 发送消息业务逻辑
        //putRuanLian(conferenceMsgEvent); 修改为定时任务
        if(StringUtils.isNotEmpty(conferenceMsgEvent.getUserIds())){
            List<String> userIds = Arrays.asList(conferenceMsgEvent.getUserIds().split(","));
            List<SysUser> sysUserList = sysUserService.list(new LambdaQueryWrapper<SysUser>().in(SysUser::getId,userIds));
            joinUser(sysUserList,conferenceMsgEvent);
            sysUserList.forEach(user -> {
                conferenceMsgEvent.setPhone(user.getAccount());
                sendConfMsg(conferenceMsgEvent, "【会议通知】",0);
            });
        }
    }

    @Override
    public void putRuanLian(RuanLianParam ruanLianParam)throws Exception{
        //RuanLianParam ruanLianParam = conferenceMsgEvent.getRuanLianParam();
        JSONObject jsonObject = ruanLianApiService.setmeeting(ruanLianParam);
        String code = jsonObject != null ? jsonObject.getStr("code") : "";
        if(!code.equals("1") ){
            log.error("软联会议预约失败！rezID:"+ruanLianParam.getRezId());
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

    @Override
    public void joinUser(List<SysUser> sysUserList,ConferenceMsgEvent conferenceMsgEvent){
        List<ConferenceUserSign> conferenceUserSignList = new ArrayList<>();
        sysUserList.forEach(user -> {
            ConferenceUserSign conferenceUserSign = new ConferenceUserSign();
            conferenceUserSign.setUserId(user.getId());
            conferenceUserSign.setUserSignName(user.getName());
            conferenceUserSign.setPhone(user.getAccount());
            conferenceUserSign.setConferenceConfigId(conferenceMsgEvent.getConferenceConfigId());
            conferenceUserSign.setConferenceConfigName(conferenceMsgEvent.getTheme());
            conferenceUserSign.setRezId(conferenceMsgEvent.getRezId());
            conferenceUserSign.setIsOutside(0);
            conferenceUserSignList.add(conferenceUserSign);
        });
        if(CollectionUtil.isNotEmpty(conferenceMsgEvent.getOutsideUsers())){
            conferenceMsgEvent.getOutsideUsers().forEach(outsideUser ->{
                ConferenceUserSign conferenceUserSign = new ConferenceUserSign();
                conferenceUserSign.setUserSignName(outsideUser.getOutsideUser());
                conferenceUserSign.setPhone(outsideUser.getPhone());
                conferenceUserSign.setIsOutside(1);
                conferenceUserSign.setConferenceConfigId(conferenceMsgEvent.getConferenceConfigId());
                conferenceUserSign.setConferenceConfigName(conferenceMsgEvent.getTheme());
                conferenceUserSign.setRezId(conferenceMsgEvent.getRezId());
                conferenceUserSignList.add(conferenceUserSign);
            });
        }
        conferenceUserSignService.saveBatch(conferenceUserSignList);
    }

    /** 释放会议*/
    @Override
    public void freeRez(String rezId){
        if(StringUtils.isEmpty(rezId)){
            throw new CommonException("找不到该会议");
        }
        ConferenceRez rez = this.queryEntity(rezId);
        rez.setEndTime(DateUtil.date());
        this.saveOrUpdate(rez);
    }

    @Override
    public void process(com.alibaba.fastjson2.JSONObject bizData, String bookId) throws Exception{
        String eventType = ObjectUtil.isNotEmpty(bizData.get(EVENT_SUB_TYPE)) ? bizData.get(EVENT_SUB_TYPE).toString() : "";
        String userId = "";
        if(ObjectUtil.isNotEmpty(bizData.get("userName"))){
            List<SysUser> sysUsers = sysUserService.list(new LambdaQueryWrapper<SysUser>().eq(SysUser::getName,bizData.get("userName").toString()));
            if(ObjectUtil.isNotEmpty(sysUsers)){ userId = sysUsers.get(0).getId(); }
        }
        String conferenceConfigId = "";
        if(ObjectUtil.isNotEmpty(bizData.get("roomName"))){ conferenceConfigId = conferenceConfigService.getIdForName(bizData.get("roomName").toString()); }
        String roomId = ObjectUtil.isNotEmpty(bizData.get("roomId")) ? bizData.get("roomId").toString() : "";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        switch(eventType){
            case BOOK_SUCCESS://预定会议
                List<ConferenceRez> conferenceRezListTemp = this.list(new LambdaQueryWrapper<ConferenceRez>().eq(ConferenceRez::getEventId,bookId));
                if(CollectionUtil.isNotEmpty(conferenceRezListTemp)){ break; }
                //theme 主题，beginTime-endTime开始和结束时间
                ConferenceRez conferenceRez = new ConferenceRez();
                conferenceRez.setExtJson(bizData.toString());
                conferenceRez.setEventId(bookId);
                if(ObjectUtil.isNotEmpty(bizData.get("startTime"))){
                    Date beginTime = sdf.parse(bizData.get("startTime").toString());
                    conferenceRez.setBeginTime(beginTime);
                }
                if(ObjectUtil.isNotEmpty(bizData.get("endTime"))){
                    Date endTime = sdf.parse(bizData.get("endTime").toString());
                    conferenceRez.setEndTime(endTime);
                }
                conferenceRez.setSn(DateUtil.format(new Date(),DatePattern.PURE_DATETIME_PATTERN)+roomId);
                conferenceRez.setConferenceConfigId(conferenceConfigId);
                conferenceRez.setTheme(ObjectUtil.isNotEmpty(bizData.get("title")) ? bizData.get("title").toString() : "");
                conferenceRez.setCreateUser(userId);
                conferenceRez.setJoinUser(ObjectUtil.isNotEmpty(bizData.get("joinUser")) ? bizData.get("joinUser").toString() : "");
                conferenceRez.setJoinSize(ObjectUtil.isNotEmpty(bizData.get("joinSize")) ? Integer.valueOf(bizData.get("joinSize").toString()) : 0);
                this.save(conferenceRez);
                break;
            case BOOK_CANCEL://取消会议
                List<ConferenceRez> conferenceRezList = this.list(new LambdaQueryWrapper<ConferenceRez>().eq(ConferenceRez::getEventId,bookId));
                if(CollectionUtil.isEmpty(conferenceRezList)){
                    log.error("未找到该会议数据！bookId："+bookId);
                    break;
                }
                try{
                    this.removeBatchByIds(conferenceRezList.stream().map(rez -> rez.getId()).collect(Collectors.toList()));
                }catch (Exception e){
                    log.error("删除数据失败！e:"+e.getMessage());
                }
                break;
        }
    }
}
