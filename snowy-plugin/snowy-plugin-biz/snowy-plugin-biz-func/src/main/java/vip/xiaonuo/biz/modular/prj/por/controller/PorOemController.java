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
import vip.xiaonuo.biz.modular.prj.por.vo.PorOemVo;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.prj.por.entity.PorOem;
import vip.xiaonuo.biz.modular.prj.por.param.PorOemAddParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorOemEditParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorOemIdParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorOemPageParam;
import vip.xiaonuo.biz.modular.prj.por.service.PorOemService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 项目产值台账 - 项目管理 - 经营费管理控制器
 *
 * @author yy
 * @date  2025/06/03 09:56
 */
@Api(tags = "项目产值台账 - 项目管理 - 经营费管理控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class PorOemController {

    @Resource
    private PorOemService porOemService;

    /**
     * 获取项目产值台账 - 项目管理 - 经营费管理分页
     *
     * @author yy
     * @date  2025/06/03 09:56
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取项目产值台账 - 项目管理 - 经营费管理分页")
//    @SaCheckPermission("/biz/prj/por/oem/page")
    @GetMapping("/biz/prj/por/oem/page")
    public CommonResult<Page<PorOemVo>> page(PorOemPageParam porOemPageParam) {
        return CommonResult.data(porOemService.page(porOemPageParam));
    }

    /**
     * 添加项目产值台账 - 项目管理 - 经营费管理
     *
     * @author yy
     * @date  2025/06/03 09:56
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加项目产值台账 - 项目管理 - 经营费管理")
    @CommonLog("添加项目产值台账 - 项目管理 - 经营费管理")
//    @SaCheckPermission("/biz/prj/por/oem/add")
    @PostMapping("/biz/prj/por/oem/add")
    public CommonResult<String> add(@RequestBody @Valid PorOemAddParam porOemAddParam) {
        porOemService.add(porOemAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑项目产值台账 - 项目管理 - 经营费管理
     *
     * @author yy
     * @date  2025/06/03 09:56
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑项目产值台账 - 项目管理 - 经营费管理")
    @CommonLog("编辑项目产值台账 - 项目管理 - 经营费管理")
//    @SaCheckPermission("/biz/prj/por/oem/edit")
    @PostMapping("/biz/prj/por/oem/edit")
    public CommonResult<String> edit(@RequestBody @Valid PorOemEditParam porOemEditParam) {
        porOemService.edit(porOemEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除项目产值台账 - 项目管理 - 经营费管理
     *
     * @author yy
     * @date  2025/06/03 09:56
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除项目产值台账 - 项目管理 - 经营费管理")
    @CommonLog("删除项目产值台账 - 项目管理 - 经营费管理")
//    @SaCheckPermission("/biz/prj/por/oem/delete")
    @PostMapping("/biz/prj/por/oem/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<PorOemIdParam> porOemIdParamList) {
        porOemService.delete(porOemIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取项目产值台账 - 项目管理 - 经营费管理详情
     *
     * @author yy
     * @date  2025/06/03 09:56
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取项目产值台账 - 项目管理 - 经营费管理详情")
//    @SaCheckPermission("/biz/prj/por/oem/detail")
    @GetMapping("/biz/prj/por/oem/detail")
    public CommonResult<PorOemVo> detail(@Valid PorOemIdParam porOemIdParam) {
        return CommonResult.data(porOemService.detail(porOemIdParam));
    }
}
