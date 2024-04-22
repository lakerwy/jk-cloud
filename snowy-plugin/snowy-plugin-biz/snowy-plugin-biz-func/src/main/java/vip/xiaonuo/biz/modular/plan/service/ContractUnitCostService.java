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
import vip.xiaonuo.biz.modular.plan.entity.ContractUnitCost;
import vip.xiaonuo.biz.modular.plan.param.ContractUnitCostAddParam;
import vip.xiaonuo.biz.modular.plan.param.ContractUnitCostEditParam;
import vip.xiaonuo.biz.modular.plan.param.ContractUnitCostIdParam;
import vip.xiaonuo.biz.modular.plan.param.ContractUnitCostPageParam;

import java.util.List;

/**
 * 合约规划 - 申报（项目承包方）- 成本Service接口
 *
 * @author yong.ye
 * @date  2024/04/22 11:44
 **/
public interface ContractUnitCostService extends IService<ContractUnitCost> {

    /**
     * 获取合约规划 - 申报（项目承包方）- 成本分页
     *
     * @author yong.ye
     * @date  2024/04/22 11:44
     */
    Page<ContractUnitCost> page(ContractUnitCostPageParam contractUnitCostPageParam);

    /**
     * 添加合约规划 - 申报（项目承包方）- 成本
     *
     * @author yong.ye
     * @date  2024/04/22 11:44
     */
    void add(ContractUnitCostAddParam contractUnitCostAddParam);

    /**
     * 编辑合约规划 - 申报（项目承包方）- 成本
     *
     * @author yong.ye
     * @date  2024/04/22 11:44
     */
    void edit(ContractUnitCostEditParam contractUnitCostEditParam);

    /**
     * 删除合约规划 - 申报（项目承包方）- 成本
     *
     * @author yong.ye
     * @date  2024/04/22 11:44
     */
    void delete(List<ContractUnitCostIdParam> contractUnitCostIdParamList);

    /**
     * 获取合约规划 - 申报（项目承包方）- 成本详情
     *
     * @author yong.ye
     * @date  2024/04/22 11:44
     */
    ContractUnitCost detail(ContractUnitCostIdParam contractUnitCostIdParam);

    /**
     * 获取合约规划 - 申报（项目承包方）- 成本详情
     *
     * @author yong.ye
     * @date  2024/04/22 11:44
     **/
    ContractUnitCost queryEntity(String id);
}
