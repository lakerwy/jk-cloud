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
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.bp.modular.platform.entity.UserPay;
import vip.xiaonuo.bp.modular.platform.param.userSign.UserPayAddParam;
import vip.xiaonuo.bp.modular.platform.param.userSign.UserPayEditParam;
import vip.xiaonuo.bp.modular.platform.param.userSign.UserPayIdParam;
import vip.xiaonuo.bp.modular.platform.param.userSign.UserPayPageParam;
import vip.xiaonuo.bp.modular.platform.service.UserPayService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 【后台】招采平台 - 用户 - （招采项目）费用缴纳信息表控制器
 *
 * @author yong.ye
 * @date  2024/05/10 10:50
 */
@Api(tags = "【后台】招采平台 - 用户 - （招采项目）费用缴纳信息表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class UserPayController {

    @Resource
    private UserPayService userPayService;

    /**
     * 获取招采平台 - 用户 - （招采项目）费用缴纳信息表分页
     *
     * @author yong.ye
     * @date  2024/05/10 10:50
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取招采平台 - 用户 - （招采项目）费用缴纳信息表分页")
//    @SaCheckPermission("/bp/platform/pay/page")
    @GetMapping("/bp/platform/pay/page")
    public CommonResult<Page<UserPay>> page(UserPayPageParam userPayPageParam) {
        return CommonResult.data(userPayService.page(userPayPageParam));
    }

    /**
     * 添加招采平台 - 用户 - （招采项目）费用缴纳信息表
     *
     * @author yong.ye
     * @date  2024/05/10 10:50
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加招采平台 - 用户 - （招采项目）费用缴纳信息表")
    @CommonLog("添加招采平台 - 用户 - （招采项目）费用缴纳信息表")
//    @SaCheckPermission("/bp/platform/pay/add")
    @PostMapping("/bp/platform/pay/add")
    public CommonResult<String> add(@RequestBody @Valid UserPayAddParam userPayAddParam) {
        userPayService.add(userPayAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑招采平台 - 用户 - （招采项目）费用缴纳信息表
     *
     * @author yong.ye
     * @date  2024/05/10 10:50
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑招采平台 - 用户 - （招采项目）费用缴纳信息表")
    @CommonLog("编辑招采平台 - 用户 - （招采项目）费用缴纳信息表")
//    @SaCheckPermission("/bp/platform/pay/edit")
    @PostMapping("/bp/platform/pay/edit")
    public CommonResult<String> edit(@RequestBody @Valid UserPayEditParam userPayEditParam) {
        userPayService.edit(userPayEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除招采平台 - 用户 - （招采项目）费用缴纳信息表
     *
     * @author yong.ye
     * @date  2024/05/10 10:50
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除招采平台 - 用户 - （招采项目）费用缴纳信息表")
    @CommonLog("删除招采平台 - 用户 - （招采项目）费用缴纳信息表")
//    @SaCheckPermission("/bp/platform/pay/delete")
    @PostMapping("/bp/platform/pay/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<UserPayIdParam> userPayIdParamList) {
        userPayService.delete(userPayIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取招采平台 - 用户 - （招采项目）费用缴纳信息表详情
     *
     * @author yong.ye
     * @date  2024/05/10 10:50
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取招采平台 - 用户 - （招采项目）费用缴纳信息表详情")
//    @SaCheckPermission("/bp/platform/pay/detail")
    @GetMapping("/bp/platform/pay/detail")
    public CommonResult<UserPay> detail(@Valid UserPayIdParam userPayIdParam) {
        return CommonResult.data(userPayService.detail(userPayIdParam));
    }
}
