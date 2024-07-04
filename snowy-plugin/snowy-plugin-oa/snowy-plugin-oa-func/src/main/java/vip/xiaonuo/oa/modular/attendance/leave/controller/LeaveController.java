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
package vip.xiaonuo.oa.modular.attendance.leave.controller;

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
import vip.xiaonuo.oa.modular.attendance.leave.entity.Holiday;
import vip.xiaonuo.oa.modular.attendance.leave.entity.Leave;
import vip.xiaonuo.oa.modular.attendance.leave.param.*;
import vip.xiaonuo.oa.modular.attendance.leave.service.HolidayService;
import vip.xiaonuo.oa.modular.attendance.leave.service.LeaveService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 请假记录表控制器
 *
 * @author mayike
 * @date  2024/06/24 16:55
 */
@Api(tags = "请假记录表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class LeaveController {

    @Resource
    private LeaveService leaveService;

    @Resource
    private HolidayService holidayService;

    /**
     * 获取请假记录表分页
     *
     * @author mayike
     * @date  2024/06/24 16:55
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取请假记录表分页")
//    @SaCheckPermission("/oa/leave/page")
    @GetMapping("/oa/leave/page")
    public CommonResult<Page<Leave>> page(LeavePageParam leavePageParam) {
        return CommonResult.data(leaveService.page(leavePageParam));
    }

    /**
     * 添加请假记录表
     *
     * @author mayike
     * @date  2024/06/24 16:55
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加请假记录表")
    @CommonLog("添加请假记录表")
//    @SaCheckPermission("/oa/leave/add")
    @PostMapping("/oa/leave/add")
    public CommonResult<String> add(@RequestBody @Valid LeaveAddParam leaveAddParam) {
        leaveService.add(leaveAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑请假记录表
     *
     * @author mayike
     * @date  2024/06/24 16:55
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑请假记录表")
    @CommonLog("编辑请假记录表")
//    @SaCheckPermission("/oa/leave/edit")
    @PostMapping("/oa/leave/edit")
    public CommonResult<String> edit(@RequestBody @Valid LeaveEditParam leaveEditParam) {
        leaveService.edit(leaveEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除请假记录表
     *
     * @author mayike
     * @date  2024/06/24 16:55
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除请假记录表")
    @CommonLog("删除请假记录表")
//    @SaCheckPermission("/oa/leave/delete")
    @PostMapping("/oa/leave/delete")
    public CommonResult<String> deleteLeave(@RequestBody @Valid @NotEmpty(message = "集合不能为空") CommonValidList<LeaveIdParam> leaveIdParamList) {
        leaveService.delete(leaveIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取请假记录表详情
     *
     * @author mayike
     * @date  2024/06/24 16:55
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取请假记录表详情")
//    @SaCheckPermission("/oa/leave/detail")
    @GetMapping("/oa/leave/detail")
    public CommonResult<Leave> detail(@Valid LeaveIdParam leaveIdParam) {
        return CommonResult.data(leaveService.detail(leaveIdParam));
    }

    /**
     * 获取公司员工假期数据表分页
     *
     * @author mayike
     * @date  2024/06/24 17:33
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取公司员工假期数据表分页")
    @SaCheckPermission("/oa/holiday/page")
    @GetMapping("/oa/holiday/page")
    public CommonResult<Page<Holiday>> page(HolidayPageParam holidayPageParam) {
        return CommonResult.data(holidayService.page(holidayPageParam));
    }

    /**
     * 添加公司员工假期数据表
     *
     * @author mayike
     * @date  2024/06/24 17:33
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加公司员工假期数据表")
    @CommonLog("添加公司员工假期数据表")
    @SaCheckPermission("/oa/holiday/add")
    @PostMapping("/oa/holiday/add")
    public CommonResult<String> add(@RequestBody @Valid HolidayAddParam holidayAddParam) {
        holidayService.add(holidayAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑公司员工假期数据表
     *
     * @author mayike
     * @date  2024/06/24 17:33
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑公司员工假期数据表")
    @CommonLog("编辑公司员工假期数据表")
    @SaCheckPermission("/oa/holiday/edit")
    @PostMapping("/oa/holiday/edit")
    public CommonResult<String> edit(@RequestBody @Valid HolidayEditParam holidayEditParam) {
        holidayService.edit(holidayEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除公司员工假期数据表
     *
     * @author mayike
     * @date  2024/06/24 17:33
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除公司员工假期数据表")
    @CommonLog("删除公司员工假期数据表")
    @SaCheckPermission("/oa/holiday/delete")
    @PostMapping("/oa/holiday/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                       CommonValidList<HolidayIdParam> holidayIdParamList) {
        holidayService.delete(holidayIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取公司员工假期数据表详情
     *
     * @author mayike
     * @date  2024/06/24 17:33
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取公司员工假期数据表详情")
    @SaCheckPermission("/oa/holiday/detail")
    @GetMapping("/oa/holiday/detail")
    public CommonResult<Holiday> detail(@Valid HolidayIdParam holidayIdParam) {
        return CommonResult.data(holidayService.detail(holidayIdParam));
    }
}
