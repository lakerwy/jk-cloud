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
package vip.xiaonuo.biz.modular.epc.flw.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.epc.flw.entity.EpcTicketAudit;
import vip.xiaonuo.biz.modular.epc.flw.mapper.EpcTicketAuditMapper;
import vip.xiaonuo.biz.modular.epc.flw.param.*;
import vip.xiaonuo.biz.modular.epc.flw.service.EpcTicketAuditService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;

import java.util.List;

/**
 * EPC-罚单审批Service接口实现类
 *
 * @author yong.ye
 * @date  2023/09/05 15:00
 **/
@Service
public class EpcTicketAuditServiceImpl extends ServiceImpl<EpcTicketAuditMapper, EpcTicketAudit> implements EpcTicketAuditService {

    @Override
    public Page<EpcTicketAudit> page(EpcTicketAuditPageParam epcTicketAuditPageParam) {
        QueryWrapper<EpcTicketAudit> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(epcTicketAuditPageParam.getTenantId())) {
            queryWrapper.lambda().eq(EpcTicketAudit::getTenantId, epcTicketAuditPageParam.getTenantId());
        }
        if(ObjectUtil.isNotEmpty(epcTicketAuditPageParam.getSn())) {
            queryWrapper.lambda().like(EpcTicketAudit::getSn, epcTicketAuditPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(epcTicketAuditPageParam.getStatus())) {
            queryWrapper.lambda().eq(EpcTicketAudit::getStatus, epcTicketAuditPageParam.getStatus());
        }
        if(ObjectUtil.isNotEmpty(epcTicketAuditPageParam.getProjectId())) {
            queryWrapper.lambda().eq(EpcTicketAudit::getProjectId, epcTicketAuditPageParam.getProjectId());
        }
        if(ObjectUtil.isNotEmpty(epcTicketAuditPageParam.getPenaltyUnit())) {
            queryWrapper.lambda().like(EpcTicketAudit::getPenaltyUnit, epcTicketAuditPageParam.getPenaltyUnit());
        }
        if(ObjectUtil.isNotEmpty(epcTicketAuditPageParam.getStartPenaltyTime()) && ObjectUtil.isNotEmpty(epcTicketAuditPageParam.getEndPenaltyTime())) {
            queryWrapper.lambda().between(EpcTicketAudit::getPenaltyTime, epcTicketAuditPageParam.getStartPenaltyTime(), epcTicketAuditPageParam.getEndPenaltyTime());
        }
        if(ObjectUtil.isAllNotEmpty(epcTicketAuditPageParam.getSortField(), epcTicketAuditPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(epcTicketAuditPageParam.getSortOrder());
            queryWrapper.orderBy(true, epcTicketAuditPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(epcTicketAuditPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(EpcTicketAudit::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(EpcTicketAuditAddParam epcTicketAuditAddParam) {
        EpcTicketAudit epcTicketAudit = BeanUtil.toBean(epcTicketAuditAddParam, EpcTicketAudit.class);
        this.save(epcTicketAudit);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(EpcTicketAuditEditParam epcTicketAuditEditParam) {
        EpcTicketAudit epcTicketAudit = this.queryEntity(epcTicketAuditEditParam.getId());
        BeanUtil.copyProperties(epcTicketAuditEditParam, epcTicketAudit);
        this.updateById(epcTicketAudit);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<EpcTicketAuditIdParam> epcTicketAuditIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(epcTicketAuditIdParamList, EpcTicketAuditIdParam::getId));
    }

    @Override
    public EpcTicketAudit detail(EpcTicketAuditIdParam epcTicketAuditIdParam) {
        return this.queryEntity(epcTicketAuditIdParam.getId());
    }

    @Override
    public EpcTicketAudit queryEntity(String id) {
        EpcTicketAudit epcTicketAudit = this.getById(id);
        if(ObjectUtil.isEmpty(epcTicketAudit)) {
            throw new CommonException("EPC-罚单审批不存在，id值为：{}", id);
        }
        return epcTicketAudit;
    }
}
