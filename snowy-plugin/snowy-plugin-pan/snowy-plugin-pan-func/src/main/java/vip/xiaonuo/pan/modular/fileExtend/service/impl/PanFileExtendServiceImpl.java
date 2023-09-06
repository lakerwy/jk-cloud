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
package vip.xiaonuo.pan.modular.fileExtend.service.impl;

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
import vip.xiaonuo.pan.modular.fileExtend.entity.PanFileExtend;
import vip.xiaonuo.pan.modular.fileExtend.mapper.PanFileExtendMapper;
import vip.xiaonuo.pan.modular.fileExtend.param.PanFileExtendAddParam;
import vip.xiaonuo.pan.modular.fileExtend.param.PanFileExtendEditParam;
import vip.xiaonuo.pan.modular.fileExtend.param.PanFileExtendIdParam;
import vip.xiaonuo.pan.modular.fileExtend.param.PanFileExtendPageParam;
import vip.xiaonuo.pan.modular.fileExtend.service.PanFileExtendService;

import java.util.List;

/**
 * 文件类型拓展Service接口实现类
 *
 * @author mayike
 * @date  2023/09/06 21:17
 **/
@Service
public class PanFileExtendServiceImpl extends ServiceImpl<PanFileExtendMapper, PanFileExtend> implements PanFileExtendService {

    @Override
    public Page<PanFileExtend> page(PanFileExtendPageParam panFileExtendPageParam) {
        QueryWrapper<PanFileExtend> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(panFileExtendPageParam.getSortField(), panFileExtendPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(panFileExtendPageParam.getSortOrder());
            queryWrapper.orderBy(true, panFileExtendPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(panFileExtendPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(PanFileExtend::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PanFileExtendAddParam panFileExtendAddParam) {
        PanFileExtend panFileExtend = BeanUtil.toBean(panFileExtendAddParam, PanFileExtend.class);
        this.save(panFileExtend);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PanFileExtendEditParam panFileExtendEditParam) {
        PanFileExtend panFileExtend = this.queryEntity(panFileExtendEditParam.getId());
        BeanUtil.copyProperties(panFileExtendEditParam, panFileExtend);
        this.updateById(panFileExtend);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PanFileExtendIdParam> panFileExtendIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(panFileExtendIdParamList, PanFileExtendIdParam::getId));
    }

    @Override
    public PanFileExtend detail(PanFileExtendIdParam panFileExtendIdParam) {
        return this.queryEntity(panFileExtendIdParam.getId());
    }

    @Override
    public PanFileExtend queryEntity(String id) {
        PanFileExtend panFileExtend = this.getById(id);
        if(ObjectUtil.isEmpty(panFileExtend)) {
            throw new CommonException("文件类型拓展不存在，id值为：{}", id);
        }
        return panFileExtend;
    }
}
