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
package vip.xiaonuo.sys.modular.hr.conference.record.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.sys.modular.hr.conference.record.entity.ConferenceConfirmUser;
import vip.xiaonuo.sys.modular.hr.conference.record.entity.ConferenceRecord;
import vip.xiaonuo.sys.modular.hr.conference.record.mapper.ConferenceRecordMapper;
import vip.xiaonuo.sys.modular.hr.conference.record.param.*;
import vip.xiaonuo.sys.modular.hr.conference.record.service.ConferenceConfirmUserService;
import vip.xiaonuo.sys.modular.hr.conference.record.service.ConferenceRecordService;
import vip.xiaonuo.sys.modular.hr.conference.record.service.ConferenceWorkArrangeService;
import vip.xiaonuo.sys.modular.hr.conference.record.vo.RecordDetailVo;
import vip.xiaonuo.sys.modular.hr.conference.record.vo.RezRecordVo;
import vip.xiaonuo.sys.modular.hr.conference.record.vo.WorkArrangeVo;
import vip.xiaonuo.sys.modular.hr.conference.rez.entity.ConferenceRez;
import vip.xiaonuo.sys.modular.hr.conference.rez.service.ConferenceRezService;
import vip.xiaonuo.sys.modular.hr.conference.rez.vo.ConferenceMsgEvent;
import vip.xiaonuo.sys.modular.hr.conference.rezPeriod.entity.ConferenceRezPeriod;
import vip.xiaonuo.sys.modular.hr.conference.rezPeriod.service.ConferenceRezPeriodService;
import vip.xiaonuo.sys.modular.hr.conference.record.entity.ConferenceWorkArrange;
import vip.xiaonuo.sys.modular.user.entity.SysUser;
import vip.xiaonuo.sys.modular.user.service.SysUserService;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 会议预约-会议记录Service接口实现类
 *
 * @author ljy
 * @date  2023/11/20 08:39
 **/
@Service
public class ConferenceRecordServiceImpl extends ServiceImpl<ConferenceRecordMapper, ConferenceRecord> implements ConferenceRecordService {

    @Resource
    ConferenceRezService conferenceRezService;
    @Resource
    ConferenceRezPeriodService conferenceRezPeriodService;
    @Resource
    private ConferenceWorkArrangeService conferenceWorkArrangeService;
    @Resource
    SysUserService sysUserService;
    @Resource
    ApplicationEventPublisher eventPublisher;
    @Resource
    ConferenceConfirmUserService conferenceConfirmUserService;

    @Override
    public Page<ConferenceRecord> page(ConferenceRecordPageParam conferenceRecordPageParam) {
        QueryWrapper<ConferenceRecord> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(conferenceRecordPageParam.getConferenceRecId())) {
            queryWrapper.lambda().eq(ConferenceRecord::getConferenceRecId, conferenceRecordPageParam.getConferenceRecId());
        }
        if(ObjectUtil.isNotEmpty(conferenceRecordPageParam.getConferenceType())) {
            queryWrapper.lambda().eq(ConferenceRecord::getConferenceType, conferenceRecordPageParam.getConferenceType());
        }
        if(ObjectUtil.isAllNotEmpty(conferenceRecordPageParam.getSortField(), conferenceRecordPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(conferenceRecordPageParam.getSortOrder());
            queryWrapper.orderBy(true, conferenceRecordPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(conferenceRecordPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(ConferenceRecord::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ConferenceRecordAddParam conferenceRecordAddParam) {
        StringBuffer absents = new StringBuffer();
        if(CollectionUtil.isNotEmpty(conferenceRecordAddParam.getAbsentList())){
            conferenceRecordAddParam.getAbsentList().forEach(absent -> absents.append(absent).append(","));
        }
        ConferenceRecord conferenceRecord = BeanUtil.toBean(conferenceRecordAddParam, ConferenceRecord.class);
        conferenceRecord.setBeginTime(conferenceRecordAddParam.getRezRecordVo().getBeginTime());
        conferenceRecord.setEndTime(conferenceRecordAddParam.getRezRecordVo().getEndTime());
        conferenceRecord.setAbsents(absents != null ? absents.toString() : "");
        conferenceRecord.setRecordConfirmIds(conferenceRecordAddParam.getRecordConfirmIds());
        this.save(conferenceRecord);

        //对会议预约信息修改
        String recordId = conferenceRecord.getId();
        Map<String,String> map = new HashMap<>();
        ConferenceRez conferenceRez = conferenceRezService.getById(conferenceRecordAddParam.getRezRecordVo().getId());
        map.put("rezId",ObjectUtil.isNotEmpty(conferenceRez)? conferenceRez.getId() : ObjectUtil.isNotEmpty(conferenceRez) ?  conferenceRez.getId() : "");
        map.put("theme",ObjectUtil.isNotEmpty(conferenceRez)? conferenceRez.getTheme() : ObjectUtil.isNotEmpty(conferenceRez) ?  conferenceRez.getTheme() : "");
        if(ObjectUtil.isEmpty(conferenceRez)){
            ConferenceRezPeriod conferenceRezPeriod = conferenceRezPeriodService.getById(conferenceRecordAddParam.getRezRecordVo().getId());
            if(ObjectUtil.isEmpty(conferenceRezPeriod)){
                throw new CommonException("查不到会议预约记录");
            }
            map.put("rezId",ObjectUtil.isNotEmpty(conferenceRezPeriod)? conferenceRezPeriod.getId() : ObjectUtil.isNotEmpty(conferenceRezPeriod) ?  conferenceRezPeriod.getId() : "");
            map.put("theme",ObjectUtil.isNotEmpty(conferenceRezPeriod)? conferenceRezPeriod.getTheme() : ObjectUtil.isNotEmpty(conferenceRezPeriod) ?  conferenceRezPeriod.getTheme() : "");
            BeanUtil.copyProperties(conferenceRecordAddParam.getRezRecordVo(), conferenceRezPeriod);
            conferenceRezPeriod.setRecordId(recordId);
            conferenceRezPeriodService.updateById(conferenceRezPeriod);
            conferenceRecord.setConferenceRecId(conferenceRezPeriod.getId());
        }else{
            BeanUtil.copyProperties(conferenceRecordAddParam.getRezRecordVo(), conferenceRez);
            conferenceRez.setRecordId(recordId);
            conferenceRezService.updateById(conferenceRez);
            conferenceRecord.setConferenceRecId(conferenceRez.getId());
        }
        this.updateById(conferenceRecord);
        //保存会议记录确认人数据
        List<String> userIds = StringUtils.isNotEmpty(conferenceRecordAddParam.getRecordConfirmIds()) ?
                Arrays.asList(conferenceRecordAddParam.getRecordConfirmIds().split(",")) : new ArrayList<>();
        if(CollectionUtil.isNotEmpty(userIds)){
            List<SysUser> sysUserList = sysUserService.list(new LambdaQueryWrapper<SysUser>().in(SysUser::getId,userIds));
            List<ConferenceConfirmUser> confirmUserList = new ArrayList<>();
            if(CollectionUtil.isNotEmpty(sysUserList)){
                sysUserList.forEach(user -> {
                    ConferenceConfirmUser conferenceConfirmUser = new ConferenceConfirmUser();
                    conferenceConfirmUser.setConfirmUserId(user.getId());
                    conferenceConfirmUser.setConfirmUserName(user.getName());
                    conferenceConfirmUser.setRecordId(conferenceRecord.getId());
                    conferenceConfirmUser.setTheme(map.get("theme"));
                    conferenceConfirmUser.setRezId(map.get("rezId"));
                    conferenceConfirmUser.setNoticeType(1);
                    confirmUserList.add(conferenceConfirmUser);
                });
            }
            if(CollectionUtil.isNotEmpty(confirmUserList)){
                conferenceConfirmUserService.saveBatch(confirmUserList);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ConferenceRecordEditParam conferenceRecordEditParam) {
        Date beginTime = conferenceRecordEditParam.getRezRecordVo().getBeginTime();
        Date endTime = conferenceRecordEditParam.getRezRecordVo().getBeginTime();
        StringBuffer absents = new StringBuffer();
        if(CollectionUtil.isNotEmpty(conferenceRecordEditParam.getAbsentList())){
            conferenceRecordEditParam.getAbsentList().forEach(absent -> absents.append(absent).append(","));
        }
        ConferenceRecord conferenceRecord = this.queryEntity(conferenceRecordEditParam.getId());
        BeanUtil.copyProperties(conferenceRecordEditParam, conferenceRecord);
        conferenceRecord.setBeginTime(beginTime);
        conferenceRecord.setEndTime(endTime);
        conferenceRecord.setAbsents(absents != null ? absents.toString() : "");
        this.updateById(conferenceRecord);
        //对会议预约信息修改
        conferenceRecordEditParam.getRezRecordVo().setBeginTime(null);
        conferenceRecordEditParam.getRezRecordVo().setEndTime(null);
        ConferenceRez conferenceRez = conferenceRezService.getById(conferenceRecordEditParam.getRezRecordVo().getId());
        if(ObjectUtil.isEmpty(conferenceRez)){
            ConferenceRezPeriod conferenceRezPeriod = conferenceRezPeriodService.getById(conferenceRecordEditParam.getRezRecordVo().getId());
            if(ObjectUtil.isEmpty(conferenceRezPeriod)){
                throw new CommonException("查不到会议预约记录");
            }
            BeanUtil.copyProperties(conferenceRecordEditParam.getRezRecordVo(), conferenceRezPeriod);
            conferenceRezPeriodService.updateById(conferenceRezPeriod);
        }else{
            BeanUtil.copyProperties(conferenceRecordEditParam.getRezRecordVo(), conferenceRez);
            conferenceRezService.updateById(conferenceRez);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<DeleteRecordParam> deleteRecordParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(deleteRecordParamList, DeleteRecordParam::getId));
    }

    @Override
    public RecordDetailVo detail(ConferenceRecordIdParam conferenceRecordIdParam) {
        RecordDetailVo recordDetailVo = new RecordDetailVo();
        ConferenceRecord conferenceRecord = this.queryEntity(conferenceRecordIdParam.getId());
        if(ObjectUtil.isNotEmpty(conferenceRecord) && StringUtils.isNotEmpty(conferenceRecord.getAbsents())){
            conferenceRecord.setAbsentList(Arrays.asList(conferenceRecord.getAbsents().split(",")));
        }
        recordDetailVo.setConferenceRecord(conferenceRecord);

        RezRecordVo rezRecordVo = new RezRecordVo();
        ConferenceRez conferenceRez = conferenceRezService.getById(conferenceRecordIdParam.getConferenceRecId());
        if(ObjectUtil.isEmpty(conferenceRez)){
            ConferenceRezPeriod conferenceRezPeriod = conferenceRezPeriodService.getById(conferenceRecordIdParam.getConferenceRecId());
            if(ObjectUtil.isEmpty(conferenceRezPeriod)){
                throw new CommonException("查不到会议记录");
            }
            rezRecordVo = BeanUtil.toBean(conferenceRezPeriod, RezRecordVo.class);
        }else{
            rezRecordVo = BeanUtil.toBean(conferenceRez, RezRecordVo.class);
        }
        recordDetailVo.setRezRecordVo(rezRecordVo);
        conferenceWorkArrangeData(conferenceRecordIdParam,recordDetailVo);
        return recordDetailVo;
    }
    private void conferenceWorkArrangeData(ConferenceRecordIdParam conferenceRecordIdParam,RecordDetailVo recordDetailVo){
        List<ConferenceWorkArrange> conferenceWorkArrangeList = conferenceWorkArrangeService.list(new LambdaQueryWrapper<ConferenceWorkArrange>()
                .eq(ConferenceWorkArrange::getRecordId,conferenceRecordIdParam.getId()));
        recordDetailVo.setConferenceWorkArrangeList(conferenceWorkArrangeList);
    }

    @Override
    public ConferenceRecord queryEntity(String id) {
        ConferenceRecord conferenceRecord = this.getById(id);
        if(ObjectUtil.isEmpty(conferenceRecord)) {
            throw new CommonException("会议预约-会议记录不存在，id值为：{}", id);
        }
        return conferenceRecord;
    }

    @Override
    public void addSummary(ConfSummaryAddParam confSummaryAddParam){
        ConferenceRecord conferenceRecord = this.getById(confSummaryAddParam.getRecordId());
        if(ObjectUtil.isEmpty(conferenceRecord)){
            throw new CommonException("无会议记录！");
        }
        if(StringUtils.isNotEmpty(conferenceRecord.getConferenceRecId())){
            ConferenceRez rec = conferenceRezService.getById(conferenceRecord.getConferenceRecId());
            ConferenceRezPeriod recPeriod = conferenceRezPeriodService.getById(conferenceRecord.getConferenceRecId());
            if(ObjectUtil.isNotEmpty(rec)){
                rec.setTheme(confSummaryAddParam.getTheme());
                conferenceRecord.setConferenceRecId(rec.getId());
                conferenceRecord.setConferenceType(1);
                conferenceRezService.updateById(rec);
            }
            if(ObjectUtil.isNotEmpty(recPeriod)){
                recPeriod.setTheme(confSummaryAddParam.getTheme());
                conferenceRecord.setConferenceRecId(recPeriod.getId());
                conferenceRecord.setConferenceType(2);
                conferenceRezPeriodService.updateById(recPeriod);
            }
        }
        conferenceRecord.setConfSummary(confSummaryAddParam.getConfSummary());
        conferenceRecord.setConfirmUserIds(confSummaryAddParam.getConfirmUserIds());
        if(StringUtils.isNotEmpty(confSummaryAddParam.getRecordConfirmIds())){
            conferenceRecord.setRecordConfirmIds(confSummaryAddParam.getRecordConfirmIds());
        }
        saveConfSummary(confSummaryAddParam);//保存工作部署
        saveConfirmUser(confSummaryAddParam);//保存会议纪要确认人，并以此发送确认通知
        this.saveOrUpdate(conferenceRecord);
    }

    /** 保存工作部署*/
    private void saveConfSummary(ConfSummaryAddParam confSummaryAddParam){
        if(CollectionUtil.isNotEmpty(confSummaryAddParam.getWorkArrangeVoList())){
            Map<String,WorkArrangeVo> updateMap = new HashMap<>();
            List<WorkArrangeVo> save = new ArrayList<>();
            List<ConferenceWorkArrange> conferenceWorkArrangeList = new ArrayList<>();
            confSummaryAddParam.getWorkArrangeVoList().forEach(arrange -> {
                if(StringUtils.isNotEmpty(arrange.getId())){
                    updateMap.put(arrange.getId(),arrange);
                }else{
                    save.add(arrange);
                }
            });
            if(CollectionUtil.isNotEmpty(save)){
                confSummaryAddParam.getWorkArrangeVoList().forEach(conf -> {
                    ConferenceWorkArrange conferenceWorkArrange = BeanUtil.toBean(conf,ConferenceWorkArrange.class);
                    conferenceWorkArrange.setRecordId(confSummaryAddParam.getRecordId());
                    conferenceWorkArrangeList.add(conferenceWorkArrange);
                });
            }
            if(CollectionUtil.isNotEmpty(updateMap.entrySet())){
                List<String> ids = updateMap.entrySet().stream().map(vo -> vo.getValue().getId()).collect(Collectors.toList());
                List<ConferenceWorkArrange> list = conferenceWorkArrangeService.list(new LambdaQueryWrapper<ConferenceWorkArrange>()
                        .in(ConferenceWorkArrange::getId,ids));
                list.forEach(arrange -> {
                    WorkArrangeVo workArrangeVo = updateMap.get(arrange.getId());
                    BeanUtil.copyProperties(workArrangeVo,arrange);
                    conferenceWorkArrangeList.add(arrange);
                });
            }
            conferenceWorkArrangeService.saveOrUpdateBatch(conferenceWorkArrangeList);
        }
    }
    /** 保存记录确认人*/
    private void saveConfirmUser(ConfSummaryAddParam confSummaryAddParam){
        Map<String,String> map = new HashMap<>();
        if(StringUtils.isNotEmpty(confSummaryAddParam.getConfirmUserIds())){
            //获取会议预约信息
            ConferenceRecord conferenceRecord = this.queryEntity(confSummaryAddParam.getRecordId());
            if(ObjectUtil.isNotEmpty(conferenceRecord)){
                ConferenceRez rec = conferenceRezService.getById(conferenceRecord.getConferenceRecId());
                ConferenceRezPeriod recPeriod = conferenceRezPeriodService.getById(conferenceRecord.getConferenceRecId());
                map.put("rezId",ObjectUtil.isNotEmpty(rec)? rec.getId() : ObjectUtil.isNotEmpty(recPeriod) ?  recPeriod.getId() : "");
                map.put("theme",ObjectUtil.isNotEmpty(rec)? rec.getTheme() : ObjectUtil.isNotEmpty(recPeriod) ?  recPeriod.getTheme() : "");
            }
            List<String> userIds = Arrays.asList(confSummaryAddParam.getConfirmUserIds().split(","));
            List<SysUser> sysUserList = sysUserService.list(new LambdaQueryWrapper<SysUser>().in(SysUser::getId,userIds));
            List<ConferenceConfirmUser> confirmUserList = new ArrayList<>();
            if(CollectionUtil.isNotEmpty(sysUserList)){
                sysUserList.forEach(user -> {
                    ConferenceConfirmUser conferenceConfirmUser = new ConferenceConfirmUser();
                    conferenceConfirmUser.setConfirmUserId(user.getId());
                    conferenceConfirmUser.setConfirmUserName(user.getName());
                    conferenceConfirmUser.setRecordId(confSummaryAddParam.getRecordId());
                    conferenceConfirmUser.setTheme(map.get("theme"));
                    conferenceConfirmUser.setRezId(map.get("rezId"));
                    conferenceConfirmUser.setNoticeType(2);
                    confirmUserList.add(conferenceConfirmUser);
                });
            }
            if(CollectionUtil.isNotEmpty(confirmUserList)){
                conferenceConfirmUserService.saveBatch(confirmUserList);
            }
        }
    }

    @Override
    public void recordNotice(String recordId){
        ConferenceRecord conferenceRecord = this.getById(recordId);
        if(ObjectUtil.isEmpty(conferenceRecord)){
            throw new CommonException("无会议记录！");
        }
        List<String> userIds = Arrays.asList(conferenceRecord.getConfirmUserIds().split(","));
        List<SysUser> userList = sysUserService.list(new LambdaQueryWrapper<SysUser>().in(SysUser::getId,userIds));
        ConferenceMsgEvent conferenceMsgEvent = new ConferenceMsgEvent(userList,"请确认会议记录！",null,null,2);
        eventPublisher.publishEvent(conferenceMsgEvent);
    }
}
