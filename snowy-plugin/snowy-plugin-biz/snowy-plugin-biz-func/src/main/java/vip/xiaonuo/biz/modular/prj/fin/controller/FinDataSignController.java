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
import vip.xiaonuo.biz.modular.prj.fin.vo.FinDataSignPageTotal;
import vip.xiaonuo.biz.modular.prj.fin.vo.ImportResult;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.prj.fin.entity.FinDataSign;
import vip.xiaonuo.biz.modular.prj.fin.service.FinDataSignService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 财务数据汇总台账 - 转入签字费控制器
 *
 * @author yy
 * @date  2025/06/05 17:27
 */
@Api(tags = "财务数据汇总台账 - 转入签字费控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class FinDataSignController {

    @Resource
    private FinDataSignService finDataSignService;

    /**
     * 获取财务数据汇总台账 - 转入签字费分页
     *
     * @author yy
     * @date  2025/06/05 17:27
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取财务数据汇总台账 - 转入签字费分页")
//    @SaCheckPermission("/biz/prj/fin/sign/page")
    @GetMapping("/biz/prj/fin/sign/page")
    public CommonResult<Page<FinDataSign>> page(FinDataSignPageParam finDataSignPageParam) {
        return CommonResult.data(finDataSignService.page(finDataSignPageParam));
    }

    /**
     * 添加财务数据汇总台账 - 转入签字费
     *
     * @author yy
     * @date  2025/06/05 17:27
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加财务数据汇总台账 - 转入签字费")
    @CommonLog("添加财务数据汇总台账 - 转入签字费")
//    @SaCheckPermission("/biz/prj/fin/sign/add")
    @PostMapping("/biz/prj/fin/sign/add")
    public CommonResult<String> add(@RequestBody @Valid FinDataSignAddParam finDataSignAddParam) {
        finDataSignService.add(finDataSignAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑财务数据汇总台账 - 转入签字费
     *
     * @author yy
     * @date  2025/06/05 17:27
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑财务数据汇总台账 - 转入签字费")
    @CommonLog("编辑财务数据汇总台账 - 转入签字费")
//    @SaCheckPermission("/biz/prj/fin/sign/edit")
    @PostMapping("/biz/prj/fin/sign/edit")
    public CommonResult<String> edit(@RequestBody @Valid FinDataSignEditParam finDataSignEditParam) {
        finDataSignService.edit(finDataSignEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除财务数据汇总台账 - 转入签字费
     *
     * @author yy
     * @date  2025/06/05 17:27
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除财务数据汇总台账 - 转入签字费")
    @CommonLog("删除财务数据汇总台账 - 转入签字费")
//    @SaCheckPermission("/biz/prj/fin/sign/delete")
    @PostMapping("/biz/prj/fin/sign/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<FinDataSignIdParam> finDataSignIdParamList) {
        finDataSignService.delete(finDataSignIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取财务数据汇总台账 - 转入签字费详情
     *
     * @author yy
     * @date  2025/06/05 17:27
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取财务数据汇总台账 - 转入签字费详情")
//    @SaCheckPermission("/biz/prj/fin/sign/detail")
    @GetMapping("/biz/prj/fin/sign/detail")
    public CommonResult<FinDataSign> detail(@Valid FinDataSignIdParam finDataSignIdParam) {
        return CommonResult.data(finDataSignService.detail(finDataSignIdParam));
    }


    /**
     * 导入 - 转入签字费
     */
    @ApiOperationSupport(order = 10)
    @ApiOperation("导入 - 转入签字费")
    @CommonLog("导入 - 转入签字费")
    @PostMapping("/biz/prj/fin/sign/doImport")
    public CommonResult<ImportResult> doImport(@RequestBody @Valid ImportParam<ImportFinDataSign> importParam) {
        return CommonResult.data(finDataSignService.doImport(importParam));
    }



    /**
     * 获取财务数据汇总台账 - 转入签字费 - 列表统计（所有满足条件的数据统计）
     */
    @ApiOperationSupport(order = 66)
    @ApiOperation("获取财务数据汇总台账 - 转入签字费 - 列表统计（所有满足条件的数据统计）")
    @GetMapping("/biz/prj/fin/sign/pageTotal")
    public CommonResult<FinDataSignPageTotal> pageTotal(FinDataSignPageParam finDataSignPageParam) {
        return CommonResult.data(finDataSignService.pageTotal(finDataSignPageParam));
    }
}
