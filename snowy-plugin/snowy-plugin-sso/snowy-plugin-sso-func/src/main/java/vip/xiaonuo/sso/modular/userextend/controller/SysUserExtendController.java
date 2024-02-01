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
package vip.xiaonuo.sso.modular.userextend.controller;

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
import vip.xiaonuo.sso.modular.userextend.entity.SysUserExtend;
import vip.xiaonuo.sso.modular.userextend.param.SysUserExtendAddParam;
import vip.xiaonuo.sso.modular.userextend.param.SysUserExtendEditParam;
import vip.xiaonuo.sso.modular.userextend.param.SysUserExtendIdParam;
import vip.xiaonuo.sso.modular.userextend.param.SysUserExtendPageParam;
import vip.xiaonuo.sso.modular.userextend.service.SysUserExtendService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 外部用户表控制器
 *
 * @author mayike
 * @date  2024/02/01 10:11
 */
@Api(tags = "外部用户表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class SysUserExtendController {

    @Resource
    private SysUserExtendService sysUserExtendService;

    /**
     * 获取外部用户表分页
     *
     * @author mayike
     * @date  2024/02/01 10:11
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取外部用户表分页")
//    @SaCheckPermission("/ssoUser/userExtend/page")
    @GetMapping("/ssoUser/userExtend/page")
    public CommonResult<Page<SysUserExtend>> page(SysUserExtendPageParam sysUserExtendPageParam) {
        return CommonResult.data(sysUserExtendService.page(sysUserExtendPageParam));
    }

    /**
     * 添加外部用户表
     *
     * @author mayike
     * @date  2024/02/01 10:11
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加外部用户表")
    @CommonLog("添加外部用户表")
//    @SaCheckPermission("/ssoUser/userExtend/add")
    @PostMapping("/ssoUser/userExtend/add")
    public CommonResult<String> add(@RequestBody @Valid SysUserExtendAddParam sysUserExtendAddParam) {
        sysUserExtendService.add(sysUserExtendAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑外部用户表
     *
     * @author mayike
     * @date  2024/02/01 10:11
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑外部用户表")
    @CommonLog("编辑外部用户表")
//    @SaCheckPermission("/ssoUser/userExtend/edit")
    @PostMapping("/ssoUser/userExtend/edit")
    public CommonResult<String> edit(@RequestBody @Valid SysUserExtendEditParam sysUserExtendEditParam) {
        sysUserExtendService.edit(sysUserExtendEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除外部用户表
     *
     * @author mayike
     * @date  2024/02/01 10:11
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除外部用户表")
    @CommonLog("删除外部用户表")
//    @SaCheckPermission("/ssoUser/userExtend/delete")
    @PostMapping("/ssoUser/userExtend/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<SysUserExtendIdParam> sysUserExtendIdParamList) {
        sysUserExtendService.delete(sysUserExtendIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取外部用户表详情
     *
     * @author mayike
     * @date  2024/02/01 10:11
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取外部用户表详情")
//    @SaCheckPermission("/ssoUser/userExtend/detail")
    @GetMapping("/ssoUser/userExtend/detail")
    public CommonResult<SysUserExtend> detail(@Valid SysUserExtendIdParam sysUserExtendIdParam) {
        return CommonResult.data(sysUserExtendService.detail(sysUserExtendIdParam));
    }

    @ApiOperationSupport(order = 6)
    @ApiOperation("同步外部人员")
    @CommonLog("同步外部人员")
//    @SaCheckPermission("/ssoUser/userExtend/sync")
    @PostMapping("/ssoUser/userExtend/sync")
    public CommonResult<String> sync(@RequestBody @Valid SysUserExtendAddParam sysUserExtendAddParam) {
        sysUserExtendService.sync(sysUserExtendAddParam);
        return CommonResult.ok();
    }
}
