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
package vip.xiaonuo.pan.modular.filePermission.controller;

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
import vip.xiaonuo.pan.modular.filePermission.entity.PanFilePermission;
import vip.xiaonuo.pan.modular.filePermission.param.PanFilePermissionAddParam;
import vip.xiaonuo.pan.modular.filePermission.param.PanFilePermissionEditParam;
import vip.xiaonuo.pan.modular.filePermission.param.PanFilePermissionIdParam;
import vip.xiaonuo.pan.modular.filePermission.param.PanFilePermissionPageParam;
import vip.xiaonuo.pan.modular.filePermission.service.PanFilePermissionService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 文件权限控制器
 *
 * @author mayike
 * @date  2023/09/06 21:15
 */
@Api(tags = "文件权限控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class PanFilePermissionController {

    @Resource
    private PanFilePermissionService panFilePermissionService;

    /**
     * 获取文件权限分页
     *
     * @author mayike
     * @date  2023/09/06 21:15
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取文件权限分页")
    @SaCheckPermission("/pan/filePermission/page")
    @GetMapping("/pan/filePermission/page")
    public CommonResult<Page<PanFilePermission>> page(PanFilePermissionPageParam panFilePermissionPageParam) {
        return CommonResult.data(panFilePermissionService.page(panFilePermissionPageParam));
    }

    /**
     * 添加文件权限
     *
     * @author mayike
     * @date  2023/09/06 21:15
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加文件权限")
    @CommonLog("添加文件权限")
    @SaCheckPermission("/pan/filePermission/add")
    @PostMapping("/pan/filePermission/add")
    public CommonResult<String> add(@RequestBody @Valid PanFilePermissionAddParam panFilePermissionAddParam) {
        panFilePermissionService.add(panFilePermissionAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑文件权限
     *
     * @author mayike
     * @date  2023/09/06 21:15
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑文件权限")
    @CommonLog("编辑文件权限")
    @SaCheckPermission("/pan/filePermission/edit")
    @PostMapping("/pan/filePermission/edit")
    public CommonResult<String> edit(@RequestBody @Valid PanFilePermissionEditParam panFilePermissionEditParam) {
        panFilePermissionService.edit(panFilePermissionEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除文件权限
     *
     * @author mayike
     * @date  2023/09/06 21:15
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除文件权限")
    @CommonLog("删除文件权限")
    @SaCheckPermission("/pan/filePermission/delete")
    @PostMapping("/pan/filePermission/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<PanFilePermissionIdParam> panFilePermissionIdParamList) {
        panFilePermissionService.delete(panFilePermissionIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取文件权限详情
     *
     * @author mayike
     * @date  2023/09/06 21:15
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取文件权限详情")
    @SaCheckPermission("/pan/filePermission/detail")
    @GetMapping("/pan/filePermission/detail")
    public CommonResult<PanFilePermission> detail(@Valid PanFilePermissionIdParam panFilePermissionIdParam) {
        return CommonResult.data(panFilePermissionService.detail(panFilePermissionIdParam));
    }
}
