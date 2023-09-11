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
package vip.xiaonuo.pan.modular.image.service.impl;

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
import vip.xiaonuo.pan.modular.image.entity.PanImage;
import vip.xiaonuo.pan.modular.image.mapper.PanImageMapper;
import vip.xiaonuo.pan.modular.image.param.PanImageAddParam;
import vip.xiaonuo.pan.modular.image.param.PanImageEditParam;
import vip.xiaonuo.pan.modular.image.param.PanImageIdParam;
import vip.xiaonuo.pan.modular.image.param.PanImagePageParam;
import vip.xiaonuo.pan.modular.image.service.PanImageService;

import java.util.List;

/**
 * 云盘-图片实体表Service接口实现类
 *
 * @author mayike
 * @date  2023/09/08 15:53
 **/
@Service
public class PanImageServiceImpl extends ServiceImpl<PanImageMapper, PanImage> implements PanImageService {

    @Override
    public Page<PanImage> page(PanImagePageParam panImagePageParam) {
        QueryWrapper<PanImage> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(panImagePageParam.getSortField(), panImagePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(panImagePageParam.getSortOrder());
            queryWrapper.orderBy(true, panImagePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(panImagePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(PanImage::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PanImageAddParam panImageAddParam) {
        PanImage panImage = BeanUtil.toBean(panImageAddParam, PanImage.class);
        this.save(panImage);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PanImageEditParam panImageEditParam) {
        PanImage panImage = this.queryEntity(panImageEditParam.getId());
        BeanUtil.copyProperties(panImageEditParam, panImage);
        this.updateById(panImage);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PanImageIdParam> panImageIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(panImageIdParamList, PanImageIdParam::getId));
    }

    @Override
    public PanImage detail(PanImageIdParam panImageIdParam) {
        return this.queryEntity(panImageIdParam.getId());
    }

    @Override
    public PanImage queryEntity(String id) {
        PanImage panImage = this.getById(id);
        if(ObjectUtil.isEmpty(panImage)) {
            throw new CommonException("云盘-图片实体表不存在，id值为：{}", id);
        }
        return panImage;
    }
}
