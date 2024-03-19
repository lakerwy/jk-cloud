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
import vip.xiaonuo.biz.modular.invoice.entity.InvoiceSaleMove;
import vip.xiaonuo.biz.modular.invoice.mapper.InvoiceSaleMoveMapper;
import vip.xiaonuo.biz.modular.invoice.param.InvoiceSaleMoveAddParam;
import vip.xiaonuo.biz.modular.invoice.param.InvoiceSaleMoveEditParam;
import vip.xiaonuo.biz.modular.invoice.param.InvoiceSaleMoveIdParam;
import vip.xiaonuo.biz.modular.invoice.param.InvoiceSaleMovePageParam;
import vip.xiaonuo.biz.modular.invoice.service.InvoiceSaleMoveService;

import java.util.List;

/**
 * 财务系统-销售发票-作废|红冲异动申请Service接口实现类
 *
 * @author yong.ye
 * @date  2024/03/19 10:21
 **/
@Service
@DS("slave_1")
public class InvoiceSaleMoveServiceImpl extends ServiceImpl<InvoiceSaleMoveMapper, InvoiceSaleMove> implements InvoiceSaleMoveService {

    @Override
    public Page<InvoiceSaleMove> page(InvoiceSaleMovePageParam invoiceSaleMovePageParam) {
        QueryWrapper<InvoiceSaleMove> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(invoiceSaleMovePageParam.getApplyUnit())) {
            queryWrapper.lambda().eq(InvoiceSaleMove::getApplyUnit, invoiceSaleMovePageParam.getApplyUnit());
        }
        if(ObjectUtil.isNotEmpty(invoiceSaleMovePageParam.getApplyId())) {
            queryWrapper.lambda().eq(InvoiceSaleMove::getApplyId, invoiceSaleMovePageParam.getApplyId());
        }
        if(ObjectUtil.isNotEmpty(invoiceSaleMovePageParam.getApplyName())) {
            queryWrapper.lambda().like(InvoiceSaleMove::getApplyName, invoiceSaleMovePageParam.getApplyName());
        }
        if(ObjectUtil.isNotEmpty(invoiceSaleMovePageParam.getMoveType())) {
            queryWrapper.lambda().eq(InvoiceSaleMove::getMoveType, invoiceSaleMovePageParam.getMoveType());
        }
        if(ObjectUtil.isNotEmpty(invoiceSaleMovePageParam.getSn())) {
            queryWrapper.lambda().like(InvoiceSaleMove::getSn, invoiceSaleMovePageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(invoiceSaleMovePageParam.getStatus())) {
            queryWrapper.lambda().eq(InvoiceSaleMove::getStatus, invoiceSaleMovePageParam.getStatus());
        }
        if(ObjectUtil.isAllNotEmpty(invoiceSaleMovePageParam.getSortField(), invoiceSaleMovePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(invoiceSaleMovePageParam.getSortOrder());
            queryWrapper.orderBy(true, invoiceSaleMovePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(invoiceSaleMovePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(InvoiceSaleMove::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(InvoiceSaleMoveAddParam invoiceSaleMoveAddParam) {
        InvoiceSaleMove invoiceSaleMove = BeanUtil.toBean(invoiceSaleMoveAddParam, InvoiceSaleMove.class);
        this.save(invoiceSaleMove);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(InvoiceSaleMoveEditParam invoiceSaleMoveEditParam) {
        InvoiceSaleMove invoiceSaleMove = this.queryEntity(invoiceSaleMoveEditParam.getId());
        BeanUtil.copyProperties(invoiceSaleMoveEditParam, invoiceSaleMove);
        this.updateById(invoiceSaleMove);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<InvoiceSaleMoveIdParam> invoiceSaleMoveIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(invoiceSaleMoveIdParamList, InvoiceSaleMoveIdParam::getId));
    }

    @Override
    public InvoiceSaleMove detail(InvoiceSaleMoveIdParam invoiceSaleMoveIdParam) {
        return this.queryEntity(invoiceSaleMoveIdParam.getId());
    }

    @Override
    public InvoiceSaleMove queryEntity(String id) {
        InvoiceSaleMove invoiceSaleMove = this.getById(id);
        if(ObjectUtil.isEmpty(invoiceSaleMove)) {
            throw new CommonException("财务系统-销售发票-作废|红冲异动申请不存在，id值为：{}", id);
        }
        return invoiceSaleMove;
    }
}
