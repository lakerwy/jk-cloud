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
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.teambition.entity.BizTaskGroup;
import vip.xiaonuo.biz.modular.teambition.mapper.BizTaskGroupMapper;
import vip.xiaonuo.biz.modular.teambition.param.BizTaskGroupAddParam;
import vip.xiaonuo.biz.modular.teambition.param.BizTaskGroupEditParam;
import vip.xiaonuo.biz.modular.teambition.param.BizTaskGroupIdParam;
import vip.xiaonuo.biz.modular.teambition.param.BizTaskGroupPageParam;
import vip.xiaonuo.biz.modular.teambition.service.BizTaskGroupService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;

import java.util.List;

/**
 * 任务来源表Service接口实现类
 *
 * @author ljy
 * @date  2023/12/08 08:58
 **/
@DS("slave_1")
@Service
public class BizTaskGroupServiceImpl extends ServiceImpl<BizTaskGroupMapper, BizTaskGroup> implements BizTaskGroupService {

    @Override
    public Page<BizTaskGroup> page(BizTaskGroupPageParam bizTaskGroupPageParam) {
        QueryWrapper<BizTaskGroup> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizTaskGroupPageParam.getTaskGroupId())) {
            queryWrapper.lambda().eq(BizTaskGroup::getTaskGroupId, bizTaskGroupPageParam.getTaskGroupId());
        }
        if(ObjectUtil.isNotEmpty(bizTaskGroupPageParam.getObjectId())) {
            queryWrapper.lambda().eq(BizTaskGroup::getObjectId, bizTaskGroupPageParam.getObjectId());
        }
        if(ObjectUtil.isAllNotEmpty(bizTaskGroupPageParam.getSortField(), bizTaskGroupPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizTaskGroupPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizTaskGroupPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizTaskGroupPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizTaskGroup::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizTaskGroupAddParam bizTaskGroupAddParam) {
        BizTaskGroup bizTaskGroup = BeanUtil.toBean(bizTaskGroupAddParam, BizTaskGroup.class);
        this.save(bizTaskGroup);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizTaskGroupEditParam bizTaskGroupEditParam) {
        BizTaskGroup bizTaskGroup = this.queryEntity(bizTaskGroupEditParam.getId());
        BeanUtil.copyProperties(bizTaskGroupEditParam, bizTaskGroup);
        this.updateById(bizTaskGroup);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizTaskGroupIdParam> bizTaskGroupIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizTaskGroupIdParamList, BizTaskGroupIdParam::getId));
    }

    @Override
    public BizTaskGroup detail(BizTaskGroupIdParam bizTaskGroupIdParam) {
        return this.queryEntity(bizTaskGroupIdParam.getId());
    }

    @Override
    public BizTaskGroup queryEntity(String id) {
        BizTaskGroup bizTaskGroup = this.getById(id);
        if(ObjectUtil.isEmpty(bizTaskGroup)) {
            throw new CommonException("任务来源表不存在，id值为：{}", id);
        }
        return bizTaskGroup;
    }
}
