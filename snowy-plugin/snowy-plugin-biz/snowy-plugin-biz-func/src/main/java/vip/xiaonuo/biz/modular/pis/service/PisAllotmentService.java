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
package vip.xiaonuo.biz.modular.pis.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.pis.entity.PisAllotment;
import vip.xiaonuo.biz.modular.pis.param.PisAllotmentAddParam;
import vip.xiaonuo.biz.modular.pis.param.PisAllotmentEditParam;
import vip.xiaonuo.biz.modular.pis.param.PisAllotmentIdParam;
import vip.xiaonuo.biz.modular.pis.param.PisAllotmentPageParam;

import java.util.List;

/**
 * 生产院及子公司项目拨付款 - 申请流程Service接口
 *
 * @author yong.ye
 * @date  2024/07/03 15:29
 **/
public interface PisAllotmentService extends IService<PisAllotment> {

    /**
     * 获取生产院及子公司项目拨付款 - 申请流程分页
     *
     * @author yong.ye
     * @date  2024/07/03 15:29
     */
    Page<PisAllotment> page(PisAllotmentPageParam pisAllotmentPageParam);

    /**
     * 添加生产院及子公司项目拨付款 - 申请流程
     *
     * @author yong.ye
     * @date  2024/07/03 15:29
     */
    void add(PisAllotmentAddParam pisAllotmentAddParam);

    /**
     * 编辑生产院及子公司项目拨付款 - 申请流程
     *
     * @author yong.ye
     * @date  2024/07/03 15:29
     */
    void edit(PisAllotmentEditParam pisAllotmentEditParam);

    /**
     * 删除生产院及子公司项目拨付款 - 申请流程
     *
     * @author yong.ye
     * @date  2024/07/03 15:29
     */
    void delete(List<PisAllotmentIdParam> pisAllotmentIdParamList);

    /**
     * 获取生产院及子公司项目拨付款 - 申请流程详情
     *
     * @author yong.ye
     * @date  2024/07/03 15:29
     */
    PisAllotment detail(PisAllotmentIdParam pisAllotmentIdParam);

    /**
     * 获取生产院及子公司项目拨付款 - 申请流程详情
     *
     * @author yong.ye
     * @date  2024/07/03 15:29
     **/
    PisAllotment queryEntity(String id);

    /**
     * 根据总包合同Id,获取前一次审批通过的拨付款单信息
     * @param htId 合同ID
     * @return
     */
    PisAllotment getByHtId(String htId);
}
