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
import vip.xiaonuo.biz.modular.epc.safety.entity.EpcPersonnelSpecial;
import vip.xiaonuo.biz.modular.epc.safety.mapper.EpcPersonnelSpecialMapper;
import vip.xiaonuo.biz.modular.epc.safety.param.*;
import vip.xiaonuo.biz.modular.epc.safety.service.EpcPersonnelSpecialService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;

import java.util.List;

/**
 * EPC-特种作业人员管理Service接口实现类
 *
 * @author yong.ye
 * @date  2023/09/05 14:54
 **/
@Service
public class EpcPersonnelSpecialServiceImpl extends ServiceImpl<EpcPersonnelSpecialMapper, EpcPersonnelSpecial> implements EpcPersonnelSpecialService {

    @Override
    public Page<EpcPersonnelSpecial> page(EpcPersonnelSpecialPageParam epcPersonnelSpecialPageParam) {
        QueryWrapper<EpcPersonnelSpecial> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(epcPersonnelSpecialPageParam.getTenantId())) {
            queryWrapper.lambda().eq(EpcPersonnelSpecial::getTenantId, epcPersonnelSpecialPageParam.getTenantId());
        }
        if(ObjectUtil.isNotEmpty(epcPersonnelSpecialPageParam.getPersonnelId())) {
            queryWrapper.lambda().eq(EpcPersonnelSpecial::getPersonnelId, epcPersonnelSpecialPageParam.getPersonnelId());
        }
        if(ObjectUtil.isNotEmpty(epcPersonnelSpecialPageParam.getCertificateNo())) {
            queryWrapper.lambda().like(EpcPersonnelSpecial::getCertificateNo, epcPersonnelSpecialPageParam.getCertificateNo());
        }
        if(ObjectUtil.isNotEmpty(epcPersonnelSpecialPageParam.getIssuingUnit())) {
            queryWrapper.lambda().like(EpcPersonnelSpecial::getIssuingUnit, epcPersonnelSpecialPageParam.getIssuingUnit());
        }
        if(ObjectUtil.isAllNotEmpty(epcPersonnelSpecialPageParam.getSortField(), epcPersonnelSpecialPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(epcPersonnelSpecialPageParam.getSortOrder());
            queryWrapper.orderBy(true, epcPersonnelSpecialPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(epcPersonnelSpecialPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(EpcPersonnelSpecial::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(EpcPersonnelSpecialAddParam epcPersonnelSpecialAddParam) {
        EpcPersonnelSpecial epcPersonnelSpecial = BeanUtil.toBean(epcPersonnelSpecialAddParam, EpcPersonnelSpecial.class);
        this.save(epcPersonnelSpecial);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(EpcPersonnelSpecialEditParam epcPersonnelSpecialEditParam) {
        EpcPersonnelSpecial epcPersonnelSpecial = this.queryEntity(epcPersonnelSpecialEditParam.getId());
        BeanUtil.copyProperties(epcPersonnelSpecialEditParam, epcPersonnelSpecial);
        this.updateById(epcPersonnelSpecial);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<EpcPersonnelSpecialIdParam> epcPersonnelSpecialIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(epcPersonnelSpecialIdParamList, EpcPersonnelSpecialIdParam::getId));
    }

    @Override
    public EpcPersonnelSpecial detail(EpcPersonnelSpecialIdParam epcPersonnelSpecialIdParam) {
        return this.queryEntity(epcPersonnelSpecialIdParam.getId());
    }

    @Override
    public EpcPersonnelSpecial queryEntity(String id) {
        EpcPersonnelSpecial epcPersonnelSpecial = this.getById(id);
        if(ObjectUtil.isEmpty(epcPersonnelSpecial)) {
            throw new CommonException("EPC-特种作业人员管理不存在，id值为：{}", id);
        }
        return epcPersonnelSpecial;
    }
}
