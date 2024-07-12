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
import vip.xiaonuo.oa.modular.attendance.rule.entity.Rule;
import vip.xiaonuo.oa.modular.attendance.rule.enums.OverWorkEnum;
import vip.xiaonuo.oa.modular.attendance.rule.enums.RuleTypeEnum;
import vip.xiaonuo.oa.modular.attendance.rule.mapper.OverWorkMapper;
import vip.xiaonuo.oa.modular.attendance.rule.mapper.RuleMapper;
import vip.xiaonuo.oa.modular.attendance.rule.param.*;
import vip.xiaonuo.oa.modular.attendance.rule.service.OverWorkService;
import vip.xiaonuo.oa.modular.attendance.rule.service.RuleService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 考勤规则配置表Service接口实现类
 *
 * @author mayike
 * @date  2024/07/10 16:29
 **/
@Service
public class OverWorkServiceImpl extends ServiceImpl<OverWorkMapper, OverWork> implements OverWorkService {

    @Override
    public Page<OverWork> page(RulePageParam rulePageParam) {
        QueryWrapper<OverWork> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(rulePageParam.getSortField(), rulePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(rulePageParam.getSortOrder());
            queryWrapper.orderBy(true, rulePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(rulePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(OverWork::getId);
        }
        queryWrapper.lambda().eq(OverWork::getRuleType, RuleTypeEnum.OVER_WORD_RULE.getType());
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(OverWorkAddParam ruleAddParam) {
//        if(ruleAddParam.getRuleType()!=RuleTypeEnum.OVER_WORD_RULE.getType()){
//            throw new CommonException("规则类型不匹配");
//        }
        OverWork overWork = BeanUtil.toBean(ruleAddParam, OverWork.class);
        overWork.setRuleType(RuleTypeEnum.OVER_WORD_RULE.getType());
        this.save(overWork);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(OverWorkEditParam ruleEditParam) {
        if(ruleEditParam.getRuleType()!=RuleTypeEnum.OVER_WORD_RULE.getType()){
            throw new CommonException("规则类型不匹配");
        }
        OverWork rule = this.queryEntity(ruleEditParam.getId());
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
    public OverWork detail(RuleIdParam ruleIdParam) {
        return this.queryEntity(ruleIdParam.getId());
    }

    @Override
    public OverWork queryEntity(String id) {
        OverWork rule = this.getById(id);
        if(ObjectUtil.isEmpty(rule)) {
            throw new CommonException("考勤规则配置表不存在，id值为：{}", id);
        }
        return rule;
    }

    @Override
    public List<Map<String, String>> configList() {
        return OverWorkEnum.getAllEnumValues();
    }
}
