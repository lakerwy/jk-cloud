package vip.xiaonuo.biz.modular.orgPerf.product.controller;

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
import vip.xiaonuo.biz.modular.orgPerf.product.param.*;
import vip.xiaonuo.biz.modular.orgPerf.product.service.BizProductService;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.orgPerf.product.entity.BizProduct;
import vip.xiaonuo.common.pojo.ImportResult;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 组织经营情况-产值情况控制器
 *
 * @author pzh
 * @date  2025/06/17 09:03
 */
@Api(tags = "产值情况控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizProductController {

    @Resource
    private BizProductService bizProductService;

    /**
     * 获取产值情况分页
     *
     * @author pzh
     * @date  2025/06/17 09:03
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取产值情况分页")
    // @SaCheckPermission("/biz/orgPerf/product/page")
    @GetMapping("/biz/orgPerf/product/page")
    public CommonResult<Page<BizProduct>> page(BizProductPageParam bizProductPageParam) {
        return CommonResult.data(bizProductService.page(bizProductPageParam));
    }

    /**
     * 添加产值情况
     *
     * @author pzh
     * @date  2025/06/17 09:03
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加产值情况")
    @CommonLog("添加产值情况")
    // @SaCheckPermission("/biz/orgPerf/product/add")
    @PostMapping("/biz/orgPerf/product/add")
    public CommonResult<String> add(@RequestBody @Valid BizProductAddParam bizProductAddParam) {
        bizProductService.add(bizProductAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑产值情况
     *
     * @author pzh
     * @date  2025/06/17 09:03
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑产值情况")
    @CommonLog("编辑产值情况")
    // @SaCheckPermission("/biz/orgPerf/product/edit")
    @PostMapping("/biz/orgPerf/product/edit")
    public CommonResult<String> edit(@RequestBody @Valid BizProductEditParam bizProductEditParam) {
        bizProductService.edit(bizProductEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除产值情况
     *
     * @author pzh
     * @date  2025/06/17 09:03
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除产值情况")
    @CommonLog("删除产值情况")
    // @SaCheckPermission("/biz/orgPerf/product/delete")
    @PostMapping("/biz/orgPerf/product/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BizProductIdParam> bizProductIdParamList) {
        bizProductService.delete(bizProductIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取产值情况详情
     *
     * @author pzh
     * @date  2025/06/17 09:03
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取产值情况详情")
    // @SaCheckPermission("/biz/orgPerf/product/detail")
    @GetMapping("/biz/orgPerf/product/detail")
    public CommonResult<BizProduct> detail(@Valid BizProductIdParam bizProductIdParam) {
        return CommonResult.data(bizProductService.detail(bizProductIdParam));
    }

    /**
     * 导入产值情况
     *
     * @author pzh
     * @date  2025/06/17 09:03
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("导入产值情况（含校验）")
    @CommonLog("导入产值情况（含校验）")
    @PostMapping("/biz/orgPerf/product/doImport")
    public CommonResult<ImportResult> doImport(@RequestBody @Valid ImportProductParam importParam) {
        return CommonResult.data(bizProductService.doImport(importParam));
    }

}
