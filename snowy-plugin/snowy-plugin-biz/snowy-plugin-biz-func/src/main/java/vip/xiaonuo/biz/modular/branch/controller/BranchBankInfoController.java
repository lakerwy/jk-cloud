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
import vip.xiaonuo.biz.modular.branch.entity.BranchBankInfo;
import vip.xiaonuo.biz.modular.branch.param.BranchBankInfoAddParam;
import vip.xiaonuo.biz.modular.branch.param.BranchBankInfoEditParam;
import vip.xiaonuo.biz.modular.branch.param.BranchBankInfoIdParam;
import vip.xiaonuo.biz.modular.branch.param.BranchBankInfoPageParam;
import vip.xiaonuo.biz.modular.branch.service.BranchBankInfoService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 分公司银行账户信息控制器
 *
 * @author pzh
 * @date  2024/08/05 11:46
 */
@Api(tags = "分公司银行账户信息控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BranchBankInfoController {

    @Resource
    private BranchBankInfoService branchBankInfoService;

    /**
     * 获取分公司银行账户信息分页
     *
     * @author pzh
     * @date  2024/08/05 11:46
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取分公司银行账户信息分页")
   //  @SaCheckPermission("/biz/branch/bank/page")
    @GetMapping("/biz/branch/bank/page")
    public CommonResult<Page<BranchBankInfo>> page(BranchBankInfoPageParam branchBankInfoPageParam) {
        return CommonResult.data(branchBankInfoService.page(branchBankInfoPageParam));
    }

    /**
     * 添加分公司银行账户信息
     *
     * @author pzh
     * @date  2024/08/05 11:46
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加分公司银行账户信息")
    @CommonLog("添加分公司银行账户信息")
   //  @SaCheckPermission("/biz/branch/bank/add")
    @PostMapping("/biz/branch/bank/add")
    public CommonResult<String> add(@RequestBody @Valid BranchBankInfoAddParam branchBankInfoAddParam) {
        branchBankInfoService.add(branchBankInfoAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑分公司银行账户信息
     *
     * @author pzh
     * @date  2024/08/05 11:46
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑分公司银行账户信息")
    @CommonLog("编辑分公司银行账户信息")
   //  @SaCheckPermission("/biz/branch/bank/edit")
    @PostMapping("/biz/branch/bank/edit")
    public CommonResult<String> edit(@RequestBody @Valid BranchBankInfoEditParam branchBankInfoEditParam) {
        branchBankInfoService.edit(branchBankInfoEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除分公司银行账户信息
     *
     * @author pzh
     * @date  2024/08/05 11:46
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除分公司银行账户信息")
    @CommonLog("删除分公司银行账户信息")
   //  @SaCheckPermission("/biz/branch/bank/delete")
    @PostMapping("/biz/branch/bank/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BranchBankInfoIdParam> branchBankInfoIdParamList) {
        branchBankInfoService.delete(branchBankInfoIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取分公司银行账户信息详情
     *
     * @author pzh
     * @date  2024/08/05 11:46
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取分公司银行账户信息详情")
   //  @SaCheckPermission("/biz/branch/bank/detail")
    @GetMapping("/biz/branch/bank/detail")
    public CommonResult<BranchBankInfo> detail(@Valid BranchBankInfoIdParam branchBankInfoIdParam) {
        return CommonResult.data(branchBankInfoService.detail(branchBankInfoIdParam));
    }
}
