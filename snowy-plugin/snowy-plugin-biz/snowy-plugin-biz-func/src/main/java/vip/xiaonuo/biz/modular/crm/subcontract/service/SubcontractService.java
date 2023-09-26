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
package vip.xiaonuo.biz.modular.crm.subcontract.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.crm.subcontract.entity.Subcontract;
import vip.xiaonuo.biz.modular.crm.subcontract.param.*;
import vip.xiaonuo.biz.modular.crm.subcontract.vo.SubContractInfoVo;

import java.util.List;

/**
 * 业务表-CRM-分包合同Service接口
 *
 * @author yong.ye
 * @date  2023/06/30 16:22
 **/
public interface SubcontractService extends IService<Subcontract> {

    /**
     * 获取业务表-CRM-分包合同分页
     *
     * @author yong.ye
     * @date  2023/06/30 16:22
     */
    Page<Subcontract> page(SubcontractPageParam subcontractPageParam);

    /**
     * 添加业务表-CRM-分包合同
     *
     * @author yong.ye
     * @date  2023/06/30 16:22
     */
    void add(SubcontractAddParam subcontractAddParam);

    /**
     * 编辑业务表-CRM-分包合同
     *
     * @author yong.ye
     * @date  2023/06/30 16:22
     */
    void edit(SubcontractEditParam subcontractEditParam);

    /**
     * 删除业务表-CRM-分包合同
     *
     * @author yong.ye
     * @date  2023/06/30 16:22
     */
    void delete(List<SubcontractIdParam> subcontractIdParamList);

    /**
     * 获取业务表-CRM-分包合同详情
     *
     * @author yong.ye
     * @date  2023/06/30 16:22
     */
    Subcontract detail(SubcontractIdParam subcontractIdParam);

    /**
     * 获取业务表-CRM-分包合同详情
     *
     * @author yong.ye
     * @date  2023/06/30 16:22
     **/
    Subcontract queryEntity(String id);

    /**
     * 根据主键ID获取分包合同详情（携带源合同信息）
     * @param subcontractIdParam
     * @return
     */
    SubContractInfoVo detailById(SubcontractIdParam subcontractIdParam);

    /**
     * 根据合同ID获取分包合同列表
     * @param subContractHetongIdParam
     * @return
     */
    List<Subcontract> detailByHetongId(SubContractHetongIdParam subContractHetongIdParam);
}
