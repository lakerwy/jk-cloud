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
package vip.xiaonuo.biz.modular.prj.settle.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.prj.settle.entity.BizPrjSettleInvoice;
import vip.xiaonuo.biz.modular.prj.settle.param.BizPrjSettleInvoiceAddParam;
import vip.xiaonuo.biz.modular.prj.settle.param.BizPrjSettleInvoiceEditParam;
import vip.xiaonuo.biz.modular.prj.settle.param.BizPrjSettleInvoiceIdParam;
import vip.xiaonuo.biz.modular.prj.settle.param.BizPrjSettleInvoicePageParam;

import java.util.List;

/**
 * 业务表-项目款结算单-本次开票Service接口
 *
 * @author yong.ye
 * @date  2023/09/28 11:37
 **/
public interface BizPrjSettleInvoiceService extends IService<BizPrjSettleInvoice> {

    /**
     * 获取业务表-项目款结算单-本次开票分页
     *
     * @author yong.ye
     * @date  2023/09/28 11:37
     */
    Page<BizPrjSettleInvoice> page(BizPrjSettleInvoicePageParam bizPrjSettleInvoicePageParam);

    /**
     * 添加业务表-项目款结算单-本次开票
     *
     * @author yong.ye
     * @date  2023/09/28 11:37
     */
    void add(BizPrjSettleInvoiceAddParam bizPrjSettleInvoiceAddParam);

    /**
     * 编辑业务表-项目款结算单-本次开票
     *
     * @author yong.ye
     * @date  2023/09/28 11:37
     */
    void edit(BizPrjSettleInvoiceEditParam bizPrjSettleInvoiceEditParam);

    /**
     * 删除业务表-项目款结算单-本次开票
     *
     * @author yong.ye
     * @date  2023/09/28 11:37
     */
    void delete(List<BizPrjSettleInvoiceIdParam> bizPrjSettleInvoiceIdParamList);

    /**
     * 获取业务表-项目款结算单-本次开票详情
     *
     * @author yong.ye
     * @date  2023/09/28 11:37
     */
    BizPrjSettleInvoice detail(BizPrjSettleInvoiceIdParam bizPrjSettleInvoiceIdParam);

    /**
     * 获取业务表-项目款结算单-本次开票详情
     *
     * @author yong.ye
     * @date  2023/09/28 11:37
     **/
    BizPrjSettleInvoice queryEntity(String id);
}
