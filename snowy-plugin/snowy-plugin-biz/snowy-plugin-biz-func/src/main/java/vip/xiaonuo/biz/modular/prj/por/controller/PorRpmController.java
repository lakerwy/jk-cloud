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
import vip.xiaonuo.biz.modular.prj.por.vo.PorRpmVo;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.prj.por.entity.PorRpm;
import vip.xiaonuo.biz.modular.prj.por.param.PorRpmAddParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorRpmEditParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorRpmIdParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorRpmPageParam;
import vip.xiaonuo.biz.modular.prj.por.service.PorRpmService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 项目产值台账 - 项目管理 - 责任上缴费用管理控制器
 *
 * @author yy
 * @date  2025/06/03 09:55
 */
@Api(tags = "项目产值台账 - 项目管理 - 责任上缴费用管理控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class PorRpmController {

    @Resource
    private PorRpmService porRpmService;

    /**
     * 获取项目产值台账 - 项目管理 - 责任上缴费用管理分页
     *
     * @author yy
     * @date  2025/06/03 09:55
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取项目产值台账 - 项目管理 - 责任上缴费用管理分页")
//    @SaCheckPermission("/biz/prj/por/rpm/page")
    @GetMapping("/biz/prj/por/rpm/page")
    public CommonResult<Page<PorRpmVo>> page(PorRpmPageParam porRpmPageParam) {
        return CommonResult.data(porRpmService.page(porRpmPageParam));
    }

    /**
     * 添加项目产值台账 - 项目管理 - 责任上缴费用管理
     *
     * @author yy
     * @date  2025/06/03 09:55
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加项目产值台账 - 项目管理 - 责任上缴费用管理")
    @CommonLog("添加项目产值台账 - 项目管理 - 责任上缴费用管理")
//    @SaCheckPermission("/biz/prj/por/rpm/add")
    @PostMapping("/biz/prj/por/rpm/add")
    public CommonResult<String> add(@RequestBody @Valid PorRpmAddParam porRpmAddParam) {
        porRpmService.add(porRpmAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑项目产值台账 - 项目管理 - 责任上缴费用管理
     *
     * @author yy
     * @date  2025/06/03 09:55
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑项目产值台账 - 项目管理 - 责任上缴费用管理")
    @CommonLog("编辑项目产值台账 - 项目管理 - 责任上缴费用管理")
//    @SaCheckPermission("/biz/prj/por/rpm/edit")
    @PostMapping("/biz/prj/por/rpm/edit")
    public CommonResult<String> edit(@RequestBody @Valid PorRpmEditParam porRpmEditParam) {
        porRpmService.edit(porRpmEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除项目产值台账 - 项目管理 - 责任上缴费用管理
     *
     * @author yy
     * @date  2025/06/03 09:55
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除项目产值台账 - 项目管理 - 责任上缴费用管理")
    @CommonLog("删除项目产值台账 - 项目管理 - 责任上缴费用管理")
//    @SaCheckPermission("/biz/prj/por/rpm/delete")
    @PostMapping("/biz/prj/por/rpm/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<PorRpmIdParam> porRpmIdParamList) {
        porRpmService.delete(porRpmIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取项目产值台账 - 项目管理 - 责任上缴费用管理详情
     *
     * @author yy
     * @date  2025/06/03 09:55
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取项目产值台账 - 项目管理 - 责任上缴费用管理详情")
//    @SaCheckPermission("/biz/prj/por/rpm/detail")
    @GetMapping("/biz/prj/por/rpm/detail")
    public CommonResult<PorRpmVo> detail(@Valid PorRpmIdParam porRpmIdParam) {
        return CommonResult.data(porRpmService.detail(porRpmIdParam));
    }
}
