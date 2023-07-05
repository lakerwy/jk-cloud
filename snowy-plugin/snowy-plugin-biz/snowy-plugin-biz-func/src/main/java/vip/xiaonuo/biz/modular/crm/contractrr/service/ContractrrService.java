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
package vip.xiaonuo.biz.modular.crm.contractrr.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.crm.contractrr.entity.Contractrr;
import vip.xiaonuo.biz.modular.crm.contractrr.param.*;
import vip.xiaonuo.biz.modular.crm.contractrr.vo.ContractrrInfoVo;
import vip.xiaonuo.biz.modular.crm.contractrr.vo.ContractrrPageInfoVo;
import vip.xiaonuo.biz.modular.crm.parameters.param.ParametersIdParam;
import vip.xiaonuo.biz.modular.crm.parameters.param.ParametersInfoPageParam;
import vip.xiaonuo.biz.modular.crm.parameters.vo.ParametersInfoVo;

import java.util.List;

/**
 * 业务表-CRM-合同收款登记Service接口
 *
 * @author yong.ye
 * @date  2023/06/30 16:47
 **/
public interface ContractrrService extends IService<Contractrr> {

    /**
     * 获取业务表-CRM-合同收款登记分页
     *
     * @author yong.ye
     * @date  2023/06/30 16:47
     */
    Page<Contractrr> page(ContractrrPageParam contractrrPageParam);

    /**
     * 添加业务表-CRM-合同收款登记
     *
     * @author yong.ye
     * @date  2023/06/30 16:47
     */
    void add(ContractrrAddParam contractrrAddParam);

    /**
     * 编辑业务表-CRM-合同收款登记
     *
     * @author yong.ye
     * @date  2023/06/30 16:47
     */
    void edit(ContractrrEditParam contractrrEditParam);

    /**
     * 删除业务表-CRM-合同收款登记
     *
     * @author yong.ye
     * @date  2023/06/30 16:47
     */
    void delete(List<ContractrrIdParam> contractrrIdParamList);

    /**
     * 获取业务表-CRM-合同收款登记详情
     *
     * @author yong.ye
     * @date  2023/06/30 16:47
     */
    Contractrr detail(ContractrrIdParam contractrrIdParam);

    /**
     * 获取业务表-CRM-合同收款登记详情
     *
     * @author yong.ye
     * @date  2023/06/30 16:47
     **/
    Contractrr queryEntity(String id);

    /**
     * 获取合同收款登记列表（携带合同信息）-分页
     * @param contractrrPageInfoParam
     * @return
     */
    IPage<ContractrrPageInfoVo> pageList(ContractrrPageInfoParam contractrrPageInfoParam);

    /**
     * 根据主键ID获取合同收款登记详情（携带合同信息）
     * @param contractrrIdParam
     * @return
     */
    ContractrrInfoVo detailById(ContractrrIdParam contractrrIdParam);
}
