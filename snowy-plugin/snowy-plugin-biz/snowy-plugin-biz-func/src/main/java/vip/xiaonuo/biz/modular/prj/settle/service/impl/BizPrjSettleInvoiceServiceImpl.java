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
package vip.xiaonuo.biz.modular.prj.settle.service.impl;

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
import vip.xiaonuo.biz.modular.prj.settle.param.*;
import vip.xiaonuo.biz.modular.prj.settle.vo.SettleInvoiceVo;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.prj.settle.entity.BizPrjSettleInvoice;
import vip.xiaonuo.biz.modular.prj.settle.mapper.BizPrjSettleInvoiceMapper;
import vip.xiaonuo.biz.modular.prj.settle.service.BizPrjSettleInvoiceService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 业务表-项目款结算单-本次开票Service接口实现类
 *
 * @author yong.ye
 * @date  2023/09/28 11:37
 **/

@DS("slave_1")
@Service
public class BizPrjSettleInvoiceServiceImpl extends ServiceImpl<BizPrjSettleInvoiceMapper, BizPrjSettleInvoice> implements BizPrjSettleInvoiceService {

    @Resource
    private BizPrjSettleInvoiceMapper bizPrjSettleInvoiceMapper;
    @Override
    public Page<BizPrjSettleInvoice> page(BizPrjSettleInvoicePageParam bizPrjSettleInvoicePageParam) {
        QueryWrapper<BizPrjSettleInvoice> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(bizPrjSettleInvoicePageParam.getSortField(), bizPrjSettleInvoicePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizPrjSettleInvoicePageParam.getSortOrder());
            queryWrapper.orderBy(true, bizPrjSettleInvoicePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizPrjSettleInvoicePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizPrjSettleInvoice::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizPrjSettleInvoiceAddParam bizPrjSettleInvoiceAddParam) {
        BizPrjSettleInvoice bizPrjSettleInvoice = BeanUtil.toBean(bizPrjSettleInvoiceAddParam, BizPrjSettleInvoice.class);
        this.save(bizPrjSettleInvoice);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizPrjSettleInvoiceEditParam bizPrjSettleInvoiceEditParam) {
        BizPrjSettleInvoice bizPrjSettleInvoice = this.queryEntity(bizPrjSettleInvoiceEditParam.getId());
        BeanUtil.copyProperties(bizPrjSettleInvoiceEditParam, bizPrjSettleInvoice);
        this.updateById(bizPrjSettleInvoice);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizPrjSettleInvoiceIdParam> bizPrjSettleInvoiceIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizPrjSettleInvoiceIdParamList, BizPrjSettleInvoiceIdParam::getId));
    }

    @Override
    public BizPrjSettleInvoice detail(BizPrjSettleInvoiceIdParam bizPrjSettleInvoiceIdParam) {
        return this.queryEntity(bizPrjSettleInvoiceIdParam.getId());
    }

    @Override
    public BizPrjSettleInvoice queryEntity(String id) {
        BizPrjSettleInvoice bizPrjSettleInvoice = this.getById(id);
        if(ObjectUtil.isEmpty(bizPrjSettleInvoice)) {
            throw new CommonException("业务表-项目款结算单-本次开票不存在，id值为：{}", id);
        }
        return bizPrjSettleInvoice;
    }

    @Override
    public Page<SettleInvoiceVo> invoicePage(SettleInvoicePage settleInvoicePage) {
        QueryWrapper<BizPrjSettleInvoice> queryWrapper = new QueryWrapper<>();
        //审核通过|审核中
        queryWrapper.in("a.STATUS","1","0");
        //有效数据
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        //合同编号
        if(ObjectUtil.isNotEmpty(settleInvoicePage.getContractNo())){
            queryWrapper.eq("a.CONTRACT_NO",settleInvoicePage.getContractNo());
        }
        //合同名称
        if(ObjectUtil.isNotEmpty(settleInvoicePage.getContractName())){
            queryWrapper.like("a.CONTRACT_NAME",settleInvoicePage.getContractName());
        }
        //计划单号
        if(ObjectUtil.isNotEmpty(settleInvoicePage.getSettleNo())){
            queryWrapper.eq("a.SETTLE_NO",settleInvoicePage.getSettleNo());
        }
        //流程状态筛选
        if(ObjectUtil.isNotEmpty(settleInvoicePage.getStatus())){
            queryWrapper.eq("a.STATUS",settleInvoicePage.getStatus());
        }
        queryWrapper.isNotNull("b.ID");
        //关键字搜索
        if(ObjectUtil.isNotEmpty(settleInvoicePage.getSearchKey())){
            queryWrapper.and(q->q.like("a.CONTRACT_NO",settleInvoicePage.getSearchKey())
                    .or().like("a.CONTRACT_NAME",settleInvoicePage.getSearchKey())
                    .or().like("a.SETTLE_NO",settleInvoicePage.getSearchKey()));
        }
        queryWrapper.orderByDesc("a.SETTLE_NO").orderByDesc("b.CREATE_TIME");
        return bizPrjSettleInvoiceMapper.getInvoicePage(CommonPageRequest.defaultPage(), queryWrapper);
    }
}
