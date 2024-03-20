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
import vip.xiaonuo.biz.modular.invoice.entity.InvoicePrjRefund;
import vip.xiaonuo.biz.modular.invoice.param.Customer.DoImport;
import vip.xiaonuo.biz.modular.invoice.param.Customer.ImportRefund;
import vip.xiaonuo.biz.modular.invoice.param.InvoicePrjRefundAddParam;
import vip.xiaonuo.biz.modular.invoice.param.InvoicePrjRefundEditParam;
import vip.xiaonuo.biz.modular.invoice.param.InvoicePrjRefundIdParam;
import vip.xiaonuo.biz.modular.invoice.param.InvoicePrjRefundPageParam;
import vip.xiaonuo.biz.modular.invoice.vo.ImportResult;

import java.util.List;

/**
 * 财务系统-项目回款录入申请Service接口
 *
 * @author yong.ye
 * @date  2024/03/19 10:24
 **/
public interface InvoicePrjRefundService extends IService<InvoicePrjRefund> {

    /**
     * 获取财务系统-项目回款录入申请分页
     *
     * @author yong.ye
     * @date  2024/03/19 10:24
     */
    Page<InvoicePrjRefund> page(InvoicePrjRefundPageParam invoicePrjRefundPageParam);

    /**
     * 添加财务系统-项目回款录入申请
     *
     * @author yong.ye
     * @date  2024/03/19 10:24
     */
    void add(InvoicePrjRefundAddParam invoicePrjRefundAddParam);

    /**
     * 编辑财务系统-项目回款录入申请
     *
     * @author yong.ye
     * @date  2024/03/19 10:24
     */
    void edit(InvoicePrjRefundEditParam invoicePrjRefundEditParam);

    /**
     * 删除财务系统-项目回款录入申请
     *
     * @author yong.ye
     * @date  2024/03/19 10:24
     */
    void delete(List<InvoicePrjRefundIdParam> invoicePrjRefundIdParamList);

    /**
     * 获取财务系统-项目回款录入申请详情
     *
     * @author yong.ye
     * @date  2024/03/19 10:24
     */
    InvoicePrjRefund detail(InvoicePrjRefundIdParam invoicePrjRefundIdParam);

    /**
     * 获取财务系统-项目回款录入申请详情
     *
     * @author yong.ye
     * @date  2024/03/19 10:24
     **/
    InvoicePrjRefund queryEntity(String id);

    /**
     * 导入项目回款信息
     * @param importRefund
     */
    ImportResult importRefund(ImportRefund importRefund);

    /**
     * 正式导入
     * @param input
     * @return
     */
    Boolean doImport(DoImport input);
}
