package vip.xiaonuo.biz.modular.orgPerf.business.controller;

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
import vip.xiaonuo.biz.modular.orgPerf.business.param.*;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.orgPerf.business.entity.BizBusiness;
import vip.xiaonuo.biz.modular.orgPerf.business.service.BizBusinessService;
import vip.xiaonuo.common.pojo.ImportResult;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 业务情况控制器
 *
 * @author pzh
 * @date  2025/06/17 14:48
 */
@Api(tags = "业务情况控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizBusinessController {

    @Resource
    private BizBusinessService bizBusinessService;

    /**
     * 获取业务情况分页
     *
     * @author pzh
     * @date  2025/06/17 14:48
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取业务情况分页")
    // @SaCheckPermission("/biz/orgPerf/business/page")
    @GetMapping("/biz/orgPerf/business/page")
    public CommonResult<Page<BizBusiness>> page(BizBusinessPageParam bizBusinessPageParam) {
        return CommonResult.data(bizBusinessService.page(bizBusinessPageParam));
    }

    /**
     * 添加业务情况
     *
     * @author pzh
     * @date  2025/06/17 14:48
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加业务情况")
    @CommonLog("添加业务情况")
    // @SaCheckPermission("/biz/orgPerf/business/add")
    @PostMapping("/biz/orgPerf/business/add")
    public CommonResult<String> add(@RequestBody @Valid BizBusinessAddParam bizBusinessAddParam) {
        bizBusinessService.add(bizBusinessAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑业务情况
     *
     * @author pzh
     * @date  2025/06/17 14:48
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑业务情况")
    @CommonLog("编辑业务情况")
    // @SaCheckPermission("/biz/orgPerf/business/edit")
    @PostMapping("/biz/orgPerf/business/edit")
    public CommonResult<String> edit(@RequestBody @Valid BizBusinessEditParam bizBusinessEditParam) {
        bizBusinessService.edit(bizBusinessEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除业务情况
     *
     * @author pzh
     * @date  2025/06/17 14:48
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除业务情况")
    @CommonLog("删除业务情况")
    // @SaCheckPermission("/biz/orgPerf/business/delete")
    @PostMapping("/biz/orgPerf/business/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BizBusinessIdParam> bizBusinessIdParamList) {
        bizBusinessService.delete(bizBusinessIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取业务情况详情
     *
     * @author pzh
     * @date  2025/06/17 14:48
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取业务情况详情")
    // @SaCheckPermission("/biz/orgPerf/business/detail")
    @GetMapping("/biz/orgPerf/business/detail")
    public CommonResult<BizBusiness> detail(@Valid BizBusinessIdParam bizBusinessIdParam) {
        return CommonResult.data(bizBusinessService.detail(bizBusinessIdParam));
    }

    /**
     * 导入业务情况
     *
     * @author pzh
     * @date  2025/06/17 14:48
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("导入业务情况（含校验）")
    @CommonLog("导入业务情况（含校验）")
    @PostMapping("/biz/orgPerf/business/doImport")
    public CommonResult<ImportResult> doImport(@RequestBody @Valid ImportBusinessParam importParam) {
        return CommonResult.data(bizBusinessService.doImport(importParam));
    }
}
