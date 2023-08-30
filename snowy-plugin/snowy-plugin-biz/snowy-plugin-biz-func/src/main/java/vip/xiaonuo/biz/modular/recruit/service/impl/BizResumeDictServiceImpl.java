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
import vip.xiaonuo.biz.modular.recruit.entity.BizResumeDict;
import vip.xiaonuo.biz.modular.recruit.mapper.BizResumeDictMapper;
import vip.xiaonuo.biz.modular.recruit.param.BizResumeDictAddParam;
import vip.xiaonuo.biz.modular.recruit.param.BizResumeDictEditParam;
import vip.xiaonuo.biz.modular.recruit.param.BizResumeDictIdParam;
import vip.xiaonuo.biz.modular.recruit.param.BizResumeDictPageParam;
import vip.xiaonuo.biz.modular.recruit.service.BizResumeDictService;

import java.util.List;

/**
 * 业务表-招聘-字典Service接口实现类
 *
 * @author yong.ye
 * @date  2023/08/28 12:44
 **/

@DS("slave_1")
@Service
public class BizResumeDictServiceImpl extends ServiceImpl<BizResumeDictMapper, BizResumeDict> implements BizResumeDictService {

    @Override
    public Page<BizResumeDict> page(BizResumeDictPageParam bizResumeDictPageParam) {
        QueryWrapper<BizResumeDict> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizResumeDictPageParam.getType())) {
            queryWrapper.lambda().eq(BizResumeDict::getType, bizResumeDictPageParam.getType());
        }
        if(ObjectUtil.isNotEmpty(bizResumeDictPageParam.getDictName())) {
            queryWrapper.lambda().like(BizResumeDict::getDictName, bizResumeDictPageParam.getDictName());
        }
        if(ObjectUtil.isAllNotEmpty(bizResumeDictPageParam.getSortField(), bizResumeDictPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizResumeDictPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizResumeDictPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizResumeDictPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizResumeDict::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizResumeDictAddParam bizResumeDictAddParam) {
        BizResumeDict bizResumeDict = BeanUtil.toBean(bizResumeDictAddParam, BizResumeDict.class);
        this.save(bizResumeDict);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizResumeDictEditParam bizResumeDictEditParam) {
        BizResumeDict bizResumeDict = this.queryEntity(bizResumeDictEditParam.getId());
        BeanUtil.copyProperties(bizResumeDictEditParam, bizResumeDict);
        this.updateById(bizResumeDict);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizResumeDictIdParam> bizResumeDictIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizResumeDictIdParamList, BizResumeDictIdParam::getId));
    }

    @Override
    public BizResumeDict detail(BizResumeDictIdParam bizResumeDictIdParam) {
        return this.queryEntity(bizResumeDictIdParam.getId());
    }

    @Override
    public BizResumeDict queryEntity(String id) {
        BizResumeDict bizResumeDict = this.getById(id);
        if(ObjectUtil.isEmpty(bizResumeDict)) {
            throw new CommonException("业务表-招聘-字典不存在，id值为：{}", id);
        }
        return bizResumeDict;
    }
}
