package vip.xiaonuo.biz.modular.prj.mou.service.impl;

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
import vip.xiaonuo.biz.modular.prj.mou.entity.BizPrjMouItem;
import vip.xiaonuo.biz.modular.prj.mou.mapper.BizPrjMouItemMapper;
import vip.xiaonuo.biz.modular.prj.mou.param.BizPrjMouItemAddParam;
import vip.xiaonuo.biz.modular.prj.mou.param.BizPrjMouItemEditParam;
import vip.xiaonuo.biz.modular.prj.mou.param.BizPrjMouItemIdParam;
import vip.xiaonuo.biz.modular.prj.mou.param.BizPrjMouItemPageParam;
import vip.xiaonuo.biz.modular.prj.mou.service.BizPrjMouItemService;

import java.util.List;

/**
 * 项目承接备忘录 - 项目生产内部分工Service接口实现类
 *
 * @author yy
 * @date  2025/06/06 11:43
 **/
@Service
@DS("slave_1")
public class BizPrjMouItemServiceImpl extends ServiceImpl<BizPrjMouItemMapper, BizPrjMouItem> implements BizPrjMouItemService {

    @Override
    public Page<BizPrjMouItem> page(BizPrjMouItemPageParam bizPrjMouItemPageParam) {
        QueryWrapper<BizPrjMouItem> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizPrjMouItemPageParam.getBizPrjMouId())) {
            queryWrapper.lambda().eq(BizPrjMouItem::getBizPrjMouId, bizPrjMouItemPageParam.getBizPrjMouId());
        }
        if(ObjectUtil.isNotEmpty(bizPrjMouItemPageParam.getMouType())) {
            queryWrapper.lambda().like(BizPrjMouItem::getMouType, bizPrjMouItemPageParam.getMouType());
        }
        if(ObjectUtil.isNotEmpty(bizPrjMouItemPageParam.getDeptId())) {
            queryWrapper.lambda().eq(BizPrjMouItem::getDeptId, bizPrjMouItemPageParam.getDeptId());
        }
        if(ObjectUtil.isNotEmpty(bizPrjMouItemPageParam.getDeptName())) {
            queryWrapper.lambda().like(BizPrjMouItem::getDeptName, bizPrjMouItemPageParam.getDeptName());
        }
        if(ObjectUtil.isNotEmpty(bizPrjMouItemPageParam.getContent())) {
            queryWrapper.lambda().like(BizPrjMouItem::getContent, bizPrjMouItemPageParam.getContent());
        }
        if(ObjectUtil.isAllNotEmpty(bizPrjMouItemPageParam.getSortField(), bizPrjMouItemPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizPrjMouItemPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizPrjMouItemPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizPrjMouItemPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizPrjMouItem::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizPrjMouItemAddParam bizPrjMouItemAddParam) {
        BizPrjMouItem bizPrjMouItem = BeanUtil.toBean(bizPrjMouItemAddParam, BizPrjMouItem.class);
        this.save(bizPrjMouItem);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizPrjMouItemEditParam bizPrjMouItemEditParam) {
        BizPrjMouItem bizPrjMouItem = this.queryEntity(bizPrjMouItemEditParam.getId());
        BeanUtil.copyProperties(bizPrjMouItemEditParam, bizPrjMouItem);
        this.updateById(bizPrjMouItem);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizPrjMouItemIdParam> bizPrjMouItemIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizPrjMouItemIdParamList, BizPrjMouItemIdParam::getId));
    }

    @Override
    public BizPrjMouItem detail(BizPrjMouItemIdParam bizPrjMouItemIdParam) {
        return this.queryEntity(bizPrjMouItemIdParam.getId());
    }

    @Override
    public BizPrjMouItem queryEntity(String id) {
        BizPrjMouItem bizPrjMouItem = this.getById(id);
        if(ObjectUtil.isEmpty(bizPrjMouItem)) {
            throw new CommonException("项目承接备忘录 - 项目生产内部分工不存在，id值为：{}", id);
        }
        return bizPrjMouItem;
    }
}
