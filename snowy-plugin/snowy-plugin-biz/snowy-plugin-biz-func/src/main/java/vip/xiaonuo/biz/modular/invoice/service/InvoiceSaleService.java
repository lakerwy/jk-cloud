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
package vip.xiaonuo.biz.modular.invoice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.invoice.entity.InvoiceSale;
import vip.xiaonuo.biz.modular.invoice.param.Customer.InvoiceSaleAdd;
import vip.xiaonuo.biz.modular.invoice.param.Customer.InvoiceSaleEdit;
import vip.xiaonuo.biz.modular.invoice.param.InvoiceSaleIdParam;
import vip.xiaonuo.biz.modular.invoice.param.InvoiceSalePageParam;
import vip.xiaonuo.biz.modular.invoice.vo.InvoiceInfo;
import vip.xiaonuo.biz.modular.invoice.vo.InvoiceLjInfoVo;
import vip.xiaonuo.biz.modular.invoice.vo.InvoiceSaleInfo;
import vip.xiaonuo.biz.modular.invoice.vo.InvoiceSaleVo;

import java.util.List;

/**
 * 财务系统-销售发票开具申请Service接口
 *
 * @author yong.ye
 * @date  2024/03/19 10:17
 **/
public interface InvoiceSaleService extends IService<InvoiceSale> {

    /**
     * 获取财务系统-销售发票开具申请分页
     *
     * @author yong.ye
     * @date  2024/03/19 10:17
     */
    Page<InvoiceSaleVo> page(InvoiceSalePageParam invoiceSalePageParam);

    /**
     * 添加财务系统-销售发票开具申请
     *
     * @author yong.ye
     * @date  2024/03/19 10:17
     */
    void add(InvoiceSaleAdd invoiceSaleAdd);

    /**
     * 编辑财务系统-销售发票开具申请
     *
     * @author yong.ye
     * @date  2024/03/19 10:17
     */
    void edit(InvoiceSaleEdit invoiceSaleEdit);

    /**
     * 删除财务系统-销售发票开具申请
     *
     * @author yong.ye
     * @date  2024/03/19 10:17
     */
    void delete(List<InvoiceSaleIdParam> invoiceSaleIdParamList);

    /**
     * 获取财务系统-销售发票开具申请详情
     *
     * @author yong.ye
     * @date  2024/03/19 10:17
     */
    InvoiceSaleVo detail(InvoiceSaleIdParam invoiceSaleIdParam);

    /**
     * 获取财务系统-销售发票开具申请详情
     *
     * @author yong.ye
     * @date  2024/03/19 10:17
     **/
    InvoiceSale queryEntity(String id);

    /**
     * 发票号维度 - 分页
     * @param invoiceSalePageParam
     * @return
     */
    Page<InvoiceSaleInfo> billPage(InvoiceSalePageParam invoiceSalePageParam);

    /**
     * 根据合同编号，获取上次审核通过的累计金额
     * @param htContractNo
     * @return
     */
    InvoiceLjInfoVo getTopLjInfo(String htContractNo);

    /**
     * 根据 销售发票申请单号 - 维度 - 统计 销售发票基本信息|开票小计|成品应收小计|开票确认小计|成本票小计 供应商+税率 维度 小计
     * @param invoiceSalePageParam
     * @return
     */
    Page<InvoiceInfo> getTotal(InvoiceSalePageParam invoiceSalePageParam);
}
