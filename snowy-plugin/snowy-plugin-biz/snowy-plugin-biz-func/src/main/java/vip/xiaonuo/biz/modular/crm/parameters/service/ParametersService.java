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
package vip.xiaonuo.biz.modular.crm.parameters.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.crm.parameters.entity.Parameters;
import vip.xiaonuo.biz.modular.crm.parameters.param.*;
import vip.xiaonuo.biz.modular.crm.parameters.vo.ParametersInfoVo;

import java.util.List;

/**
 * 业务表-CRM-合同参数Service接口
 *
 * @author yong.ye
 * @date  2023/06/30 15:50
 **/
public interface ParametersService extends IService<Parameters> {

    /**
     * 获取业务表-CRM-合同参数分页
     *
     * @author yong.ye
     * @date  2023/06/30 15:50
     */
    Page<Parameters> page(ParametersPageParam parametersPageParam);

    /**
     * 添加业务表-CRM-合同参数
     *
     * @author yong.ye
     * @date  2023/06/30 15:50
     */
    void add(ParametersAddParam parametersAddParam);

    /**
     * 编辑业务表-CRM-合同参数
     *
     * @author yong.ye
     * @date  2023/06/30 15:50
     */
    void edit(ParametersEditParam parametersEditParam);

    /**
     * 删除业务表-CRM-合同参数
     *
     * @author yong.ye
     * @date  2023/06/30 15:50
     */
    void delete(List<ParametersIdParam> parametersIdParamList);

    /**
     * 获取业务表-CRM-合同参数详情
     *
     * @author yong.ye
     * @date  2023/06/30 15:50
     */
    Parameters detail(ParametersIdParam parametersIdParam);

    /**
     * 获取业务表-CRM-合同参数详情
     *
     * @author yong.ye
     * @date  2023/06/30 15:50
     **/
    Parameters queryEntity(String id);

    /**
     * 获取合同参数列表（携带合同信息）-分页
     * @param parametersInfoPageParam
     * @return
     */
    IPage<ParametersInfoVo> pageList(ParametersInfoPageParam parametersInfoPageParam);

    /**
     * 根据主键ID获取合同参数详情（携带合同信息）
     * @param parametersIdParam
     * @return
     */
    ParametersInfoVo detailById(ParametersIdParam parametersIdParam);

    /**
     * 根据合同ID获取合同参数详情（携带合同信息）
     * @param parametersHetongIdParam
     * @return
     */
    ParametersInfoVo detailByHetongId(ParametersHetongIdParam parametersHetongIdParam);
}
