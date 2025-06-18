package vip.xiaonuo.biz.modular.orgPerf.goal.controller;

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
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizCompanyGoal;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizCompanyGoalAddParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizCompanyGoalEditParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizCompanyGoalIdParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizCompanyGoalPageParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.service.BizCompanyGoalService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 公司绩效考核-目标登记控制器
 *
 * @author pzh
 * @date  2025/06/18 10:02
 */
@Api(tags = "公司绩效考核-目标登记控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizCompanyGoalController {

    @Resource
    private BizCompanyGoalService bizCompanyGoalService;

    /**
     * 获取公司绩效考核-目标登记分页
     *
     * @author pzh
     * @date  2025/06/18 10:02
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取公司绩效考核-目标登记分页")
    // @SaCheckPermission("/biz/orgPerf/companyGoal/page")
    @GetMapping("/biz/orgPerf/companyGoal/page")
    public CommonResult<Page<BizCompanyGoal>> page(BizCompanyGoalPageParam bizCompanyGoalPageParam) {
        return CommonResult.data(bizCompanyGoalService.page(bizCompanyGoalPageParam));
    }

    /**
     * 添加公司绩效考核-目标登记
     *
     * @author pzh
     * @date  2025/06/18 10:02
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加公司绩效考核-目标登记")
    @CommonLog("添加公司绩效考核-目标登记")
    // @SaCheckPermission("/biz/orgPerf/companyGoal/add")
    @PostMapping("/biz/orgPerf/companyGoal/add")
    public CommonResult<String> add(@RequestBody @Valid BizCompanyGoalAddParam bizCompanyGoalAddParam) {
        bizCompanyGoalService.add(bizCompanyGoalAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑公司绩效考核-目标登记
     *
     * @author pzh
     * @date  2025/06/18 10:02
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑公司绩效考核-目标登记")
    @CommonLog("编辑公司绩效考核-目标登记")
    // @SaCheckPermission("/biz/orgPerf/companyGoal/edit")
    @PostMapping("/biz/orgPerf/companyGoal/edit")
    public CommonResult<String> edit(@RequestBody @Valid BizCompanyGoalEditParam bizCompanyGoalEditParam) {
        bizCompanyGoalService.edit(bizCompanyGoalEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除公司绩效考核-目标登记
     *
     * @author pzh
     * @date  2025/06/18 10:02
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除公司绩效考核-目标登记")
    @CommonLog("删除公司绩效考核-目标登记")
    // @SaCheckPermission("/biz/orgPerf/companyGoal/delete")
    @PostMapping("/biz/orgPerf/companyGoal/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BizCompanyGoalIdParam> bizCompanyGoalIdParamList) {
        bizCompanyGoalService.delete(bizCompanyGoalIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取公司绩效考核-目标登记详情
     *
     * @author pzh
     * @date  2025/06/18 10:02
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取公司绩效考核-目标登记详情")
    // @SaCheckPermission("/biz/orgPerf/companyGoal/detail")
    @GetMapping("/biz/orgPerf/companyGoal/detail")
    public CommonResult<BizCompanyGoal> detail(@Valid BizCompanyGoalIdParam bizCompanyGoalIdParam) {
        return CommonResult.data(bizCompanyGoalService.detail(bizCompanyGoalIdParam));
    }
}
