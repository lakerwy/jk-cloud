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
package vip.xiaonuo.pan.modular.uploadTask.service.impl;

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
import vip.xiaonuo.pan.modular.uploadTask.entity.PanUploadTask;
import vip.xiaonuo.pan.modular.uploadTask.mapper.PanUploadTaskMapper;
import vip.xiaonuo.pan.modular.uploadTask.param.PanUploadTaskAddParam;
import vip.xiaonuo.pan.modular.uploadTask.param.PanUploadTaskEditParam;
import vip.xiaonuo.pan.modular.uploadTask.param.PanUploadTaskIdParam;
import vip.xiaonuo.pan.modular.uploadTask.param.PanUploadTaskPageParam;
import vip.xiaonuo.pan.modular.uploadTask.service.PanUploadTaskService;

import java.util.List;

/**
 * 云盘-上传任务主表Service接口实现类
 *
 * @author mayike
 * @date  2023/09/08 16:21
 **/
@Service
public class PanUploadTaskServiceImpl extends ServiceImpl<PanUploadTaskMapper, PanUploadTask> implements PanUploadTaskService {

    @Override
    public Page<PanUploadTask> page(PanUploadTaskPageParam panUploadTaskPageParam) {
        QueryWrapper<PanUploadTask> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(panUploadTaskPageParam.getSortField(), panUploadTaskPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(panUploadTaskPageParam.getSortOrder());
            queryWrapper.orderBy(true, panUploadTaskPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(panUploadTaskPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(PanUploadTask::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PanUploadTaskAddParam panUploadTaskAddParam) {
        PanUploadTask panUploadTask = BeanUtil.toBean(panUploadTaskAddParam, PanUploadTask.class);
        this.save(panUploadTask);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PanUploadTaskEditParam panUploadTaskEditParam) {
        PanUploadTask panUploadTask = this.queryEntity(panUploadTaskEditParam.getId());
        BeanUtil.copyProperties(panUploadTaskEditParam, panUploadTask);
        this.updateById(panUploadTask);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PanUploadTaskIdParam> panUploadTaskIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(panUploadTaskIdParamList, PanUploadTaskIdParam::getId));
    }

    @Override
    public PanUploadTask detail(PanUploadTaskIdParam panUploadTaskIdParam) {
        return this.queryEntity(panUploadTaskIdParam.getId());
    }

    @Override
    public PanUploadTask queryEntity(String id) {
        PanUploadTask panUploadTask = this.getById(id);
        if(ObjectUtil.isEmpty(panUploadTask)) {
            throw new CommonException("云盘-上传任务主表不存在，id值为：{}", id);
        }
        return panUploadTask;
    }
}
