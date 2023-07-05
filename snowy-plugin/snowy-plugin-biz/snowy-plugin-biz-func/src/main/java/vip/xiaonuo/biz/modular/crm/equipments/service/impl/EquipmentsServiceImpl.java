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
package vip.xiaonuo.biz.modular.crm.equipments.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.crm.cooperation.entity.Cooperation;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.crm.equipments.entity.Equipments;
import vip.xiaonuo.biz.modular.crm.equipments.mapper.EquipmentsMapper;
import vip.xiaonuo.biz.modular.crm.equipments.param.EquipmentsAddParam;
import vip.xiaonuo.biz.modular.crm.equipments.param.EquipmentsEditParam;
import vip.xiaonuo.biz.modular.crm.equipments.param.EquipmentsIdParam;
import vip.xiaonuo.biz.modular.crm.equipments.param.EquipmentsPageParam;
import vip.xiaonuo.biz.modular.crm.equipments.service.EquipmentsService;

import java.util.List;

/**
 * 业务表-CRM-设备登记Service接口实现类
 *
 * @author yong.ye
 * @date  2023/06/30 16:38
 **/

@DS("slave_1")
@Service
public class EquipmentsServiceImpl extends ServiceImpl<EquipmentsMapper, Equipments> implements EquipmentsService {

    @Override
    public Page<Equipments> page(EquipmentsPageParam equipmentsPageParam) {
        QueryWrapper<Equipments> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotEmpty(equipmentsPageParam.getSearchKey())) {
            queryWrapper.lambda().like(Equipments::getSn, equipmentsPageParam.getSearchKey()).or()
                    .like(Equipments::getDept, equipmentsPageParam.getSearchKey()).or()
                    .like(Equipments::getUsers, equipmentsPageParam.getSearchKey()).or()
                    .like(Equipments::getEqNum, equipmentsPageParam.getSearchKey()).or()
                    .like(Equipments::getEqType, equipmentsPageParam.getSearchKey());
        }
        if(ObjectUtil.isNotEmpty(equipmentsPageParam.getSn())) {
            queryWrapper.lambda().like(Equipments::getSn, equipmentsPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(equipmentsPageParam.getDept())) {
            queryWrapper.lambda().like(Equipments::getDept, equipmentsPageParam.getDept());
        }
        if(ObjectUtil.isNotEmpty(equipmentsPageParam.getUsers())) {
            queryWrapper.lambda().like(Equipments::getUsers, equipmentsPageParam.getUsers());
        }
        if(ObjectUtil.isNotEmpty(equipmentsPageParam.getEqNum())) {
            queryWrapper.lambda().like(Equipments::getEqNum, equipmentsPageParam.getEqNum());
        }
        if(ObjectUtil.isNotEmpty(equipmentsPageParam.getEqType())) {
            queryWrapper.lambda().eq(Equipments::getEqType, equipmentsPageParam.getEqType());
        }
        if(ObjectUtil.isNotEmpty(equipmentsPageParam.getStatus())) {
            queryWrapper.lambda().eq(Equipments::getStatus, equipmentsPageParam.getStatus());
        }
        if(ObjectUtil.isNotEmpty(equipmentsPageParam.getStartPurchaseDate()) && ObjectUtil.isNotEmpty(equipmentsPageParam.getEndPurchaseDate())) {
            queryWrapper.lambda().between(Equipments::getPurchaseDate, equipmentsPageParam.getStartPurchaseDate(), equipmentsPageParam.getEndPurchaseDate());
        }
        if(ObjectUtil.isAllNotEmpty(equipmentsPageParam.getSortField(), equipmentsPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(equipmentsPageParam.getSortOrder());
            queryWrapper.orderBy(true, equipmentsPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(equipmentsPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(Equipments::getSn);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(EquipmentsAddParam equipmentsAddParam) {
        Equipments equipments = BeanUtil.toBean(equipmentsAddParam, Equipments.class);
        this.save(equipments);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(EquipmentsEditParam equipmentsEditParam) {
        Equipments equipments = this.queryEntity(equipmentsEditParam.getId());
        BeanUtil.copyProperties(equipmentsEditParam, equipments);
        this.updateById(equipments);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<EquipmentsIdParam> equipmentsIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(equipmentsIdParamList, EquipmentsIdParam::getId));
    }

    @Override
    public Equipments detail(EquipmentsIdParam equipmentsIdParam) {
        return this.queryEntity(equipmentsIdParam.getId());
    }

    @Override
    public Equipments queryEntity(String id) {
        Equipments equipments = this.getById(id);
        if(ObjectUtil.isEmpty(equipments)) {
            throw new CommonException("业务表-CRM-设备登记不存在，id值为：{}", id);
        }
        return equipments;
    }
}
