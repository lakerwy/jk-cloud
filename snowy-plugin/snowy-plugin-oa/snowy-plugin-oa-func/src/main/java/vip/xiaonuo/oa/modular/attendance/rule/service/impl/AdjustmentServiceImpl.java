package vip.xiaonuo.oa.modular.attendance.rule.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.oa.modular.attendance.rule.entity.Adjustment;
import vip.xiaonuo.oa.modular.attendance.rule.entity.Rule;
import vip.xiaonuo.oa.modular.attendance.rule.enums.RuleTypeEnum;
import vip.xiaonuo.oa.modular.attendance.rule.mapper.AdjustmentMapper;
import vip.xiaonuo.oa.modular.attendance.rule.param.*;
import vip.xiaonuo.oa.modular.attendance.rule.service.AdjustmentService;

import java.util.List;

/**
 * 考勤规则配置表Service接口实现类
 *
 * @author mayike
 * @date  2024/07/10 16:29
 **/
@Service
public class AdjustmentServiceImpl extends ServiceImpl<AdjustmentMapper, Adjustment> implements AdjustmentService {

    @Override
    public Page<Adjustment> page(RulePageParam rulePageParam) {
        QueryWrapper<Adjustment> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(rulePageParam.getSortField(), rulePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(rulePageParam.getSortOrder());
            queryWrapper.orderBy(true, rulePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(rulePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(Rule::getId);
        }
        queryWrapper.lambda().eq(Adjustment::getRuleType, RuleTypeEnum.ADJUSTMENT_RULE.getType());
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(AdjustmentAddParam ruleAddParam) {
//        if(ruleAddParam.getRuleType()!=RuleTypeEnum.ADJUSTMENT_RULE.getType()){
//            throw new CommonException("规则类型不匹配");
//        }
        Adjustment rule = BeanUtil.toBean(ruleAddParam, Adjustment.class);
        rule.setRuleType(RuleTypeEnum.ADJUSTMENT_RULE.getType());
        this.save(rule);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(AdjustmentEditParam ruleEditParam) {
        if(ruleEditParam.getRuleType()!=RuleTypeEnum.ADJUSTMENT_RULE.getType()){
            throw new CommonException("规则类型不匹配");
        }
        Adjustment rule = this.queryEntity(ruleEditParam.getId());
        BeanUtil.copyProperties(ruleEditParam, rule);
        this.updateById(rule);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<RuleIdParam> ruleIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(ruleIdParamList, RuleIdParam::getId));
    }

    @Override
    public Adjustment detail(RuleIdParam ruleIdParam) {
        return this.queryEntity(ruleIdParam.getId());
    }

    @Override
    public Adjustment queryEntity(String id) {
        Adjustment rule = this.getById(id);
        if(ObjectUtil.isEmpty(rule)) {
            throw new CommonException("考勤规则配置表不存在，id值为：{}", id);
        }
        return rule;
    }
}
