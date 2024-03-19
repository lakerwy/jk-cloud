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
package vip.xiaonuo.biz.modular.invoice.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.invoice.entity.InvoiceCost;
import vip.xiaonuo.biz.modular.invoice.mapper.InvoiceCostMapper;
import vip.xiaonuo.biz.modular.invoice.param.InvoiceCostAddParam;
import vip.xiaonuo.biz.modular.invoice.param.InvoiceCostEditParam;
import vip.xiaonuo.biz.modular.invoice.param.InvoiceCostIdParam;
import vip.xiaonuo.biz.modular.invoice.param.InvoiceCostPageParam;
import vip.xiaonuo.biz.modular.invoice.service.InvoiceCostService;

import java.util.List;

/**
 * 财务系统-成本发票申请Service接口实现类
 *
 * @author yong.ye
 * @date  2024/03/19 10:22
 **/
@Service
@DS("slave_1")
public class InvoiceCostServiceImpl extends ServiceImpl<InvoiceCostMapper, InvoiceCost> implements InvoiceCostService {

    @Override
    public Page<InvoiceCost> page(InvoiceCostPageParam invoiceCostPageParam) {
        QueryWrapper<InvoiceCost> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(invoiceCostPageParam.getApplyUnit())) {
            queryWrapper.lambda().eq(InvoiceCost::getApplyUnit, invoiceCostPageParam.getApplyUnit());
        }
        if(ObjectUtil.isNotEmpty(invoiceCostPageParam.getApplyId())) {
            queryWrapper.lambda().eq(InvoiceCost::getApplyId, invoiceCostPageParam.getApplyId());
        }
        if(ObjectUtil.isNotEmpty(invoiceCostPageParam.getApplyName())) {
            queryWrapper.lambda().like(InvoiceCost::getApplyName, invoiceCostPageParam.getApplyName());
        }
        if(ObjectUtil.isNotEmpty(invoiceCostPageParam.getKpSn())) {
            queryWrapper.lambda().like(InvoiceCost::getKpSn, invoiceCostPageParam.getKpSn());
        }
        if(ObjectUtil.isNotEmpty(invoiceCostPageParam.getHtProjectName())) {
            queryWrapper.lambda().like(InvoiceCost::getHtProjectName, invoiceCostPageParam.getHtProjectName());
        }
        if(ObjectUtil.isNotEmpty(invoiceCostPageParam.getHtContractNo())) {
            queryWrapper.lambda().like(InvoiceCost::getHtContractNo, invoiceCostPageParam.getHtContractNo());
        }
        if(ObjectUtil.isNotEmpty(invoiceCostPageParam.getSn())) {
            queryWrapper.lambda().like(InvoiceCost::getSn, invoiceCostPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(invoiceCostPageParam.getStatus())) {
            queryWrapper.lambda().eq(InvoiceCost::getStatus, invoiceCostPageParam.getStatus());
        }
        if(ObjectUtil.isAllNotEmpty(invoiceCostPageParam.getSortField(), invoiceCostPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(invoiceCostPageParam.getSortOrder());
            queryWrapper.orderBy(true, invoiceCostPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(invoiceCostPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(InvoiceCost::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(InvoiceCostAddParam invoiceCostAddParam) {
        InvoiceCost invoiceCost = BeanUtil.toBean(invoiceCostAddParam, InvoiceCost.class);
        this.save(invoiceCost);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(InvoiceCostEditParam invoiceCostEditParam) {
        InvoiceCost invoiceCost = this.queryEntity(invoiceCostEditParam.getId());
        BeanUtil.copyProperties(invoiceCostEditParam, invoiceCost);
        this.updateById(invoiceCost);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<InvoiceCostIdParam> invoiceCostIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(invoiceCostIdParamList, InvoiceCostIdParam::getId));
    }

    @Override
    public InvoiceCost detail(InvoiceCostIdParam invoiceCostIdParam) {
        return this.queryEntity(invoiceCostIdParam.getId());
    }

    @Override
    public InvoiceCost queryEntity(String id) {
        InvoiceCost invoiceCost = this.getById(id);
        if(ObjectUtil.isEmpty(invoiceCost)) {
            throw new CommonException("财务系统-成本发票申请不存在，id值为：{}", id);
        }
        return invoiceCost;
    }
}
