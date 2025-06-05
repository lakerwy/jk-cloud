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
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.prj.por.entity.PorWarn;
import vip.xiaonuo.biz.modular.prj.por.param.PorWarnAddParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorWarnEditParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorWarnIdParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorWarnPageParam;
import vip.xiaonuo.biz.modular.prj.por.service.PorWarnService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 项目产值台账 - 项目管理 - 项目预警记录控制器
 *
 * @author yy
 * @date  2025/06/03 09:41
 */
@Api(tags = "项目产值台账 - 项目管理 - 项目预警记录控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class PorWarnController {

    @Resource
    private PorWarnService porWarnService;

    /**
     * 获取项目产值台账 - 项目管理 - 项目预警记录分页
     *
     * @author yy
     * @date  2025/06/03 09:41
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取项目产值台账 - 项目管理 - 项目预警记录分页")
//    @SaCheckPermission("/biz/prj/por/warn/page")
    @GetMapping("/biz/prj/por/warn/page")
    public CommonResult<Page<PorWarn>> page(PorWarnPageParam porWarnPageParam) {
        return CommonResult.data(porWarnService.page(porWarnPageParam));
    }

    /**
     * 添加项目产值台账 - 项目管理 - 项目预警记录
     *
     * @author yy
     * @date  2025/06/03 09:41
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加项目产值台账 - 项目管理 - 项目预警记录")
    @CommonLog("添加项目产值台账 - 项目管理 - 项目预警记录")
//    @SaCheckPermission("/biz/prj/por/warn/add")
    @PostMapping("/biz/prj/por/warn/add")
    public CommonResult<String> add(@RequestBody @Valid PorWarnAddParam porWarnAddParam) {
        porWarnService.add(porWarnAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑项目产值台账 - 项目管理 - 项目预警记录
     *
     * @author yy
     * @date  2025/06/03 09:41
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑项目产值台账 - 项目管理 - 项目预警记录")
    @CommonLog("编辑项目产值台账 - 项目管理 - 项目预警记录")
//    @SaCheckPermission("/biz/prj/por/warn/edit")
    @PostMapping("/biz/prj/por/warn/edit")
    public CommonResult<String> edit(@RequestBody @Valid PorWarnEditParam porWarnEditParam) {
        porWarnService.edit(porWarnEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除项目产值台账 - 项目管理 - 项目预警记录
     *
     * @author yy
     * @date  2025/06/03 09:41
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除项目产值台账 - 项目管理 - 项目预警记录")
    @CommonLog("删除项目产值台账 - 项目管理 - 项目预警记录")
//    @SaCheckPermission("/biz/prj/por/warn/delete")
    @PostMapping("/biz/prj/por/warn/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<PorWarnIdParam> porWarnIdParamList) {
        porWarnService.delete(porWarnIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取项目产值台账 - 项目管理 - 项目预警记录详情
     *
     * @author yy
     * @date  2025/06/03 09:41
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取项目产值台账 - 项目管理 - 项目预警记录详情")
//    @SaCheckPermission("/biz/prj/por/warn/detail")
    @GetMapping("/biz/prj/por/warn/detail")
    public CommonResult<PorWarn> detail(@Valid PorWarnIdParam porWarnIdParam) {
        return CommonResult.data(porWarnService.detail(porWarnIdParam));
    }
}
