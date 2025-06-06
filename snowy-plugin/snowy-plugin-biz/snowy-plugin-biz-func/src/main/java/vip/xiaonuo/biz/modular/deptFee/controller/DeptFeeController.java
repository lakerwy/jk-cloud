package vip.xiaonuo.biz.modular.deptFee.controller;

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
import vip.xiaonuo.biz.modular.deptFee.param.*;
import vip.xiaonuo.biz.modular.deptFee.service.DeptFeeService;
import vip.xiaonuo.biz.modular.deptFee.vo.ImportResult;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.deptFee.entity.DeptFee;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 部门费用管理控制器
 *
 * @author pzh
 * @date  2025/06/05 14:56
 */
@Api(tags = "部门费用管理控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class DeptFeeController {

    @Resource
    private DeptFeeService deptFeeService;

    /**
     * 获取部门费用管理分页
     *
     * @author pzh
     * @date  2025/06/05 14:56
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取部门费用管理分页")
    // @SaCheckPermission("/biz/deptFee/page")
    @GetMapping("/biz/deptFee/page")
    public CommonResult<Page<DeptFee>> page(DeptFeePageParam deptFeePageParam) {
        return CommonResult.data(deptFeeService.page(deptFeePageParam));
    }

    /**
     * 添加部门费用管理
     *
     * @author pzh
     * @date  2025/06/05 14:56
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加部门费用管理")
    @CommonLog("添加部门费用管理")
    // @SaCheckPermission("/biz/deptFee/add")
    @PostMapping("/biz/deptFee/add")
    public CommonResult<String> add(@RequestBody @Valid DeptFeeAddParam deptFeeAddParam) {
        deptFeeService.add(deptFeeAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑部门费用管理
     *
     * @author pzh
     * @date  2025/06/05 14:56
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑部门费用管理")
    @CommonLog("编辑部门费用管理")
    // @SaCheckPermission("/biz/deptFee/edit")
    @PostMapping("/biz/deptFee/edit")
    public CommonResult<String> edit(@RequestBody @Valid DeptFeeEditParam deptFeeEditParam) {
        deptFeeService.edit(deptFeeEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除部门费用管理
     *
     * @author pzh
     * @date  2025/06/05 14:56
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除部门费用管理")
    @CommonLog("删除部门费用管理")
    // @SaCheckPermission("/biz/deptFee/delete")
    @PostMapping("/biz/deptFee/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<DeptFeeIdParam> deptFeeIdParamList) {
        deptFeeService.delete(deptFeeIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取部门费用管理详情
     *
     * @author pzh
     * @date  2025/06/05 14:56
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取部门费用管理详情")
    // @SaCheckPermission("/biz/deptFee/detail")
    @GetMapping("/biz/deptFee/detail")
    public CommonResult<DeptFee> detail(@Valid DeptFeeIdParam deptFeeIdParam) {
        return CommonResult.data(deptFeeService.detail(deptFeeIdParam));
    }

    /**
     * 导入部门费用 - 校验逻辑
     *
     * @author pzh
     * @date  2025/06/05 15:40
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("导入部门费用 - 校验逻辑")
    @CommonLog("导入部门费用 - 校验逻辑")
    @PostMapping("/biz/deptFee/importCheck")
    public CommonResult<ImportResult> importCheck(@RequestBody @Valid ImportParam importParam) {
        return CommonResult.data(deptFeeService.importCheck(importParam));
    }

    /**
     * 导入部门费用 - 确认导入
     *
     * @author pzh
     * @date  2025/06/05 15:40
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("导入部门费用 - 确认导入")
    @CommonLog("导入部门费用 - 确认导入")
    @PostMapping("/biz/deptFee/doImport")
    public CommonResult<Boolean> doImport(@RequestBody @Valid ImportParam importParam) {
        return CommonResult.data(deptFeeService.doImport(importParam));
    }

}
