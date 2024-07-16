package vip.xiaonuo.oa.modular.attendance.rule.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.oa.modular.attendance.rule.entity.*;
import vip.xiaonuo.oa.modular.attendance.rule.enums.RuleTypeEnum;
import vip.xiaonuo.oa.modular.attendance.rule.param.*;
import vip.xiaonuo.oa.modular.attendance.rule.service.RuleService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

/**
 * 考勤规则配置表控制器
 *
 * @author mayike
 * @date  2024/07/10 16:29
 */
@Api(tags = "考勤规则配置表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class RuleController {

    @Resource
    private RuleService ruleService;

    /**
     * 获取考勤规则配置表分页
     *
     * @author mayike
     * @date  2024/07/10 16:29
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("分页获取加班规则表")
//    @SaCheckPermission("/oa/rule/OverWorkRule/Page")
    @GetMapping("/oa/rule/overWork/Page")
    public CommonResult<Page<Rule<OverWorkRule>>> overWorkPage(RulePageParam rulePageParam) {
        rulePageParam.setRuleType(RuleTypeEnum.OVER_WORD_RULE.getType());
        return CommonResult.data(ruleService.page(rulePageParam, OverWorkRule.class));
    }

    /**
     * 添加考勤规则配置表
     *
     * @author mayike
     * @date  2024/07/10 16:29
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加加班考勤规则")
    @CommonLog("添加加班考勤规则")
//    @SaCheckPermission("/oa/rule/add")
    @PostMapping("/oa/rule/overWork/add")
    public CommonResult<String> overWorkAdd(@RequestBody @Valid OverWorkAddParam ruleAddParam) {
        ruleService.add(ruleAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑考勤规则配置表
     *
     * @author mayike
     * @date  2024/07/10 16:29
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑加班考勤规则")
    @CommonLog("编辑加班考勤规则")
//    @SaCheckPermission("/oa/rule/edit")
    @PostMapping("/oa/rule/overWork/edit")
    public CommonResult<String> overWorkEdit(@RequestBody @Valid OverWorkEditParam ruleEditParam) {
        ruleService.edit(ruleEditParam);
        return CommonResult.ok();
    }

    /**
     * 获取考勤规则配置表详情
     *
     * @author mayike
     * @date  2024/07/10 16:29
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("获取加班考勤规则配置详情")
//    @SaCheckPermission("/oa/rule/overWork/detail")
    @GetMapping("/oa/rule/overWork/detail")
    public CommonResult<Rule> overWorkDetail(@Valid RuleIdParam ruleIdParam) {
        return CommonResult.data(ruleService.detail(ruleIdParam,OverWorkRule.class));
    }

    @ApiOperationSupport(order = 5)
    @ApiOperation("获取加班配置列表")
//    @SaCheckPermission("/oa/rule/OverWorkRule/Page")
    @GetMapping("/oa/rule/overWork/configList")
    public CommonResult<List<Map<String, String>>> configList() {
        return CommonResult.data(ruleService.overWorkConfigList());
    }


    /**
     * 获取补班规则配置表分页
     *
     * @author mayike
     * @date  2024/07/10 16:29
     */
    @ApiOperationSupport(order = 11)
    @ApiOperation("分页获取补班规则")
//    @SaCheckPermission("/oa/rule/OverWorkRule/Page")
    @GetMapping("/oa/rule/adjustment/Page")
    public CommonResult<Page<Rule<AdjustmentRule>>> adjustmentPage(RulePageParam rulePageParam) {
        rulePageParam.setRuleType(RuleTypeEnum.ADJUSTMENT_RULE.getType());
        return CommonResult.data(ruleService.page(rulePageParam, AdjustmentRule.class));
    }

    /**
     * 添加考勤规则配置表
     *
     * @author mayike
     * @date  2024/07/10 16:29
     */
    @ApiOperationSupport(order = 12)
    @ApiOperation("添加补班考勤规则")
    @CommonLog("添加补班考勤规则")
//    @SaCheckPermission("/oa/rule/add")
    @PostMapping("/oa/rule/adjustment/add")
    public CommonResult<String> adjustmentAdd(@RequestBody @Valid AdjustmentAddParam ruleAddParam) {
        ruleService.add(ruleAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑考勤规则配置表
     *
     * @author mayike
     * @date  2024/07/10 16:29
     */
    @ApiOperationSupport(order = 13)
    @ApiOperation("编辑补班考勤规则")
    @CommonLog("编辑补班考勤规则")
//    @SaCheckPermission("/oa/rule/edit")
    @PostMapping("/oa/rule/adjustment/edit")
    public CommonResult<String> adjustmentEdit(@RequestBody @Valid AdjustmentEditParam ruleEditParam) {
        ruleService.edit(ruleEditParam);
        return CommonResult.ok();
    }

    /**
     * 获取考勤规则配置表详情
     *
     * @author mayike
     * @date  2024/07/10 16:29
     */
    @ApiOperationSupport(order = 14)
    @ApiOperation("获取补卡考勤规则配置表详情")
//    @SaCheckPermission("/oa/rule/adjustment/detail")
    @GetMapping("/oa/rule/adjustment/detail")
    public CommonResult<Rule> adjustmentDetail(@Valid RuleIdParam ruleIdParam) {
        return CommonResult.data(ruleService.detail(ruleIdParam,AdjustmentRule.class));
    }

    /**
     * 删除考勤规则配置表
     *
     * @author mayike
     * @date  2024/07/10 16:29
     */
    @ApiOperationSupport(order = 35)
    @ApiOperation("删除考勤规则")
    @CommonLog("删除考勤规则")
//    @SaCheckPermission("/oa/rule/delete")
    @PostMapping("/oa/rule/delete")
    public CommonResult<String> overWorkDelete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<RuleIdParam> ruleIdParamList) {
        ruleService.delete(ruleIdParamList);
        return CommonResult.ok();
    }

}
