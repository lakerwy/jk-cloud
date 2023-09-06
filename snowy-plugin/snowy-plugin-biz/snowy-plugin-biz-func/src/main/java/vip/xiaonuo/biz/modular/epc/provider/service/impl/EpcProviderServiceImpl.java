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
package vip.xiaonuo.biz.modular.epc.provider.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.epc.provider.entity.EpcProvider;
import vip.xiaonuo.biz.modular.epc.provider.mapper.EpcProviderMapper;
import vip.xiaonuo.biz.modular.epc.provider.param.*;
import vip.xiaonuo.biz.modular.epc.provider.service.EpcProviderService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;


import java.util.List;

/**
 * EPC-供应商库表Service接口实现类
 *
 * @author yong.ye
 * @date  2023/09/05 14:57
 **/
@Service
public class EpcProviderServiceImpl extends ServiceImpl<EpcProviderMapper, EpcProvider> implements EpcProviderService {

    @Override
    public Page<EpcProvider> page(EpcProviderPageParam epcProviderPageParam) {
        QueryWrapper<EpcProvider> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(epcProviderPageParam.getTenantId())) {
            queryWrapper.lambda().eq(EpcProvider::getTenantId, epcProviderPageParam.getTenantId());
        }
        if(ObjectUtil.isNotEmpty(epcProviderPageParam.getPrvType())) {
            queryWrapper.lambda().eq(EpcProvider::getPrvType, epcProviderPageParam.getPrvType());
        }
        if(ObjectUtil.isNotEmpty(epcProviderPageParam.getPrvName())) {
            queryWrapper.lambda().like(EpcProvider::getPrvName, epcProviderPageParam.getPrvName());
        }
        if(ObjectUtil.isNotEmpty(epcProviderPageParam.getSocialCode())) {
            queryWrapper.lambda().like(EpcProvider::getSocialCode, epcProviderPageParam.getSocialCode());
        }
        if(ObjectUtil.isAllNotEmpty(epcProviderPageParam.getSortField(), epcProviderPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(epcProviderPageParam.getSortOrder());
            queryWrapper.orderBy(true, epcProviderPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(epcProviderPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(EpcProvider::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(EpcProviderAddParam epcProviderAddParam) {
        EpcProvider epcProvider = BeanUtil.toBean(epcProviderAddParam, EpcProvider.class);
        this.save(epcProvider);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(EpcProviderEditParam epcProviderEditParam) {
        EpcProvider epcProvider = this.queryEntity(epcProviderEditParam.getId());
        BeanUtil.copyProperties(epcProviderEditParam, epcProvider);
        this.updateById(epcProvider);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<EpcProviderIdParam> epcProviderIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(epcProviderIdParamList, EpcProviderIdParam::getId));
    }

    @Override
    public EpcProvider detail(EpcProviderIdParam epcProviderIdParam) {
        return this.queryEntity(epcProviderIdParam.getId());
    }

    @Override
    public EpcProvider queryEntity(String id) {
        EpcProvider epcProvider = this.getById(id);
        if(ObjectUtil.isEmpty(epcProvider)) {
            throw new CommonException("EPC-供应商库表不存在，id值为：{}", id);
        }
        return epcProvider;
    }
}
