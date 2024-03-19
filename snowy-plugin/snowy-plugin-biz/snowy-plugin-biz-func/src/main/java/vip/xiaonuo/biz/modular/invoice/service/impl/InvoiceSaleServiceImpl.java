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
import vip.xiaonuo.biz.modular.invoice.entity.InvoiceSale;
import vip.xiaonuo.biz.modular.invoice.mapper.InvoiceSaleMapper;
import vip.xiaonuo.biz.modular.invoice.param.InvoiceSaleAddParam;
import vip.xiaonuo.biz.modular.invoice.param.InvoiceSaleEditParam;
import vip.xiaonuo.biz.modular.invoice.param.InvoiceSaleIdParam;
import vip.xiaonuo.biz.modular.invoice.param.InvoiceSalePageParam;
import vip.xiaonuo.biz.modular.invoice.service.InvoiceSaleService;

import java.util.List;

/**
 * 财务系统-销售发票开具申请Service接口实现类
 *
 * @author yong.ye
 * @date  2024/03/19 10:17
 **/
@Service
@DS("slave_1")
public class InvoiceSaleServiceImpl extends ServiceImpl<InvoiceSaleMapper, InvoiceSale> implements InvoiceSaleService {

    @Override
    public Page<InvoiceSale> page(InvoiceSalePageParam invoiceSalePageParam) {
        QueryWrapper<InvoiceSale> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getApplyUnit())) {
            queryWrapper.lambda().eq(InvoiceSale::getApplyUnit, invoiceSalePageParam.getApplyUnit());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getApplyId())) {
            queryWrapper.lambda().eq(InvoiceSale::getApplyId, invoiceSalePageParam.getApplyId());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getApplyName())) {
            queryWrapper.lambda().like(InvoiceSale::getApplyName, invoiceSalePageParam.getApplyName());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getBillId())) {
            queryWrapper.lambda().eq(InvoiceSale::getBillId, invoiceSalePageParam.getBillId());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getBillUnit())) {
            queryWrapper.lambda().like(InvoiceSale::getBillUnit, invoiceSalePageParam.getBillUnit());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getHtProjectName())) {
            queryWrapper.lambda().like(InvoiceSale::getHtProjectName, invoiceSalePageParam.getHtProjectName());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getHtContractNo())) {
            queryWrapper.lambda().eq(InvoiceSale::getHtContractNo, invoiceSalePageParam.getHtContractNo());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getPurchaser())) {
            queryWrapper.lambda().like(InvoiceSale::getPurchaser, invoiceSalePageParam.getPurchaser());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getTaxpayerType())) {
            queryWrapper.lambda().eq(InvoiceSale::getTaxpayerType, invoiceSalePageParam.getTaxpayerType());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getFpType())) {
            queryWrapper.lambda().eq(InvoiceSale::getFpType, invoiceSalePageParam.getFpType());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getTaxpayerNo())) {
            queryWrapper.lambda().like(InvoiceSale::getTaxpayerNo, invoiceSalePageParam.getTaxpayerNo());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getBankInfo())) {
            queryWrapper.lambda().like(InvoiceSale::getBankInfo, invoiceSalePageParam.getBankInfo());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getGatherType())) {
            queryWrapper.lambda().eq(InvoiceSale::getGatherType, invoiceSalePageParam.getGatherType());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getSn())) {
            queryWrapper.lambda().like(InvoiceSale::getSn, invoiceSalePageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getStatus())) {
            queryWrapper.lambda().eq(InvoiceSale::getStatus, invoiceSalePageParam.getStatus());
        }
        if(ObjectUtil.isAllNotEmpty(invoiceSalePageParam.getSortField(), invoiceSalePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(invoiceSalePageParam.getSortOrder());
            queryWrapper.orderBy(true, invoiceSalePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(invoiceSalePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(InvoiceSale::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(InvoiceSaleAddParam invoiceSaleAddParam) {
        InvoiceSale invoiceSale = BeanUtil.toBean(invoiceSaleAddParam, InvoiceSale.class);
        this.save(invoiceSale);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(InvoiceSaleEditParam invoiceSaleEditParam) {
        InvoiceSale invoiceSale = this.queryEntity(invoiceSaleEditParam.getId());
        BeanUtil.copyProperties(invoiceSaleEditParam, invoiceSale);
        this.updateById(invoiceSale);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<InvoiceSaleIdParam> invoiceSaleIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(invoiceSaleIdParamList, InvoiceSaleIdParam::getId));
    }

    @Override
    public InvoiceSale detail(InvoiceSaleIdParam invoiceSaleIdParam) {
        return this.queryEntity(invoiceSaleIdParam.getId());
    }

    @Override
    public InvoiceSale queryEntity(String id) {
        InvoiceSale invoiceSale = this.getById(id);
        if(ObjectUtil.isEmpty(invoiceSale)) {
            throw new CommonException("财务系统-销售发票开具申请不存在，id值为：{}", id);
        }
        return invoiceSale;
    }
}
