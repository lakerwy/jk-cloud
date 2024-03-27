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
import vip.xiaonuo.biz.modular.invoice.vo.InvoiceCostBillVo;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.invoice.entity.InvoiceCostBill;
import vip.xiaonuo.biz.modular.invoice.mapper.InvoiceCostBillMapper;
import vip.xiaonuo.biz.modular.invoice.param.InvoiceCostBillAddParam;
import vip.xiaonuo.biz.modular.invoice.param.InvoiceCostBillEditParam;
import vip.xiaonuo.biz.modular.invoice.param.InvoiceCostBillIdParam;
import vip.xiaonuo.biz.modular.invoice.param.InvoiceCostBillPageParam;
import vip.xiaonuo.biz.modular.invoice.service.InvoiceCostBillService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 财务系统-成本发票申请【正式成本发票表】Service接口实现类
 *
 * @author yong.ye
 * @date  2024/03/19 10:23
 **/
@Service
@DS("slave_1")
public class InvoiceCostBillServiceImpl extends ServiceImpl<InvoiceCostBillMapper, InvoiceCostBill> implements InvoiceCostBillService {

    @Resource
    private InvoiceCostBillMapper invoiceCostBillMapper;

    @Override
    public Page<InvoiceCostBillVo> page(InvoiceCostBillPageParam invoiceCostBillPageParam) {
        QueryWrapper<InvoiceCostBill> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        queryWrapper.eq("b.DELETE_FLAG","NOT_DELETE");
        queryWrapper.in("b.STATUS", FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
        if(ObjectUtil.isNotEmpty(invoiceCostBillPageParam.getStartKpDate()) && ObjectUtil.isNotEmpty(invoiceCostBillPageParam.getEndKpDate())) {
            queryWrapper.between("a.KP_DATE", invoiceCostBillPageParam.getStartKpDate(), invoiceCostBillPageParam.getEndKpDate());
        }
        if(ObjectUtil.isNotEmpty(invoiceCostBillPageParam.getFbContractNo())) {
            queryWrapper.like("a.FB_CONTRACT_NO", invoiceCostBillPageParam.getFbContractNo());
        }
        if(ObjectUtil.isNotEmpty(invoiceCostBillPageParam.getPrvName())) {
            queryWrapper.like("a.PRV_NAME", invoiceCostBillPageParam.getPrvName());
        }
        if(ObjectUtil.isNotEmpty(invoiceCostBillPageParam.getFpType())) {
            queryWrapper.eq("a.FP_TYPE", invoiceCostBillPageParam.getFpType());
        }
        if(ObjectUtil.isNotEmpty(invoiceCostBillPageParam.getFpNo())) {
            queryWrapper.like("a.FP_NO", invoiceCostBillPageParam.getFpNo());
        }
        if(ObjectUtil.isNotEmpty(invoiceCostBillPageParam.getContent())) {
            queryWrapper.like("a.CONTENT", invoiceCostBillPageParam.getContent());
        }
        if(ObjectUtil.isNotEmpty(invoiceCostBillPageParam.getUnitCode())) {
            queryWrapper.like("a.UNIT_CODE", invoiceCostBillPageParam.getUnitCode());
        }
        if(ObjectUtil.isNotEmpty(invoiceCostBillPageParam.getUnitName())) {
            queryWrapper.like("a.UNIT_NAME", invoiceCostBillPageParam.getUnitName());
        }
        if(ObjectUtil.isNotEmpty(invoiceCostBillPageParam.getSearchKey())){
            queryWrapper.and(q->q.like("a.FB_CONTRACT_NO",invoiceCostBillPageParam.getSearchKey())
                    .or().like("a.FP_NO",invoiceCostBillPageParam.getSearchKey())
                    .or().like("a.UNIT_CODE",invoiceCostBillPageParam.getSearchKey())
                    .or().like("a.UNIT_NAME",invoiceCostBillPageParam.getSearchKey())
                    .or().like("a.CONTENT",invoiceCostBillPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(invoiceCostBillPageParam.getSortField(), invoiceCostBillPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(invoiceCostBillPageParam.getSortOrder());
            queryWrapper.orderBy(true, invoiceCostBillPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(invoiceCostBillPageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("a.CREATE_TIME");
        }
        return invoiceCostBillMapper.getCostBillPage(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(InvoiceCostBillAddParam invoiceCostBillAddParam) {
        InvoiceCostBill invoiceCostBill = BeanUtil.toBean(invoiceCostBillAddParam, InvoiceCostBill.class);
        this.save(invoiceCostBill);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(InvoiceCostBillEditParam invoiceCostBillEditParam) {
        InvoiceCostBill invoiceCostBill = this.queryEntity(invoiceCostBillEditParam.getId());
        BeanUtil.copyProperties(invoiceCostBillEditParam, invoiceCostBill);
        this.updateById(invoiceCostBill);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<InvoiceCostBillIdParam> invoiceCostBillIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(invoiceCostBillIdParamList, InvoiceCostBillIdParam::getId));
    }

    @Override
    public InvoiceCostBill detail(InvoiceCostBillIdParam invoiceCostBillIdParam) {
        return this.queryEntity(invoiceCostBillIdParam.getId());
    }

    @Override
    public InvoiceCostBill queryEntity(String id) {
        InvoiceCostBill invoiceCostBill = this.getById(id);
        if(ObjectUtil.isEmpty(invoiceCostBill)) {
            throw new CommonException("财务系统-成本发票申请【正式成本发票表】不存在，id值为：{}", id);
        }
        return invoiceCostBill;
    }
}
