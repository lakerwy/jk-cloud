package vip.xiaonuo.oa.modular.attendance.rule.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.oa.modular.attendance.rule.entity.BaseRule;
import vip.xiaonuo.oa.modular.attendance.rule.entity.OverWorkRule;
import vip.xiaonuo.oa.modular.attendance.rule.entity.Rule;
import vip.xiaonuo.oa.modular.attendance.rule.enums.OverWorkEnum;
import vip.xiaonuo.oa.modular.attendance.rule.enums.RuleTypeEnum;
import vip.xiaonuo.oa.modular.attendance.rule.mapper.RuleMapper;
import vip.xiaonuo.oa.modular.attendance.rule.param.RuleAddParam;
import vip.xiaonuo.oa.modular.attendance.rule.param.RuleEditParam;
import vip.xiaonuo.oa.modular.attendance.rule.param.RuleIdParam;
import vip.xiaonuo.oa.modular.attendance.rule.param.RulePageParam;
import vip.xiaonuo.oa.modular.attendance.rule.service.RuleService;

import java.util.List;
import java.util.Map;

/**
 * 考勤规则配置表Service接口实现类
 *
 * @author mayike
 * @date  2024/07/10 16:29
 **/
@Service
public class RuleServiceImpl<T extends BaseRule> extends ServiceImpl<RuleMapper<T>, Rule<T>> implements RuleService<T> {

    @Override
    public Page<Rule<T>> page(RulePageParam rulePageParam,Class<T> clazz) {
        QueryWrapper<Rule<T>> queryWrapper = new QueryWrapper<>();
        if(rulePageParam.getRuleType()!=0){
            queryWrapper.lambda().eq(Rule::getRuleType,rulePageParam.getRuleType());
        }
        if(ObjectUtil.isAllNotEmpty(rulePageParam.getSortField(), rulePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(rulePageParam.getSortOrder());
            queryWrapper.orderBy(true, rulePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(rulePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(Rule::getId);
        }
        Page<Rule<T>> page = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        // 处理 ruleJson 字段的类型转换
        List<Rule<T>> records = page.getRecords();
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
        for (Rule<T> rule : records) {
            String ruleJsonStr = rule.getRuleJsonStr();
            if(StrUtil.isNotEmpty(ruleJsonStr)){
                try {
                    List<T> tList = objectMapper.readValue(ruleJsonStr, listType);
                    rule.setRuleJson(tList);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return page;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(RuleAddParam ruleAddParam) {
        Rule<T> rule = BeanUtil.toBean(ruleAddParam, Rule.class);
        List<T> ruleJson = ruleAddParam.getRuleJson();
        if(CollUtil.isNotEmpty(ruleJson)){
            String ruleJsonStr = JSON.toJSONString(ruleJson);
            rule.setRuleJsonStr(ruleJsonStr);
        }
        this.save(rule);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(RuleEditParam ruleEditParam) {
        Rule rule = this.queryEntity(ruleEditParam.getId());
        BeanUtil.copyProperties(ruleEditParam, rule);
        List<T> ruleJson = ruleEditParam.getRuleJson();
        if(CollUtil.isNotEmpty(ruleJson)){
            String ruleJsonStr = JSON.toJSONString(ruleJson);
            rule.setRuleJsonStr(ruleJsonStr);
        }
        this.updateById(rule);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<RuleIdParam> ruleIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(ruleIdParamList, RuleIdParam::getId));
    }

    @Override
    public Rule detail(RuleIdParam ruleIdParam,Class<T> clazz) {
        Rule rule = this.queryEntity(ruleIdParam.getId());
        String ruleJsonStr = rule.getRuleJsonStr();
        if(StrUtil.isNotEmpty(ruleJsonStr)){
            ObjectMapper objectMapper = new ObjectMapper();
            JavaType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
            try {
                List<T> tList = objectMapper.readValue(ruleJsonStr, listType);
                rule.setRuleJson(tList);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return rule;
    }

    @Override
    public Rule queryEntity(String id) {
        Rule rule = this.getById(id);
        if(ObjectUtil.isEmpty(rule)) {
            throw new CommonException("考勤规则配置表不存在，id值为：{}", id);
        }
        return rule;
    }

    @Override
    public List<Map<String, String>> overWorkConfigList() {
        return OverWorkEnum.getAllEnumValues();
    }

    @Override
    public List<Rule<T>> getOwnerRules(List<String> objectIds, int type) {
        LambdaQueryWrapper<Rule<T>> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Rule::getRuleType, RuleTypeEnum.OVER_WORD_RULE.getType())
                .and(wrapper ->
                        wrapper.isNotNull(Rule::getAttObjects)
                                .or().and(innerWrapper ->
                                        innerWrapper.apply(buildJsonContainsCondition(objectIds, type))
                                )
                );
        List<Rule<T>> list = list(queryWrapper);
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, OverWorkRule.class);
        for (Rule<T> rule : list) {
            String ruleJsonStr = rule.getRuleJsonStr();
            if(StrUtil.isNotEmpty(ruleJsonStr)){
                try {
                    List<T> tList = objectMapper.readValue(ruleJsonStr, listType);
                    rule.setRuleJson(tList);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    private String buildJsonContainsCondition(List<String> objectIds, int type) {
        StringBuilder condition = new StringBuilder();
        condition.append("(");
        for (int i = 0; i < objectIds.size(); i++) {
            condition.append("JSON_CONTAINS(ATT_OBJECTS, '{\"objectId\":\"").append(objectIds.get(i)).append("\",\"type\":").append(type).append("}', '$')");
            if (i < objectIds.size() - 1) {
                condition.append(" OR ");
            }
        }
        condition.append(")");
        return condition.toString();
    }
}
