package vip.xiaonuo.sys.modular.hr.financialSheet.controller;

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
import vip.xiaonuo.sys.modular.hr.financialSheet.entity.FinancialSheet;
import vip.xiaonuo.sys.modular.hr.financialSheet.param.FinancialSheetAddParam;
import vip.xiaonuo.sys.modular.hr.financialSheet.param.FinancialSheetEditParam;
import vip.xiaonuo.sys.modular.hr.financialSheet.param.FinancialSheetIdParam;
import vip.xiaonuo.sys.modular.hr.financialSheet.param.FinancialSheetPageParam;
import vip.xiaonuo.sys.modular.hr.financialSheet.service.FinancialSheetService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 财务状况表控制器
 *
 * @author pzh
 * @date  2024/10/18 15:53
 */
@Api(tags = "财务状况表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class FinancialSheetController {

    @Resource
    private FinancialSheetService financialSheetService;

    /**
     * 获取财务状况表分页
     *
     * @author pzh
     * @date  2024/10/18 15:53
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取财务状况表分页")
    // @SaCheckPermission("/sys/financialSheet/page")
    @GetMapping("/sys/financialSheet/page")
    public CommonResult<Page<FinancialSheet>> page(FinancialSheetPageParam financialSheetPageParam) {
        return CommonResult.data(financialSheetService.page(financialSheetPageParam));
    }

    /**
     * 添加财务状况表
     *
     * @author pzh
     * @date  2024/10/18 15:53
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加财务状况表")
    @CommonLog("添加财务状况表")
    // @SaCheckPermission("/sys/financialSheet/add")
    @PostMapping("/sys/financialSheet/add")
    public CommonResult<String> add(@RequestBody @Valid FinancialSheetAddParam financialSheetAddParam) {
        financialSheetService.add(financialSheetAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑财务状况表
     *
     * @author pzh
     * @date  2024/10/18 15:53
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑财务状况表")
    @CommonLog("编辑财务状况表")
    // @SaCheckPermission("/sys/financialSheet/edit")
    @PostMapping("/sys/financialSheet/edit")
    public CommonResult<String> edit(@RequestBody @Valid FinancialSheetEditParam financialSheetEditParam) {
        financialSheetService.edit(financialSheetEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除财务状况表
     *
     * @author pzh
     * @date  2024/10/18 15:53
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除财务状况表")
    @CommonLog("删除财务状况表")
    // @SaCheckPermission("/sys/financialSheet/delete")
    @PostMapping("/sys/financialSheet/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                       CommonValidList<FinancialSheetIdParam> financialSheetIdParamList) {
        financialSheetService.delete(financialSheetIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取财务状况表详情
     *
     * @author pzh
     * @date  2024/10/18 15:53
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取财务状况表详情")
    // @SaCheckPermission("/sys/financialSheet/detail")
    @GetMapping("/sys/financialSheet/detail")
    public CommonResult<FinancialSheet> detail(@Valid FinancialSheetIdParam financialSheetIdParam) {
        return CommonResult.data(financialSheetService.detail(financialSheetIdParam));
    }
}
