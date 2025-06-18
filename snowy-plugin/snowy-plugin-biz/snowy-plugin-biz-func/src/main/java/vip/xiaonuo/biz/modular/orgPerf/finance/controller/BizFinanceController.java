package vip.xiaonuo.biz.modular.orgPerf.finance.controller;

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
import vip.xiaonuo.biz.modular.orgPerf.finance.entity.BizFinance;
import vip.xiaonuo.biz.modular.orgPerf.finance.param.*;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.orgPerf.finance.service.BizFinanceService;
import vip.xiaonuo.common.pojo.ImportResult;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 组织经营情况-财务情况控制器
 *
 * @author pzh
 * @date  2025/06/17 11:47
 */
@Api(tags = "财务情况控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizFinanceController {

    @Resource
    private BizFinanceService bizFinanceService;

    /**
     * 获取财务情况分页
     *
     * @author pzh
     * @date  2025/06/17 11:47
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取财务情况分页")
    // @SaCheckPermission("/biz/orgPerf/finance/page")
    @GetMapping("/biz/orgPerf/finance/page")
    public CommonResult<Page<BizFinance>> page(BizFinancePageParam bizFinancePageParam) {
        return CommonResult.data(bizFinanceService.page(bizFinancePageParam));
    }

    /**
     * 添加财务情况
     *
     * @author pzh
     * @date  2025/06/17 11:47
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加财务情况")
    @CommonLog("添加财务情况")
    // @SaCheckPermission("/biz/orgPerf/finance/add")
    @PostMapping("/biz/orgPerf/finance/add")
    public CommonResult<String> add(@RequestBody @Valid BizFinanceAddParam bizFinanceAddParam) {
        bizFinanceService.add(bizFinanceAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑财务情况
     *
     * @author pzh
     * @date  2025/06/17 11:47
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑财务情况")
    @CommonLog("编辑财务情况")
    // @SaCheckPermission("/biz/orgPerf/finance/edit")
    @PostMapping("/biz/orgPerf/finance/edit")
    public CommonResult<String> edit(@RequestBody @Valid BizFinanceEditParam bizFinanceEditParam) {
        bizFinanceService.edit(bizFinanceEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除财务情况
     *
     * @author pzh
     * @date  2025/06/17 11:47
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除财务情况")
    @CommonLog("删除财务情况")
    // @SaCheckPermission("/biz/orgPerf/finance/delete")
    @PostMapping("/biz/orgPerf/finance/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BizFinanceIdParam> bizFinanceIdParamList) {
        bizFinanceService.delete(bizFinanceIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取财务情况详情
     *
     * @author pzh
     * @date  2025/06/17 11:47
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取财务情况详情")
    // @SaCheckPermission("/biz/orgPerf/finance/detail")
    @GetMapping("/biz/orgPerf/finance/detail")
    public CommonResult<BizFinance> detail(@Valid BizFinanceIdParam bizFinanceIdParam) {
        return CommonResult.data(bizFinanceService.detail(bizFinanceIdParam));
    }

    /**
     * 导入财务情况
     *
     * @author pzh
     * @date  2025/06/17 11:47
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("导入财务情况（含校验）")
    @CommonLog("导入财务情况（含校验）")
    @PostMapping("/biz/orgPerf/finance/doImport")
    public CommonResult<ImportResult> doImport(@RequestBody @Valid ImportFinanceParam importParam) {
        return CommonResult.data(bizFinanceService.doImport(importParam));
    }
}
