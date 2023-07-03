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
package vip.xiaonuo.biz.modular.crm.invoiceregister.service.impl;

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
import vip.xiaonuo.biz.modular.crm.invoiceregister.entity.Invoiceregister;
import vip.xiaonuo.biz.modular.crm.invoiceregister.mapper.InvoiceregisterMapper;
import vip.xiaonuo.biz.modular.crm.invoiceregister.param.InvoiceregisterAddParam;
import vip.xiaonuo.biz.modular.crm.invoiceregister.param.InvoiceregisterEditParam;
import vip.xiaonuo.biz.modular.crm.invoiceregister.param.InvoiceregisterIdParam;
import vip.xiaonuo.biz.modular.crm.invoiceregister.param.InvoiceregisterPageParam;
import vip.xiaonuo.biz.modular.crm.invoiceregister.service.InvoiceregisterService;

import java.util.List;

/**
 * 业务表-CRM-合同开票登记Service接口实现类
 *
 * @author yong.ye
 * @date  2023/06/30 16:48
 **/

@DS("slave_1")
@Service
public class InvoiceregisterServiceImpl extends ServiceImpl<InvoiceregisterMapper, Invoiceregister> implements InvoiceregisterService {

    @Override
    public Page<Invoiceregister> page(InvoiceregisterPageParam invoiceregisterPageParam) {
        QueryWrapper<Invoiceregister> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(invoiceregisterPageParam.getSn())) {
            queryWrapper.lambda().like(Invoiceregister::getSn, invoiceregisterPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(invoiceregisterPageParam.getOpt())) {
            queryWrapper.lambda().like(Invoiceregister::getOpt, invoiceregisterPageParam.getOpt());
        }
        if(ObjectUtil.isNotEmpty(invoiceregisterPageParam.getStartOptDate()) && ObjectUtil.isNotEmpty(invoiceregisterPageParam.getEndOptDate())) {
            queryWrapper.lambda().between(Invoiceregister::getOptDate, invoiceregisterPageParam.getStartOptDate(), invoiceregisterPageParam.getEndOptDate());
        }
        if(ObjectUtil.isNotEmpty(invoiceregisterPageParam.getContractNo())) {
            queryWrapper.lambda().like(Invoiceregister::getContractNo, invoiceregisterPageParam.getContractNo());
        }
        if(ObjectUtil.isAllNotEmpty(invoiceregisterPageParam.getSortField(), invoiceregisterPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(invoiceregisterPageParam.getSortOrder());
            queryWrapper.orderBy(true, invoiceregisterPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(invoiceregisterPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(Invoiceregister::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(InvoiceregisterAddParam invoiceregisterAddParam) {
        Invoiceregister invoiceregister = BeanUtil.toBean(invoiceregisterAddParam, Invoiceregister.class);
        this.save(invoiceregister);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(InvoiceregisterEditParam invoiceregisterEditParam) {
        Invoiceregister invoiceregister = this.queryEntity(invoiceregisterEditParam.getId());
        BeanUtil.copyProperties(invoiceregisterEditParam, invoiceregister);
        this.updateById(invoiceregister);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<InvoiceregisterIdParam> invoiceregisterIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(invoiceregisterIdParamList, InvoiceregisterIdParam::getId));
    }

    @Override
    public Invoiceregister detail(InvoiceregisterIdParam invoiceregisterIdParam) {
        return this.queryEntity(invoiceregisterIdParam.getId());
    }

    @Override
    public Invoiceregister queryEntity(String id) {
        Invoiceregister invoiceregister = this.getById(id);
        if(ObjectUtil.isEmpty(invoiceregister)) {
            throw new CommonException("业务表-CRM-合同开票登记不存在，id值为：{}", id);
        }
        return invoiceregister;
    }
}
