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
import vip.xiaonuo.sys.modular.hr.conference.record.entity.ConferenceWorkArrange;
import vip.xiaonuo.sys.modular.hr.conference.record.mapper.ConferenceWorkArrangeMapper;
import vip.xiaonuo.sys.modular.hr.conference.record.param.ConferenceWorkArrangeAddParam;
import vip.xiaonuo.sys.modular.hr.conference.record.param.ConferenceWorkArrangeEditParam;
import vip.xiaonuo.sys.modular.hr.conference.record.param.ConferenceWorkArrangeIdParam;
import vip.xiaonuo.sys.modular.hr.conference.record.param.ConferenceWorkArrangePageParam;
import vip.xiaonuo.sys.modular.hr.conference.record.service.ConferenceWorkArrangeService;

import java.util.List;

/**
 * 工作部署Service接口实现类
 *
 * @author ljy
 * @date  2023/11/28 16:31
 **/
@Service
public class ConferenceWorkArrangeServiceImpl extends ServiceImpl<ConferenceWorkArrangeMapper, ConferenceWorkArrange> implements ConferenceWorkArrangeService {

    @Override
    public Page<ConferenceWorkArrange> page(ConferenceWorkArrangePageParam conferenceWorkArrangePageParam) {
        QueryWrapper<ConferenceWorkArrange> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(conferenceWorkArrangePageParam.getDutyOrgId())) {
            queryWrapper.lambda().eq(ConferenceWorkArrange::getDutyOrgId, conferenceWorkArrangePageParam.getDutyOrgId());
        }
        if(ObjectUtil.isNotEmpty(conferenceWorkArrangePageParam.getDutyUserId())) {
            queryWrapper.lambda().eq(ConferenceWorkArrange::getDutyUserId, conferenceWorkArrangePageParam.getDutyUserId());
        }
        if(ObjectUtil.isNotEmpty(conferenceWorkArrangePageParam.getDutyUserName())) {
            queryWrapper.lambda().like(ConferenceWorkArrange::getDutyUserName, conferenceWorkArrangePageParam.getDutyUserName());
        }
        if(ObjectUtil.isNotEmpty(conferenceWorkArrangePageParam.getRecordId())) {
            queryWrapper.lambda().eq(ConferenceWorkArrange::getRecordId, conferenceWorkArrangePageParam.getRecordId());
        }
        if(ObjectUtil.isAllNotEmpty(conferenceWorkArrangePageParam.getSortField(), conferenceWorkArrangePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(conferenceWorkArrangePageParam.getSortOrder());
            queryWrapper.orderBy(true, conferenceWorkArrangePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(conferenceWorkArrangePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(ConferenceWorkArrange::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ConferenceWorkArrangeAddParam conferenceWorkArrangeAddParam) {
        ConferenceWorkArrange conferenceWorkArrange = BeanUtil.toBean(conferenceWorkArrangeAddParam, ConferenceWorkArrange.class);
        this.save(conferenceWorkArrange);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ConferenceWorkArrangeEditParam conferenceWorkArrangeEditParam) {
        ConferenceWorkArrange conferenceWorkArrange = this.queryEntity(conferenceWorkArrangeEditParam.getId());
        BeanUtil.copyProperties(conferenceWorkArrangeEditParam, conferenceWorkArrange);
        this.updateById(conferenceWorkArrange);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ConferenceWorkArrangeIdParam> conferenceWorkArrangeIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(conferenceWorkArrangeIdParamList, ConferenceWorkArrangeIdParam::getId));
    }

    @Override
    public ConferenceWorkArrange detail(ConferenceWorkArrangeIdParam conferenceWorkArrangeIdParam) {
        return this.queryEntity(conferenceWorkArrangeIdParam.getId());
    }

    @Override
    public ConferenceWorkArrange queryEntity(String id) {
        ConferenceWorkArrange conferenceWorkArrange = this.getById(id);
        if(ObjectUtil.isEmpty(conferenceWorkArrange)) {
            throw new CommonException("工作部署不存在，id值为：{}", id);
        }
        return conferenceWorkArrange;
    }
}
