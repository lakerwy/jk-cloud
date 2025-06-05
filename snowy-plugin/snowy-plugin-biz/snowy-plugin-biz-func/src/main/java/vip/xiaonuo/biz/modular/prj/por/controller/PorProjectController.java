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
import vip.xiaonuo.biz.modular.prj.por.vo.PorProjectVo;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.prj.por.entity.PorProject;
import vip.xiaonuo.biz.modular.prj.por.param.PorProjectAddParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorProjectEditParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorProjectIdParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorProjectPageParam;
import vip.xiaonuo.biz.modular.prj.por.service.PorProjectService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 项目产值台账 - 项目管理控制器
 *
 * @author yy
 * @date  2025/06/03 09:39
 */
@Api(tags = "项目产值台账 - 项目管理控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class PorProjectController {

    @Resource
    private PorProjectService porProjectService;

    /**
     * 获取项目产值台账 - 项目管理分页
     *
     * @author yy
     * @date  2025/06/03 09:39
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取项目产值台账 - 项目管理分页")
//    @SaCheckPermission("/biz/prj/por/page")
    @GetMapping("/biz/prj/por/page")
    public CommonResult<Page<PorProjectVo>> page(PorProjectPageParam porProjectPageParam) {
        return CommonResult.data(porProjectService.page(porProjectPageParam));
    }

    /**
     * 添加项目产值台账 - 项目管理
     *
     * @author yy
     * @date  2025/06/03 09:39
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加项目产值台账 - 项目管理")
    @CommonLog("添加项目产值台账 - 项目管理")
//    @SaCheckPermission("/biz/prj/por/add")
    @PostMapping("/biz/prj/por/add")
    public CommonResult<String> add(@RequestBody @Valid PorProjectAddParam porProjectAddParam) {
        porProjectService.add(porProjectAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑项目产值台账 - 项目管理
     *
     * @author yy
     * @date  2025/06/03 09:39
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑项目产值台账 - 项目管理")
    @CommonLog("编辑项目产值台账 - 项目管理")
//    @SaCheckPermission("/biz/prj/por/edit")
    @PostMapping("/biz/prj/por/edit")
    public CommonResult<String> edit(@RequestBody @Valid PorProjectEditParam porProjectEditParam) {
        porProjectService.edit(porProjectEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除项目产值台账 - 项目管理
     *
     * @author yy
     * @date  2025/06/03 09:39
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除项目产值台账 - 项目管理")
    @CommonLog("删除项目产值台账 - 项目管理")
//    @SaCheckPermission("/biz/prj/por/delete")
    @PostMapping("/biz/prj/por/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<PorProjectIdParam> porProjectIdParamList) {
        porProjectService.delete(porProjectIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取项目产值台账 - 项目管理详情
     *
     * @author yy
     * @date  2025/06/03 09:39
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取项目产值台账 - 项目管理详情")
//    @SaCheckPermission("/biz/prj/por/detail")
    @GetMapping("/biz/prj/por/detail")
    public CommonResult<PorProjectVo> detail(@Valid PorProjectIdParam porProjectIdParam) {
        return CommonResult.data(porProjectService.detail(porProjectIdParam));
    }
}
