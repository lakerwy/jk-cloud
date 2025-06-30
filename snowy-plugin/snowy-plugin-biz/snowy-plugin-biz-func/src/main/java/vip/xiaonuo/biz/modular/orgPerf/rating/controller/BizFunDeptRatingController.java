package vip.xiaonuo.biz.modular.orgPerf.rating.controller;

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
import vip.xiaonuo.biz.modular.orgPerf.rating.entity.BizFunDeptRating;
import vip.xiaonuo.biz.modular.orgPerf.rating.param.BizFunDeptRatingAddParam;
import vip.xiaonuo.biz.modular.orgPerf.rating.param.BizFunDeptRatingEditParam;
import vip.xiaonuo.biz.modular.orgPerf.rating.param.BizFunDeptRatingIdParam;
import vip.xiaonuo.biz.modular.orgPerf.rating.param.BizFunDeptRatingPageParam;
import vip.xiaonuo.biz.modular.orgPerf.rating.service.BizFunDeptRatingService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 职能部门-绩效评分控制器
 *
 * @author pzh
 * @date  2025/06/30 16:37
 */
@Api(tags = "职能部门-绩效评分控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizFunDeptRatingController {

    @Resource
    private BizFunDeptRatingService bizFunDeptRatingService;

    /**
     * 获取职能部门-绩效评分分页
     *
     * @author pzh
     * @date  2025/06/30 16:37
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取职能部门-绩效评分分页")
    // @SaCheckPermission("/biz/orgPerf/funDept/rating/page")
    @GetMapping("/biz/orgPerf/funDept/rating/page")
    public CommonResult<Page<BizFunDeptRating>> page(BizFunDeptRatingPageParam bizFunDeptRatingPageParam) {
        return CommonResult.data(bizFunDeptRatingService.page(bizFunDeptRatingPageParam));
    }

    /**
     * 添加职能部门-绩效评分
     *
     * @author pzh
     * @date  2025/06/30 16:37
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加职能部门-绩效评分")
    @CommonLog("添加职能部门-绩效评分")
    // @SaCheckPermission("/biz/orgPerf/funDept/rating/add")
    @PostMapping("/biz/orgPerf/funDept/rating/add")
    public CommonResult<String> add(@RequestBody @Valid BizFunDeptRatingAddParam bizFunDeptRatingAddParam) {
        bizFunDeptRatingService.add(bizFunDeptRatingAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑职能部门-绩效评分
     *
     * @author pzh
     * @date  2025/06/30 16:37
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑职能部门-绩效评分")
    @CommonLog("编辑职能部门-绩效评分")
    // @SaCheckPermission("/biz/orgPerf/funDept/rating/edit")
    @PostMapping("/biz/orgPerf/funDept/rating/edit")
    public CommonResult<String> edit(@RequestBody @Valid BizFunDeptRatingEditParam bizFunDeptRatingEditParam) {
        bizFunDeptRatingService.edit(bizFunDeptRatingEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除职能部门-绩效评分
     *
     * @author pzh
     * @date  2025/06/30 16:37
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除职能部门-绩效评分")
    @CommonLog("删除职能部门-绩效评分")
    // @SaCheckPermission("/biz/orgPerf/funDept/rating/delete")
    @PostMapping("/biz/orgPerf/funDept/rating/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BizFunDeptRatingIdParam> bizFunDeptRatingIdParamList) {
        bizFunDeptRatingService.delete(bizFunDeptRatingIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取职能部门-绩效评分详情
     *
     * @author pzh
     * @date  2025/06/30 16:37
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取职能部门-绩效评分详情")
    // @SaCheckPermission("/biz/orgPerf/funDept/rating/detail")
    @GetMapping("/biz/orgPerf/funDept/rating/detail")
    public CommonResult<BizFunDeptRating> detail(@Valid BizFunDeptRatingIdParam bizFunDeptRatingIdParam) {
        return CommonResult.data(bizFunDeptRatingService.detail(bizFunDeptRatingIdParam));
    }
}
