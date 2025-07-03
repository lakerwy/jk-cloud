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
import vip.xiaonuo.biz.modular.orgPerf.perfAss.vo.BizFunDeptPerfAssDataVo;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.entity.BizFunDeptPerfAss;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.param.BizFunDeptPerfAssAddParam;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.param.BizFunDeptPerfAssEditParam;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.param.BizFunDeptPerfAssIdParam;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.param.BizFunDeptPerfAssPageParam;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.service.BizFunDeptPerfAssService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 职能部门-绩效评估控制器
 *
 * @author pzh
 * @date  2025/06/30 10:41
 */
@Api(tags = "职能部门-绩效评估控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizFunDeptPerfAssController {

    @Resource
    private BizFunDeptPerfAssService bizFunDeptPerfAssService;

    /**
     * 获取职能部门-绩效评估分页
     *
     * @author pzh
     * @date  2025/06/30 10:41
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取职能部门-绩效评估分页")
    // @SaCheckPermission("/biz/orgPerf/funAss/page")
    @GetMapping("/biz/orgPerf/funAss/page")
    public CommonResult<Page<BizFunDeptPerfAss>> page(BizFunDeptPerfAssPageParam bizFunDeptPerfAssPageParam) {
        return CommonResult.data(bizFunDeptPerfAssService.page(bizFunDeptPerfAssPageParam));
    }

    /**
     * 添加职能部门-绩效评估
     *
     * @author pzh
     * @date  2025/06/30 10:41
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加职能部门-绩效评估")
    @CommonLog("添加职能部门-绩效评估")
    // @SaCheckPermission("/biz/orgPerf/funAss/add")
    @PostMapping("/biz/orgPerf/funAss/add")
    public CommonResult<String> add(@RequestBody @Valid BizFunDeptPerfAssAddParam bizFunDeptPerfAssAddParam) {
        bizFunDeptPerfAssService.add(bizFunDeptPerfAssAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑职能部门-绩效评估
     *
     * @author pzh
     * @date  2025/06/30 10:41
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑职能部门-绩效评估")
    @CommonLog("编辑职能部门-绩效评估")
    // @SaCheckPermission("/biz/orgPerf/funAss/edit")
    @PostMapping("/biz/orgPerf/funAss/edit")
    public CommonResult<String> edit(@RequestBody @Valid BizFunDeptPerfAssEditParam bizFunDeptPerfAssEditParam) {
        bizFunDeptPerfAssService.edit(bizFunDeptPerfAssEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除职能部门-绩效评估
     *
     * @author pzh
     * @date  2025/06/30 10:41
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除职能部门-绩效评估")
    @CommonLog("删除职能部门-绩效评估")
    // @SaCheckPermission("/biz/orgPerf/funAss/delete")
    @PostMapping("/biz/orgPerf/funAss/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BizFunDeptPerfAssIdParam> bizFunDeptPerfAssIdParamList) {
        bizFunDeptPerfAssService.delete(bizFunDeptPerfAssIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取职能部门-绩效评估详情
     *
     * @author pzh
     * @date  2025/06/30 10:41
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取职能部门-绩效评估详情")
    // @SaCheckPermission("/biz/orgPerf/funAss/detail")
    @GetMapping("/biz/orgPerf/funAss/detail")
    public CommonResult<BizFunDeptPerfAss> detail(@Valid BizFunDeptPerfAssIdParam bizFunDeptPerfAssIdParam) {
        return CommonResult.data(bizFunDeptPerfAssService.detail(bizFunDeptPerfAssIdParam));
    }

    /**
     * 获取绩效评估-指标数据
     *
     * @author pzh
     * @date  2025/07/03 09:10
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("获取绩效评估-指标数据")
    // @SaCheckPermission("/biz/orgPerf/funAss/getData")
    @GetMapping("/biz/orgPerf/funAss/getData")
    public CommonResult<BizFunDeptPerfAssDataVo> getData(@Valid BizGoalIdParam bizGoalIdParam) {
        return CommonResult.data(bizFunDeptPerfAssService.getData(bizGoalIdParam));
    }
}
