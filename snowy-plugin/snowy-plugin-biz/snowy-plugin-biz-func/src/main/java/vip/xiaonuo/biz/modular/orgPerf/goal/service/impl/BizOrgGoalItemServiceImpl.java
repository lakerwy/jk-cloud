package vip.xiaonuo.biz.modular.orgPerf.goal.service.impl;

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
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizOrgGoalItem;
import vip.xiaonuo.biz.modular.orgPerf.goal.mapper.BizOrgGoalItemMapper;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizOrgGoalItemAddParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizOrgGoalItemEditParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizOrgGoalItemIdParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizOrgGoalItemPageParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.service.BizOrgGoalItemService;

import java.util.List;

/**
 * 组织绩效考核-考核指标Service接口实现类
 *
 * @author pzh
 * @date  2025/06/24 16:45
 **/
@Service
@DS("slave_1")
public class BizOrgGoalItemServiceImpl extends ServiceImpl<BizOrgGoalItemMapper, BizOrgGoalItem> implements BizOrgGoalItemService {

    @Override
    public Page<BizOrgGoalItem> page(BizOrgGoalItemPageParam bizOrgGoalItemPageParam) {
        QueryWrapper<BizOrgGoalItem> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(bizOrgGoalItemPageParam.getSortField(), bizOrgGoalItemPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizOrgGoalItemPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizOrgGoalItemPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizOrgGoalItemPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizOrgGoalItem::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizOrgGoalItemAddParam bizOrgGoalItemAddParam) {
        BizOrgGoalItem bizOrgGoalItem = BeanUtil.toBean(bizOrgGoalItemAddParam, BizOrgGoalItem.class);
        this.save(bizOrgGoalItem);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizOrgGoalItemEditParam bizOrgGoalItemEditParam) {
        BizOrgGoalItem bizOrgGoalItem = this.queryEntity(bizOrgGoalItemEditParam.getId());
        BeanUtil.copyProperties(bizOrgGoalItemEditParam, bizOrgGoalItem);
        this.updateById(bizOrgGoalItem);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizOrgGoalItemIdParam> bizOrgGoalItemIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizOrgGoalItemIdParamList, BizOrgGoalItemIdParam::getId));
    }

    @Override
    public BizOrgGoalItem detail(BizOrgGoalItemIdParam bizOrgGoalItemIdParam) {
        return this.queryEntity(bizOrgGoalItemIdParam.getId());
    }

    @Override
    public BizOrgGoalItem queryEntity(String id) {
        BizOrgGoalItem bizOrgGoalItem = this.getById(id);
        if(ObjectUtil.isEmpty(bizOrgGoalItem)) {
            throw new CommonException("组织绩效考核-考核指标不存在，id值为：{}", id);
        }
        return bizOrgGoalItem;
    }
}
