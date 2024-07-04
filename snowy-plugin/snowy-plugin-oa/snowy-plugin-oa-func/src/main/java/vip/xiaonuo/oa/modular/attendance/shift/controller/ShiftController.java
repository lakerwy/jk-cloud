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
package vip.xiaonuo.oa.modular.attendance.shift.controller;

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
import vip.xiaonuo.oa.modular.attendance.shift.param.ShiftAddParam;
import vip.xiaonuo.oa.modular.attendance.shift.service.ShiftService;
import vip.xiaonuo.oa.modular.attendance.shift.entity.Shift;
import vip.xiaonuo.oa.modular.attendance.shift.param.ShiftAddParam;
import vip.xiaonuo.oa.modular.attendance.shift.param.ShiftEditParam;
import vip.xiaonuo.oa.modular.attendance.shift.param.ShiftIdParam;
import vip.xiaonuo.oa.modular.attendance.shift.param.ShiftPageParam;
import vip.xiaonuo.oa.modular.attendance.shift.service.ShiftService;
import vip.xiaonuo.oa.modular.attendance.shift.entity.Shift;
import vip.xiaonuo.oa.modular.attendance.shift.param.ShiftAddParam;
import vip.xiaonuo.oa.modular.attendance.shift.param.ShiftIdParam;
import vip.xiaonuo.oa.modular.attendance.shift.param.ShiftPageParam;
import vip.xiaonuo.oa.modular.attendance.shift.service.ShiftService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 班次表，用于存储班次的基本信息控制器
 *
 * @author mayike
 * @date  2024/06/11 09:32
 */
@Api(tags = "班次表")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class ShiftController {

    @Resource
    private ShiftService shiftService;

    /**
     * 获取班次表，用于存储班次的基本信息分页
     *
     * @author mayike
     * @date  2024/06/11 09:32
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取班次")
//    @SaCheckPermission("/oa/shift/page")
    @GetMapping("/oa/shift/page")
    public CommonResult<Page<Shift>> page(ShiftPageParam shiftPageParam) {
        return CommonResult.data(shiftService.page(shiftPageParam));
    }

    /**
     * 添加班次表，用于存储班次的基本信息
     *
     * @author mayike
     * @date  2024/06/11 09:32
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加班次表")
    @CommonLog("添加班次表")
//    @SaCheckPermission("/oa/shift/add")
    @PostMapping("/oa/shift/add")
    public CommonResult<String> add(@RequestBody @Valid ShiftAddParam shiftAddParam) {
        shiftService.add(shiftAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑班次表，用于存储班次的基本信息
     *
     * @author mayike
     * @date  2024/06/11 09:32
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑班次表")
    @CommonLog("编辑班次表")
//    @SaCheckPermission("/oa/shift/edit")
    @PostMapping("/oa/shift/edit")
    public CommonResult<String> edit(@RequestBody @Valid ShiftEditParam shiftEditParam) {
        shiftService.edit(shiftEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除班次表，用于存储班次的基本信息
     *
     * @author mayike
     * @date  2024/06/11 09:32
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除班次表")
    @CommonLog("删除班次表")
//    @SaCheckPermission("/oa/shift/delete")
    @PostMapping("/oa/shift/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<ShiftIdParam> shiftIdParamList) {
        shiftService.delete(shiftIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取班次表，用于存储班次的基本信息详情
     *
     * @author mayike
     * @date  2024/06/11 09:32
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取班次表")
//    @SaCheckPermission("/oa/shift/detail")
    @GetMapping("/oa/shift/detail")
    public CommonResult<Shift> detail(@Valid ShiftIdParam shiftIdParam) {
        return CommonResult.data(shiftService.detail(shiftIdParam));
    }


    @ApiOperationSupport(order = 6)
    @ApiOperation("设置班次为默认班次")
//    @SaCheckPermission("/oa/shift/detail")
    @GetMapping("/oa/shift/setDefaultShift")
    public CommonResult<String> setDefaultShift(@Valid ShiftIdParam shiftIdParam) {
        shiftService.setDefaultShift(shiftIdParam);
        return CommonResult.ok();
    }
}
