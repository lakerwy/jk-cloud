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
package vip.xiaonuo.pan.modular.fileCenter.service.impl;

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
import vip.xiaonuo.pan.modular.fileCenter.entity.PanMusic;
import vip.xiaonuo.pan.modular.fileCenter.mapper.PanMusicMapper;
import vip.xiaonuo.pan.modular.fileCenter.param.PanMusicAddParam;
import vip.xiaonuo.pan.modular.fileCenter.param.PanMusicEditParam;
import vip.xiaonuo.pan.modular.fileCenter.param.PanMusicIdParam;
import vip.xiaonuo.pan.modular.fileCenter.param.PanMusicPageParam;
import vip.xiaonuo.pan.modular.fileCenter.service.PanMusicService;

import java.util.List;

/**
 * 云盘-音乐实体类Service接口实现类
 *
 * @author mayike
 * @date  2023/09/08 14:13
 **/
@Service
public class PanMusicServiceImpl extends ServiceImpl<PanMusicMapper, PanMusic> implements PanMusicService {

    @Override
    public Page<PanMusic> page(PanMusicPageParam panMusicPageParam) {
        QueryWrapper<PanMusic> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(panMusicPageParam.getSortField(), panMusicPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(panMusicPageParam.getSortOrder());
            queryWrapper.orderBy(true, panMusicPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(panMusicPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(PanMusic::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PanMusicAddParam panMusicAddParam) {
        PanMusic panMusic = BeanUtil.toBean(panMusicAddParam, PanMusic.class);
        this.save(panMusic);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PanMusicEditParam panMusicEditParam) {
        PanMusic panMusic = this.queryEntity(panMusicEditParam.getId());
        BeanUtil.copyProperties(panMusicEditParam, panMusic);
        this.updateById(panMusic);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PanMusicIdParam> panMusicIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(panMusicIdParamList, PanMusicIdParam::getId));
    }

    @Override
    public PanMusic detail(PanMusicIdParam panMusicIdParam) {
        return this.queryEntity(panMusicIdParam.getId());
    }

    @Override
    public PanMusic queryEntity(String id) {
        PanMusic panMusic = this.getById(id);
        if(ObjectUtil.isEmpty(panMusic)) {
            throw new CommonException("云盘-音乐实体类不存在，id值为：{}", id);
        }
        return panMusic;
    }
}
