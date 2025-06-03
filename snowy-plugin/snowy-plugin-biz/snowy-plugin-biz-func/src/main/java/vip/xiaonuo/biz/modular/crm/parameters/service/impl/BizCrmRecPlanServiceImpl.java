package vip.xiaonuo.biz.modular.crm.parameters.service.impl;

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
import vip.xiaonuo.biz.modular.crm.parameters.entity.BizCrmRecPlan;
import vip.xiaonuo.biz.modular.crm.parameters.mapper.BizCrmRecPlanMapper;
import vip.xiaonuo.biz.modular.crm.parameters.param.BizCrmRecPlanAddParam;
import vip.xiaonuo.biz.modular.crm.parameters.param.BizCrmRecPlanEditParam;
import vip.xiaonuo.biz.modular.crm.parameters.param.BizCrmRecPlanIdParam;
import vip.xiaonuo.biz.modular.crm.parameters.param.BizCrmRecPlanPageParam;
import vip.xiaonuo.biz.modular.crm.parameters.service.BizCrmRecPlanService;

import java.util.List;

/**
 * 合同收款计划Service接口实现类
 *
 * @author pzh
 * @date  2025/05/30 09:35
 **/
@DS("slave_1")
@Service
public class BizCrmRecPlanServiceImpl extends ServiceImpl<BizCrmRecPlanMapper, BizCrmRecPlan> implements BizCrmRecPlanService {

    @Override
    public Page<BizCrmRecPlan> page(BizCrmRecPlanPageParam bizCrmRecPlanPageParam) {
        QueryWrapper<BizCrmRecPlan> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(bizCrmRecPlanPageParam.getSortField(), bizCrmRecPlanPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizCrmRecPlanPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizCrmRecPlanPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizCrmRecPlanPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizCrmRecPlan::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizCrmRecPlanAddParam bizCrmRecPlanAddParam) {
        BizCrmRecPlan bizCrmRecPlan = BeanUtil.toBean(bizCrmRecPlanAddParam, BizCrmRecPlan.class);
        this.save(bizCrmRecPlan);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizCrmRecPlanEditParam bizCrmRecPlanEditParam) {
        BizCrmRecPlan bizCrmRecPlan = this.queryEntity(bizCrmRecPlanEditParam.getId());
        BeanUtil.copyProperties(bizCrmRecPlanEditParam, bizCrmRecPlan);
        this.updateById(bizCrmRecPlan);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizCrmRecPlanIdParam> bizCrmRecPlanIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizCrmRecPlanIdParamList, BizCrmRecPlanIdParam::getId));
    }

    @Override
    public BizCrmRecPlan detail(BizCrmRecPlanIdParam bizCrmRecPlanIdParam) {
        return this.queryEntity(bizCrmRecPlanIdParam.getId());
    }

    @Override
    public BizCrmRecPlan queryEntity(String id) {
        BizCrmRecPlan bizCrmRecPlan = this.getById(id);
        if(ObjectUtil.isEmpty(bizCrmRecPlan)) {
            throw new CommonException("合同收款计划不存在，id值为：{}", id);
        }
        return bizCrmRecPlan;
    }
}
