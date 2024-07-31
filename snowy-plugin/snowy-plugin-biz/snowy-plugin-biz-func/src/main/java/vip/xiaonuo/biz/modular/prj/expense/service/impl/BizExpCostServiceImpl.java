package vip.xiaonuo.biz.modular.prj.expense.service.impl;

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
import vip.xiaonuo.biz.modular.prj.expense.entity.BizExpCost;
import vip.xiaonuo.biz.modular.prj.expense.mapper.BizExpCostMapper;
import vip.xiaonuo.biz.modular.prj.expense.param.BizExpCostAddParam;
import vip.xiaonuo.biz.modular.prj.expense.param.BizExpCostEditParam;
import vip.xiaonuo.biz.modular.prj.expense.param.BizExpCostIdParam;
import vip.xiaonuo.biz.modular.prj.expense.param.BizExpCostPageParam;
import vip.xiaonuo.biz.modular.prj.expense.service.BizExpCostService;

import java.util.List;

/**
 * 财务管理 - 费用报销|差旅费用报销 - 成本一览表Service接口实现类
 *
 * @author yong.ye
 * @date  2024/07/30 15:32
 **/

@DS("slave_1")
@Service
public class BizExpCostServiceImpl extends ServiceImpl<BizExpCostMapper, BizExpCost> implements BizExpCostService {

    @Override
    public Page<BizExpCost> page(BizExpCostPageParam bizExpCostPageParam) {
        QueryWrapper<BizExpCost> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizExpCostPageParam.getPrjNo())) {
            queryWrapper.lambda().like(BizExpCost::getPrjNo, bizExpCostPageParam.getPrjNo());
        }
        if(ObjectUtil.isNotEmpty(bizExpCostPageParam.getPrjName())) {
            queryWrapper.lambda().like(BizExpCost::getPrjName, bizExpCostPageParam.getPrjName());
        }
        if(ObjectUtil.isNotEmpty(bizExpCostPageParam.getItemId())) {
            queryWrapper.lambda().eq(BizExpCost::getItemId, bizExpCostPageParam.getItemId());
        }
        if(ObjectUtil.isNotEmpty(bizExpCostPageParam.getItem())) {
            queryWrapper.lambda().like(BizExpCost::getItem, bizExpCostPageParam.getItem());
        }
        if(ObjectUtil.isAllNotEmpty(bizExpCostPageParam.getSortField(), bizExpCostPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizExpCostPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizExpCostPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizExpCostPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizExpCost::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizExpCostAddParam bizExpCostAddParam) {
        BizExpCost bizExpCost = BeanUtil.toBean(bizExpCostAddParam, BizExpCost.class);
        this.save(bizExpCost);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizExpCostEditParam bizExpCostEditParam) {
        BizExpCost bizExpCost = this.queryEntity(bizExpCostEditParam.getId());
        BeanUtil.copyProperties(bizExpCostEditParam, bizExpCost);
        this.updateById(bizExpCost);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizExpCostIdParam> bizExpCostIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizExpCostIdParamList, BizExpCostIdParam::getId));
    }

    @Override
    public BizExpCost detail(BizExpCostIdParam bizExpCostIdParam) {
        return this.queryEntity(bizExpCostIdParam.getId());
    }

    @Override
    public BizExpCost queryEntity(String id) {
        BizExpCost bizExpCost = this.getById(id);
        if(ObjectUtil.isEmpty(bizExpCost)) {
            throw new CommonException("财务管理 - 费用报销|差旅费用报销 - 成本一览表不存在，id值为：{}", id);
        }
        return bizExpCost;
    }
}
