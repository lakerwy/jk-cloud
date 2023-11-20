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
package vip.xiaonuo.biz.modular.prj.allotment.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.prj.allotment.entity.BizPrjAllotment;
import vip.xiaonuo.biz.modular.prj.allotment.param.BizPrjAllotmentAddParam;
import vip.xiaonuo.biz.modular.prj.allotment.param.BizPrjAllotmentEditParam;
import vip.xiaonuo.biz.modular.prj.allotment.param.BizPrjAllotmentIdParam;
import vip.xiaonuo.biz.modular.prj.allotment.param.BizPrjAllotmentPageParam;
import vip.xiaonuo.biz.modular.prj.allotment.vo.BizPrjAllotmentVo;
import vip.xiaonuo.biz.modular.prj.plan.param.InvoiceParam;

import java.util.List;

/**
 * 业务表-项目计划付款单Service接口
 *
 * @author yong.ye
 * @date  2023/09/28 11:30
 **/
public interface BizPrjAllotmentService extends IService<BizPrjAllotment> {

    /**
     * 获取业务表-项目计划付款单分页
     *
     * @author yong.ye
     * @date  2023/09/28 11:30
     */
    Page<BizPrjAllotmentVo> page(BizPrjAllotmentPageParam bizPrjAllotmentPageParam);

    /**
     * 添加业务表-项目计划付款单
     *
     * @author yong.ye
     * @date  2023/09/28 11:30
     */
    void add(BizPrjAllotmentAddParam bizPrjAllotmentAddParam);

    /**
     * 编辑业务表-项目计划付款单
     *
     * @author yong.ye
     * @date  2023/09/28 11:30
     */
    void edit(BizPrjAllotmentEditParam bizPrjAllotmentEditParam);

    /**
     * 删除业务表-项目计划付款单
     *
     * @author yong.ye
     * @date  2023/09/28 11:30
     */
    void delete(List<BizPrjAllotmentIdParam> bizPrjAllotmentIdParamList);

    /**
     * 获取业务表-项目计划付款单详情
     *
     * @author yong.ye
     * @date  2023/09/28 11:30
     */
    BizPrjAllotmentVo detail(BizPrjAllotmentIdParam bizPrjAllotmentIdParam);

    /**
     * 获取业务表-项目计划付款单详情
     *
     * @author yong.ye
     * @date  2023/09/28 11:30
     **/
    BizPrjAllotment queryEntity(String id);

    /**
     * 根据 【合同编号】 - 获取 审核通过 的历史【项目付款单申请】集合
     * @param invoiceParam
     * @return
     */
    List<BizPrjAllotmentVo> allotmentList(InvoiceParam invoiceParam);
}
