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
import vip.xiaonuo.biz.modular.teambition.entity.BizTaskGroupRes;
import vip.xiaonuo.biz.modular.teambition.mapper.BizTaskGroupResMapper;
import vip.xiaonuo.biz.modular.teambition.param.BizTaskGroupResAddParam;
import vip.xiaonuo.biz.modular.teambition.param.BizTaskGroupResEditParam;
import vip.xiaonuo.biz.modular.teambition.param.BizTaskGroupResIdParam;
import vip.xiaonuo.biz.modular.teambition.param.BizTaskGroupResPageParam;
import vip.xiaonuo.biz.modular.teambition.service.BizTaskGroupResService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import java.util.List;

/**
 * 任务分组关联表（人或部门）Service接口实现类
 *
 * @author ljy
 * @date  2023/12/08 09:00
 **/
@Service
public class BizTaskGroupResServiceImpl extends ServiceImpl<BizTaskGroupResMapper, BizTaskGroupRes> implements BizTaskGroupResService {

    @Override
    public Page<BizTaskGroupRes> page(BizTaskGroupResPageParam bizTaskGroupResPageParam) {
        QueryWrapper<BizTaskGroupRes> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizTaskGroupResPageParam.getTaskGroupId())) {
            queryWrapper.lambda().eq(BizTaskGroupRes::getTaskGroupId, bizTaskGroupResPageParam.getTaskGroupId());
        }
        if(ObjectUtil.isNotEmpty(bizTaskGroupResPageParam.getObjectId())) {
            queryWrapper.lambda().eq(BizTaskGroupRes::getObjectId, bizTaskGroupResPageParam.getObjectId());
        }
        if(ObjectUtil.isNotEmpty(bizTaskGroupResPageParam.getDataScopeId())) {
            queryWrapper.lambda().eq(BizTaskGroupRes::getDataScopeId, bizTaskGroupResPageParam.getDataScopeId());
        }
        if(ObjectUtil.isAllNotEmpty(bizTaskGroupResPageParam.getSortField(), bizTaskGroupResPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizTaskGroupResPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizTaskGroupResPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizTaskGroupResPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizTaskGroupRes::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizTaskGroupResAddParam bizTaskGroupResAddParam) {
        BizTaskGroupRes bizTaskGroupRes = BeanUtil.toBean(bizTaskGroupResAddParam, BizTaskGroupRes.class);
        this.save(bizTaskGroupRes);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizTaskGroupResEditParam bizTaskGroupResEditParam) {
        BizTaskGroupRes bizTaskGroupRes = this.queryEntity(bizTaskGroupResEditParam.getId());
        BeanUtil.copyProperties(bizTaskGroupResEditParam, bizTaskGroupRes);
        this.updateById(bizTaskGroupRes);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizTaskGroupResIdParam> bizTaskGroupResIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizTaskGroupResIdParamList, BizTaskGroupResIdParam::getId));
    }

    @Override
    public BizTaskGroupRes detail(BizTaskGroupResIdParam bizTaskGroupResIdParam) {
        return this.queryEntity(bizTaskGroupResIdParam.getId());
    }

    @Override
    public BizTaskGroupRes queryEntity(String id) {
        BizTaskGroupRes bizTaskGroupRes = this.getById(id);
        if(ObjectUtil.isEmpty(bizTaskGroupRes)) {
            throw new CommonException("任务分组关联表（人或部门）不存在，id值为：{}", id);
        }
        return bizTaskGroupRes;
    }
}
