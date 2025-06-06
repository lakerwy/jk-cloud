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
import vip.xiaonuo.biz.modular.prj.fin.entity.FinDataManage;
import vip.xiaonuo.biz.modular.prj.fin.service.FinDataManageService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 财务数据汇总台账 - 转入管理费控制器
 *
 * @author yy
 * @date  2025/06/05 17:25
 */
@Api(tags = "财务数据汇总台账 - 转入管理费控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class FinDataManageController {

    @Resource
    private FinDataManageService finDataManageService;

    /**
     * 获取财务数据汇总台账 - 转入管理费分页
     *
     * @author yy
     * @date  2025/06/05 17:25
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取财务数据汇总台账 - 转入管理费分页")
//    @SaCheckPermission("/biz/prj/fin/manage/page")
    @GetMapping("/biz/prj/fin/manage/page")
    public CommonResult<Page<FinDataManage>> page(FinDataManagePageParam finDataManagePageParam) {
        return CommonResult.data(finDataManageService.page(finDataManagePageParam));
    }

    /**
     * 添加财务数据汇总台账 - 转入管理费
     *
     * @author yy
     * @date  2025/06/05 17:25
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加财务数据汇总台账 - 转入管理费")
    @CommonLog("添加财务数据汇总台账 - 转入管理费")
//    @SaCheckPermission("/biz/prj/fin/manage/add")
    @PostMapping("/biz/prj/fin/manage/add")
    public CommonResult<String> add(@RequestBody @Valid FinDataManageAddParam finDataManageAddParam) {
        finDataManageService.add(finDataManageAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑财务数据汇总台账 - 转入管理费
     *
     * @author yy
     * @date  2025/06/05 17:25
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑财务数据汇总台账 - 转入管理费")
    @CommonLog("编辑财务数据汇总台账 - 转入管理费")
//    @SaCheckPermission("/biz/prj/fin/manage/edit")
    @PostMapping("/biz/prj/fin/manage/edit")
    public CommonResult<String> edit(@RequestBody @Valid FinDataManageEditParam finDataManageEditParam) {
        finDataManageService.edit(finDataManageEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除财务数据汇总台账 - 转入管理费
     *
     * @author yy
     * @date  2025/06/05 17:25
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除财务数据汇总台账 - 转入管理费")
    @CommonLog("删除财务数据汇总台账 - 转入管理费")
//    @SaCheckPermission("/biz/prj/fin/manage/delete")
    @PostMapping("/biz/prj/fin/manage/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<FinDataManageIdParam> finDataManageIdParamList) {
        finDataManageService.delete(finDataManageIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取财务数据汇总台账 - 转入管理费详情
     *
     * @author yy
     * @date  2025/06/05 17:25
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取财务数据汇总台账 - 转入管理费详情")
//    @SaCheckPermission("/biz/prj/fin/manage/detail")
    @GetMapping("/biz/prj/fin/manage/detail")
    public CommonResult<FinDataManage> detail(@Valid FinDataManageIdParam finDataManageIdParam) {
        return CommonResult.data(finDataManageService.detail(finDataManageIdParam));
    }


    /**
     * 导入 - 转入管理费
     */
    @ApiOperationSupport(order = 10)
    @ApiOperation("导入 - 转入管理费")
    @CommonLog("导入 - 转入管理费")
    @PostMapping("/biz/prj/fin/manage/doImport")
    public CommonResult<ImportResult> doImport(@RequestBody @Valid ImportParam<ImportFinDataManage> importParam) {
        return CommonResult.data(finDataManageService.doImport(importParam));
    }
}
