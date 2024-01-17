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
package vip.xiaonuo.biz.modular.prj.plan.service.impl;

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
import vip.xiaonuo.biz.modular.prj.plan.param.*;
import vip.xiaonuo.biz.modular.prj.plan.vo.PlanInvoiceVo;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.prj.plan.entity.BizPrjPlanInvoice;
import vip.xiaonuo.biz.modular.prj.plan.mapper.BizPrjPlanInvoiceMapper;
import vip.xiaonuo.biz.modular.prj.plan.service.BizPrjPlanInvoiceService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 业务表-项目计划付款单-本次提供发票Service接口实现类
 *
 * @author yong.ye
 * @date  2023/09/28 11:28
 **/

@DS("slave_1")
@Service
public class BizPrjPlanInvoiceServiceImpl extends ServiceImpl<BizPrjPlanInvoiceMapper, BizPrjPlanInvoice> implements BizPrjPlanInvoiceService {

    @Resource
    private BizPrjPlanInvoiceMapper bizPrjPlanInvoiceMapper;
    @Override
    public Page<BizPrjPlanInvoice> page(BizPrjPlanInvoicePageParam bizPrjPlanInvoicePageParam) {
        QueryWrapper<BizPrjPlanInvoice> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(bizPrjPlanInvoicePageParam.getSortField(), bizPrjPlanInvoicePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizPrjPlanInvoicePageParam.getSortOrder());
            queryWrapper.orderBy(true, bizPrjPlanInvoicePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizPrjPlanInvoicePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizPrjPlanInvoice::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizPrjPlanInvoiceAddParam bizPrjPlanInvoiceAddParam) {
        BizPrjPlanInvoice bizPrjPlanInvoice = BeanUtil.toBean(bizPrjPlanInvoiceAddParam, BizPrjPlanInvoice.class);
        this.save(bizPrjPlanInvoice);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizPrjPlanInvoiceEditParam bizPrjPlanInvoiceEditParam) {
        BizPrjPlanInvoice bizPrjPlanInvoice = this.queryEntity(bizPrjPlanInvoiceEditParam.getId());
        BeanUtil.copyProperties(bizPrjPlanInvoiceEditParam, bizPrjPlanInvoice);
        this.updateById(bizPrjPlanInvoice);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizPrjPlanInvoiceIdParam> bizPrjPlanInvoiceIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizPrjPlanInvoiceIdParamList, BizPrjPlanInvoiceIdParam::getId));
    }

    @Override
    public BizPrjPlanInvoice detail(BizPrjPlanInvoiceIdParam bizPrjPlanInvoiceIdParam) {
        return this.queryEntity(bizPrjPlanInvoiceIdParam.getId());
    }

    @Override
    public BizPrjPlanInvoice queryEntity(String id) {
        BizPrjPlanInvoice bizPrjPlanInvoice = this.getById(id);
        if(ObjectUtil.isEmpty(bizPrjPlanInvoice)) {
            throw new CommonException("业务表-项目计划付款单-本次提供发票不存在，id值为：{}", id);
        }
        return bizPrjPlanInvoice;
    }

    @Override
    public Page<PlanInvoiceVo> invoicePage(PlanInvoicePage planInvoicePage) {
        QueryWrapper<BizPrjPlanInvoice> queryWrapper = new QueryWrapper<>();
        //审核通过|审核中
        queryWrapper.in("a.STATUS","1","0");
        //有效数据
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        //合同编号
        if(ObjectUtil.isNotEmpty(planInvoicePage.getContractNo())){
            queryWrapper.eq("a.CONTRACT_NO",planInvoicePage.getContractNo());
        }
        //合同名称
        if(ObjectUtil.isNotEmpty(planInvoicePage.getContractName())){
            queryWrapper.like("a.CONTRACT_NAME",planInvoicePage.getContractName());
        }
        //计划单号
        if(ObjectUtil.isNotEmpty(planInvoicePage.getPlanNo())){
            queryWrapper.eq("a.PLAN_NO",planInvoicePage.getPlanNo());
        }
        //流程状态筛选
        if(ObjectUtil.isNotEmpty(planInvoicePage.getStatus())){
            queryWrapper.eq("a.STATUS",planInvoicePage.getStatus());
        }
        //关键字搜索
        if(ObjectUtil.isNotEmpty(planInvoicePage.getSearchKey())){
            queryWrapper.and(q->q.like("a.CONTRACT_NO",planInvoicePage.getSearchKey())
                    .or().like("a.CONTRACT_NAME",planInvoicePage.getSearchKey())
                    .or().like("a.PLAN_NO",planInvoicePage.getSearchKey()));
        }
        queryWrapper.orderByDesc("a.PLAN_NO").orderByDesc("b.CREATE_TIME");
        return bizPrjPlanInvoiceMapper.getInvoicePage(CommonPageRequest.defaultPage(), queryWrapper);
    }
}
