package vip.xiaonuo.biz.modular.orgPerf.perfAss.controller;

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
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizGoalIdParam;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.vo.BizOrgPerfAssDataVo;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.entity.BizOrgPerfAss;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.param.BizOrgPerfAssAddParam;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.param.BizOrgPerfAssEditParam;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.param.BizOrgPerfAssIdParam;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.param.BizOrgPerfAssPageParam;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.service.BizOrgPerfAssService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 子公司及生产部门绩效考核-绩效评估控制器
 *
 * @author pzh
 * @date  2025/06/25 15:01
 */
@Api(tags = "子公司及生产部门-绩效评估控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizOrgPerfAssController {

    @Resource
    private BizOrgPerfAssService bizOrgPerfAssService;

    /**
     * 获取子公司及生产部门-绩效评估分页
     *
     * @author pzh
     * @date  2025/06/25 15:01
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取子公司及生产部门-绩效评估分页")
    // @SaCheckPermission("/biz/orgPerf/orgAss/page")
    @GetMapping("/biz/orgPerf/orgAss/page")
    public CommonResult<Page<BizOrgPerfAss>> page(BizOrgPerfAssPageParam bizOrgPerfAssPageParam) {
        return CommonResult.data(bizOrgPerfAssService.page(bizOrgPerfAssPageParam));
    }

    /**
     * 添加子公司及生产部门-绩效评估
     *
     * @author pzh
     * @date  2025/06/25 15:01
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加子公司及生产部门-绩效评估")
    @CommonLog("添加子公司及生产部门-绩效评估")
    // @SaCheckPermission("/biz/orgPerf/orgAss/add")
    @PostMapping("/biz/orgPerf/orgAss/add")
    public CommonResult<String> add(@RequestBody @Valid BizOrgPerfAssAddParam bizOrgPerfAssAddParam) {
        bizOrgPerfAssService.add(bizOrgPerfAssAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑子公司及生产部门-绩效评估
     *
     * @author pzh
     * @date  2025/06/25 15:01
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑子公司及生产部门-绩效评估")
    @CommonLog("编辑子公司及生产部门-绩效评估")
    // @SaCheckPermission("/biz/orgPerf/orgAss/edit")
    @PostMapping("/biz/orgPerf/orgAss/edit")
    public CommonResult<String> edit(@RequestBody @Valid BizOrgPerfAssEditParam bizOrgPerfAssEditParam) {
        bizOrgPerfAssService.edit(bizOrgPerfAssEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除子公司及生产部门-绩效评估
     *
     * @author pzh
     * @date  2025/06/25 15:01
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除子公司及生产部门-绩效评估")
    @CommonLog("删除子公司及生产部门-绩效评估")
    // @SaCheckPermission("/biz/orgPerf/orgAss/delete")
    @PostMapping("/biz/orgPerf/orgAss/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BizOrgPerfAssIdParam> bizOrgPerfAssIdParamList) {
        bizOrgPerfAssService.delete(bizOrgPerfAssIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取子公司及生产部门-绩效评估详情
     *
     * @author pzh
     * @date  2025/06/25 15:01
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取子公司及生产部门-绩效评估详情")
    // @SaCheckPermission("/biz/orgPerf/orgAss/detail")
    @GetMapping("/biz/orgPerf/orgAss/detail")
    public CommonResult<BizOrgPerfAss> detail(@Valid BizOrgPerfAssIdParam bizOrgPerfAssIdParam) {
        return CommonResult.data(bizOrgPerfAssService.detail(bizOrgPerfAssIdParam));
    }

    /**
     * 获取绩效评估-指标数据
     *
     * @author pzh
     * @date  2025/07/02 15:35
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("获取绩效评估-指标数据")
    // @SaCheckPermission("/biz/orgPerf/orgAss/getData")
    @GetMapping("/biz/orgPerf/orgAss/getData")
    public CommonResult<BizOrgPerfAssDataVo> getData(@Valid BizGoalIdParam bizGoalIdParam) {
        return CommonResult.data(bizOrgPerfAssService.getData(bizGoalIdParam));
    }
}
