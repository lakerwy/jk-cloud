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
package vip.xiaonuo.biz.modular.plan.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.plan.entity.ContractPlan;
import vip.xiaonuo.biz.modular.plan.param.ContractPlanAddParam;
import vip.xiaonuo.biz.modular.plan.param.ContractPlanEditParam;
import vip.xiaonuo.biz.modular.plan.param.ContractPlanIdParam;
import vip.xiaonuo.biz.modular.plan.param.ContractPlanPageParam;
import vip.xiaonuo.biz.modular.plan.vo.ContractPlanVo;

import java.util.List;

/**
 * 合约规划 - 申请流程Service接口
 *
 * @author yong.ye
 * @date  2024/04/22 11:41
 **/
public interface ContractPlanService extends IService<ContractPlan> {

    /**
     * 获取合约规划 - 申请流程分页
     *
     * @author yong.ye
     * @date  2024/04/22 11:41
     */
    Page<ContractPlanVo> page(ContractPlanPageParam contractPlanPageParam);

    /**
     * 添加合约规划 - 申请流程
     *
     * @author yong.ye
     * @date  2024/04/22 11:41
     */
    void add(ContractPlanAddParam contractPlanAddParam);

    /**
     * 编辑合约规划 - 申请流程
     *
     * @author yong.ye
     * @date  2024/04/22 11:41
     */
    void edit(ContractPlanEditParam contractPlanEditParam);

    /**
     * 删除合约规划 - 申请流程
     *
     * @author yong.ye
     * @date  2024/04/22 11:41
     */
    void delete(List<ContractPlanIdParam> contractPlanIdParamList);

    /**
     * 获取合约规划 - 申请流程详情
     *
     * @author yong.ye
     * @date  2024/04/22 11:41
     */
    ContractPlanVo detail(ContractPlanIdParam contractPlanIdParam);

    /**
     * 获取合约规划 - 申请流程详情
     *
     * @author yong.ye
     * @date  2024/04/22 11:41
     **/
    ContractPlan queryEntity(String id);
}
