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
import vip.xiaonuo.biz.modular.prj.por.entity.PorMsg;
import vip.xiaonuo.biz.modular.prj.por.param.PorMsgAddParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorMsgEditParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorMsgIdParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorMsgPageParam;
import vip.xiaonuo.biz.modular.prj.por.service.PorMsgService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 项目产值台账 - 收款提醒控制器
 *
 * @author yy
 * @date  2025/06/18 16:13
 */
@Api(tags = "项目产值台账 - 收款提醒控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class PorMsgController {

    @Resource
    private PorMsgService porMsgService;

    /**
     * 获取项目产值台账 - 收款提醒分页
     *
     * @author yy
     * @date  2025/06/18 16:13
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取项目产值台账 - 收款提醒分页")
//    @SaCheckPermission("/biz/prj/por/msg/page")
    @GetMapping("/biz/prj/por/msg/page")
    public CommonResult<Page<PorMsg>> page(PorMsgPageParam porMsgPageParam) {
        return CommonResult.data(porMsgService.page(porMsgPageParam));
    }

    /**
     * 添加项目产值台账 - 收款提醒
     *
     * @author yy
     * @date  2025/06/18 16:13
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加项目产值台账 - 收款提醒")
    @CommonLog("添加项目产值台账 - 收款提醒")
//    @SaCheckPermission("/biz/prj/por/msg/add")
    @PostMapping("/biz/prj/por/msg/add")
    public CommonResult<String> add(@RequestBody @Valid PorMsgAddParam porMsgAddParam) {
        porMsgService.add(porMsgAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑项目产值台账 - 收款提醒
     *
     * @author yy
     * @date  2025/06/18 16:13
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑项目产值台账 - 收款提醒")
    @CommonLog("编辑项目产值台账 - 收款提醒")
//    @SaCheckPermission("/biz/prj/por/msg/edit")
    @PostMapping("/biz/prj/por/msg/edit")
    public CommonResult<String> edit(@RequestBody @Valid PorMsgEditParam porMsgEditParam) {
        porMsgService.edit(porMsgEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除项目产值台账 - 收款提醒
     *
     * @author yy
     * @date  2025/06/18 16:13
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除项目产值台账 - 收款提醒")
    @CommonLog("删除项目产值台账 - 收款提醒")
//    @SaCheckPermission("/biz/prj/por/msg/delete")
    @PostMapping("/biz/prj/por/msg/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<PorMsgIdParam> porMsgIdParamList) {
        porMsgService.delete(porMsgIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取项目产值台账 - 收款提醒详情
     *
     * @author yy
     * @date  2025/06/18 16:13
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取项目产值台账 - 收款提醒详情")
//    @SaCheckPermission("/biz/prj/por/msg/detail")
    @GetMapping("/biz/prj/por/msg/detail")
    public CommonResult<PorMsg> detail(@Valid PorMsgIdParam porMsgIdParam) {
        return CommonResult.data(porMsgService.detail(porMsgIdParam));
    }
}
