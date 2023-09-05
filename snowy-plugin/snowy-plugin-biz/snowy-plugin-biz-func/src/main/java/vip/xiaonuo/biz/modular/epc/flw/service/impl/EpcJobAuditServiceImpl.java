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
import vip.xiaonuo.biz.modular.epc.flw.entity.EpcJobAudit;
import vip.xiaonuo.biz.modular.epc.flw.mapper.EpcJobAuditMapper;
import vip.xiaonuo.biz.modular.epc.flw.param.*;
import vip.xiaonuo.biz.modular.epc.flw.service.EpcJobAuditService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;

import java.util.List;

/**
 * EPC-危险作业审批Service接口实现类
 *
 * @author yong.ye
 * @date  2023/09/05 14:57
 **/
@Service
public class EpcJobAuditServiceImpl extends ServiceImpl<EpcJobAuditMapper, EpcJobAudit> implements EpcJobAuditService {

    @Override
    public Page<EpcJobAudit> page(EpcJobAuditPageParam epcJobAuditPageParam) {
        QueryWrapper<EpcJobAudit> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(epcJobAuditPageParam.getTenantId())) {
            queryWrapper.lambda().eq(EpcJobAudit::getTenantId, epcJobAuditPageParam.getTenantId());
        }
        if(ObjectUtil.isNotEmpty(epcJobAuditPageParam.getSn())) {
            queryWrapper.lambda().like(EpcJobAudit::getSn, epcJobAuditPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(epcJobAuditPageParam.getStatus())) {
            queryWrapper.lambda().eq(EpcJobAudit::getStatus, epcJobAuditPageParam.getStatus());
        }
        if(ObjectUtil.isNotEmpty(epcJobAuditPageParam.getProjectId())) {
            queryWrapper.lambda().eq(EpcJobAudit::getProjectId, epcJobAuditPageParam.getProjectId());
        }
        if(ObjectUtil.isNotEmpty(epcJobAuditPageParam.getType())) {
            queryWrapper.lambda().eq(EpcJobAudit::getType, epcJobAuditPageParam.getType());
        }
        if(ObjectUtil.isNotEmpty(epcJobAuditPageParam.getConstractionUnit())) {
            queryWrapper.lambda().like(EpcJobAudit::getConstractionUnit, epcJobAuditPageParam.getConstractionUnit());
        }
        if(ObjectUtil.isAllNotEmpty(epcJobAuditPageParam.getSortField(), epcJobAuditPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(epcJobAuditPageParam.getSortOrder());
            queryWrapper.orderBy(true, epcJobAuditPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(epcJobAuditPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(EpcJobAudit::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(EpcJobAuditAddParam epcJobAuditAddParam) {
        EpcJobAudit epcJobAudit = BeanUtil.toBean(epcJobAuditAddParam, EpcJobAudit.class);
        this.save(epcJobAudit);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(EpcJobAuditEditParam epcJobAuditEditParam) {
        EpcJobAudit epcJobAudit = this.queryEntity(epcJobAuditEditParam.getId());
        BeanUtil.copyProperties(epcJobAuditEditParam, epcJobAudit);
        this.updateById(epcJobAudit);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<EpcJobAuditIdParam> epcJobAuditIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(epcJobAuditIdParamList, EpcJobAuditIdParam::getId));
    }

    @Override
    public EpcJobAudit detail(EpcJobAuditIdParam epcJobAuditIdParam) {
        return this.queryEntity(epcJobAuditIdParam.getId());
    }

    @Override
    public EpcJobAudit queryEntity(String id) {
        EpcJobAudit epcJobAudit = this.getById(id);
        if(ObjectUtil.isEmpty(epcJobAudit)) {
            throw new CommonException("EPC-危险作业审批不存在，id值为：{}", id);
        }
        return epcJobAudit;
    }
}
