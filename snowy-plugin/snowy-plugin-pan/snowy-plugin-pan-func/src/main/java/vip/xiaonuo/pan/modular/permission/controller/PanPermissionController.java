/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.pan.modular.permission.controller;

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
import vip.xiaonuo.pan.modular.permission.entity.PanPermission;
import vip.xiaonuo.pan.modular.permission.param.PanPermissionAddParam;
import vip.xiaonuo.pan.modular.permission.param.PanPermissionEditParam;
import vip.xiaonuo.pan.modular.permission.param.PanPermissionIdParam;
import vip.xiaonuo.pan.modular.permission.param.PanPermissionPageParam;
import vip.xiaonuo.pan.modular.permission.service.PanPermissionService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 云盘-权限表控制器
 *
 * @author mayike
 * @date  2023/09/08 16:07
 */
@Api(tags = "云盘-权限表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class PanPermissionController {

    @Resource
    private PanPermissionService panPermissionService;

    /**
     * 获取云盘-权限表分页
     *
     * @author mayike
     * @date  2023/09/08 16:07
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取云盘-权限表分页")
    @SaCheckPermission("/pan/permission/page")
    @GetMapping("/pan/permission/page")
    public CommonResult<Page<PanPermission>> page(PanPermissionPageParam panPermissionPageParam) {
        return CommonResult.data(panPermissionService.page(panPermissionPageParam));
    }

    /**
     * 添加云盘-权限表
     *
     * @author mayike
     * @date  2023/09/08 16:07
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加云盘-权限表")
    @CommonLog("添加云盘-权限表")
    @SaCheckPermission("/pan/permission/add")
    @PostMapping("/pan/permission/add")
    public CommonResult<String> add(@RequestBody @Valid PanPermissionAddParam panPermissionAddParam) {
        panPermissionService.add(panPermissionAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑云盘-权限表
     *
     * @author mayike
     * @date  2023/09/08 16:07
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑云盘-权限表")
    @CommonLog("编辑云盘-权限表")
    @SaCheckPermission("/pan/permission/edit")
    @PostMapping("/pan/permission/edit")
    public CommonResult<String> edit(@RequestBody @Valid PanPermissionEditParam panPermissionEditParam) {
        panPermissionService.edit(panPermissionEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除云盘-权限表
     *
     * @author mayike
     * @date  2023/09/08 16:07
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除云盘-权限表")
    @CommonLog("删除云盘-权限表")
    @SaCheckPermission("/pan/permission/delete")
    @PostMapping("/pan/permission/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<PanPermissionIdParam> panPermissionIdParamList) {
        panPermissionService.delete(panPermissionIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取云盘-权限表详情
     *
     * @author mayike
     * @date  2023/09/08 16:07
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取云盘-权限表详情")
    @SaCheckPermission("/pan/permission/detail")
    @GetMapping("/pan/permission/detail")
    public CommonResult<PanPermission> detail(@Valid PanPermissionIdParam panPermissionIdParam) {
        return CommonResult.data(panPermissionService.detail(panPermissionIdParam));
    }
}
