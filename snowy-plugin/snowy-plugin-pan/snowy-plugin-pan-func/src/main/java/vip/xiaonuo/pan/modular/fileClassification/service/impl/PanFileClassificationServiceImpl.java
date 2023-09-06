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
package vip.xiaonuo.pan.modular.fileClassification.service.impl;

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
import vip.xiaonuo.pan.modular.fileClassification.entity.PanFileClassification;
import vip.xiaonuo.pan.modular.fileClassification.mapper.PanFileClassificationMapper;
import vip.xiaonuo.pan.modular.fileClassification.param.PanFileClassificationAddParam;
import vip.xiaonuo.pan.modular.fileClassification.param.PanFileClassificationEditParam;
import vip.xiaonuo.pan.modular.fileClassification.param.PanFileClassificationIdParam;
import vip.xiaonuo.pan.modular.fileClassification.param.PanFileClassificationPageParam;
import vip.xiaonuo.pan.modular.fileClassification.service.PanFileClassificationService;

import java.util.List;

/**
 * 文件类型拓展表Service接口实现类
 *
 * @author mayike
 * @date  2023/09/06 21:16
 **/
@Service
public class PanFileClassificationServiceImpl extends ServiceImpl<PanFileClassificationMapper, PanFileClassification> implements PanFileClassificationService {

    @Override
    public Page<PanFileClassification> page(PanFileClassificationPageParam panFileClassificationPageParam) {
        QueryWrapper<PanFileClassification> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(panFileClassificationPageParam.getSortField(), panFileClassificationPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(panFileClassificationPageParam.getSortOrder());
            queryWrapper.orderBy(true, panFileClassificationPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(panFileClassificationPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(PanFileClassification::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PanFileClassificationAddParam panFileClassificationAddParam) {
        PanFileClassification panFileClassification = BeanUtil.toBean(panFileClassificationAddParam, PanFileClassification.class);
        this.save(panFileClassification);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PanFileClassificationEditParam panFileClassificationEditParam) {
        PanFileClassification panFileClassification = this.queryEntity(panFileClassificationEditParam.getId());
        BeanUtil.copyProperties(panFileClassificationEditParam, panFileClassification);
        this.updateById(panFileClassification);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PanFileClassificationIdParam> panFileClassificationIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(panFileClassificationIdParamList, PanFileClassificationIdParam::getId));
    }

    @Override
    public PanFileClassification detail(PanFileClassificationIdParam panFileClassificationIdParam) {
        return this.queryEntity(panFileClassificationIdParam.getId());
    }

    @Override
    public PanFileClassification queryEntity(String id) {
        PanFileClassification panFileClassification = this.getById(id);
        if(ObjectUtil.isEmpty(panFileClassification)) {
            throw new CommonException("文件类型拓展表不存在，id值为：{}", id);
        }
        return panFileClassification;
    }
}
