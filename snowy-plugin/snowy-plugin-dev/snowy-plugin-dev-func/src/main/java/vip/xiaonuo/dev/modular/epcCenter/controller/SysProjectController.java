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
package vip.xiaonuo.dev.modular.epcCenter.controller;

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
import vip.xiaonuo.dev.modular.epcCenter.entity.SysProject;
import vip.xiaonuo.dev.modular.epcCenter.param.SysProjectAddParam;
import vip.xiaonuo.dev.modular.epcCenter.param.SysProjectEditParam;
import vip.xiaonuo.dev.modular.epcCenter.param.SysProjectIdParam;
import vip.xiaonuo.dev.modular.epcCenter.param.SysProjectPageParam;
import vip.xiaonuo.dev.modular.epcCenter.service.SysProjectService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 系统项目表控制器
 *
 * @author ljy
 * @date  2024/01/31 11:39
 */
@Api(tags = "系统项目表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class SysProjectController {

    @Resource
    private SysProjectService sysProjectService;

    /**
     * 获取系统项目表分页
     *
     * @author ljy
     * @date  2024/01/31 11:39
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取系统项目表分页")
    @GetMapping("/sys/project/page")
    public CommonResult<Page<SysProject>> page(SysProjectPageParam sysProjectPageParam) {
        return CommonResult.data(sysProjectService.page(sysProjectPageParam));
    }

    /**
     * 添加系统项目表
     *
     * @author ljy
     * @date  2024/01/31 11:39
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加系统项目表")
    @CommonLog("添加系统项目表")
    @PostMapping("/sys/project/add")
    public CommonResult<String> add(@RequestBody @Valid SysProjectAddParam sysProjectAddParam) {
        sysProjectService.add(sysProjectAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑系统项目表
     *
     * @author ljy
     * @date  2024/01/31 11:39
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑系统项目表")
    @CommonLog("编辑系统项目表")
    @PostMapping("/sys/project/edit")
    public CommonResult<String> edit(@RequestBody @Valid SysProjectEditParam sysProjectEditParam) {
        sysProjectService.edit(sysProjectEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除系统项目表
     *
     * @author ljy
     * @date  2024/01/31 11:39
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除系统项目表")
    @CommonLog("删除系统项目表")
    @PostMapping("/sys/project/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<SysProjectIdParam> sysProjectIdParamList) {
        sysProjectService.delete(sysProjectIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取系统项目表详情
     *
     * @author ljy
     * @date  2024/01/31 11:39
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取系统项目表详情")
    @GetMapping("/sys/project/detail")
    public CommonResult<SysProject> detail(@Valid SysProjectIdParam sysProjectIdParam) {
        return CommonResult.data(sysProjectService.detail(sysProjectIdParam));
    }
}
