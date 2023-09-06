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
package vip.xiaonuo.pan.modular.fileType.service.impl;

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
import vip.xiaonuo.pan.modular.fileType.entity.PanFileType;
import vip.xiaonuo.pan.modular.fileType.mapper.PanFileTypeMapper;
import vip.xiaonuo.pan.modular.fileType.param.PanFileTypeAddParam;
import vip.xiaonuo.pan.modular.fileType.param.PanFileTypeEditParam;
import vip.xiaonuo.pan.modular.fileType.param.PanFileTypeIdParam;
import vip.xiaonuo.pan.modular.fileType.param.PanFileTypePageParam;
import vip.xiaonuo.pan.modular.fileType.service.PanFileTypeService;

import java.util.List;

/**
 * 文件类型Service接口实现类
 *
 * @author mayike
 * @date  2023/09/06 21:18
 **/
@Service
public class PanFileTypeServiceImpl extends ServiceImpl<PanFileTypeMapper, PanFileType> implements PanFileTypeService {

    @Override
    public Page<PanFileType> page(PanFileTypePageParam panFileTypePageParam) {
        QueryWrapper<PanFileType> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(panFileTypePageParam.getSortField(), panFileTypePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(panFileTypePageParam.getSortOrder());
            queryWrapper.orderBy(true, panFileTypePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(panFileTypePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(PanFileType::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PanFileTypeAddParam panFileTypeAddParam) {
        PanFileType panFileType = BeanUtil.toBean(panFileTypeAddParam, PanFileType.class);
        this.save(panFileType);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PanFileTypeEditParam panFileTypeEditParam) {
        PanFileType panFileType = this.queryEntity(panFileTypeEditParam.getId());
        BeanUtil.copyProperties(panFileTypeEditParam, panFileType);
        this.updateById(panFileType);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PanFileTypeIdParam> panFileTypeIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(panFileTypeIdParamList, PanFileTypeIdParam::getId));
    }

    @Override
    public PanFileType detail(PanFileTypeIdParam panFileTypeIdParam) {
        return this.queryEntity(panFileTypeIdParam.getId());
    }

    @Override
    public PanFileType queryEntity(String id) {
        PanFileType panFileType = this.getById(id);
        if(ObjectUtil.isEmpty(panFileType)) {
            throw new CommonException("文件类型不存在，id值为：{}", id);
        }
        return panFileType;
    }
}
