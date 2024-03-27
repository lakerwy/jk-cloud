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
import vip.xiaonuo.biz.modular.invoice.entity.InvoicePrjContract;
import vip.xiaonuo.biz.modular.invoice.param.InvoicePrjContractAddParam;
import vip.xiaonuo.biz.modular.invoice.param.InvoicePrjContractEditParam;
import vip.xiaonuo.biz.modular.invoice.param.InvoicePrjContractIdParam;
import vip.xiaonuo.biz.modular.invoice.param.InvoicePrjContractPageParam;
import vip.xiaonuo.biz.modular.invoice.vo.InvoicePrjContractVo;

import java.util.List;

/**
 * 财务系统-项目回款录入申请-销售合同Service接口
 *
 * @author yong.ye
 * @date  2024/03/27 11:30
 **/
public interface InvoicePrjContractService extends IService<InvoicePrjContract> {

    /**
     * 获取财务系统-项目回款录入申请-销售合同分页
     *
     * @author yong.ye
     * @date  2024/03/27 11:30
     */
    Page<InvoicePrjContractVo> page(InvoicePrjContractPageParam invoicePrjContractPageParam);

    /**
     * 添加财务系统-项目回款录入申请-销售合同
     *
     * @author yong.ye
     * @date  2024/03/27 11:30
     */
    void add(InvoicePrjContractAddParam invoicePrjContractAddParam);

    /**
     * 编辑财务系统-项目回款录入申请-销售合同
     *
     * @author yong.ye
     * @date  2024/03/27 11:30
     */
    void edit(InvoicePrjContractEditParam invoicePrjContractEditParam);

    /**
     * 删除财务系统-项目回款录入申请-销售合同
     *
     * @author yong.ye
     * @date  2024/03/27 11:30
     */
    void delete(List<InvoicePrjContractIdParam> invoicePrjContractIdParamList);

    /**
     * 获取财务系统-项目回款录入申请-销售合同详情
     *
     * @author yong.ye
     * @date  2024/03/27 11:30
     */
    InvoicePrjContract detail(InvoicePrjContractIdParam invoicePrjContractIdParam);

    /**
     * 获取财务系统-项目回款录入申请-销售合同详情
     *
     * @author yong.ye
     * @date  2024/03/27 11:30
     **/
    InvoicePrjContract queryEntity(String id);
}
