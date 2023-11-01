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
package vip.xiaonuo.sys.modular.hr.userApplyBusinessTrip.controller;

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
import vip.xiaonuo.sys.modular.hr.userApplyBusinessTrip.entity.HrUserApplyBusinessTrip;
import vip.xiaonuo.sys.modular.hr.userApplyBusinessTrip.param.HrUserApplyBusinessTripAddParam;
import vip.xiaonuo.sys.modular.hr.userApplyBusinessTrip.param.HrUserApplyBusinessTripEditParam;
import vip.xiaonuo.sys.modular.hr.userApplyBusinessTrip.param.HrUserApplyBusinessTripIdParam;
import vip.xiaonuo.sys.modular.hr.userApplyBusinessTrip.param.HrUserApplyBusinessTripPageParam;
import vip.xiaonuo.sys.modular.hr.userApplyBusinessTrip.service.HrUserApplyBusinessTripService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 用户出差申请表控制器
 *
 * @author ljy
 * @date  2023/11/01 23:12
 */
@Api(tags = "用户出差申请表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class HrUserApplyBusinessTripController {

    @Resource
    private HrUserApplyBusinessTripService hrUserApplyBusinessTripService;

    /**
     * 获取用户出差申请表分页
     *
     * @author ljy
     * @date  2023/11/01 23:12
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取用户出差申请表分页")
    @SaCheckPermission("/sys/userApplyBusinessTrip/page")
    @GetMapping("/sys/userApplyBusinessTrip/page")
    public CommonResult<Page<HrUserApplyBusinessTrip>> page(HrUserApplyBusinessTripPageParam hrUserApplyBusinessTripPageParam) {
        return CommonResult.data(hrUserApplyBusinessTripService.page(hrUserApplyBusinessTripPageParam));
    }

    /**
     * 添加用户出差申请表
     *
     * @author ljy
     * @date  2023/11/01 23:12
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加用户出差申请表")
    @CommonLog("添加用户出差申请表")
    @SaCheckPermission("/sys/userApplyBusinessTrip/add")
    @PostMapping("/sys/userApplyBusinessTrip/add")
    public CommonResult<String> add(@RequestBody @Valid HrUserApplyBusinessTripAddParam hrUserApplyBusinessTripAddParam) {
        hrUserApplyBusinessTripService.add(hrUserApplyBusinessTripAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑用户出差申请表
     *
     * @author ljy
     * @date  2023/11/01 23:12
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑用户出差申请表")
    @CommonLog("编辑用户出差申请表")
    @SaCheckPermission("/sys/userApplyBusinessTrip/edit")
    @PostMapping("/sys/userApplyBusinessTrip/edit")
    public CommonResult<String> edit(@RequestBody @Valid HrUserApplyBusinessTripEditParam hrUserApplyBusinessTripEditParam) {
        hrUserApplyBusinessTripService.edit(hrUserApplyBusinessTripEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除用户出差申请表
     *
     * @author ljy
     * @date  2023/11/01 23:12
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除用户出差申请表")
    @CommonLog("删除用户出差申请表")
    @SaCheckPermission("/sys/userApplyBusinessTrip/delete")
    @PostMapping("/sys/userApplyBusinessTrip/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<HrUserApplyBusinessTripIdParam> hrUserApplyBusinessTripIdParamList) {
        hrUserApplyBusinessTripService.delete(hrUserApplyBusinessTripIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取用户出差申请表详情
     *
     * @author ljy
     * @date  2023/11/01 23:12
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取用户出差申请表详情")
    @SaCheckPermission("/sys/userApplyBusinessTrip/detail")
    @GetMapping("/sys/userApplyBusinessTrip/detail")
    public CommonResult<HrUserApplyBusinessTrip> detail(@Valid HrUserApplyBusinessTripIdParam hrUserApplyBusinessTripIdParam) {
        return CommonResult.data(hrUserApplyBusinessTripService.detail(hrUserApplyBusinessTripIdParam));
    }
}
