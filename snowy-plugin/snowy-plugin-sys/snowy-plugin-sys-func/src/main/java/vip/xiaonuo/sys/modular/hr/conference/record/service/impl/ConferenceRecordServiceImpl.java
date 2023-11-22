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
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.sys.modular.hr.conference.record.entity.ConferenceRecord;
import vip.xiaonuo.sys.modular.hr.conference.record.mapper.ConferenceRecordMapper;
import vip.xiaonuo.sys.modular.hr.conference.record.param.*;
import vip.xiaonuo.sys.modular.hr.conference.record.service.ConferenceRecordService;
import vip.xiaonuo.sys.modular.hr.conference.record.vo.RecordDetailVo;
import vip.xiaonuo.sys.modular.hr.conference.record.vo.RezRecordVo;
import vip.xiaonuo.sys.modular.hr.conference.rez.entity.ConferenceRez;
import vip.xiaonuo.sys.modular.hr.conference.rez.service.ConferenceRezService;
import vip.xiaonuo.sys.modular.hr.conference.rezPeriod.entity.ConferenceRezPeriod;
import vip.xiaonuo.sys.modular.hr.conference.rezPeriod.service.ConferenceRezPeriodService;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
        ConferenceRez conferenceRez = conferenceRezService.getById(conferenceRecordAddParam.getRezRecordVo().getId());
        if(ObjectUtil.isEmpty(conferenceRez)){
            ConferenceRezPeriod conferenceRezPeriod = conferenceRezPeriodService.getById(conferenceRecordAddParam.getRezRecordVo().getId());
            if(ObjectUtil.isEmpty(conferenceRezPeriod)){
                throw new CommonException("查不到会议记录");
            }
            BeanUtil.copyProperties(conferenceRecordAddParam.getRezRecordVo(), conferenceRezPeriod);
            conferenceRezPeriodService.updateById(conferenceRezPeriod);
        }else{
            BeanUtil.copyProperties(conferenceRecordAddParam.getRezRecordVo(), conferenceRez);
            conferenceRezService.updateById(conferenceRez);
        }
        ConferenceRecord conferenceRecord = BeanUtil.toBean(conferenceRecordAddParam, ConferenceRecord.class);
        conferenceRecord.setBeginTime(conferenceRecordAddParam.getRezRecordVo().getBeginTime());
        conferenceRecord.setEndTime(conferenceRecordAddParam.getRezRecordVo().getEndTime());
        conferenceRecord.setAbsents(absents != null ? absents.toString() : "");
        this.save(conferenceRecord);
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
        conferenceRecordEditParam.getRezRecordVo().setBeginTime(null);
        conferenceRecordEditParam.getRezRecordVo().setEndTime(null);
        ConferenceRez conferenceRez = conferenceRezService.getById(conferenceRecordEditParam.getRezRecordVo().getId());
        if(ObjectUtil.isEmpty(conferenceRez)){
            ConferenceRezPeriod conferenceRezPeriod = conferenceRezPeriodService.getById(conferenceRecordEditParam.getRezRecordVo().getId());
            if(ObjectUtil.isEmpty(conferenceRezPeriod)){
                throw new CommonException("查不到会议记录");
            }
            BeanUtil.copyProperties(conferenceRecordEditParam.getRezRecordVo(), conferenceRezPeriod);
            conferenceRezPeriodService.updateById(conferenceRezPeriod);
        }else{
            BeanUtil.copyProperties(conferenceRecordEditParam.getRezRecordVo(), conferenceRez);
            conferenceRezService.updateById(conferenceRez);
        }
        ConferenceRecord conferenceRecord = this.queryEntity(conferenceRecordEditParam.getId());
        BeanUtil.copyProperties(conferenceRecordEditParam, conferenceRecord);
        conferenceRecord.setBeginTime(beginTime);
        conferenceRecord.setEndTime(endTime);
        conferenceRecord.setAbsents(absents != null ? absents.toString() : "");
        this.updateById(conferenceRecord);
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
        return recordDetailVo;
    }

    @Override
    public ConferenceRecord queryEntity(String id) {
        ConferenceRecord conferenceRecord = this.getById(id);
        if(ObjectUtil.isEmpty(conferenceRecord)) {
            throw new CommonException("会议预约-会议记录不存在，id值为：{}", id);
        }
        return conferenceRecord;
    }
}
