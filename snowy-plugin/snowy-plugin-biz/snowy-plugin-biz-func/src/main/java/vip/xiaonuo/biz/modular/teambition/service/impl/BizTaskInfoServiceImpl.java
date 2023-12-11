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
import vip.xiaonuo.biz.modular.teambition.entity.BizTaskInfo;
import vip.xiaonuo.biz.modular.teambition.mapper.BizTaskInfoMapper;
import vip.xiaonuo.biz.modular.teambition.param.BizTaskInfoAddParam;
import vip.xiaonuo.biz.modular.teambition.param.BizTaskInfoEditParam;
import vip.xiaonuo.biz.modular.teambition.param.BizTaskInfoIdParam;
import vip.xiaonuo.biz.modular.teambition.param.BizTaskInfoPageParam;
import vip.xiaonuo.biz.modular.teambition.service.BizTaskInfoService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;

import java.util.List;

/**
 * 任务主表Service接口实现类
 *
 * @author ljy
 * @date  2023/12/08 09:03
 **/
@Service
public class BizTaskInfoServiceImpl extends ServiceImpl<BizTaskInfoMapper, BizTaskInfo> implements BizTaskInfoService {

    @Override
    public Page<BizTaskInfo> page(BizTaskInfoPageParam bizTaskInfoPageParam) {
        QueryWrapper<BizTaskInfo> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizTaskInfoPageParam.getParentId())) {
            queryWrapper.lambda().eq(BizTaskInfo::getParentId, bizTaskInfoPageParam.getParentId());
        }
        if(ObjectUtil.isNotEmpty(bizTaskInfoPageParam.getTaskName())) {
            queryWrapper.lambda().like(BizTaskInfo::getTaskName, bizTaskInfoPageParam.getTaskName());
        }
        if(ObjectUtil.isNotEmpty(bizTaskInfoPageParam.getProId())) {
            queryWrapper.lambda().eq(BizTaskInfo::getProId, bizTaskInfoPageParam.getProId());
        }
        if(ObjectUtil.isNotEmpty(bizTaskInfoPageParam.getStartTaskStartTime()) && ObjectUtil.isNotEmpty(bizTaskInfoPageParam.getEndTaskStartTime())) {
            queryWrapper.lambda().between(BizTaskInfo::getTaskStartTime, bizTaskInfoPageParam.getStartTaskStartTime(), bizTaskInfoPageParam.getEndTaskStartTime());
        }
        if(ObjectUtil.isNotEmpty(bizTaskInfoPageParam.getStartTaskEndTime()) && ObjectUtil.isNotEmpty(bizTaskInfoPageParam.getEndTaskEndTime())) {
            queryWrapper.lambda().between(BizTaskInfo::getTaskEndTime, bizTaskInfoPageParam.getStartTaskEndTime(), bizTaskInfoPageParam.getEndTaskEndTime());
        }
        if(ObjectUtil.isNotEmpty(bizTaskInfoPageParam.getBelonger())) {
            queryWrapper.lambda().eq(BizTaskInfo::getBelonger, bizTaskInfoPageParam.getBelonger());
        }
        if(ObjectUtil.isAllNotEmpty(bizTaskInfoPageParam.getSortField(), bizTaskInfoPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizTaskInfoPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizTaskInfoPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizTaskInfoPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizTaskInfo::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizTaskInfoAddParam bizTaskInfoAddParam) {
        BizTaskInfo bizTaskInfo = BeanUtil.toBean(bizTaskInfoAddParam, BizTaskInfo.class);
        this.save(bizTaskInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizTaskInfoEditParam bizTaskInfoEditParam) {
        BizTaskInfo bizTaskInfo = this.queryEntity(bizTaskInfoEditParam.getId());
        BeanUtil.copyProperties(bizTaskInfoEditParam, bizTaskInfo);
        this.updateById(bizTaskInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizTaskInfoIdParam> bizTaskInfoIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizTaskInfoIdParamList, BizTaskInfoIdParam::getId));
    }

    @Override
    public BizTaskInfo detail(BizTaskInfoIdParam bizTaskInfoIdParam) {
        return this.queryEntity(bizTaskInfoIdParam.getId());
    }

    @Override
    public BizTaskInfo queryEntity(String id) {
        BizTaskInfo bizTaskInfo = this.getById(id);
        if(ObjectUtil.isEmpty(bizTaskInfo)) {
            throw new CommonException("任务主表不存在，id值为：{}", id);
        }
        return bizTaskInfo;
    }
}
