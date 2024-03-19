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
import vip.xiaonuo.biz.modular.invoice.entity.InvoiceSaleCost;
import vip.xiaonuo.biz.modular.invoice.mapper.InvoiceSaleCostMapper;
import vip.xiaonuo.biz.modular.invoice.param.InvoiceSaleCostAddParam;
import vip.xiaonuo.biz.modular.invoice.param.InvoiceSaleCostEditParam;
import vip.xiaonuo.biz.modular.invoice.param.InvoiceSaleCostIdParam;
import vip.xiaonuo.biz.modular.invoice.param.InvoiceSaleCostPageParam;
import vip.xiaonuo.biz.modular.invoice.service.InvoiceSaleCostService;

import java.util.List;

/**
 * 财务系统-销售发票开具申请-成本票应收Service接口实现类
 *
 * @author yong.ye
 * @date  2024/03/19 10:19
 **/
@Service
@DS("slave_1")
public class InvoiceSaleCostServiceImpl extends ServiceImpl<InvoiceSaleCostMapper, InvoiceSaleCost> implements InvoiceSaleCostService {

    @Override
    public Page<InvoiceSaleCost> page(InvoiceSaleCostPageParam invoiceSaleCostPageParam) {
        QueryWrapper<InvoiceSaleCost> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(invoiceSaleCostPageParam.getCostUnit())) {
            queryWrapper.lambda().like(InvoiceSaleCost::getCostUnit, invoiceSaleCostPageParam.getCostUnit());
        }
        if(ObjectUtil.isAllNotEmpty(invoiceSaleCostPageParam.getSortField(), invoiceSaleCostPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(invoiceSaleCostPageParam.getSortOrder());
            queryWrapper.orderBy(true, invoiceSaleCostPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(invoiceSaleCostPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(InvoiceSaleCost::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(InvoiceSaleCostAddParam invoiceSaleCostAddParam) {
        InvoiceSaleCost invoiceSaleCost = BeanUtil.toBean(invoiceSaleCostAddParam, InvoiceSaleCost.class);
        this.save(invoiceSaleCost);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(InvoiceSaleCostEditParam invoiceSaleCostEditParam) {
        InvoiceSaleCost invoiceSaleCost = this.queryEntity(invoiceSaleCostEditParam.getId());
        BeanUtil.copyProperties(invoiceSaleCostEditParam, invoiceSaleCost);
        this.updateById(invoiceSaleCost);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<InvoiceSaleCostIdParam> invoiceSaleCostIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(invoiceSaleCostIdParamList, InvoiceSaleCostIdParam::getId));
    }

    @Override
    public InvoiceSaleCost detail(InvoiceSaleCostIdParam invoiceSaleCostIdParam) {
        return this.queryEntity(invoiceSaleCostIdParam.getId());
    }

    @Override
    public InvoiceSaleCost queryEntity(String id) {
        InvoiceSaleCost invoiceSaleCost = this.getById(id);
        if(ObjectUtil.isEmpty(invoiceSaleCost)) {
            throw new CommonException("财务系统-销售发票开具申请-成本票应收不存在，id值为：{}", id);
        }
        return invoiceSaleCost;
    }
}
