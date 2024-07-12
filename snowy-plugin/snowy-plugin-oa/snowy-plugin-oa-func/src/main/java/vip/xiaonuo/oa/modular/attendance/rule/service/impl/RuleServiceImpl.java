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
import vip.xiaonuo.oa.modular.attendance.rule.entity.OverWork;
import vip.xiaonuo.oa.modular.attendance.rule.entity.OverWorkRule;
import vip.xiaonuo.oa.modular.attendance.rule.entity.Rule;
import vip.xiaonuo.oa.modular.attendance.rule.mapper.RuleMapper;
import vip.xiaonuo.oa.modular.attendance.rule.param.RuleAddParam;
import vip.xiaonuo.oa.modular.attendance.rule.param.RuleEditParam;
import vip.xiaonuo.oa.modular.attendance.rule.param.RuleIdParam;
import vip.xiaonuo.oa.modular.attendance.rule.param.RulePageParam;
import vip.xiaonuo.oa.modular.attendance.rule.service.RuleService;

import java.util.List;

/**
 * 考勤规则配置表Service接口实现类
 *
 * @author mayike
 * @date  2024/07/10 16:29
 **/
@Service
public class RuleServiceImpl extends ServiceImpl<RuleMapper, Rule> implements RuleService {

    @Override
    public Page<Rule> page(RulePageParam rulePageParam) {
        QueryWrapper<Rule> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(rulePageParam.getSortField(), rulePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(rulePageParam.getSortOrder());
            queryWrapper.orderBy(true, rulePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(rulePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(Rule::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(RuleAddParam ruleAddParam) {
        Rule rule = BeanUtil.toBean(ruleAddParam, Rule.class);
        this.save(rule);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(RuleEditParam ruleEditParam) {
        Rule rule = this.queryEntity(ruleEditParam.getId());
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
    public Rule detail(RuleIdParam ruleIdParam) {
        return this.queryEntity(ruleIdParam.getId());
    }

    @Override
    public Rule queryEntity(String id) {
        Rule rule = this.getById(id);
        if(ObjectUtil.isEmpty(rule)) {
            throw new CommonException("考勤规则配置表不存在，id值为：{}", id);
        }
        return rule;
    }
}
