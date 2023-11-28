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
package vip.xiaonuo.biz.modular.crm.pursalecontract.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.crm.pursalecontract.entity.Pursalecontract;
import vip.xiaonuo.biz.modular.crm.pursalecontract.param.PursalecontractAddParam;
import vip.xiaonuo.biz.modular.crm.pursalecontract.param.PursalecontractEditParam;
import vip.xiaonuo.biz.modular.crm.pursalecontract.param.PursalecontractIdParam;
import vip.xiaonuo.biz.modular.crm.pursalecontract.param.PursalecontractPageParam;

import java.util.List;

/**
 * 业务表-CRM-购销合同Service接口
 *
 * @author yong.ye
 * @date  2023/06/30 16:03
 **/
public interface PursalecontractService extends IService<Pursalecontract> {

    /**
     * 获取业务表-CRM-购销合同分页
     *
     * @author yong.ye
     * @date  2023/06/30 16:03
     */
    Page<Pursalecontract> page(PursalecontractPageParam pursalecontractPageParam);

    /**
     * 添加业务表-CRM-购销合同
     *
     * @author yong.ye
     * @date  2023/06/30 16:03
     */
    void add(PursalecontractAddParam pursalecontractAddParam);

    /**
     * 编辑业务表-CRM-购销合同
     *
     * @author yong.ye
     * @date  2023/06/30 16:03
     */
    void edit(PursalecontractEditParam pursalecontractEditParam);

    /**
     * 删除业务表-CRM-购销合同
     *
     * @author yong.ye
     * @date  2023/06/30 16:03
     */
    void delete(List<PursalecontractIdParam> pursalecontractIdParamList);

    /**
     * 获取业务表-CRM-购销合同详情
     *
     * @author yong.ye
     * @date  2023/06/30 16:03
     */
    Pursalecontract detail(PursalecontractIdParam pursalecontractIdParam);

    /**
     * 获取业务表-CRM-购销合同详情
     *
     * @author yong.ye
     * @date  2023/06/30 16:03
     **/
    Pursalecontract queryEntity(String id);

    /**
     * 获取业务表-CRM-购销合同分页【含权限设置-由于page接口有许多调用，故但起一个权限接口】
     * @param pursalecontractPageParam
     * @return
     */
    Page<Pursalecontract> limitPage(PursalecontractPageParam pursalecontractPageParam);
}
