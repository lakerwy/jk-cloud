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
package vip.xiaonuo.biz.modular.epc.progress.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.epc.progress.entity.EpcBuildImage;
import vip.xiaonuo.biz.modular.epc.progress.mapper.EpcBuildImageMapper;
import vip.xiaonuo.biz.modular.epc.progress.param.*;
import vip.xiaonuo.biz.modular.epc.progress.service.EpcBuildImageService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;

import java.util.List;

/**
 * EPC-施工影像表Service接口实现类
 *
 * @author yong.ye
 * @date  2023/09/05 14:50
 **/
@Service
public class EpcBuildImageServiceImpl extends ServiceImpl<EpcBuildImageMapper, EpcBuildImage> implements EpcBuildImageService {

    @Override
    public Page<EpcBuildImage> page(EpcBuildImagePageParam epcBuildImagePageParam) {
        QueryWrapper<EpcBuildImage> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(epcBuildImagePageParam.getTenantId())) {
            queryWrapper.lambda().eq(EpcBuildImage::getTenantId, epcBuildImagePageParam.getTenantId());
        }
        if(ObjectUtil.isNotEmpty(epcBuildImagePageParam.getProjectId())) {
            queryWrapper.lambda().eq(EpcBuildImage::getProjectId, epcBuildImagePageParam.getProjectId());
        }
        if(ObjectUtil.isNotEmpty(epcBuildImagePageParam.getType())) {
            queryWrapper.lambda().eq(EpcBuildImage::getType, epcBuildImagePageParam.getType());
        }
        if(ObjectUtil.isNotEmpty(epcBuildImagePageParam.getStartShootTime()) && ObjectUtil.isNotEmpty(epcBuildImagePageParam.getEndShootTime())) {
            queryWrapper.lambda().between(EpcBuildImage::getShootTime, epcBuildImagePageParam.getStartShootTime(), epcBuildImagePageParam.getEndShootTime());
        }
        if(ObjectUtil.isAllNotEmpty(epcBuildImagePageParam.getSortField(), epcBuildImagePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(epcBuildImagePageParam.getSortOrder());
            queryWrapper.orderBy(true, epcBuildImagePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(epcBuildImagePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(EpcBuildImage::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(EpcBuildImageAddParam epcBuildImageAddParam) {
        EpcBuildImage epcBuildImage = BeanUtil.toBean(epcBuildImageAddParam, EpcBuildImage.class);
        this.save(epcBuildImage);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(EpcBuildImageEditParam epcBuildImageEditParam) {
        EpcBuildImage epcBuildImage = this.queryEntity(epcBuildImageEditParam.getId());
        BeanUtil.copyProperties(epcBuildImageEditParam, epcBuildImage);
        this.updateById(epcBuildImage);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<EpcBuildImageIdParam> epcBuildImageIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(epcBuildImageIdParamList, EpcBuildImageIdParam::getId));
    }

    @Override
    public EpcBuildImage detail(EpcBuildImageIdParam epcBuildImageIdParam) {
        return this.queryEntity(epcBuildImageIdParam.getId());
    }

    @Override
    public EpcBuildImage queryEntity(String id) {
        EpcBuildImage epcBuildImage = this.getById(id);
        if(ObjectUtil.isEmpty(epcBuildImage)) {
            throw new CommonException("EPC-施工影像表不存在，id值为：{}", id);
        }
        return epcBuildImage;
    }
}
