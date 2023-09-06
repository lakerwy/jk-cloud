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
import vip.xiaonuo.biz.modular.epc.safety.entity.EpcFacilityDanger;
import vip.xiaonuo.biz.modular.epc.safety.mapper.EpcFacilityDangerMapper;
import vip.xiaonuo.biz.modular.epc.safety.param.*;
import vip.xiaonuo.biz.modular.epc.safety.service.EpcFacilityDangerService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;

import java.util.List;

/**
 * EPC-危险源识别Service接口实现类
 *
 * @author yong.ye
 * @date  2023/09/05 14:51
 **/
@Service
public class EpcFacilityDangerServiceImpl extends ServiceImpl<EpcFacilityDangerMapper, EpcFacilityDanger> implements EpcFacilityDangerService {

    @Override
    public Page<EpcFacilityDanger> page(EpcFacilityDangerPageParam epcFacilityDangerPageParam) {
        QueryWrapper<EpcFacilityDanger> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(epcFacilityDangerPageParam.getTenantId())) {
            queryWrapper.lambda().eq(EpcFacilityDanger::getTenantId, epcFacilityDangerPageParam.getTenantId());
        }
        if(ObjectUtil.isNotEmpty(epcFacilityDangerPageParam.getProjectId())) {
            queryWrapper.lambda().eq(EpcFacilityDanger::getProjectId, epcFacilityDangerPageParam.getProjectId());
        }
        if(ObjectUtil.isNotEmpty(epcFacilityDangerPageParam.getDangerSource())) {
            queryWrapper.lambda().like(EpcFacilityDanger::getDangerSource, epcFacilityDangerPageParam.getDangerSource());
        }
        if(ObjectUtil.isAllNotEmpty(epcFacilityDangerPageParam.getSortField(), epcFacilityDangerPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(epcFacilityDangerPageParam.getSortOrder());
            queryWrapper.orderBy(true, epcFacilityDangerPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(epcFacilityDangerPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(EpcFacilityDanger::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(EpcFacilityDangerAddParam epcFacilityDangerAddParam) {
        EpcFacilityDanger epcFacilityDanger = BeanUtil.toBean(epcFacilityDangerAddParam, EpcFacilityDanger.class);
        this.save(epcFacilityDanger);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(EpcFacilityDangerEditParam epcFacilityDangerEditParam) {
        EpcFacilityDanger epcFacilityDanger = this.queryEntity(epcFacilityDangerEditParam.getId());
        BeanUtil.copyProperties(epcFacilityDangerEditParam, epcFacilityDanger);
        this.updateById(epcFacilityDanger);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<EpcFacilityDangerIdParam> epcFacilityDangerIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(epcFacilityDangerIdParamList, EpcFacilityDangerIdParam::getId));
    }

    @Override
    public EpcFacilityDanger detail(EpcFacilityDangerIdParam epcFacilityDangerIdParam) {
        return this.queryEntity(epcFacilityDangerIdParam.getId());
    }

    @Override
    public EpcFacilityDanger queryEntity(String id) {
        EpcFacilityDanger epcFacilityDanger = this.getById(id);
        if(ObjectUtil.isEmpty(epcFacilityDanger)) {
            throw new CommonException("EPC-危险源识别不存在，id值为：{}", id);
        }
        return epcFacilityDanger;
    }
}
