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
import vip.xiaonuo.biz.modular.task.entity.BizTaskFlw;
import vip.xiaonuo.biz.modular.task.mapper.BizTaskFlwMapper;
import vip.xiaonuo.biz.modular.task.param.*;
import vip.xiaonuo.biz.modular.task.service.BizTaskFlwService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;

import java.util.List;

/**
 * 业务表-绩效考核-任务流程单（流程审批）Service接口实现类
 *
 * @author yong.ye
 * @date  2023/08/04 17:33
 **/

@DS("slave_1")
@Service
public class BizTaskFlwServiceImpl extends ServiceImpl<BizTaskFlwMapper, BizTaskFlw> implements BizTaskFlwService {

    @Override
    public Page<BizTaskFlw> page(BizTaskFlwPageParam bizTaskFlwPageParam) {
        QueryWrapper<BizTaskFlw> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(bizTaskFlwPageParam.getSortField(), bizTaskFlwPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizTaskFlwPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizTaskFlwPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizTaskFlwPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizTaskFlw::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizTaskFlwAddParam bizTaskFlwAddParam) {
        BizTaskFlw bizTaskFlw = BeanUtil.toBean(bizTaskFlwAddParam, BizTaskFlw.class);
        this.save(bizTaskFlw);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizTaskFlwEditParam bizTaskFlwEditParam) {
        BizTaskFlw bizTaskFlw = this.queryEntity(bizTaskFlwEditParam.getId());
        BeanUtil.copyProperties(bizTaskFlwEditParam, bizTaskFlw);
        this.updateById(bizTaskFlw);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizTaskFlwIdParam> bizTaskFlwIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizTaskFlwIdParamList, BizTaskFlwIdParam::getId));
    }

    @Override
    public BizTaskFlw detail(BizTaskFlwIdParam bizTaskFlwIdParam) {
        return this.queryEntity(bizTaskFlwIdParam.getId());
    }

    @Override
    public BizTaskFlw queryEntity(String id) {
        BizTaskFlw bizTaskFlw = this.getById(id);
        if(ObjectUtil.isEmpty(bizTaskFlw)) {
            throw new CommonException("业务表-绩效考核-任务流程单（流程审批）不存在，id值为：{}", id);
        }
        return bizTaskFlw;
    }
}
