package vip.xiaonuo.oa.modular.attendance.rule.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
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
import vip.xiaonuo.oa.modular.attendance.rule.entity.Adjustment;
import vip.xiaonuo.oa.modular.attendance.rule.entity.OverWork;
import vip.xiaonuo.oa.modular.attendance.rule.param.*;
import vip.xiaonuo.oa.modular.attendance.rule.service.AdjustmentService;
import vip.xiaonuo.oa.modular.attendance.rule.service.OverWorkService;
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

    @Resource
    private OverWorkService overWorkService;

    @Resource
    private AdjustmentService adjustmentService;


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
    public CommonResult<Page<OverWork>> overWorkPage(RulePageParam rulePageParam) {
        return CommonResult.data(overWorkService.page(rulePageParam));
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
        overWorkService.add(ruleAddParam);
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
        overWorkService.edit(ruleEditParam);
        return CommonResult.ok();
    }

    /**
     * 获取考勤规则配置表详情
     *
     * @author mayike
     * @date  2024/07/10 16:29
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("获取考勤规则配置表详情")
//    @SaCheckPermission("/oa/rule/overWork/detail")
    @GetMapping("/oa/rule/overWork/detail")
    public CommonResult<OverWork> overWorkDetail(@Valid RuleIdParam ruleIdParam) {
        return CommonResult.data(overWorkService.detail(ruleIdParam));
    }

    @ApiOperationSupport(order = 5)
    @ApiOperation("获取加班配置列表")
//    @SaCheckPermission("/oa/rule/OverWorkRule/Page")
    @GetMapping("/oa/rule/overWork/configList")
    public CommonResult<List<Map<String, String>>> configList() {
        return CommonResult.data(overWorkService.configList());
    }


    /**
     * 获取考勤规则配置表分页
     *
     * @author mayike
     * @date  2024/07/10 16:29
     */
    @ApiOperationSupport(order = 11)
    @ApiOperation("分页获取补班规则")
//    @SaCheckPermission("/oa/rule/OverWorkRule/Page")
    @GetMapping("/oa/rule/adjustment/Page")
    public CommonResult<Page<Adjustment>> adjustmentPage(RulePageParam rulePageParam) {
        return CommonResult.data(adjustmentService.page(rulePageParam));
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
        adjustmentService.add(ruleAddParam);
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
        adjustmentService.edit(ruleEditParam);
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
    public CommonResult<Adjustment> adjustmentDetail(@Valid RuleIdParam ruleIdParam) {
        return CommonResult.data(adjustmentService.detail(ruleIdParam));
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
