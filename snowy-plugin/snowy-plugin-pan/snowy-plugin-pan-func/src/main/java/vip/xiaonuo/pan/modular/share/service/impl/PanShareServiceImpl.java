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
package vip.xiaonuo.pan.modular.share.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.pan.modular.share.entity.PanShare;
import vip.xiaonuo.pan.modular.share.mapper.PanShareMapper;
import vip.xiaonuo.pan.modular.share.param.*;
import vip.xiaonuo.pan.modular.share.service.PanShareService;

import java.util.List;

/**
 * 网盘-分享表Service接口实现类
 *
 * @author yong.ye
 * @date  2023/09/07 15:55
 **/
@Service
public class PanShareServiceImpl extends ServiceImpl<PanShareMapper, PanShare> implements PanShareService {

    @Override
    public Page<PanShare> page(PanSharePageParam panSharePageParam) {
        QueryWrapper<PanShare> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(panSharePageParam.getTenantId())) {
            queryWrapper.lambda().eq(PanShare::getTenantId, panSharePageParam.getTenantId());
        }
        if(ObjectUtil.isNotEmpty(panSharePageParam.getUserId())) {
            queryWrapper.lambda().eq(PanShare::getUserId, panSharePageParam.getUserId());
        }
        if(ObjectUtil.isNotEmpty(panSharePageParam.getStartShareTime()) && ObjectUtil.isNotEmpty(panSharePageParam.getEndShareTime())) {
            queryWrapper.lambda().between(PanShare::getShareTime, panSharePageParam.getStartShareTime(), panSharePageParam.getEndShareTime());
        }
        if(ObjectUtil.isNotEmpty(panSharePageParam.getShareBatchNum())) {
            queryWrapper.lambda().eq(PanShare::getShareBatchNum, panSharePageParam.getShareBatchNum());
        }
        if(ObjectUtil.isNotEmpty(panSharePageParam.getShareType())) {
            queryWrapper.lambda().eq(PanShare::getShareType, panSharePageParam.getShareType());
        }
        if(ObjectUtil.isNotEmpty(panSharePageParam.getShareStatus())) {
            queryWrapper.lambda().eq(PanShare::getShareStatus, panSharePageParam.getShareStatus());
        }
        if(ObjectUtil.isAllNotEmpty(panSharePageParam.getSortField(), panSharePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(panSharePageParam.getSortOrder());
            queryWrapper.orderBy(true, panSharePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(panSharePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(PanShare::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PanShareAddParam panShareAddParam) {
        PanShare panShare = BeanUtil.toBean(panShareAddParam, PanShare.class);
        this.save(panShare);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PanShareEditParam panShareEditParam) {
        PanShare panShare = this.queryEntity(panShareEditParam.getId());
        BeanUtil.copyProperties(panShareEditParam, panShare);
        this.updateById(panShare);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PanShareIdParam> panShareIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(panShareIdParamList, PanShareIdParam::getId));
    }

    @Override
    public PanShare detail(PanShareIdParam panShareIdParam) {
        return this.queryEntity(panShareIdParam.getId());
    }

    @Override
    public PanShare queryEntity(String id) {
        PanShare panShare = this.getById(id);
        if(ObjectUtil.isEmpty(panShare)) {
            throw new CommonException("网盘-分享表不存在，id值为：{}", id);
        }
        return panShare;
    }
}
