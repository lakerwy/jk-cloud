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
package vip.xiaonuo.dev.modular.userRelation.controller;

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
import vip.xiaonuo.dev.modular.userRelation.entity.SystemUserRelation;
import vip.xiaonuo.dev.modular.userRelation.param.*;
import vip.xiaonuo.dev.modular.userRelation.service.SystemUserRelationService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

/**
 * 项目系统和账户的关联关系表控制器
 *
 * @author mayike
 * @date  2024/02/29 11:22
 */
@Api(tags = "项目系统和账户的关联关系表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class SystemUserRelationController {

    @Resource
    private SystemUserRelationService systemUserRelationService;

    /**
     * 获取项目系统和账户的关联关系表分页
     *
     * @author mayike
     * @date  2024/02/29 11:22
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取项目系统和账户的关联关系表分页")
//    @SaCheckPermission("/dev/userRelation/page")
    @GetMapping("/dev/userRelation/page")
    public CommonResult<Page<SystemUserRelation>> page(SystemUserRelationPageParam systemUserRelationPageParam) {
        return CommonResult.data(systemUserRelationService.page(systemUserRelationPageParam));
    }

    /**
     * 添加项目系统和账户的关联关系表
     *
     * @author mayike
     * @date  2024/02/29 11:22
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加项目系统和账户的关联关系表")
    @CommonLog("添加项目系统和账户的关联关系表")
//    @SaCheckPermission("/dev/userRelation/add")
    @PostMapping("/dev/userRelation/add")
    public CommonResult<String> add(@RequestBody @Valid SystemUserRelationAddParam systemUserRelationAddParam) {
        systemUserRelationService.add(systemUserRelationAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑项目系统和账户的关联关系表
     *
     * @author mayike
     * @date  2024/02/29 11:22
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑项目系统和账户的关联关系表")
    @CommonLog("编辑项目系统和账户的关联关系表")
//    @SaCheckPermission("/dev/userRelation/edit")
    @PostMapping("/dev/userRelation/edit")
    public CommonResult<String> edit(@RequestBody @Valid SystemUserRelationEditParam systemUserRelationEditParam) {
        systemUserRelationService.edit(systemUserRelationEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除项目系统和账户的关联关系表
     *
     * @author mayike
     * @date  2024/02/29 11:22
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除项目系统和账户的关联关系表")
    @CommonLog("删除项目系统和账户的关联关系表")
//    @SaCheckPermission("/dev/userRelation/delete")
    @PostMapping("/dev/userRelation/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<SystemUserRelationIdParam> systemUserRelationIdParamList) {
        systemUserRelationService.delete(systemUserRelationIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取项目系统和账户的关联关系表详情
     *
     * @author mayike
     * @date  2024/02/29 11:22
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取项目系统和账户的关联关系表详情")
//    @SaCheckPermission("/dev/userRelation/detail")
    @GetMapping("/dev/userRelation/detail")
    public CommonResult<SystemUserRelation> detail(@Valid SystemUserRelationIdParam systemUserRelationIdParam) {
        return CommonResult.data(systemUserRelationService.detail(systemUserRelationIdParam));
    }

    @ApiOperationSupport(order = 6)
    @ApiOperation("用户同步")
//    @SaCheckPermission("/dev/userRelation/detail")
    @PostMapping("/dev/userRelation/syncUser")
    public CommonResult<String> syncUser(@RequestBody @Valid SyncParam syncParam) {
        systemUserRelationService.syncUser(syncParam);
        return CommonResult.ok();
    }

    @ApiOperationSupport(order = 7)
    @ApiOperation("获取我的项目列表")
//    @SaCheckPermission("/dev/userRelation/page")
    @GetMapping("/dev/userRelation/myServer")
    public CommonResult<Page<SystemServer>> myServer(SystemUserRelationPageParam systemUserRelationPageParam) {
        return CommonResult.data(systemUserRelationService.myServer(systemUserRelationPageParam));
    }

    @ApiOperationSupport(order = 8)
    @ApiOperation("获取项目的设置人员")
//    @SaCheckPermission("/dev/userRelation/page")
    @GetMapping("/dev/userRelation/getListRoleKey")
    public CommonResult<Map<String, List<SystemUserRelation>>> getListRoleKey(SystemUserRelationIdParam systemUserRelationIdParam) {
        return CommonResult.data(systemUserRelationService.getListByRoleKey(systemUserRelationIdParam.getId()));
    }
}
