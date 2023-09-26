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
package vip.xiaonuo.biz.modular.recruit.service.impl;

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
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.recruit.entity.BizRecruitFlw;
import vip.xiaonuo.biz.modular.recruit.mapper.BizRecruitFlwMapper;
import vip.xiaonuo.biz.modular.recruit.param.BizRecruitFlwAddParam;
import vip.xiaonuo.biz.modular.recruit.param.BizRecruitFlwEditParam;
import vip.xiaonuo.biz.modular.recruit.param.BizRecruitFlwIdParam;
import vip.xiaonuo.biz.modular.recruit.param.BizRecruitFlwPageParam;
import vip.xiaonuo.biz.modular.recruit.service.BizRecruitFlwService;

import java.util.List;

/**
 * 业务表-招聘-部门计划流程审批单Service接口实现类
 *
 * @author yong.ye
 * @date  2023/08/23 09:00
 **/

@DS("slave_1")
@Service
public class BizRecruitFlwServiceImpl extends ServiceImpl<BizRecruitFlwMapper, BizRecruitFlw> implements BizRecruitFlwService {

    @Override
    public Page<BizRecruitFlw> page(BizRecruitFlwPageParam bizRecruitFlwPageParam) {
        QueryWrapper<BizRecruitFlw> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(bizRecruitFlwPageParam.getSortField(), bizRecruitFlwPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizRecruitFlwPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizRecruitFlwPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizRecruitFlwPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizRecruitFlw::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizRecruitFlwAddParam bizRecruitFlwAddParam) {
        BizRecruitFlw bizRecruitFlw = BeanUtil.toBean(bizRecruitFlwAddParam, BizRecruitFlw.class);
        this.save(bizRecruitFlw);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizRecruitFlwEditParam bizRecruitFlwEditParam) {
        BizRecruitFlw bizRecruitFlw = this.queryEntity(bizRecruitFlwEditParam.getId());
        BeanUtil.copyProperties(bizRecruitFlwEditParam, bizRecruitFlw);
        this.updateById(bizRecruitFlw);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizRecruitFlwIdParam> bizRecruitFlwIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizRecruitFlwIdParamList, BizRecruitFlwIdParam::getId));
    }

    @Override
    public BizRecruitFlw detail(BizRecruitFlwIdParam bizRecruitFlwIdParam) {
        return this.queryEntity(bizRecruitFlwIdParam.getId());
    }

    @Override
    public BizRecruitFlw queryEntity(String id) {
        BizRecruitFlw bizRecruitFlw = this.getById(id);
        if(ObjectUtil.isEmpty(bizRecruitFlw)) {
            throw new CommonException("业务表-招聘-部门计划流程审批单不存在，id值为：{}", id);
        }
        return bizRecruitFlw;
    }
}
