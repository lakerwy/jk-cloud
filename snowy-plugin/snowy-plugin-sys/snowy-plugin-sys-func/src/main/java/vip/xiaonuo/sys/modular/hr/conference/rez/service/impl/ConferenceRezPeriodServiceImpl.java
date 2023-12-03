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
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.sys.modular.hr.conference.rez.entity.ConferenceRezPeriod;
import vip.xiaonuo.sys.modular.hr.conference.rez.mapper.ConferenceRezPeriodMapper;
import vip.xiaonuo.sys.modular.hr.conference.rez.param.ConferenceRezPeriodAddParam;
import vip.xiaonuo.sys.modular.hr.conference.rez.param.ConferenceRezPeriodEditParam;
import vip.xiaonuo.sys.modular.hr.conference.rez.param.ConferenceRezPeriodIdParam;
import vip.xiaonuo.sys.modular.hr.conference.rez.param.ConferenceRezPeriodPageParam;
import vip.xiaonuo.sys.modular.hr.conference.rez.service.ConferenceRezPeriodService;

import java.util.List;

/**
 * 会议模块-周期预约会议Service接口实现类
 *
 * @author ljy
 * @date  2023/12/02 10:18
 **/
@Service
public class ConferenceRezPeriodServiceImpl extends ServiceImpl<ConferenceRezPeriodMapper, ConferenceRezPeriod> implements ConferenceRezPeriodService {

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
}
