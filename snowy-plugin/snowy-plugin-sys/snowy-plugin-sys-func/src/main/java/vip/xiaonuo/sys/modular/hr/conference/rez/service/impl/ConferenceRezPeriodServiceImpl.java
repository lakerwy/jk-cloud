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
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.util.DateUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.common.util.CommonTimeFormatUtil;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.dbs.core.util.BizTableCommon;
import vip.xiaonuo.dbs.core.util.CommonUtils;
import vip.xiaonuo.sys.modular.hr.conference.config.entity.ConferenceConfig;
import vip.xiaonuo.sys.modular.hr.conference.config.service.ConferenceConfigService;
import vip.xiaonuo.sys.modular.hr.conference.rez.entity.ConferenceRez;
import vip.xiaonuo.sys.modular.hr.conference.rez.entity.ConferenceRezPeriod;
import vip.xiaonuo.sys.modular.hr.conference.rez.mapper.ConferenceRezPeriodMapper;
import vip.xiaonuo.sys.modular.hr.conference.rez.param.*;
import vip.xiaonuo.sys.modular.hr.conference.rez.service.ConferenceRezPeriodService;
import vip.xiaonuo.sys.modular.hr.conference.rez.service.ConferenceRezService;
import vip.xiaonuo.sys.modular.hr.conference.rez.vo.AddConfPeriodEvent;
import vip.xiaonuo.sys.modular.hr.conference.rez.vo.ConferenceMsgEvent;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.enums.HrUserAttendanceStatEnum;
import vip.xiaonuo.sys.modular.user.entity.SysUser;
import vip.xiaonuo.sys.modular.user.service.SysUserService;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 会议模块-周期预约会议Service接口实现类
 *
 * @author ljy
 * @date  2023/12/02 10:18
 **/
@Service
public class ConferenceRezPeriodServiceImpl extends ServiceImpl<ConferenceRezPeriodMapper, ConferenceRezPeriod> implements ConferenceRezPeriodService {

    @Resource
    ConferenceRezService conferenceRezService;
    @Resource
    ConferenceConfigService conferenceConfigService;

    @Resource
    SysUserService sysUserService;
    @Override
    public Page<ConferenceRezPeriod> page(ConferenceRezPeriodPageParam conferenceRezPeriodPageParam) {
        QueryWrapper<ConferenceRezPeriod> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(conferenceRezPeriodPageParam.getConferenceConfigId())) {
            queryWrapper.lambda().eq(ConferenceRezPeriod::getConferenceConfigId, conferenceRezPeriodPageParam.getConferenceConfigId());
        }
        if(ObjectUtil.isNotEmpty(conferenceRezPeriodPageParam.getRezUser())) {
            queryWrapper.lambda().eq(ConferenceRezPeriod::getRezUser, conferenceRezPeriodPageParam.getRezUser());
        }
        if(ObjectUtil.isNotEmpty(conferenceRezPeriodPageParam.getRecordUser())) {
            queryWrapper.lambda().eq(ConferenceRezPeriod::getRecordUser, conferenceRezPeriodPageParam.getRecordUser());
        }
        if(ObjectUtil.isNotEmpty(conferenceRezPeriodPageParam.getWeekTime())) {
            queryWrapper.lambda().eq(ConferenceRezPeriod::getWeekTime, conferenceRezPeriodPageParam.getWeekTime());
        }
        if(ObjectUtil.isNotEmpty(conferenceRezPeriodPageParam.getDayTime())) {
            queryWrapper.lambda().eq(ConferenceRezPeriod::getDayTime, conferenceRezPeriodPageParam.getDayTime());
        }
        if(ObjectUtil.isAllNotEmpty(conferenceRezPeriodPageParam.getSortField(), conferenceRezPeriodPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(conferenceRezPeriodPageParam.getSortOrder());
            queryWrapper.orderBy(true, conferenceRezPeriodPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(conferenceRezPeriodPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(ConferenceRezPeriod::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ConferenceRezPeriodAddParam conferenceRezPeriodAddParam) {
        ConferenceRezPeriod conferenceRezPeriod = BeanUtil.toBean(conferenceRezPeriodAddParam, ConferenceRezPeriod.class);
        this.save(conferenceRezPeriod);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ConferenceRezPeriodEditParam conferenceRezPeriodEditParam) {
        ConferenceRezPeriod conferenceRezPeriod = this.queryEntity(conferenceRezPeriodEditParam.getId());
        BeanUtil.copyProperties(conferenceRezPeriodEditParam, conferenceRezPeriod);
        this.updateById(conferenceRezPeriod);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ConferenceRezPeriodIdParam> conferenceRezPeriodIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(conferenceRezPeriodIdParamList, ConferenceRezPeriodIdParam::getId));
    }

    @Override
    public ConferenceRezPeriod detail(ConferenceRezPeriodIdParam conferenceRezPeriodIdParam) {
        return this.queryEntity(conferenceRezPeriodIdParam.getId());
    }

    @Override
    public ConferenceRezPeriod queryEntity(String id) {
        ConferenceRezPeriod conferenceRezPeriod = this.getById(id);
        if(ObjectUtil.isEmpty(conferenceRezPeriod)) {
            throw new CommonException("会议模块-周期预约会议不存在，id值为：{}", id);
        }
        return conferenceRezPeriod;
    }

    /** 周期预约，当前只生成当前周或者下周一次标准会议，开一次生成一次，避免数据冗余*/
    /** 修改为异步 */
    @Override
    @EventListener
    @Async
    public void periodAddRez(AddConfPeriodEvent addConfPeriodEvent){
        ConferenceRezAddParam conferenceRezAddParam = addConfPeriodEvent.getConferenceRezAddParam();
        ConferenceRezPeriod conferenceRezPeriod = BeanUtil.toBean(conferenceRezAddParam, ConferenceRezPeriod.class);
        this.save(conferenceRezPeriod);
        String beginTimeParam = CommonTimeFormatUtil.dateToStr(conferenceRezAddParam.getBeginTime(), DatePattern.NORM_TIME_PATTERN);
        String endTimeParam = CommonTimeFormatUtil.dateToStr(conferenceRezAddParam.getEndTime(),DatePattern.NORM_TIME_PATTERN);
        ConferenceConfig conferenceConfig = conferenceConfigService.getById(addConfPeriodEvent.getConferenceRezAddParam().getConferenceConfigId());
        SysUser sysUser = sysUserService.getById(addConfPeriodEvent.getConferenceRezAddParam().getRezUser());

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
                endTime = DateUtils.parseDate(endLocalDate.format(formatter)+ HrUserAttendanceStatEnum.BEGIN_TIMNE.getValue());
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
                    Integer trueNum = conferenceRezService.addTrue(param);
                    if(trueNum > 0){
                        String msg = trueNum == 3 ? "当前的时间已有预约" : "传入时间有误！";
                        throw new CommonException(msg);
                    }
                    conferenceRez.setBeginTime(begin);
                    conferenceRez.setEndTime(end);
                    String sn = CommonUtils.getTableSn(BizTableCommon.BIZ_CHART_REVIEWS, DbsConstant.SLAVE_1_DATASOURCE_NAME);
                    conferenceRez.setSn(sn);
                    //conferenceRezList.add(conferenceRez);
                    conferenceRezService.save(conferenceRez);
                    ruanLianParam(conferenceRez,conferenceConfig.getName(),sysUser.getName());
                });
            }catch (Exception e){
                log.error("时间生成错误！");
            }
            conferenceRezService.saveBatch(conferenceRezList);
            return;
        }
        if(ObjectUtil.isNotEmpty(conferenceRezAddParam.getDayTime())){

        }
    }

    private void ruanLianParam(ConferenceRez conferenceRez,String conferenceConfigName,String userName){
        try{
            RuanLianParam ruanLianParam = new RuanLianParam();
            ruanLianParam.setRezId(conferenceRez.getId());
            ruanLianParam.setZtname(conferenceRez.getTheme());
            ruanLianParam.setYytimes(DateUtil.format(conferenceRez.getBeginTime(),DatePattern.NORM_DATETIME_PATTERN));
            ruanLianParam.setYytimee(DateUtil.format(conferenceRez.getEndTime(),DatePattern.NORM_DATETIME_PATTERN));
            ruanLianParam.setCjname(userName);
            ruanLianParam.setMianbanName(conferenceConfigName);
            ConferenceMsgEvent conferenceMsgEvent = new ConferenceMsgEvent();
            conferenceMsgEvent.setRuanLianParam(ruanLianParam);
            conferenceRezService.putRuanLian(conferenceMsgEvent);
        }catch (Exception e){
            log.error("批量预约软联会议失败！e:"+e.toString()+",periodId:"+conferenceRez.getRezPeriodId());
        }
    }
}
