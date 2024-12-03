package vip.xiaonuo.sys.modular.hr.financialAuditReport.controller;

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
import vip.xiaonuo.sys.modular.hr.financialAuditReport.entity.FinancialAuditReport;
import vip.xiaonuo.sys.modular.hr.financialAuditReport.param.FinancialAuditReportAddParam;
import vip.xiaonuo.sys.modular.hr.financialAuditReport.param.FinancialAuditReportEditParam;
import vip.xiaonuo.sys.modular.hr.financialAuditReport.param.FinancialAuditReportIdParam;
import vip.xiaonuo.sys.modular.hr.financialAuditReport.param.FinancialAuditReportPageParam;
import vip.xiaonuo.sys.modular.hr.financialAuditReport.service.FinancialAuditReportService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 财务审计报告控制器
 *
 * @author pzh
 * @date  2024/10/18 14:51
 */
@Api(tags = "财务审计报告控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class FinancialAuditReportController {

    @Resource
    private FinancialAuditReportService financialAuditReportService;

    /**
     * 获取财务审计报告分页
     *
     * @author pzh
     * @date  2024/10/18 14:51
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取财务审计报告分页")
    // @SaCheckPermission("/sys/financialAuditReport/page")
    @GetMapping("/sys/financialAuditReport/page")
    public CommonResult<Page<FinancialAuditReport>> page(FinancialAuditReportPageParam financialAuditReportPageParam) {
        return CommonResult.data(financialAuditReportService.page(financialAuditReportPageParam));
    }

    /**
     * 添加财务审计报告
     *
     * @author pzh
     * @date  2024/10/18 14:51
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加财务审计报告")
    @CommonLog("添加财务审计报告")
    // @SaCheckPermission("/sys/financialAuditReport/add")
    @PostMapping("/sys/financialAuditReport/add")
    public CommonResult<String> add(@RequestBody @Valid FinancialAuditReportAddParam financialAuditReportAddParam) {
        financialAuditReportService.add(financialAuditReportAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑财务审计报告
     *
     * @author pzh
     * @date  2024/10/18 14:51
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑财务审计报告")
    @CommonLog("编辑财务审计报告")
    // @SaCheckPermission("/sys/financialAuditReport/edit")
    @PostMapping("/sys/financialAuditReport/edit")
    public CommonResult<String> edit(@RequestBody @Valid FinancialAuditReportEditParam financialAuditReportEditParam) {
        financialAuditReportService.edit(financialAuditReportEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除财务审计报告
     *
     * @author pzh
     * @date  2024/10/18 14:51
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除财务审计报告")
    @CommonLog("删除财务审计报告")
    // @SaCheckPermission("/sys/financialAuditReport/delete")
    @PostMapping("/sys/financialAuditReport/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                       CommonValidList<FinancialAuditReportIdParam> financialAuditReportIdParamList) {
        financialAuditReportService.delete(financialAuditReportIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取财务审计报告详情
     *
     * @author pzh
     * @date  2024/10/18 14:51
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取财务审计报告详情")
    // @SaCheckPermission("/sys/financialAuditReport/detail")
    @GetMapping("/sys/financialAuditReport/detail")
    public CommonResult<FinancialAuditReport> detail(@Valid FinancialAuditReportIdParam financialAuditReportIdParam) {
        return CommonResult.data(financialAuditReportService.detail(financialAuditReportIdParam));
    }
}
