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
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizCompanyGoalModify;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizCompanyGoalModifyAddParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizCompanyGoalModifyEditParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizCompanyGoalModifyIdParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizCompanyGoalModifyPageParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.service.BizCompanyGoalModifyService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 公司绩效考核-目标调整控制器
 *
 * @author pzh
 * @date  2025/06/18 14:24
 */
@Api(tags = "公司绩效考核-目标调整控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizCompanyGoalModifyController {

    @Resource
    private BizCompanyGoalModifyService bizCompanyGoalModifyService;

    /**
     * 获取公司绩效考核-目标调整分页
     *
     * @author pzh
     * @date  2025/06/18 14:24
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取公司绩效考核-目标调整分页")
    // @SaCheckPermission("/biz/orgPerf/companyGoal/modify/page")
    @GetMapping("/biz/orgPerf/companyGoal/modify/page")
    public CommonResult<Page<BizCompanyGoalModify>> page(BizCompanyGoalModifyPageParam bizCompanyGoalModifyPageParam) {
        return CommonResult.data(bizCompanyGoalModifyService.page(bizCompanyGoalModifyPageParam));
    }

    /**
     * 添加公司绩效考核-目标调整
     *
     * @author pzh
     * @date  2025/06/18 14:24
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加公司绩效考核-目标调整")
    @CommonLog("添加公司绩效考核-目标调整")
    // @SaCheckPermission("/biz/orgPerf/companyGoal/modify/add")
    @PostMapping("/biz/orgPerf/companyGoal/modify/add")
    public CommonResult<String> add(@RequestBody @Valid BizCompanyGoalModifyAddParam bizCompanyGoalModifyAddParam) {
        bizCompanyGoalModifyService.add(bizCompanyGoalModifyAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑公司绩效考核-目标调整
     *
     * @author pzh
     * @date  2025/06/18 14:24
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑公司绩效考核-目标调整")
    @CommonLog("编辑公司绩效考核-目标调整")
    // @SaCheckPermission("/biz/orgPerf/companyGoal/modify/edit")
    @PostMapping("/biz/orgPerf/companyGoal/modify/edit")
    public CommonResult<String> edit(@RequestBody @Valid BizCompanyGoalModifyEditParam bizCompanyGoalModifyEditParam) {
        bizCompanyGoalModifyService.edit(bizCompanyGoalModifyEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除公司绩效考核-目标调整
     *
     * @author pzh
     * @date  2025/06/18 14:24
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除公司绩效考核-目标调整")
    @CommonLog("删除公司绩效考核-目标调整")
    // @SaCheckPermission("/biz/orgPerf/companyGoal/modify/delete")
    @PostMapping("/biz/orgPerf/companyGoal/modify/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BizCompanyGoalModifyIdParam> bizCompanyGoalModifyIdParamList) {
        bizCompanyGoalModifyService.delete(bizCompanyGoalModifyIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取公司绩效考核-目标调整详情
     *
     * @author pzh
     * @date  2025/06/18 14:24
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取公司绩效考核-目标调整详情")
    // @SaCheckPermission("/biz/orgPerf/companyGoal/modify/detail")
    @GetMapping("/biz/orgPerf/companyGoal/modify/detail")
    public CommonResult<BizCompanyGoalModify> detail(@Valid BizCompanyGoalModifyIdParam bizCompanyGoalModifyIdParam) {
        return CommonResult.data(bizCompanyGoalModifyService.detail(bizCompanyGoalModifyIdParam));
    }
}
