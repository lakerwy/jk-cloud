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
package vip.xiaonuo.biz.modular.prj.plan.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.prj.plan.entity.BizPrjPlanInvoice;
import vip.xiaonuo.biz.modular.prj.plan.param.*;
import vip.xiaonuo.biz.modular.prj.plan.vo.PlanInvoiceVo;

import java.util.List;

/**
 * 业务表-项目计划付款单-本次提供发票Service接口
 *
 * @author yong.ye
 * @date  2023/09/28 11:28
 **/
public interface BizPrjPlanInvoiceService extends IService<BizPrjPlanInvoice> {

    /**
     * 获取业务表-项目计划付款单-本次提供发票分页
     *
     * @author yong.ye
     * @date  2023/09/28 11:28
     */
    Page<BizPrjPlanInvoice> page(BizPrjPlanInvoicePageParam bizPrjPlanInvoicePageParam);

    /**
     * 添加业务表-项目计划付款单-本次提供发票
     *
     * @author yong.ye
     * @date  2023/09/28 11:28
     */
    void add(BizPrjPlanInvoiceAddParam bizPrjPlanInvoiceAddParam);

    /**
     * 编辑业务表-项目计划付款单-本次提供发票
     *
     * @author yong.ye
     * @date  2023/09/28 11:28
     */
    void edit(BizPrjPlanInvoiceEditParam bizPrjPlanInvoiceEditParam);

    /**
     * 删除业务表-项目计划付款单-本次提供发票
     *
     * @author yong.ye
     * @date  2023/09/28 11:28
     */
    void delete(List<BizPrjPlanInvoiceIdParam> bizPrjPlanInvoiceIdParamList);

    /**
     * 获取业务表-项目计划付款单-本次提供发票详情
     *
     * @author yong.ye
     * @date  2023/09/28 11:28
     */
    BizPrjPlanInvoice detail(BizPrjPlanInvoiceIdParam bizPrjPlanInvoiceIdParam);

    /**
     * 获取业务表-项目计划付款单-本次提供发票详情
     *
     * @author yong.ye
     * @date  2023/09/28 11:28
     **/
    BizPrjPlanInvoice queryEntity(String id);

    /**
     * 计划的 - 发票信息 分页查询
     * @param planInvoicePage
     * @return
     */
    Page<PlanInvoiceVo> invoicePage(PlanInvoicePage planInvoicePage);
}
