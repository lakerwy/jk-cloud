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
package vip.xiaonuo.dev.modular.server.controller;

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
import vip.xiaonuo.dev.modular.server.entity.SystemServer;
import vip.xiaonuo.dev.modular.server.param.SystemServerAddParam;
import vip.xiaonuo.dev.modular.server.param.SystemServerEditParam;
import vip.xiaonuo.dev.modular.server.param.SystemServerIdParam;
import vip.xiaonuo.dev.modular.server.param.SystemServerPageParam;
import vip.xiaonuo.dev.modular.server.service.SystemServerService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 系统服务表控制器
 *
 * @author ljy
 * @date  2023/12/29 11:31
 */
@Api(tags = "系统服务表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class SystemServerController {

    @Resource
    private SystemServerService systemServerService;

    /**
     * 获取系统服务表分页
     *
     * @author ljy
     * @date  2023/12/29 11:31
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取系统服务表分页")
    //@SaCheckPermission("/sys/server/page")
    @GetMapping("/sys/server/page")
    public CommonResult<Page<SystemServer>> page(SystemServerPageParam systemServerPageParam) {
        return CommonResult.data(systemServerService.page(systemServerPageParam));
    }

    /**
     * 添加系统服务表
     *
     * @author ljy
     * @date  2023/12/29 11:31
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加系统服务表")
    @CommonLog("添加系统服务表")
    //@SaCheckPermission("/sys/server/add")
    @PostMapping("/sys/server/add")
    public CommonResult<String> add(@RequestBody @Valid SystemServerAddParam systemServerAddParam) {
        systemServerService.add(systemServerAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑系统服务表
     *
     * @author ljy
     * @date  2023/12/29 11:31
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑系统服务表")
    @CommonLog("编辑系统服务表")
    //@SaCheckPermission("/sys/server/edit")
    @PostMapping("/sys/server/edit")
    public CommonResult<String> edit(@RequestBody @Valid SystemServerEditParam systemServerEditParam) {
        systemServerService.edit(systemServerEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除系统服务表
     *
     * @author ljy
     * @date  2023/12/29 11:31
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除系统服务表")
    @CommonLog("删除系统服务表")
    //@SaCheckPermission("/sys/server/delete")
    @PostMapping("/sys/server/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<SystemServerIdParam> systemServerIdParamList) {
        systemServerService.delete(systemServerIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取系统服务表详情
     *
     * @author ljy
     * @date  2023/12/29 11:31
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取系统服务表详情")
    //@SaCheckPermission("/sys/server/detail")
    @GetMapping("/sys/server/detail")
    public CommonResult<SystemServer> detail(@Valid SystemServerIdParam systemServerIdParam) {
        return CommonResult.data(systemServerService.detail(systemServerIdParam));
    }
}
