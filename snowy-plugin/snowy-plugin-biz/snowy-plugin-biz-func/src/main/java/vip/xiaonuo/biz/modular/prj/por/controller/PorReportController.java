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
import vip.xiaonuo.biz.modular.prj.por.vo.PorReportVo;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.prj.por.entity.PorReport;
import vip.xiaonuo.biz.modular.prj.por.param.PorReportAddParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorReportEditParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorReportIdParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorReportPageParam;
import vip.xiaonuo.biz.modular.prj.por.service.PorReportService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 项目产值台账 - 项目管理 - 项目周报|月报控制器
 *
 * @author yy
 * @date  2025/06/03 09:44
 */
@Api(tags = "项目产值台账 - 项目管理 - 项目周报|月报控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class PorReportController {

    @Resource
    private PorReportService porReportService;

    /**
     * 获取项目产值台账 - 项目管理 - 项目周报|月报分页
     *
     * @author yy
     * @date  2025/06/03 09:44
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取项目产值台账 - 项目管理 - 项目周报|月报分页")
//    @SaCheckPermission("/biz/prj/por/report/page")
    @GetMapping("/biz/prj/por/report/page")
    public CommonResult<Page<PorReportVo>> page(PorReportPageParam porReportPageParam) {
        return CommonResult.data(porReportService.page(porReportPageParam));
    }

    /**
     * 添加项目产值台账 - 项目管理 - 项目周报|月报
     *
     * @author yy
     * @date  2025/06/03 09:44
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加项目产值台账 - 项目管理 - 项目周报|月报")
    @CommonLog("添加项目产值台账 - 项目管理 - 项目周报|月报")
//    @SaCheckPermission("/biz/prj/por/report/add")
    @PostMapping("/biz/prj/por/report/add")
    public CommonResult<String> add(@RequestBody @Valid PorReportAddParam porReportAddParam) {
        porReportService.add(porReportAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑项目产值台账 - 项目管理 - 项目周报|月报
     *
     * @author yy
     * @date  2025/06/03 09:44
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑项目产值台账 - 项目管理 - 项目周报|月报")
    @CommonLog("编辑项目产值台账 - 项目管理 - 项目周报|月报")
//    @SaCheckPermission("/biz/prj/por/report/edit")
    @PostMapping("/biz/prj/por/report/edit")
    public CommonResult<String> edit(@RequestBody @Valid PorReportEditParam porReportEditParam) {
        porReportService.edit(porReportEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除项目产值台账 - 项目管理 - 项目周报|月报
     *
     * @author yy
     * @date  2025/06/03 09:44
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除项目产值台账 - 项目管理 - 项目周报|月报")
    @CommonLog("删除项目产值台账 - 项目管理 - 项目周报|月报")
//    @SaCheckPermission("/biz/prj/por/report/delete")
    @PostMapping("/biz/prj/por/report/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<PorReportIdParam> porReportIdParamList) {
        porReportService.delete(porReportIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取项目产值台账 - 项目管理 - 项目周报|月报详情
     *
     * @author yy
     * @date  2025/06/03 09:44
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取项目产值台账 - 项目管理 - 项目周报|月报详情")
//    @SaCheckPermission("/biz/prj/por/report/detail")
    @GetMapping("/biz/prj/por/report/detail")
    public CommonResult<PorReportVo> detail(@Valid PorReportIdParam porReportIdParam) {
        return CommonResult.data(porReportService.detail(porReportIdParam));
    }
}
