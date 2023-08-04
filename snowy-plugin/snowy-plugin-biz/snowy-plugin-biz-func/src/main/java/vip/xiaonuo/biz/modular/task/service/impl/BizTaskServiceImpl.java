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
package vip.xiaonuo.biz.modular.task.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.task.param.*;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.task.entity.BizTask;
import vip.xiaonuo.biz.modular.task.mapper.BizTaskMapper;
import vip.xiaonuo.biz.modular.task.service.BizTaskService;

import java.util.List;

/**
 * 业务表-绩效考核-任务表Service接口实现类
 *
 * @author yong.ye
 * @date  2023/08/04 14:20
 **/
@Service
public class BizTaskServiceImpl extends ServiceImpl<BizTaskMapper, BizTask> implements BizTaskService {

    @Override
    public Page<BizTask> page(BizTaskPageParam bizTaskPageParam) {
        QueryWrapper<BizTask> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizTaskPageParam.getOrgId())) {
            queryWrapper.lambda().eq(BizTask::getOrgId, bizTaskPageParam.getOrgId());
        }
        if(ObjectUtil.isNotEmpty(bizTaskPageParam.getTaskName())) {
            queryWrapper.lambda().like(BizTask::getTaskName, bizTaskPageParam.getTaskName());
        }
        if(ObjectUtil.isAllNotEmpty(bizTaskPageParam.getSortField(), bizTaskPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizTaskPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizTaskPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizTaskPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizTask::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizTaskAddParam bizTaskAddParam) {
        BizTask bizTask = BeanUtil.toBean(bizTaskAddParam, BizTask.class);
        this.save(bizTask);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizTaskEditParam bizTaskEditParam) {
        BizTask bizTask = this.queryEntity(bizTaskEditParam.getId());
        BeanUtil.copyProperties(bizTaskEditParam, bizTask);
        this.updateById(bizTask);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizTaskIdParam> bizTaskIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizTaskIdParamList, BizTaskIdParam::getId));
    }

    @Override
    public BizTask detail(BizTaskIdParam bizTaskIdParam) {
        return this.queryEntity(bizTaskIdParam.getId());
    }

    @Override
    public BizTask queryEntity(String id) {
        BizTask bizTask = this.getById(id);
        if(ObjectUtil.isEmpty(bizTask)) {
            throw new CommonException("业务表-绩效考核-任务表不存在，id值为：{}", id);
        }
        return bizTask;
    }

    @Override
    public void batchAdd(BizTaskBatchAddParam bizTaskBatchAddParam) {

    }


}
