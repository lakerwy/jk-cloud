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
import vip.xiaonuo.biz.modular.prj.fin.entity.FinDataPpc;
import vip.xiaonuo.biz.modular.prj.fin.service.FinDataPpcService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 财务数据汇总台账 - 项目回款控制器
 *
 * @author yy
 * @date  2025/06/05 17:26
 */
@Api(tags = "财务数据汇总台账 - 项目回款控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class FinDataPpcController {

    @Resource
    private FinDataPpcService finDataPpcService;

    /**
     * 获取财务数据汇总台账 - 项目回款分页
     *
     * @author yy
     * @date  2025/06/05 17:26
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取财务数据汇总台账 - 项目回款分页")
//    @SaCheckPermission("/biz/prj/fin/ppc/page")
    @GetMapping("/biz/prj/fin/ppc/page")
    public CommonResult<Page<FinDataPpc>> page(FinDataPpcPageParam finDataPpcPageParam) {
        return CommonResult.data(finDataPpcService.page(finDataPpcPageParam));
    }

    /**
     * 添加财务数据汇总台账 - 项目回款
     *
     * @author yy
     * @date  2025/06/05 17:26
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加财务数据汇总台账 - 项目回款")
    @CommonLog("添加财务数据汇总台账 - 项目回款")
//    @SaCheckPermission("/biz/prj/fin/ppc/add")
    @PostMapping("/biz/prj/fin/ppc/add")
    public CommonResult<String> add(@RequestBody @Valid FinDataPpcAddParam finDataPpcAddParam) {
        finDataPpcService.add(finDataPpcAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑财务数据汇总台账 - 项目回款
     *
     * @author yy
     * @date  2025/06/05 17:26
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑财务数据汇总台账 - 项目回款")
    @CommonLog("编辑财务数据汇总台账 - 项目回款")
//    @SaCheckPermission("/biz/prj/fin/ppc/edit")
    @PostMapping("/biz/prj/fin/ppc/edit")
    public CommonResult<String> edit(@RequestBody @Valid FinDataPpcEditParam finDataPpcEditParam) {
        finDataPpcService.edit(finDataPpcEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除财务数据汇总台账 - 项目回款
     *
     * @author yy
     * @date  2025/06/05 17:26
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除财务数据汇总台账 - 项目回款")
    @CommonLog("删除财务数据汇总台账 - 项目回款")
//    @SaCheckPermission("/biz/prj/fin/ppc/delete")
    @PostMapping("/biz/prj/fin/ppc/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<FinDataPpcIdParam> finDataPpcIdParamList) {
        finDataPpcService.delete(finDataPpcIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取财务数据汇总台账 - 项目回款详情
     *
     * @author yy
     * @date  2025/06/05 17:26
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取财务数据汇总台账 - 项目回款详情")
//    @SaCheckPermission("/biz/prj/fin/ppc/detail")
    @GetMapping("/biz/prj/fin/ppc/detail")
    public CommonResult<FinDataPpc> detail(@Valid FinDataPpcIdParam finDataPpcIdParam) {
        return CommonResult.data(finDataPpcService.detail(finDataPpcIdParam));
    }



    /**
     * 导入 - 项目回款
     */
    @ApiOperationSupport(order = 10)
    @ApiOperation("导入 - 项目回款")
    @CommonLog("导入 - 项目回款")
    @PostMapping("/biz/prj/fin/ppc/doImport")
    public CommonResult<ImportResult> doImport(@RequestBody @Valid ImportParam<ImportFinDataPpc> importParam) {
        return CommonResult.data(finDataPpcService.doImport(importParam));
    }
}
