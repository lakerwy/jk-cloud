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
import vip.xiaonuo.biz.modular.invoice.entity.InvoiceSaleKp;
import vip.xiaonuo.biz.modular.invoice.mapper.InvoiceSaleKpMapper;
import vip.xiaonuo.biz.modular.invoice.param.InvoiceSaleKpAddParam;
import vip.xiaonuo.biz.modular.invoice.param.InvoiceSaleKpEditParam;
import vip.xiaonuo.biz.modular.invoice.param.InvoiceSaleKpIdParam;
import vip.xiaonuo.biz.modular.invoice.param.InvoiceSaleKpPageParam;
import vip.xiaonuo.biz.modular.invoice.service.InvoiceSaleKpService;

import java.util.List;

/**
 * 财务系统-销售发票开具申请-开票信息Service接口实现类
 *
 * @author yong.ye
 * @date  2024/03/19 10:18
 **/
@Service
@DS("slave_1")
public class InvoiceSaleKpServiceImpl extends ServiceImpl<InvoiceSaleKpMapper, InvoiceSaleKp> implements InvoiceSaleKpService {

    @Override
    public Page<InvoiceSaleKp> page(InvoiceSaleKpPageParam invoiceSaleKpPageParam) {
        QueryWrapper<InvoiceSaleKp> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(invoiceSaleKpPageParam.getContent())) {
            queryWrapper.lambda().like(InvoiceSaleKp::getContent, invoiceSaleKpPageParam.getContent());
        }
        if(ObjectUtil.isNotEmpty(invoiceSaleKpPageParam.getModel())){
            queryWrapper.lambda().like(InvoiceSaleKp::getModel,invoiceSaleKpPageParam.getModel());
        }
        if(ObjectUtil.isAllNotEmpty(invoiceSaleKpPageParam.getSortField(), invoiceSaleKpPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(invoiceSaleKpPageParam.getSortOrder());
            queryWrapper.orderBy(true, invoiceSaleKpPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(invoiceSaleKpPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(InvoiceSaleKp::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(InvoiceSaleKpAddParam invoiceSaleKpAddParam) {
        InvoiceSaleKp invoiceSaleKp = BeanUtil.toBean(invoiceSaleKpAddParam, InvoiceSaleKp.class);
        this.save(invoiceSaleKp);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(InvoiceSaleKpEditParam invoiceSaleKpEditParam) {
        InvoiceSaleKp invoiceSaleKp = this.queryEntity(invoiceSaleKpEditParam.getId());
        BeanUtil.copyProperties(invoiceSaleKpEditParam, invoiceSaleKp);
        this.updateById(invoiceSaleKp);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<InvoiceSaleKpIdParam> invoiceSaleKpIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(invoiceSaleKpIdParamList, InvoiceSaleKpIdParam::getId));
    }

    @Override
    public InvoiceSaleKp detail(InvoiceSaleKpIdParam invoiceSaleKpIdParam) {
        return this.queryEntity(invoiceSaleKpIdParam.getId());
    }

    @Override
    public InvoiceSaleKp queryEntity(String id) {
        InvoiceSaleKp invoiceSaleKp = this.getById(id);
        if(ObjectUtil.isEmpty(invoiceSaleKp)) {
            throw new CommonException("财务系统-销售发票开具申请-开票信息不存在，id值为：{}", id);
        }
        return invoiceSaleKp;
    }
}
