package vip.xiaonuo.sys.modular.hr.taxGradeEvaluation.controller;

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
import vip.xiaonuo.sys.modular.hr.taxGradeEvaluation.entity.TaxGradeEvaluation;
import vip.xiaonuo.sys.modular.hr.taxGradeEvaluation.param.TaxGradeEvaluationAddParam;
import vip.xiaonuo.sys.modular.hr.taxGradeEvaluation.param.TaxGradeEvaluationEditParam;
import vip.xiaonuo.sys.modular.hr.taxGradeEvaluation.param.TaxGradeEvaluationIdParam;
import vip.xiaonuo.sys.modular.hr.taxGradeEvaluation.param.TaxGradeEvaluationPageParam;
import vip.xiaonuo.sys.modular.hr.taxGradeEvaluation.service.TaxGradeEvaluationService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 纳税等级评价控制器
 *
 * @author pzh
 * @date  2024/11/27 14:45
 */
@Api(tags = "纳税等级评价控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class TaxGradeEvaluationController {

    @Resource
    private TaxGradeEvaluationService taxGradeEvaluationService;

    /**
     * 获取纳税等级评价分页
     *
     * @author pzh
     * @date  2024/11/27 14:45
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取纳税等级评价分页")
    // @SaCheckPermission("/sys/taxGradeEvaluation/page")
    @GetMapping("/sys/taxGradeEvaluation/page")
    public CommonResult<Page<TaxGradeEvaluation>> page(TaxGradeEvaluationPageParam taxGradeEvaluationPageParam) {
        return CommonResult.data(taxGradeEvaluationService.page(taxGradeEvaluationPageParam));
    }

    /**
     * 添加纳税等级评价
     *
     * @author pzh
     * @date  2024/11/27 14:45
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加纳税等级评价")
    @CommonLog("添加纳税等级评价")
    // @SaCheckPermission("/sys/taxGradeEvaluation/add")
    @PostMapping("/sys/taxGradeEvaluation/add")
    public CommonResult<String> add(@RequestBody @Valid TaxGradeEvaluationAddParam taxGradeEvaluationAddParam) {
        taxGradeEvaluationService.add(taxGradeEvaluationAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑纳税等级评价
     *
     * @author pzh
     * @date  2024/11/27 14:45
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑纳税等级评价")
    @CommonLog("编辑纳税等级评价")
    // @SaCheckPermission("/sys/taxGradeEvaluation/edit")
    @PostMapping("/sys/taxGradeEvaluation/edit")
    public CommonResult<String> edit(@RequestBody @Valid TaxGradeEvaluationEditParam taxGradeEvaluationEditParam) {
        taxGradeEvaluationService.edit(taxGradeEvaluationEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除纳税等级评价
     *
     * @author pzh
     * @date  2024/11/27 14:45
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除纳税等级评价")
    @CommonLog("删除纳税等级评价")
    // @SaCheckPermission("/sys/taxGradeEvaluation/delete")
    @PostMapping("/sys/taxGradeEvaluation/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                       CommonValidList<TaxGradeEvaluationIdParam> taxGradeEvaluationIdParamList) {
        taxGradeEvaluationService.delete(taxGradeEvaluationIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取纳税等级评价详情
     *
     * @author pzh
     * @date  2024/11/27 14:45
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取纳税等级评价详情")
    // @SaCheckPermission("/sys/taxGradeEvaluation/detail")
    @GetMapping("/sys/taxGradeEvaluation/detail")
    public CommonResult<TaxGradeEvaluation> detail(@Valid TaxGradeEvaluationIdParam taxGradeEvaluationIdParam) {
        return CommonResult.data(taxGradeEvaluationService.detail(taxGradeEvaluationIdParam));
    }
}
