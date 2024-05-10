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
package vip.xiaonuo.bp.modular.platform.controller;

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
import vip.xiaonuo.bp.modular.platform.param.user.*;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.bp.modular.platform.entity.BpUser;
import vip.xiaonuo.bp.modular.platform.service.BpUserService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 招采平台 - 用户表控制器
 *
 * @author yong.ye
 * @date  2024/05/09 08:41
 */
@Api(tags = "【后台】招采平台 - 用户表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BpUserController {

    @Resource
    private BpUserService bpUserService;

    /**
     * 获取招采平台 - 用户表分页
     *
     * @author yong.ye
     * @date  2024/05/09 08:41
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取招采平台 - 用户表分页")
//    @SaCheckPermission("/bp/platform/user/page")
    @GetMapping("/bp/platform/user/page")
    public CommonResult<Page<BpUser>> page(BpUserPageParam bpUserPageParam) {
        return CommonResult.data(bpUserService.page(bpUserPageParam));
    }

    /**
     * 添加招采平台 - 用户表
     *
     * @author yong.ye
     * @date  2024/05/09 08:41
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加招采平台 - 用户表")
    @CommonLog("添加招采平台 - 用户表")
//    @SaCheckPermission("/bp/platform/user/add")
    @PostMapping("/bp/platform/user/add")
    public CommonResult<String> add(@RequestBody @Valid BpUserAddParam bpUserAddParam) {
        bpUserService.add(bpUserAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑招采平台 - 用户表
     *
     * @author yong.ye
     * @date  2024/05/09 08:41
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑招采平台 - 用户表")
    @CommonLog("编辑招采平台 - 用户表")
//    @SaCheckPermission("/bp/platform/user/edit")
    @PostMapping("/bp/platform/user/edit")
    public CommonResult<String> edit(@RequestBody @Valid BpUserEditParam bpUserEditParam) {
        bpUserService.edit(bpUserEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除招采平台 - 用户表
     *
     * @author yong.ye
     * @date  2024/05/09 08:41
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除招采平台 - 用户表")
    @CommonLog("删除招采平台 - 用户表")
//    @SaCheckPermission("/bp/platform/user/delete")
    @PostMapping("/bp/platform/user/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BpUserIdParam> bpUserIdParamList) {
        bpUserService.delete(bpUserIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取招采平台 - 用户表详情
     *
     * @author yong.ye
     * @date  2024/05/09 08:41
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取招采平台 - 用户表详情")
//    @SaCheckPermission("/bp/platform/user/detail")
    @GetMapping("/bp/platform/user/detail")
    public CommonResult<BpUser> detail(@Valid BpUserIdParam bpUserIdParam) {
        return CommonResult.data(bpUserService.detail(bpUserIdParam));
    }

}
