package vip.xiaonuo.sys.modular.hr.creditEvaluation.controller;

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
import vip.xiaonuo.sys.modular.hr.creditEvaluation.entity.CreditEvaluation;
import vip.xiaonuo.sys.modular.hr.creditEvaluation.param.CreditEvaluationAddParam;
import vip.xiaonuo.sys.modular.hr.creditEvaluation.param.CreditEvaluationEditParam;
import vip.xiaonuo.sys.modular.hr.creditEvaluation.param.CreditEvaluationIdParam;
import vip.xiaonuo.sys.modular.hr.creditEvaluation.param.CreditEvaluationPageParam;
import vip.xiaonuo.sys.modular.hr.creditEvaluation.service.CreditEvaluationService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 信用评价控制器
 *
 * @author pzh
 * @date  2024/10/18 10:49
 */
@Api(tags = "信用评价控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class CreditEvaluationController {

    @Resource
    private CreditEvaluationService creditEvaluationService;

    /**
     * 获取信用评价分页
     *
     * @author pzh
     * @date  2024/10/18 10:49
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取信用评价分页")
    // @SaCheckPermission("/sys/creditEvaluation/page")
    @GetMapping("/sys/creditEvaluation/page")
    public CommonResult<Page<CreditEvaluation>> page(CreditEvaluationPageParam creditEvaluationPageParam) {
        return CommonResult.data(creditEvaluationService.page(creditEvaluationPageParam));
    }

    /**
     * 添加信用评价
     *
     * @author pzh
     * @date  2024/10/18 10:49
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加信用评价")
    @CommonLog("添加信用评价")
    // @SaCheckPermission("/sys/creditEvaluation/add")
    @PostMapping("/sys/creditEvaluation/add")
    public CommonResult<String> add(@RequestBody @Valid CreditEvaluationAddParam creditEvaluationAddParam) {
        creditEvaluationService.add(creditEvaluationAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑信用评价
     *
     * @author pzh
     * @date  2024/10/18 10:49
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑信用评价")
    @CommonLog("编辑信用评价")
    // @SaCheckPermission("/sys/creditEvaluation/edit")
    @PostMapping("/sys/creditEvaluation/edit")
    public CommonResult<String> edit(@RequestBody @Valid CreditEvaluationEditParam creditEvaluationEditParam) {
        creditEvaluationService.edit(creditEvaluationEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除信用评价
     *
     * @author pzh
     * @date  2024/10/18 10:49
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除信用评价")
    @CommonLog("删除信用评价")
    // @SaCheckPermission("/sys/creditEvaluation/delete")
    @PostMapping("/sys/creditEvaluation/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                       CommonValidList<CreditEvaluationIdParam> creditEvaluationIdParamList) {
        creditEvaluationService.delete(creditEvaluationIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取信用评价详情
     *
     * @author pzh
     * @date  2024/10/18 10:49
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取信用评价详情")
    // @SaCheckPermission("/sys/creditEvaluation/detail")
    @GetMapping("/sys/creditEvaluation/detail")
    public CommonResult<CreditEvaluation> detail(@Valid CreditEvaluationIdParam creditEvaluationIdParam) {
        return CommonResult.data(creditEvaluationService.detail(creditEvaluationIdParam));
    }
}
