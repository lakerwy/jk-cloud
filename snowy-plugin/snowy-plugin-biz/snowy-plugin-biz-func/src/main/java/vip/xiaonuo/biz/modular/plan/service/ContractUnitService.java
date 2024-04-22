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
import vip.xiaonuo.biz.modular.plan.entity.ContractUnit;
import vip.xiaonuo.biz.modular.plan.param.ContractUnitAddParam;
import vip.xiaonuo.biz.modular.plan.param.ContractUnitEditParam;
import vip.xiaonuo.biz.modular.plan.param.ContractUnitIdParam;
import vip.xiaonuo.biz.modular.plan.param.ContractUnitPageParam;

import java.util.List;

/**
 * 合约规划 - 申报（项目承包方）-收入&效益Service接口
 *
 * @author yong.ye
 * @date  2024/04/22 11:43
 **/
public interface ContractUnitService extends IService<ContractUnit> {

    /**
     * 获取合约规划 - 申报（项目承包方）-收入&效益分页
     *
     * @author yong.ye
     * @date  2024/04/22 11:43
     */
    Page<ContractUnit> page(ContractUnitPageParam contractUnitPageParam);

    /**
     * 添加合约规划 - 申报（项目承包方）-收入&效益
     *
     * @author yong.ye
     * @date  2024/04/22 11:43
     */
    void add(ContractUnitAddParam contractUnitAddParam);

    /**
     * 编辑合约规划 - 申报（项目承包方）-收入&效益
     *
     * @author yong.ye
     * @date  2024/04/22 11:43
     */
    void edit(ContractUnitEditParam contractUnitEditParam);

    /**
     * 删除合约规划 - 申报（项目承包方）-收入&效益
     *
     * @author yong.ye
     * @date  2024/04/22 11:43
     */
    void delete(List<ContractUnitIdParam> contractUnitIdParamList);

    /**
     * 获取合约规划 - 申报（项目承包方）-收入&效益详情
     *
     * @author yong.ye
     * @date  2024/04/22 11:43
     */
    ContractUnit detail(ContractUnitIdParam contractUnitIdParam);

    /**
     * 获取合约规划 - 申报（项目承包方）-收入&效益详情
     *
     * @author yong.ye
     * @date  2024/04/22 11:43
     **/
    ContractUnit queryEntity(String id);
}
