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
package vip.xiaonuo.biz.modular.teambition.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.teambition.entity.BizTaskFile;
import vip.xiaonuo.biz.modular.teambition.mapper.BizTaskFileMapper;
import vip.xiaonuo.biz.modular.teambition.param.BizTaskFileAddParam;
import vip.xiaonuo.biz.modular.teambition.param.BizTaskFileEditParam;
import vip.xiaonuo.biz.modular.teambition.param.BizTaskFileIdParam;
import vip.xiaonuo.biz.modular.teambition.param.BizTaskFilePageParam;
import vip.xiaonuo.biz.modular.teambition.service.BizTaskFileService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;

import java.util.List;

/**
 * 任务管理附件数据表Service接口实现类
 *
 * @author ljy
 * @date  2023/12/11 10:57
 **/
@Service
public class BizTaskFileServiceImpl extends ServiceImpl<BizTaskFileMapper, BizTaskFile> implements BizTaskFileService {

    @Override
    public Page<BizTaskFile> page(BizTaskFilePageParam bizTaskFilePageParam) {
        QueryWrapper<BizTaskFile> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizTaskFilePageParam.getTaskProjectId())) {
            queryWrapper.lambda().eq(BizTaskFile::getTaskProjectId, bizTaskFilePageParam.getTaskProjectId());
        }
        if(ObjectUtil.isNotEmpty(bizTaskFilePageParam.getTaskInfoId())) {
            queryWrapper.lambda().eq(BizTaskFile::getTaskInfoId, bizTaskFilePageParam.getTaskInfoId());
        }
        if(ObjectUtil.isNotEmpty(bizTaskFilePageParam.getFileId())) {
            queryWrapper.lambda().eq(BizTaskFile::getFileId, bizTaskFilePageParam.getFileId());
        }
        if(ObjectUtil.isAllNotEmpty(bizTaskFilePageParam.getSortField(), bizTaskFilePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizTaskFilePageParam.getSortOrder());
            queryWrapper.orderBy(true, bizTaskFilePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizTaskFilePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizTaskFile::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizTaskFileAddParam bizTaskFileAddParam) {
        BizTaskFile bizTaskFile = BeanUtil.toBean(bizTaskFileAddParam, BizTaskFile.class);
        this.save(bizTaskFile);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizTaskFileEditParam bizTaskFileEditParam) {
        BizTaskFile bizTaskFile = this.queryEntity(bizTaskFileEditParam.getId());
        BeanUtil.copyProperties(bizTaskFileEditParam, bizTaskFile);
        this.updateById(bizTaskFile);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizTaskFileIdParam> bizTaskFileIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizTaskFileIdParamList, BizTaskFileIdParam::getId));
    }

    @Override
    public BizTaskFile detail(BizTaskFileIdParam bizTaskFileIdParam) {
        return this.queryEntity(bizTaskFileIdParam.getId());
    }

    @Override
    public BizTaskFile queryEntity(String id) {
        BizTaskFile bizTaskFile = this.getById(id);
        if(ObjectUtil.isEmpty(bizTaskFile)) {
            throw new CommonException("任务管理附件数据表不存在，id值为：{}", id);
        }
        return bizTaskFile;
    }
}
