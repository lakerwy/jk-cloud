package vip.xiaonuo.biz.modular.crm.prjValue.controller;

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
import vip.xiaonuo.biz.modular.crm.prjValue.entity.PrjValueAllocation;
import vip.xiaonuo.biz.modular.crm.prjValue.param.PrjValueAllocationAddParam;
import vip.xiaonuo.biz.modular.crm.prjValue.param.PrjValueAllocationEditParam;
import vip.xiaonuo.biz.modular.crm.prjValue.param.PrjValueAllocationIdParam;
import vip.xiaonuo.biz.modular.crm.prjValue.param.PrjValueAllocationPageParam;
import vip.xiaonuo.biz.modular.crm.prjValue.service.PrjValueAllocationService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 项目产值划分控制器
 *
 * @author pzh
 * @date  2025/06/04 16:35
 */
@Api(tags = "项目产值划分控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class PrjValueAllocationController {

    @Resource
    private PrjValueAllocationService prjValueAllocationService;

    /**
     * 获取项目产值划分分页
     *
     * @author pzh
     * @date  2025/06/04 16:35
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取项目产值划分分页")
    @SaCheckPermission("/biz/crm/prjValueAllocation/page")
    @GetMapping("/biz/crm/prjValueAllocation/page")
    public CommonResult<Page<PrjValueAllocation>> page(PrjValueAllocationPageParam prjValueAllocationPageParam) {
        return CommonResult.data(prjValueAllocationService.page(prjValueAllocationPageParam));
    }

    /**
     * 添加项目产值划分
     *
     * @author pzh
     * @date  2025/06/04 16:35
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加项目产值划分")
    @CommonLog("添加项目产值划分")
    // @SaCheckPermission("/biz/crm/prjValueAllocation/add")
    @PostMapping("/biz/crm/prjValueAllocation/add")
    public CommonResult<String> add(@RequestBody @Valid PrjValueAllocationAddParam prjValueAllocationAddParam) {
        prjValueAllocationService.add(prjValueAllocationAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑项目产值划分
     *
     * @author pzh
     * @date  2025/06/04 16:35
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑项目产值划分")
    @CommonLog("编辑项目产值划分")
    // @SaCheckPermission("/biz/crm/prjValueAllocation/edit")
    @PostMapping("/biz/crm/prjValueAllocation/edit")
    public CommonResult<String> edit(@RequestBody @Valid PrjValueAllocationEditParam prjValueAllocationEditParam) {
        prjValueAllocationService.edit(prjValueAllocationEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除项目产值划分
     *
     * @author pzh
     * @date  2025/06/04 16:35
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除项目产值划分")
    @CommonLog("删除项目产值划分")
    // @SaCheckPermission("/biz/crm/prjValueAllocation/delete")
    @PostMapping("/biz/crm/prjValueAllocation/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<PrjValueAllocationIdParam> prjValueAllocationIdParamList) {
        prjValueAllocationService.delete(prjValueAllocationIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取项目产值划分详情
     *
     * @author pzh
     * @date  2025/06/04 16:35
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取项目产值划分详情")
    // @SaCheckPermission("/biz/crm/prjValueAllocation/detail")
    @GetMapping("/biz/crm/prjValueAllocation/detail")
    public CommonResult<PrjValueAllocation> detail(@Valid PrjValueAllocationIdParam prjValueAllocationIdParam) {
        return CommonResult.data(prjValueAllocationService.detail(prjValueAllocationIdParam));
    }
}
