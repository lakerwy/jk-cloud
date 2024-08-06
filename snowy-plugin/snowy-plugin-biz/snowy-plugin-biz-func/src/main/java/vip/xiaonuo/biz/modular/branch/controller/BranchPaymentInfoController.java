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
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.branch.entity.BranchPaymentInfo;
import vip.xiaonuo.biz.modular.branch.param.BranchPaymentInfoAddParam;
import vip.xiaonuo.biz.modular.branch.param.BranchPaymentInfoEditParam;
import vip.xiaonuo.biz.modular.branch.param.BranchPaymentInfoIdParam;
import vip.xiaonuo.biz.modular.branch.param.BranchPaymentInfoPageParam;
import vip.xiaonuo.biz.modular.branch.service.BranchPaymentInfoService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 分公司日常费用拨付 - 付款信息控制器
 *
 * @author pzh
 * @date  2024/08/06 14:03
 */
@Api(tags = "分公司日常费用拨付 - 付款信息控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BranchPaymentInfoController {

    @Resource
    private BranchPaymentInfoService branchPaymentInfoService;

    /**
     * 获取分公司日常费用拨付 - 付款信息分页
     *
     * @author pzh
     * @date  2024/08/06 14:03
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取分公司日常费用拨付 - 付款信息分页")
    // @SaCheckPermission("/biz/branch/payment/page")
    @GetMapping("/biz/branch/payment/page")
    public CommonResult<Page<BranchPaymentInfo>> page(BranchPaymentInfoPageParam branchPaymentInfoPageParam) {
        return CommonResult.data(branchPaymentInfoService.page(branchPaymentInfoPageParam));
    }

    /**
     * 添加分公司日常费用拨付 - 付款信息
     *
     * @author pzh
     * @date  2024/08/06 14:03
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加分公司日常费用拨付 - 付款信息")
    @CommonLog("添加分公司日常费用拨付 - 付款信息")
    // @SaCheckPermission("/biz/branch/payment/add")
    @PostMapping("/biz/branch/payment/add")
    public CommonResult<String> add(@RequestBody @Valid BranchPaymentInfoAddParam branchPaymentInfoAddParam) {
        branchPaymentInfoService.add(branchPaymentInfoAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑分公司日常费用拨付 - 付款信息
     *
     * @author pzh
     * @date  2024/08/06 14:03
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑分公司日常费用拨付 - 付款信息")
    @CommonLog("编辑分公司日常费用拨付 - 付款信息")
    // @SaCheckPermission("/biz/branch/payment/edit")
    @PostMapping("/biz/branch/payment/edit")
    public CommonResult<String> edit(@RequestBody @Valid BranchPaymentInfoEditParam branchPaymentInfoEditParam) {
        branchPaymentInfoService.edit(branchPaymentInfoEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除分公司日常费用拨付 - 付款信息
     *
     * @author pzh
     * @date  2024/08/06 14:03
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除分公司日常费用拨付 - 付款信息")
    @CommonLog("删除分公司日常费用拨付 - 付款信息")
    // @SaCheckPermission("/biz/branch/payment/delete")
    @PostMapping("/biz/branch/payment/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BranchPaymentInfoIdParam> branchPaymentInfoIdParamList) {
        branchPaymentInfoService.delete(branchPaymentInfoIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取分公司日常费用拨付 - 付款信息详情
     *
     * @author pzh
     * @date  2024/08/06 14:03
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取分公司日常费用拨付 - 付款信息详情")
    // @SaCheckPermission("/biz/branch/payment/detail")
    @GetMapping("/biz/branch/payment/detail")
    public CommonResult<BranchPaymentInfo> detail(@Valid BranchPaymentInfoIdParam branchPaymentInfoIdParam) {
        return CommonResult.data(branchPaymentInfoService.detail(branchPaymentInfoIdParam));
    }

    /**
     * 根据分公司拨付id获取付款信息列表
     *
     * @author pzh
     * @date  2024/08/06 16:00
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("根据分公司拨付id获取付款信息列表")
    // @SaCheckPermission("/biz/branch/payment/getListByAllotmentId")
    @GetMapping("/biz/branch/payment/getListByAllotmentId")
    public CommonResult<List<BranchPaymentInfo>> getListByAllotmentId(@Valid BranchPaymentInfoIdParam branchPaymentInfoIdParam) {
        return CommonResult.data(branchPaymentInfoService.getListByAllotmentId(branchPaymentInfoIdParam));
    }

    /**
     * 批量更新分公司日常费用拨付 - 付款信息
     *
     * @author pzh
     * @date  2024/08/06 16:39
     */
    @ApiOperationSupport(order = 8)
    @ApiOperation("批量更新分公司日常费用拨付 - 付款信息")
    @CommonLog("批量更新分公司日常费用拨付 - 付款信息")
    // @SaCheckPermission("/biz/branch/payment/batchUpdate")
    @PostMapping("/biz/branch/payment/batchUpdate")
    public CommonResult<String> batchUpdate(@RequestBody @Valid List<BranchPaymentInfoEditParam> branchPaymentInfoEditParamList) {
        branchPaymentInfoService.batchUpdate(branchPaymentInfoEditParamList);
        return CommonResult.ok();
    }
}
