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
package vip.xiaonuo.biz.modular.epc.provider.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.epc.provider.entity.EpcProvider;
import vip.xiaonuo.biz.modular.epc.provider.param.*;

import java.util.List;

/**
 * EPC-供应商库表Service接口
 *
 * @author yong.ye
 * @date  2023/09/05 14:57
 **/
public interface EpcProviderService extends IService<EpcProvider> {

    /**
     * 获取EPC-供应商库表分页
     *
     * @author yong.ye
     * @date  2023/09/05 14:57
     */
    Page<EpcProvider> page(EpcProviderPageParam epcProviderPageParam);

    /**
     * 添加EPC-供应商库表
     *
     * @author yong.ye
     * @date  2023/09/05 14:57
     */
    void add(EpcProviderAddParam epcProviderAddParam);

    /**
     * 编辑EPC-供应商库表
     *
     * @author yong.ye
     * @date  2023/09/05 14:57
     */
    void edit(EpcProviderEditParam epcProviderEditParam);

    /**
     * 删除EPC-供应商库表
     *
     * @author yong.ye
     * @date  2023/09/05 14:57
     */
    void delete(List<EpcProviderIdParam> epcProviderIdParamList);

    /**
     * 获取EPC-供应商库表详情
     *
     * @author yong.ye
     * @date  2023/09/05 14:57
     */
    EpcProvider detail(EpcProviderIdParam epcProviderIdParam);

    /**
     * 获取EPC-供应商库表详情
     *
     * @author yong.ye
     * @date  2023/09/05 14:57
     **/
    EpcProvider queryEntity(String id);
}
