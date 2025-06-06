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
import vip.xiaonuo.biz.modular.prj.fin.entity.FinDataDbd;
import vip.xiaonuo.biz.modular.prj.fin.service.FinDataDbdService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 财务数据汇总台账 - 拨付明细控制器
 *
 * @author yy
 * @date  2025/06/05 17:23
 */
@Api(tags = "财务数据汇总台账 - 拨付明细控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class FinDataDbdController {

    @Resource
    private FinDataDbdService finDataDbdService;

    /**
     * 获取财务数据汇总台账 - 拨付明细分页
     *
     * @author yy
     * @date  2025/06/05 17:23
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取财务数据汇总台账 - 拨付明细分页")
//    @SaCheckPermission("/biz/prj/fin/dbd/page")
    @GetMapping("/biz/prj/fin/dbd/page")
    public CommonResult<Page<FinDataDbd>> page(FinDataDbdPageParam finDataDbdPageParam) {
        return CommonResult.data(finDataDbdService.page(finDataDbdPageParam));
    }

    /**
     * 添加财务数据汇总台账 - 拨付明细
     *
     * @author yy
     * @date  2025/06/05 17:23
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加财务数据汇总台账 - 拨付明细")
    @CommonLog("添加财务数据汇总台账 - 拨付明细")
//    @SaCheckPermission("/biz/prj/fin/dbd/add")
    @PostMapping("/biz/prj/fin/dbd/add")
    public CommonResult<String> add(@RequestBody @Valid FinDataDbdAddParam finDataDbdAddParam) {
        finDataDbdService.add(finDataDbdAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑财务数据汇总台账 - 拨付明细
     *
     * @author yy
     * @date  2025/06/05 17:23
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑财务数据汇总台账 - 拨付明细")
    @CommonLog("编辑财务数据汇总台账 - 拨付明细")
//    @SaCheckPermission("/biz/prj/fin/dbd/edit")
    @PostMapping("/biz/prj/fin/dbd/edit")
    public CommonResult<String> edit(@RequestBody @Valid FinDataDbdEditParam finDataDbdEditParam) {
        finDataDbdService.edit(finDataDbdEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除财务数据汇总台账 - 拨付明细
     *
     * @author yy
     * @date  2025/06/05 17:23
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除财务数据汇总台账 - 拨付明细")
    @CommonLog("删除财务数据汇总台账 - 拨付明细")
//    @SaCheckPermission("/biz/prj/fin/dbd/delete")
    @PostMapping("/biz/prj/fin/dbd/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<FinDataDbdIdParam> finDataDbdIdParamList) {
        finDataDbdService.delete(finDataDbdIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取财务数据汇总台账 - 拨付明细详情
     *
     * @author yy
     * @date  2025/06/05 17:23
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取财务数据汇总台账 - 拨付明细详情")
//    @SaCheckPermission("/biz/prj/fin/dbd/detail")
    @GetMapping("/biz/prj/fin/dbd/detail")
    public CommonResult<FinDataDbd> detail(@Valid FinDataDbdIdParam finDataDbdIdParam) {
        return CommonResult.data(finDataDbdService.detail(finDataDbdIdParam));
    }


    /**
     * 导入 - 拨付明细
     */
    @ApiOperationSupport(order = 10)
    @ApiOperation("导入 - 拨付明细")
    @CommonLog("导入 - 拨付明细")
    @PostMapping("/biz/prj/fin/dbd/doImport")
    public CommonResult<ImportResult> doImport(@RequestBody @Valid ImportParam<ImportFinDataDbd> importParam) {
        return CommonResult.data(finDataDbdService.doImport(importParam));
    }
}
