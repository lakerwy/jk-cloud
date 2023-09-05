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
package vip.xiaonuo.biz.modular.epc.safety.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.epc.safety.entity.EpcSafetyPatrol;
import vip.xiaonuo.biz.modular.epc.safety.mapper.EpcSafetyPatrolMapper;
import vip.xiaonuo.biz.modular.epc.safety.param.*;
import vip.xiaonuo.biz.modular.epc.safety.service.EpcSafetyPatrolService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;

import java.util.List;

/**
 * EPC-安全巡查Service接口实现类
 *
 * @author yong.ye
 * @date  2023/09/05 14:59
 **/
@Service
public class EpcSafetyPatrolServiceImpl extends ServiceImpl<EpcSafetyPatrolMapper, EpcSafetyPatrol> implements EpcSafetyPatrolService {

    @Override
    public Page<EpcSafetyPatrol> page(EpcSafetyPatrolPageParam epcSafetyPatrolPageParam) {
        QueryWrapper<EpcSafetyPatrol> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(epcSafetyPatrolPageParam.getTenantId())) {
            queryWrapper.lambda().eq(EpcSafetyPatrol::getTenantId, epcSafetyPatrolPageParam.getTenantId());
        }
        if(ObjectUtil.isNotEmpty(epcSafetyPatrolPageParam.getProjectId())) {
            queryWrapper.lambda().eq(EpcSafetyPatrol::getProjectId, epcSafetyPatrolPageParam.getProjectId());
        }
        if(ObjectUtil.isNotEmpty(epcSafetyPatrolPageParam.getStartPatrolTime()) && ObjectUtil.isNotEmpty(epcSafetyPatrolPageParam.getEndPatrolTime())) {
            queryWrapper.lambda().between(EpcSafetyPatrol::getPatrolTime, epcSafetyPatrolPageParam.getStartPatrolTime(), epcSafetyPatrolPageParam.getEndPatrolTime());
        }
        if(ObjectUtil.isNotEmpty(epcSafetyPatrolPageParam.getLevel())) {
            queryWrapper.lambda().eq(EpcSafetyPatrol::getLevel, epcSafetyPatrolPageParam.getLevel());
        }
        if(ObjectUtil.isAllNotEmpty(epcSafetyPatrolPageParam.getSortField(), epcSafetyPatrolPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(epcSafetyPatrolPageParam.getSortOrder());
            queryWrapper.orderBy(true, epcSafetyPatrolPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(epcSafetyPatrolPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(EpcSafetyPatrol::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(EpcSafetyPatrolAddParam epcSafetyPatrolAddParam) {
        EpcSafetyPatrol epcSafetyPatrol = BeanUtil.toBean(epcSafetyPatrolAddParam, EpcSafetyPatrol.class);
        this.save(epcSafetyPatrol);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(EpcSafetyPatrolEditParam epcSafetyPatrolEditParam) {
        EpcSafetyPatrol epcSafetyPatrol = this.queryEntity(epcSafetyPatrolEditParam.getId());
        BeanUtil.copyProperties(epcSafetyPatrolEditParam, epcSafetyPatrol);
        this.updateById(epcSafetyPatrol);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<EpcSafetyPatrolIdParam> epcSafetyPatrolIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(epcSafetyPatrolIdParamList, EpcSafetyPatrolIdParam::getId));
    }

    @Override
    public EpcSafetyPatrol detail(EpcSafetyPatrolIdParam epcSafetyPatrolIdParam) {
        return this.queryEntity(epcSafetyPatrolIdParam.getId());
    }

    @Override
    public EpcSafetyPatrol queryEntity(String id) {
        EpcSafetyPatrol epcSafetyPatrol = this.getById(id);
        if(ObjectUtil.isEmpty(epcSafetyPatrol)) {
            throw new CommonException("EPC-安全巡查不存在，id值为：{}", id);
        }
        return epcSafetyPatrol;
    }
}
