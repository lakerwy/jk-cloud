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
import vip.xiaonuo.biz.modular.epc.provider.entity.EpcProviderAss;
import vip.xiaonuo.biz.modular.epc.provider.param.*;
import vip.xiaonuo.biz.modular.epc.provider.vo.ProviderAssVo;

import java.util.List;

/**
 * EPC 供应商库-考核表Service接口
 *
 * @author yong.ye
 * @date  2023/09/25 16:26
 **/
public interface EpcProviderAssService extends IService<EpcProviderAss> {

    /**
     * 获取EPC 供应商库-考核表分页
     *
     * @author yong.ye
     * @date  2023/09/25 16:26
     */
    Page<EpcProviderAss> page(EpcProviderAssPageParam epcProviderAssPageParam);

    /**
     * 添加EPC 供应商库-考核表
     *
     * @author yong.ye
     * @date  2023/09/25 16:26
     */
    void add(EpcProviderAssAddParam epcProviderAssAddParam);

    /**
     * 编辑EPC 供应商库-考核表
     *
     * @author yong.ye
     * @date  2023/09/25 16:26
     */
    void edit(EpcProviderAssEditParam epcProviderAssEditParam);

    /**
     * 删除EPC 供应商库-考核表
     *
     * @author yong.ye
     * @date  2023/09/25 16:26
     */
    void delete(List<EpcProviderAssIdParam> epcProviderAssIdParamList);

    /**
     * 获取EPC 供应商库-考核表详情
     *
     * @author yong.ye
     * @date  2023/09/25 16:26
     */
    EpcProviderAss detail(EpcProviderAssIdParam epcProviderAssIdParam);

    /**
     * 获取EPC 供应商库-考核表详情
     *
     * @author yong.ye
     * @date  2023/09/25 16:26
     **/
    EpcProviderAss queryEntity(String id);


    /***
     * 校验 同一供应商+同一年+同一季度+同一考核类型 是否已考核
     * @param epcProviderAssCheckParam
     * @return
     */
    Boolean check(EpcProviderAssCheckParam epcProviderAssCheckParam);

    /**
     * 获取EPC 供应商库-考核表分页
     *
     * @author yong.ye
     * @date  2023/09/25 16:26
     */
    Page<ProviderAssVo> pageList(EpcProviderAssPageParam epcProviderAssPageParam);
}
