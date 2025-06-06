package vip.xiaonuo.biz.modular.prj.fin.controller;

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
import vip.xiaonuo.biz.modular.prj.fin.param.*;
import vip.xiaonuo.biz.modular.prj.fin.vo.ImportResult;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.prj.fin.entity.FinDataInvoice;
import vip.xiaonuo.biz.modular.prj.fin.service.FinDataInvoiceService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 财务数据汇总台账 - 销售发票控制器
 *
 * @author yy
 * @date  2025/06/05 17:24
 */
@Api(tags = "财务数据汇总台账 - 销售发票控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class FinDataInvoiceController {

    @Resource
    private FinDataInvoiceService finDataInvoiceService;

    /**
     * 获取财务数据汇总台账 - 销售发票分页
     *
     * @author yy
     * @date  2025/06/05 17:24
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取财务数据汇总台账 - 销售发票分页")
//    @SaCheckPermission("/biz/prj/fin/invoice/page")
    @GetMapping("/biz/prj/fin/invoice/page")
    public CommonResult<Page<FinDataInvoice>> page(FinDataInvoicePageParam finDataInvoicePageParam) {
        return CommonResult.data(finDataInvoiceService.page(finDataInvoicePageParam));
    }

    /**
     * 添加财务数据汇总台账 - 销售发票
     *
     * @author yy
     * @date  2025/06/05 17:24
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加财务数据汇总台账 - 销售发票")
    @CommonLog("添加财务数据汇总台账 - 销售发票")
//    @SaCheckPermission("/biz/prj/fin/invoice/add")
    @PostMapping("/biz/prj/fin/invoice/add")
    public CommonResult<String> add(@RequestBody @Valid FinDataInvoiceAddParam finDataInvoiceAddParam) {
        finDataInvoiceService.add(finDataInvoiceAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑财务数据汇总台账 - 销售发票
     *
     * @author yy
     * @date  2025/06/05 17:24
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑财务数据汇总台账 - 销售发票")
    @CommonLog("编辑财务数据汇总台账 - 销售发票")
//    @SaCheckPermission("/biz/prj/fin/invoice/edit")
    @PostMapping("/biz/prj/fin/invoice/edit")
    public CommonResult<String> edit(@RequestBody @Valid FinDataInvoiceEditParam finDataInvoiceEditParam) {
        finDataInvoiceService.edit(finDataInvoiceEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除财务数据汇总台账 - 销售发票
     *
     * @author yy
     * @date  2025/06/05 17:24
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除财务数据汇总台账 - 销售发票")
    @CommonLog("删除财务数据汇总台账 - 销售发票")
//    @SaCheckPermission("/biz/prj/fin/invoice/delete")
    @PostMapping("/biz/prj/fin/invoice/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<FinDataInvoiceIdParam> finDataInvoiceIdParamList) {
        finDataInvoiceService.delete(finDataInvoiceIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取财务数据汇总台账 - 销售发票详情
     *
     * @author yy
     * @date  2025/06/05 17:24
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取财务数据汇总台账 - 销售发票详情")
//    @SaCheckPermission("/biz/prj/fin/invoice/detail")
    @GetMapping("/biz/prj/fin/invoice/detail")
    public CommonResult<FinDataInvoice> detail(@Valid FinDataInvoiceIdParam finDataInvoiceIdParam) {
        return CommonResult.data(finDataInvoiceService.detail(finDataInvoiceIdParam));
    }


    /**
     * 导入 - 销售发票
     */
    @ApiOperationSupport(order = 10)
    @ApiOperation("导入 - 销售发票")
    @CommonLog("导入 - 销售发票")
    @PostMapping("/biz/prj/fin/invoice/doImport")
    public CommonResult<ImportResult> doImport(@RequestBody @Valid ImportParam<ImportFinDataInvoice> importParam) {
        return CommonResult.data(finDataInvoiceService.doImport(importParam));
    }
}
