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
import vip.xiaonuo.biz.modular.prj.por.vo.PorSiteFeeVo;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.prj.por.entity.PorSiteFee;
import vip.xiaonuo.biz.modular.prj.por.param.PorSiteFeeAddParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorSiteFeeEditParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorSiteFeeIdParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorSiteFeePageParam;
import vip.xiaonuo.biz.modular.prj.por.service.PorSiteFeeService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 项目产值台账 - 项目管理 - 项目现场管理费用控制器
 *
 * @author yy
 * @date  2025/06/03 09:45
 */
@Api(tags = "项目产值台账 - 项目管理 - 项目现场管理费用控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class PorSiteFeeController {

    @Resource
    private PorSiteFeeService porSiteFeeService;

    /**
     * 获取项目产值台账 - 项目管理 - 项目现场管理费用分页
     *
     * @author yy
     * @date  2025/06/03 09:45
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取项目产值台账 - 项目管理 - 项目现场管理费用分页")
//    @SaCheckPermission("/biz/prj/por/site/fee/page")
    @GetMapping("/biz/prj/por/site/fee/page")
    public CommonResult<Page<PorSiteFeeVo>> page(PorSiteFeePageParam porSiteFeePageParam) {
        return CommonResult.data(porSiteFeeService.page(porSiteFeePageParam));
    }

    /**
     * 添加项目产值台账 - 项目管理 - 项目现场管理费用
     *
     * @author yy
     * @date  2025/06/03 09:45
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加项目产值台账 - 项目管理 - 项目现场管理费用")
    @CommonLog("添加项目产值台账 - 项目管理 - 项目现场管理费用")
//    @SaCheckPermission("/biz/prj/por/site/fee/add")
    @PostMapping("/biz/prj/por/site/fee/add")
    public CommonResult<String> add(@RequestBody @Valid PorSiteFeeAddParam porSiteFeeAddParam) {
        porSiteFeeService.add(porSiteFeeAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑项目产值台账 - 项目管理 - 项目现场管理费用
     *
     * @author yy
     * @date  2025/06/03 09:45
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑项目产值台账 - 项目管理 - 项目现场管理费用")
    @CommonLog("编辑项目产值台账 - 项目管理 - 项目现场管理费用")
//    @SaCheckPermission("/biz/prj/por/site/fee/edit")
    @PostMapping("/biz/prj/por/site/fee/edit")
    public CommonResult<String> edit(@RequestBody @Valid PorSiteFeeEditParam porSiteFeeEditParam) {
        porSiteFeeService.edit(porSiteFeeEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除项目产值台账 - 项目管理 - 项目现场管理费用
     *
     * @author yy
     * @date  2025/06/03 09:45
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除项目产值台账 - 项目管理 - 项目现场管理费用")
    @CommonLog("删除项目产值台账 - 项目管理 - 项目现场管理费用")
//    @SaCheckPermission("/biz/prj/por/site/fee/delete")
    @PostMapping("/biz/prj/por/site/fee/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<PorSiteFeeIdParam> porSiteFeeIdParamList) {
        porSiteFeeService.delete(porSiteFeeIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取项目产值台账 - 项目管理 - 项目现场管理费用详情
     *
     * @author yy
     * @date  2025/06/03 09:45
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取项目产值台账 - 项目管理 - 项目现场管理费用详情")
//    @SaCheckPermission("/biz/prj/por/site/fee/detail")
    @GetMapping("/biz/prj/por/site/fee/detail")
    public CommonResult<PorSiteFeeVo> detail(@Valid PorSiteFeeIdParam porSiteFeeIdParam) {
        return CommonResult.data(porSiteFeeService.detail(porSiteFeeIdParam));
    }
}
