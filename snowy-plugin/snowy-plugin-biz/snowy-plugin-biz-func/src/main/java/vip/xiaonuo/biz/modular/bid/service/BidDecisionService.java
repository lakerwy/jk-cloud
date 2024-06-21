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
package vip.xiaonuo.biz.modular.bid.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.bid.entity.BidDecision;
import vip.xiaonuo.biz.modular.bid.param.BidDecisionAddParam;
import vip.xiaonuo.biz.modular.bid.param.BidDecisionEditParam;
import vip.xiaonuo.biz.modular.bid.param.BidDecisionIdParam;
import vip.xiaonuo.biz.modular.bid.param.BidDecisionPageParam;

import java.util.List;

/**
 * 投标决策 - 申请流程Service接口
 *
 * @author yong.ye
 * @date  2024/06/21 10:30
 **/
public interface BidDecisionService extends IService<BidDecision> {

    /**
     * 获取投标决策 - 申请流程分页
     *
     * @author yong.ye
     * @date  2024/06/21 10:30
     */
    Page<BidDecision> page(BidDecisionPageParam bidDecisionPageParam);

    /**
     * 添加投标决策 - 申请流程
     *
     * @author yong.ye
     * @date  2024/06/21 10:30
     */
    void add(BidDecisionAddParam bidDecisionAddParam);

    /**
     * 编辑投标决策 - 申请流程
     *
     * @author yong.ye
     * @date  2024/06/21 10:30
     */
    void edit(BidDecisionEditParam bidDecisionEditParam);

    /**
     * 删除投标决策 - 申请流程
     *
     * @author yong.ye
     * @date  2024/06/21 10:30
     */
    void delete(List<BidDecisionIdParam> bidDecisionIdParamList);

    /**
     * 获取投标决策 - 申请流程详情
     *
     * @author yong.ye
     * @date  2024/06/21 10:30
     */
    BidDecision detail(BidDecisionIdParam bidDecisionIdParam);

    /**
     * 获取投标决策 - 申请流程详情
     *
     * @author yong.ye
     * @date  2024/06/21 10:30
     **/
    BidDecision queryEntity(String id);
}
