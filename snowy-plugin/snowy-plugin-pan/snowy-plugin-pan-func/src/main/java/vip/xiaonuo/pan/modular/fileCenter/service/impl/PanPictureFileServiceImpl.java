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
import vip.xiaonuo.pan.modular.fileCenter.entity.PanPictureFile;
import vip.xiaonuo.pan.modular.fileCenter.mapper.PanPictureFileMapper;
import vip.xiaonuo.pan.modular.fileCenter.param.PanPictureFileAddParam;
import vip.xiaonuo.pan.modular.fileCenter.param.PanPictureFileEditParam;
import vip.xiaonuo.pan.modular.fileCenter.param.PanPictureFileIdParam;
import vip.xiaonuo.pan.modular.fileCenter.param.PanPictureFilePageParam;
import vip.xiaonuo.pan.modular.fileCenter.service.PanPictureFileService;

import java.util.List;

/**
 * 云盘-图片文件Service接口实现类
 *
 * @author mayike
 * @date  2023/09/08 16:16
 **/
@Service
public class PanPictureFileServiceImpl extends ServiceImpl<PanPictureFileMapper, PanPictureFile> implements PanPictureFileService {

    @Override
    public Page<PanPictureFile> page(PanPictureFilePageParam panPictureFilePageParam) {
        QueryWrapper<PanPictureFile> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(panPictureFilePageParam.getSortField(), panPictureFilePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(panPictureFilePageParam.getSortOrder());
            queryWrapper.orderBy(true, panPictureFilePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(panPictureFilePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(PanPictureFile::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PanPictureFileAddParam panPictureFileAddParam) {
        PanPictureFile panPictureFile = BeanUtil.toBean(panPictureFileAddParam, PanPictureFile.class);
        this.save(panPictureFile);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PanPictureFileEditParam panPictureFileEditParam) {
        PanPictureFile panPictureFile = this.queryEntity(panPictureFileEditParam.getId());
        BeanUtil.copyProperties(panPictureFileEditParam, panPictureFile);
        this.updateById(panPictureFile);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PanPictureFileIdParam> panPictureFileIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(panPictureFileIdParamList, PanPictureFileIdParam::getId));
    }

    @Override
    public PanPictureFile detail(PanPictureFileIdParam panPictureFileIdParam) {
        return this.queryEntity(panPictureFileIdParam.getId());
    }

    @Override
    public PanPictureFile queryEntity(String id) {
        PanPictureFile panPictureFile = this.getById(id);
        if(ObjectUtil.isEmpty(panPictureFile)) {
            throw new CommonException("云盘-图片文件不存在，id值为：{}", id);
        }
        return panPictureFile;
    }
}
