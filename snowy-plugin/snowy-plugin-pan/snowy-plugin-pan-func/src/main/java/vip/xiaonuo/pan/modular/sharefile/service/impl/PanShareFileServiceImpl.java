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
package vip.xiaonuo.pan.modular.sharefile.service.impl;

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
import vip.xiaonuo.pan.modular.sharefile.entity.PanShareFile;
import vip.xiaonuo.pan.modular.sharefile.mapper.PanShareFileMapper;
import vip.xiaonuo.pan.modular.sharefile.param.*;
import vip.xiaonuo.pan.modular.sharefile.service.PanShareFileService;

import java.util.List;

/**
 * 网盘-文件分享表Service接口实现类
 *
 * @author yong.ye
 * @date  2023/09/07 15:56
 **/
@Service
public class PanShareFileServiceImpl extends ServiceImpl<PanShareFileMapper, PanShareFile> implements PanShareFileService {

    @Override
    public Page<PanShareFile> page(PanShareFilePageParam panShareFilePageParam) {
        QueryWrapper<PanShareFile> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(panShareFilePageParam.getTenantId())) {
            queryWrapper.lambda().eq(PanShareFile::getTenantId, panShareFilePageParam.getTenantId());
        }
        if(ObjectUtil.isNotEmpty(panShareFilePageParam.getShareBatchNum())) {
            queryWrapper.lambda().eq(PanShareFile::getShareBatchNum, panShareFilePageParam.getShareBatchNum());
        }
        if(ObjectUtil.isAllNotEmpty(panShareFilePageParam.getSortField(), panShareFilePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(panShareFilePageParam.getSortOrder());
            queryWrapper.orderBy(true, panShareFilePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(panShareFilePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(PanShareFile::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PanShareFileAddParam panShareFileAddParam) {
        PanShareFile panShareFile = BeanUtil.toBean(panShareFileAddParam, PanShareFile.class);
        this.save(panShareFile);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PanShareFileEditParam panShareFileEditParam) {
        PanShareFile panShareFile = this.queryEntity(panShareFileEditParam.getId());
        BeanUtil.copyProperties(panShareFileEditParam, panShareFile);
        this.updateById(panShareFile);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PanShareFileIdParam> panShareFileIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(panShareFileIdParamList, PanShareFileIdParam::getId));
    }

    @Override
    public PanShareFile detail(PanShareFileIdParam panShareFileIdParam) {
        return this.queryEntity(panShareFileIdParam.getId());
    }

    @Override
    public PanShareFile queryEntity(String id) {
        PanShareFile panShareFile = this.getById(id);
        if(ObjectUtil.isEmpty(panShareFile)) {
            throw new CommonException("网盘-文件分享表不存在，id值为：{}", id);
        }
        return panShareFile;
    }
}
