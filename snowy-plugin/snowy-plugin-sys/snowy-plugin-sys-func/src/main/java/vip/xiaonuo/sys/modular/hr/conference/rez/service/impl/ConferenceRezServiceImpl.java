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
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.common.util.CommonTimeFormatUtil;
import vip.xiaonuo.sys.modular.hr.conference.config.entity.ConferenceConfig;
import vip.xiaonuo.sys.modular.hr.conference.config.service.ConferenceConfigService;
import vip.xiaonuo.sys.modular.hr.conference.rez.entity.ConferenceRez;
import vip.xiaonuo.sys.modular.hr.conference.rez.mapper.ConferenceRezMapper;
import vip.xiaonuo.sys.modular.hr.conference.rez.param.*;
import vip.xiaonuo.sys.modular.hr.conference.rez.service.ConferenceRezService;
import vip.xiaonuo.sys.modular.hr.conference.rez.vo.ConferenceMsgEvent;


import javax.annotation.Resource;
import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Override
    public Page<ConferenceRez> page(ConferenceRezPageParam conferenceRezPageParam) {
        QueryWrapper<ConferenceRez> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(conferenceRezPageParam.getConferenceConfigId())) {
            queryWrapper.lambda().eq(ConferenceRez::getConferenceConfigId, conferenceRezPageParam.getConferenceConfigId());
        }
        if(ObjectUtil.isNotEmpty(conferenceRezPageParam.getRezUser())) {
            queryWrapper.lambda().eq(ConferenceRez::getRezUser, conferenceRezPageParam.getRezUser());
        }
        if(ObjectUtil.isAllNotEmpty(conferenceRezPageParam.getSortField(), conferenceRezPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(conferenceRezPageParam.getSortOrder());
            queryWrapper.orderBy(true, conferenceRezPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(conferenceRezPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(ConferenceRez::getId);
        }
        Page<ConferenceRez> page = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        List<ConferenceRez> conferenceRezList = page.getRecords();
        if(CollectionUtil.isNotEmpty(conferenceRezList)){
            conferenceRezList.forEach(rez -> rez.setShowTime(caltTime(rez)));
            page.setRecords(conferenceRezList);
        }
        return page;
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
        if(ObjectUtil.isNotEmpty(conferenceRezAddParam.getStatus())){
            Boolean isTrue = ObjectUtil.isEmpty(conferenceRezAddParam.getWeekTime()) && ObjectUtil.isEmpty(conferenceRezAddParam.getDayTime());
            if(isTrue){ throw new CommonException("周期预约时，时间类型不能都为空");}
            updateConferenceConfig(conferenceRezAddParam);
        }
        ConferenceRez conferenceRez = BeanUtil.toBean(conferenceRezAddParam, ConferenceRez.class);
        this.save(conferenceRez);
        //异步推送消息
        ConferenceMsgEvent conferenceMsgEvent = new ConferenceMsgEvent("","");
        eventPublisher.publishEvent(conferenceMsgEvent);
    }
    private void updateConferenceConfig(ConferenceRezAddParam conferenceRezAddParam){
        ConferenceConfig conferenceConfig = conferenceConfigService.queryEntity(conferenceRezAddParam.getConferenceConfigId());
        //时间处理
        Time beginTime = Time.valueOf(CommonTimeFormatUtil.dateToStr(conferenceRezAddParam.getBeginTime(), DatePattern.NORM_TIME_PATTERN));
        Time endTime = Time.valueOf(CommonTimeFormatUtil.dateToStr(conferenceRezAddParam.getEndTime(), DatePattern.NORM_TIME_PATTERN));
        conferenceConfig.setBeginTime(beginTime);
        conferenceConfig.setEndTime(endTime);
        conferenceConfig.setStatus(conferenceRezAddParam.getStatus());
        conferenceConfig.setWeekTime(conferenceRezAddParam.getWeekTime());
        conferenceConfig.setDayTime(conferenceConfig.getDayTime());
        conferenceConfigService.updateById(conferenceConfig);
    }

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
            throw new CommonException("会议预约-预约会议表数据不存在，id值为：{}", id);
        }
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
        List<ConferenceRez> conferenceRezList = this.list(new LambdaQueryWrapper<ConferenceRez>()
                .between(ConferenceRez::getBeginTime,conferenceRoomParam.getBeginTime(),conferenceRoomParam.getEndTime())
                .between(ConferenceRez::getEndTime,conferenceRoomParam.getBeginTime(),conferenceRoomParam.getEndTime()));
        Map<String,String> map = new HashMap<>();
        conferenceRezList.forEach(rez -> map.put(rez.getConferenceConfigId(),rez.getConferenceConfigId()));

        List<ConferenceConfig> conferenceConfigList = conferenceConfigService.list();
        conferenceConfigList = conferenceConfigList.stream().filter(config -> map.get(config.getId()) == null).collect(Collectors.toList());

        return conferenceConfigList;
    }

}
