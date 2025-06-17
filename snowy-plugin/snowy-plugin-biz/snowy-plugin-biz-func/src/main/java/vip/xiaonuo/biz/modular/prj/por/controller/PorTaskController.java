package vip.xiaonuo.biz.modular.prj.por.controller;

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
import vip.xiaonuo.biz.modular.prj.por.param.*;
import vip.xiaonuo.biz.modular.prj.por.vo.PorTaskVo;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.prj.por.service.PorTaskService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 项目产值台账 - 项目管理 - 项目计划管理控制器
 *
 * @author yy
 * @date  2025/06/03 09:42
 */
@Api(tags = "项目产值台账 - 项目管理 - 项目计划管理控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class PorTaskController {

    @Resource
    private PorTaskService porTaskService;

    /**
     * 获取项目产值台账 - 项目管理 - 项目计划管理分页
     *
     * @author yy
     * @date  2025/06/03 09:42
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取项目产值台账 - 项目管理 - 项目计划管理分页")
//    @SaCheckPermission("/biz/prj/por/task/page")
    @GetMapping("/biz/prj/por/task/page")
    public CommonResult<Page<PorTaskVo>> page(PorTaskPageParam porTaskPageParam) {
        return CommonResult.data(porTaskService.page(porTaskPageParam));
    }

    /**
     * 添加项目产值台账 - 项目管理 - 项目计划管理
     *
     * @author yy
     * @date  2025/06/03 09:42
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加项目产值台账 - 项目管理 - 项目计划管理")
    @CommonLog("添加项目产值台账 - 项目管理 - 项目计划管理")
//    @SaCheckPermission("/biz/prj/por/task/add")
    @PostMapping("/biz/prj/por/task/add")
    public CommonResult<String> add(@RequestBody @Valid PorTaskAddParam porTaskAddParam) {
        porTaskService.add(porTaskAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑项目产值台账 - 项目管理 - 项目计划管理
     *
     * @author yy
     * @date  2025/06/03 09:42
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑项目产值台账 - 项目管理 - 项目计划管理")
    @CommonLog("编辑项目产值台账 - 项目管理 - 项目计划管理")
//    @SaCheckPermission("/biz/prj/por/task/edit")
    @PostMapping("/biz/prj/por/task/edit")
    public CommonResult<String> edit(@RequestBody @Valid PorTaskEditParam porTaskEditParam) {
        porTaskService.edit(porTaskEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除项目产值台账 - 项目管理 - 项目计划管理
     *
     * @author yy
     * @date  2025/06/03 09:42
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除项目产值台账 - 项目管理 - 项目计划管理")
    @CommonLog("删除项目产值台账 - 项目管理 - 项目计划管理")
//    @SaCheckPermission("/biz/prj/por/task/delete")
    @PostMapping("/biz/prj/por/task/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<PorTaskIdParam> porTaskIdParamList) {
        porTaskService.delete(porTaskIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取项目产值台账 - 项目管理 - 项目计划管理详情
     *
     * @author yy
     * @date  2025/06/03 09:42
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取项目产值台账 - 项目管理 - 项目计划管理详情")
//    @SaCheckPermission("/biz/prj/por/task/detail")
    @GetMapping("/biz/prj/por/task/detail")
    public CommonResult<PorTaskVo> detail(@Valid PorTaskIdParam porTaskIdParam) {
        return CommonResult.data(porTaskService.detail(porTaskIdParam));
    }
}
