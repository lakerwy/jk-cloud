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
package vip.xiaonuo.biz.modular.crm.equipments.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.crm.equipments.entity.Equipments;
import vip.xiaonuo.biz.modular.crm.equipments.param.EquipmentsAddParam;
import vip.xiaonuo.biz.modular.crm.equipments.param.EquipmentsEditParam;
import vip.xiaonuo.biz.modular.crm.equipments.param.EquipmentsIdParam;
import vip.xiaonuo.biz.modular.crm.equipments.param.EquipmentsPageParam;

import java.util.List;

/**
 * 业务表-CRM-设备登记Service接口
 *
 * @author yong.ye
 * @date  2023/06/30 16:38
 **/
public interface EquipmentsService extends IService<Equipments> {

    /**
     * 获取业务表-CRM-设备登记分页
     *
     * @author yong.ye
     * @date  2023/06/30 16:38
     */
    Page<Equipments> page(EquipmentsPageParam equipmentsPageParam);

    /**
     * 添加业务表-CRM-设备登记
     *
     * @author yong.ye
     * @date  2023/06/30 16:38
     */
    void add(EquipmentsAddParam equipmentsAddParam);

    /**
     * 编辑业务表-CRM-设备登记
     *
     * @author yong.ye
     * @date  2023/06/30 16:38
     */
    void edit(EquipmentsEditParam equipmentsEditParam);

    /**
     * 删除业务表-CRM-设备登记
     *
     * @author yong.ye
     * @date  2023/06/30 16:38
     */
    void delete(List<EquipmentsIdParam> equipmentsIdParamList);

    /**
     * 获取业务表-CRM-设备登记详情
     *
     * @author yong.ye
     * @date  2023/06/30 16:38
     */
    Equipments detail(EquipmentsIdParam equipmentsIdParam);

    /**
     * 获取业务表-CRM-设备登记详情
     *
     * @author yong.ye
     * @date  2023/06/30 16:38
     **/
    Equipments queryEntity(String id);
}
