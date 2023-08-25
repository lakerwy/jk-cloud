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
import vip.xiaonuo.biz.modular.recruit.entity.BizResumePerser;
import vip.xiaonuo.biz.modular.recruit.mapper.BizResumePerserMapper;
import vip.xiaonuo.biz.modular.recruit.param.BizResumePerserAddParam;
import vip.xiaonuo.biz.modular.recruit.param.BizResumePerserEditParam;
import vip.xiaonuo.biz.modular.recruit.param.BizResumePerserIdParam;
import vip.xiaonuo.biz.modular.recruit.param.BizResumePerserPageParam;
import vip.xiaonuo.biz.modular.recruit.service.BizResumePerserService;

import java.util.List;

/**
 * 业务表-招聘-简历解析接口调用记录Service接口实现类
 *
 * @author yong.ye
 * @date  2023/08/25 08:46
 **/
@DS("slave_1")
@Service
public class BizResumePerserServiceImpl extends ServiceImpl<BizResumePerserMapper, BizResumePerser> implements BizResumePerserService {

    @Override
    public Page<BizResumePerser> page(BizResumePerserPageParam bizResumePerserPageParam) {
        QueryWrapper<BizResumePerser> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(bizResumePerserPageParam.getSortField(), bizResumePerserPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizResumePerserPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizResumePerserPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizResumePerserPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizResumePerser::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizResumePerserAddParam bizResumePerserAddParam) {
        BizResumePerser bizResumePerser = BeanUtil.toBean(bizResumePerserAddParam, BizResumePerser.class);
        this.save(bizResumePerser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizResumePerserEditParam bizResumePerserEditParam) {
        BizResumePerser bizResumePerser = this.queryEntity(bizResumePerserEditParam.getId());
        BeanUtil.copyProperties(bizResumePerserEditParam, bizResumePerser);
        this.updateById(bizResumePerser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizResumePerserIdParam> bizResumePerserIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizResumePerserIdParamList, BizResumePerserIdParam::getId));
    }

    @Override
    public BizResumePerser detail(BizResumePerserIdParam bizResumePerserIdParam) {
        return this.queryEntity(bizResumePerserIdParam.getId());
    }

    @Override
    public BizResumePerser queryEntity(String id) {
        BizResumePerser bizResumePerser = this.getById(id);
        if(ObjectUtil.isEmpty(bizResumePerser)) {
            throw new CommonException("业务表-招聘-简历解析接口调用记录不存在，id值为：{}", id);
        }
        return bizResumePerser;
    }

    @Override
    public BizResumePerser usageRemaining() {
        return this.getOne(new QueryWrapper<BizResumePerser>().lambda().eq(BizResumePerser::getCode,"200").orderByDesc(BizResumePerser::getCreateTime).last("limit 1"));
    }
}
