package vip.xiaonuo.biz.modular.branch.controller;

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
import vip.xiaonuo.biz.modular.branch.param.*;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.branch.entity.BranchAllotmentApply;
import vip.xiaonuo.biz.modular.branch.service.BranchAllotmentApplyService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 分公司日常费用拨付 - 申请流程控制器
 *
 * @author pzh
 * @date  2024/08/06 14:03
 */
@Api(tags = "分公司日常费用拨付 - 申请流程控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BranchAllotmentApplyController {

    @Resource
    private BranchAllotmentApplyService branchAllotmentApplyService;

    /**
     * 获取分公司日常费用拨付 - 申请流程分页
     *
     * @author pzh
     * @date  2024/08/06 14:03
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取分公司日常费用拨付 - 申请流程分页")
    // @SaCheckPermission("/biz/branch/allotment/page")
    @GetMapping("/biz/branch/allotment/page")
    public CommonResult<Page<BranchAllotmentApply>> page(BranchAllotmentApplyPageParam branchAllotmentApplyPageParam) {
        return CommonResult.data(branchAllotmentApplyService.page(branchAllotmentApplyPageParam));
    }

    /**
     * 添加分公司日常费用拨付 - 申请流程
     *
     * @author pzh
     * @date  2024/08/06 14:03
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加分公司日常费用拨付 - 申请流程")
    @CommonLog("添加分公司日常费用拨付 - 申请流程")
    // @SaCheckPermission("/biz/branch/allotment/add")
    @PostMapping("/biz/branch/allotment/add")
    public CommonResult<String> add(@RequestBody @Valid BranchAllotmentApplyAddParam branchAllotmentApplyAddParam) {
        branchAllotmentApplyService.add(branchAllotmentApplyAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑分公司日常费用拨付 - 申请流程
     *
     * @author pzh
     * @date  2024/08/06 14:03
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑分公司日常费用拨付 - 申请流程")
    @CommonLog("编辑分公司日常费用拨付 - 申请流程")
    // @SaCheckPermission("/biz/branch/allotment/edit")
    @PostMapping("/biz/branch/allotment/edit")
    public CommonResult<String> edit(@RequestBody @Valid BranchAllotmentApplyEditParam branchAllotmentApplyEditParam) {
        branchAllotmentApplyService.edit(branchAllotmentApplyEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除分公司日常费用拨付 - 申请流程
     *
     * @author pzh
     * @date  2024/08/06 14:03
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除分公司日常费用拨付 - 申请流程")
    @CommonLog("删除分公司日常费用拨付 - 申请流程")
    // @SaCheckPermission("/biz/branch/allotment/delete")
    @PostMapping("/biz/branch/allotment/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BranchAllotmentApplyIdParam> branchAllotmentApplyIdParamList) {
        branchAllotmentApplyService.delete(branchAllotmentApplyIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取分公司日常费用拨付 - 申请流程详情
     *
     * @author pzh
     * @date  2024/08/06 14:03
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取分公司日常费用拨付 - 申请流程详情")
    // @SaCheckPermission("/biz/branch/allotment/detail")
    @GetMapping("/biz/branch/allotment/detail")
    public CommonResult<BranchAllotmentApply> detail(@Valid BranchAllotmentApplyIdParam branchAllotmentApplyIdParam) {
        return CommonResult.data(branchAllotmentApplyService.detail(branchAllotmentApplyIdParam));
    }

    /**
     * 编辑分公司日常费用拨付 - 申请流程
     *
     * @author pzh
     * @date  2024/08/06 14:03
     */
/*    @ApiOperationSupport(order = 6)
    @ApiOperation("更新出纳登记账户余额")
    @CommonLog("更新出纳登记账户余额")
    // @SaCheckPermission("/biz/branch/allotment/edit")
    @PostMapping("/biz/branch/allotment/updateRealBalance")
    public CommonResult<String> updateRealBalance(@RequestBody @Valid BranchAllotmentApplyRealBalanceEditParam editParam) {
        branchAllotmentApplyService.updateRealBalance(editParam);
        return CommonResult.ok();
    }*/
}
