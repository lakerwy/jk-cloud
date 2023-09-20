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
import vip.xiaonuo.biz.modular.epc.safety.entity.EpcSafetySubmit;
import vip.xiaonuo.biz.modular.epc.safety.mapper.EpcSafetySubmitMapper;
import vip.xiaonuo.biz.modular.epc.safety.param.*;
import vip.xiaonuo.biz.modular.epc.safety.service.EpcSafetySubmitService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;

import java.util.List;

/**
 * EPC-安全技术交底Service接口实现类
 *
 * @author yong.ye
 * @date  2023/09/05 14:59
 **/
@Service
public class EpcSafetySubmitServiceImpl extends ServiceImpl<EpcSafetySubmitMapper, EpcSafetySubmit> implements EpcSafetySubmitService {

    @Override
    public Page<EpcSafetySubmit> page(EpcSafetySubmitPageParam epcSafetySubmitPageParam) {
        QueryWrapper<EpcSafetySubmit> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(epcSafetySubmitPageParam.getTenantId())) {
            queryWrapper.lambda().eq(EpcSafetySubmit::getTenantId, epcSafetySubmitPageParam.getTenantId());
        }
        if(ObjectUtil.isNotEmpty(epcSafetySubmitPageParam.getProjectId())) {
            queryWrapper.lambda().eq(EpcSafetySubmit::getProjectId, epcSafetySubmitPageParam.getProjectId());
        }
        if(ObjectUtil.isNotEmpty(epcSafetySubmitPageParam.getSubmitName())) {
            queryWrapper.lambda().like(EpcSafetySubmit::getSubmitName, epcSafetySubmitPageParam.getSubmitName());
        }
        if(ObjectUtil.isNotEmpty(epcSafetySubmitPageParam.getStartSubmitTime()) && ObjectUtil.isNotEmpty(epcSafetySubmitPageParam.getEndSubmitTime())) {
            queryWrapper.lambda().between(EpcSafetySubmit::getSubmitTime, epcSafetySubmitPageParam.getStartSubmitTime(), epcSafetySubmitPageParam.getEndSubmitTime());
        }
        if(ObjectUtil.isNotEmpty(epcSafetySubmitPageParam.getSearchKey())){
            queryWrapper.and(q->q.lambda().like(EpcSafetySubmit::getSubmitName,epcSafetySubmitPageParam.getSearchKey())
                    .or().like(EpcSafetySubmit::getSubmitPerson,epcSafetySubmitPageParam.getSearchKey())
                    .or().like(EpcSafetySubmit::getSubmitObject,epcSafetySubmitPageParam.getSearchKey()));
        }

        if(ObjectUtil.isAllNotEmpty(epcSafetySubmitPageParam.getSortField(), epcSafetySubmitPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(epcSafetySubmitPageParam.getSortOrder());
            queryWrapper.orderBy(true, epcSafetySubmitPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(epcSafetySubmitPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(EpcSafetySubmit::getSubmitTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(EpcSafetySubmitAddParam epcSafetySubmitAddParam) {
        EpcSafetySubmit epcSafetySubmit = BeanUtil.toBean(epcSafetySubmitAddParam, EpcSafetySubmit.class);
        this.save(epcSafetySubmit);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(EpcSafetySubmitEditParam epcSafetySubmitEditParam) {
        EpcSafetySubmit epcSafetySubmit = this.queryEntity(epcSafetySubmitEditParam.getId());
        BeanUtil.copyProperties(epcSafetySubmitEditParam, epcSafetySubmit);
        this.updateById(epcSafetySubmit);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<EpcSafetySubmitIdParam> epcSafetySubmitIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(epcSafetySubmitIdParamList, EpcSafetySubmitIdParam::getId));
    }

    @Override
    public EpcSafetySubmit detail(EpcSafetySubmitIdParam epcSafetySubmitIdParam) {
        return this.queryEntity(epcSafetySubmitIdParam.getId());
    }

    @Override
    public EpcSafetySubmit queryEntity(String id) {
        EpcSafetySubmit epcSafetySubmit = this.getById(id);
        if(ObjectUtil.isEmpty(epcSafetySubmit)) {
            throw new CommonException("EPC-安全技术交底不存在，id值为：{}", id);
        }
        return epcSafetySubmit;
    }
}
