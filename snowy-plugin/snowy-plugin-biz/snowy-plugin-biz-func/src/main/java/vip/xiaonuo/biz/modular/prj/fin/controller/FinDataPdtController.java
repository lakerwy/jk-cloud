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
import vip.xiaonuo.biz.modular.prj.fin.vo.FinDataPdtPageTotal;
import vip.xiaonuo.biz.modular.prj.fin.vo.FinDataResult;
import vip.xiaonuo.biz.modular.prj.fin.vo.ImportResult;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.prj.fin.entity.FinDataPdt;
import vip.xiaonuo.biz.modular.prj.fin.service.FinDataPdtService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 财务数据汇总台账 - 生产院控制器
 *
 * @author yy
 * @date  2025/06/05 17:26
 */
@Api(tags = "财务数据汇总台账 - 生产院控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class FinDataPdtController {

    @Resource
    private FinDataPdtService finDataPdtService;

    /**
     * 获取财务数据汇总台账 - 生产院分页
     *
     * @author yy
     * @date  2025/06/05 17:26
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取财务数据汇总台账 - 生产院分页")
//    @SaCheckPermission("/biz/prj/fin/pdt/page")
    @GetMapping("/biz/prj/fin/pdt/page")
    public CommonResult<Page<FinDataPdt>> page(FinDataPdtPageParam finDataPdtPageParam) {
        return CommonResult.data(finDataPdtService.page(finDataPdtPageParam));
    }

    /**
     * 添加财务数据汇总台账 - 生产院
     *
     * @author yy
     * @date  2025/06/05 17:26
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加财务数据汇总台账 - 生产院")
    @CommonLog("添加财务数据汇总台账 - 生产院")
//    @SaCheckPermission("/biz/prj/fin/pdt/add")
    @PostMapping("/biz/prj/fin/pdt/add")
    public CommonResult<String> add(@RequestBody @Valid FinDataPdtAddParam finDataPdtAddParam) {
        finDataPdtService.add(finDataPdtAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑财务数据汇总台账 - 生产院
     *
     * @author yy
     * @date  2025/06/05 17:26
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑财务数据汇总台账 - 生产院")
    @CommonLog("编辑财务数据汇总台账 - 生产院")
//    @SaCheckPermission("/biz/prj/fin/pdt/edit")
    @PostMapping("/biz/prj/fin/pdt/edit")
    public CommonResult<String> edit(@RequestBody @Valid FinDataPdtEditParam finDataPdtEditParam) {
        finDataPdtService.edit(finDataPdtEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除财务数据汇总台账 - 生产院
     *
     * @author yy
     * @date  2025/06/05 17:26
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除财务数据汇总台账 - 生产院")
    @CommonLog("删除财务数据汇总台账 - 生产院")
//    @SaCheckPermission("/biz/prj/fin/pdt/delete")
    @PostMapping("/biz/prj/fin/pdt/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<FinDataPdtIdParam> finDataPdtIdParamList) {
        finDataPdtService.delete(finDataPdtIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取财务数据汇总台账 - 生产院详情
     *
     * @author yy
     * @date  2025/06/05 17:26
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取财务数据汇总台账 - 生产院详情")
//    @SaCheckPermission("/biz/prj/fin/pdt/detail")
    @GetMapping("/biz/prj/fin/pdt/detail")
    public CommonResult<FinDataPdt> detail(@Valid FinDataPdtIdParam finDataPdtIdParam) {
        return CommonResult.data(finDataPdtService.detail(finDataPdtIdParam));
    }



    /**
     * 导入 - 生产院
     */
    @ApiOperationSupport(order = 10)
    @ApiOperation("导入 - 生产院")
    @CommonLog("导入 - 生产院")
    @PostMapping("/biz/prj/fin/pdt/doImport")
    public CommonResult<ImportResult> doImport(@RequestBody @Valid ImportParam<ImportFinDataPdt> importParam) {
        return CommonResult.data(finDataPdtService.doImport(importParam));
    }


    /**
     * 获取财务数据汇总台账 - 生产院 - 列表统计（所有满足条件的数据统计）
     */
    @ApiOperationSupport(order = 66)
    @ApiOperation("获取财务数据汇总台账 - 生产院 - 列表统计（所有满足条件的数据统计）")
    @GetMapping("/biz/prj/fin/pdt/pageTotal")
    public CommonResult<FinDataPdtPageTotal> pageTotal(FinDataPdtPageParam finDataPdtPageParam) {
        return CommonResult.data(finDataPdtService.pageTotal(finDataPdtPageParam));
    }





    /**
     * 项目收支明细表
     */
    @ApiOperationSupport(order = 100)
    @ApiOperation("项目收支明细表")
    @GetMapping("/biz/prj/fin/pageList")
    public CommonResult<Page<FinDataResult>> pageList(FinDataPageParam param) {
        return CommonResult.data(finDataPdtService.pageList(param));
    }

}
