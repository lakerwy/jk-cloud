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
package vip.xiaonuo.sys.modular.hr.userApplygoOut.controller;

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
import vip.xiaonuo.sys.modular.hr.userApplygoOut.entity.HrUserApplyGoout;
import vip.xiaonuo.sys.modular.hr.userApplygoOut.param.HrUserApplyGooutAddParam;
import vip.xiaonuo.sys.modular.hr.userApplygoOut.param.HrUserApplyGooutEditParam;
import vip.xiaonuo.sys.modular.hr.userApplygoOut.param.HrUserApplyGooutIdParam;
import vip.xiaonuo.sys.modular.hr.userApplygoOut.param.HrUserApplyGooutPageParam;
import vip.xiaonuo.sys.modular.hr.userApplygoOut.service.HrUserApplyGooutService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 用户外出申请表控制器
 *
 * @author ljy
 * @date  2023/11/01 22:53
 */
@Api(tags = "用户外出申请表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class HrUserApplyGooutController {

    @Resource
    private HrUserApplyGooutService hrUserApplyGooutService;

    /**
     * 获取用户外出申请表分页
     *
     * @author ljy
     * @date  2023/11/01 22:53
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取用户外出申请表分页")
    @SaCheckPermission("/sys/userApplygoOut/page")
    @GetMapping("/sys/userApplygoOut/page")
    public CommonResult<Page<HrUserApplyGoout>> page(HrUserApplyGooutPageParam hrUserApplyGooutPageParam) {
        return CommonResult.data(hrUserApplyGooutService.page(hrUserApplyGooutPageParam));
    }

    /**
     * 添加用户外出申请表
     *
     * @author ljy
     * @date  2023/11/01 22:53
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加用户外出申请表")
    @CommonLog("添加用户外出申请表")
    @SaCheckPermission("/sys/userApplygoOut/add")
    @PostMapping("/sys/userApplygoOut/add")
    public CommonResult<String> add(@RequestBody @Valid HrUserApplyGooutAddParam hrUserApplyGooutAddParam) {
        hrUserApplyGooutService.add(hrUserApplyGooutAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑用户外出申请表
     *
     * @author ljy
     * @date  2023/11/01 22:53
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑用户外出申请表")
    @CommonLog("编辑用户外出申请表")
    @SaCheckPermission("/sys/userApplygoOut/edit")
    @PostMapping("/sys/userApplygoOut/edit")
    public CommonResult<String> edit(@RequestBody @Valid HrUserApplyGooutEditParam hrUserApplyGooutEditParam) {
        hrUserApplyGooutService.edit(hrUserApplyGooutEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除用户外出申请表
     *
     * @author ljy
     * @date  2023/11/01 22:53
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除用户外出申请表")
    @CommonLog("删除用户外出申请表")
    @SaCheckPermission("/sys/userApplygoOut/delete")
    @PostMapping("/sys/userApplygoOut/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<HrUserApplyGooutIdParam> hrUserApplyGooutIdParamList) {
        hrUserApplyGooutService.delete(hrUserApplyGooutIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取用户外出申请表详情
     *
     * @author ljy
     * @date  2023/11/01 22:53
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取用户外出申请表详情")
    @SaCheckPermission("/sys/userApplygoOut/detail")
    @GetMapping("/sys/userApplygoOut/detail")
    public CommonResult<HrUserApplyGoout> detail(@Valid HrUserApplyGooutIdParam hrUserApplyGooutIdParam) {
        return CommonResult.data(hrUserApplyGooutService.detail(hrUserApplyGooutIdParam));
    }
}
