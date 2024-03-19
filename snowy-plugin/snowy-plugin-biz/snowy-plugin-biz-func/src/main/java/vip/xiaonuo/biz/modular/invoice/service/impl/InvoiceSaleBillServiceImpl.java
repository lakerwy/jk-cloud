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
import vip.xiaonuo.biz.modular.invoice.entity.InvoiceSaleBill;
import vip.xiaonuo.biz.modular.invoice.mapper.InvoiceSaleBillMapper;
import vip.xiaonuo.biz.modular.invoice.param.InvoiceSaleBillAddParam;
import vip.xiaonuo.biz.modular.invoice.param.InvoiceSaleBillEditParam;
import vip.xiaonuo.biz.modular.invoice.param.InvoiceSaleBillIdParam;
import vip.xiaonuo.biz.modular.invoice.param.InvoiceSaleBillPageParam;
import vip.xiaonuo.biz.modular.invoice.service.InvoiceSaleBillService;

import java.util.List;

/**
 * 财务系统-销售发票开具申请-开票确认【正式销售发票表】Service接口实现类
 *
 * @author yong.ye
 * @date  2024/03/19 10:19
 **/
@Service
@DS("slave_1")
public class InvoiceSaleBillServiceImpl extends ServiceImpl<InvoiceSaleBillMapper, InvoiceSaleBill> implements InvoiceSaleBillService {

    @Override
    public Page<InvoiceSaleBill> page(InvoiceSaleBillPageParam invoiceSaleBillPageParam) {
        QueryWrapper<InvoiceSaleBill> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(invoiceSaleBillPageParam.getFpNo())) {
            queryWrapper.lambda().like(InvoiceSaleBill::getFpNo, invoiceSaleBillPageParam.getFpNo());
        }
        if(ObjectUtil.isNotEmpty(invoiceSaleBillPageParam.getStartKpDate()) && ObjectUtil.isNotEmpty(invoiceSaleBillPageParam.getEndKpDate())) {
            queryWrapper.lambda().between(InvoiceSaleBill::getKpDate, invoiceSaleBillPageParam.getStartKpDate(), invoiceSaleBillPageParam.getEndKpDate());
        }
        if(ObjectUtil.isNotEmpty(invoiceSaleBillPageParam.getContent())) {
            queryWrapper.lambda().like(InvoiceSaleBill::getContent, invoiceSaleBillPageParam.getContent());
        }
        if(ObjectUtil.isAllNotEmpty(invoiceSaleBillPageParam.getSortField(), invoiceSaleBillPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(invoiceSaleBillPageParam.getSortOrder());
            queryWrapper.orderBy(true, invoiceSaleBillPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(invoiceSaleBillPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(InvoiceSaleBill::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(InvoiceSaleBillAddParam invoiceSaleBillAddParam) {
        InvoiceSaleBill invoiceSaleBill = BeanUtil.toBean(invoiceSaleBillAddParam, InvoiceSaleBill.class);
        this.save(invoiceSaleBill);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(InvoiceSaleBillEditParam invoiceSaleBillEditParam) {
        InvoiceSaleBill invoiceSaleBill = this.queryEntity(invoiceSaleBillEditParam.getId());
        BeanUtil.copyProperties(invoiceSaleBillEditParam, invoiceSaleBill);
        this.updateById(invoiceSaleBill);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<InvoiceSaleBillIdParam> invoiceSaleBillIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(invoiceSaleBillIdParamList, InvoiceSaleBillIdParam::getId));
    }

    @Override
    public InvoiceSaleBill detail(InvoiceSaleBillIdParam invoiceSaleBillIdParam) {
        return this.queryEntity(invoiceSaleBillIdParam.getId());
    }

    @Override
    public InvoiceSaleBill queryEntity(String id) {
        InvoiceSaleBill invoiceSaleBill = this.getById(id);
        if(ObjectUtil.isEmpty(invoiceSaleBill)) {
            throw new CommonException("财务系统-销售发票开具申请-开票确认【正式销售发票表】不存在，id值为：{}", id);
        }
        return invoiceSaleBill;
    }
}
