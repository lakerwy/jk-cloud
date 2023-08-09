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
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.task.entity.BizDeptAssDict;
import vip.xiaonuo.biz.modular.task.mapper.BizDeptAssDictMapper;
import vip.xiaonuo.biz.modular.task.param.*;
import vip.xiaonuo.biz.modular.task.service.BizDeptAssDictService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;


import java.util.List;

/**
 * 业务表-部门绩效考核-数据字典Service接口实现类
 *
 * @author yong.ye
 * @date  2023/08/08 17:19
 **/

@DS("slave_1")
@Service
public class BizDeptAssDictServiceImpl extends ServiceImpl<BizDeptAssDictMapper, BizDeptAssDict> implements BizDeptAssDictService {

    @Override
    public Page<BizDeptAssDict> page(BizDeptAssDictPageParam bizDeptAssDictPageParam) {
        QueryWrapper<BizDeptAssDict> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizDeptAssDictPageParam.getDictType())) {
            queryWrapper.lambda().eq(BizDeptAssDict::getDictType, bizDeptAssDictPageParam.getDictType());
        }
        if(ObjectUtil.isNotEmpty(bizDeptAssDictPageParam.getDictName())) {
            queryWrapper.lambda().like(BizDeptAssDict::getDictName, bizDeptAssDictPageParam.getDictName());
        }
        if(ObjectUtil.isAllNotEmpty(bizDeptAssDictPageParam.getSortField(), bizDeptAssDictPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizDeptAssDictPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizDeptAssDictPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizDeptAssDictPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizDeptAssDict::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizDeptAssDictAddParam bizDeptAssDictAddParam) {
        BizDeptAssDict bizDeptAssDict = BeanUtil.toBean(bizDeptAssDictAddParam, BizDeptAssDict.class);
        this.save(bizDeptAssDict);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizDeptAssDictEditParam bizDeptAssDictEditParam) {
        BizDeptAssDict bizDeptAssDict = this.queryEntity(bizDeptAssDictEditParam.getId());
        BeanUtil.copyProperties(bizDeptAssDictEditParam, bizDeptAssDict);
        this.updateById(bizDeptAssDict);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizDeptAssDictIdParam> bizDeptAssDictIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizDeptAssDictIdParamList, BizDeptAssDictIdParam::getId));
    }

    @Override
    public BizDeptAssDict detail(BizDeptAssDictIdParam bizDeptAssDictIdParam) {
        return this.queryEntity(bizDeptAssDictIdParam.getId());
    }

    @Override
    public BizDeptAssDict queryEntity(String id) {
        BizDeptAssDict bizDeptAssDict = this.getById(id);
        if(ObjectUtil.isEmpty(bizDeptAssDict)) {
            throw new CommonException("业务表-部门绩效考核-数据字典不存在，id值为：{}", id);
        }
        return bizDeptAssDict;
    }
}
