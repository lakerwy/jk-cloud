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
import vip.xiaonuo.oa.modular.attendance.shift.entity.Calendar;
import vip.xiaonuo.oa.modular.attendance.shift.param.*;
import vip.xiaonuo.oa.modular.attendance.shift.service.CalendarService;
import vip.xiaonuo.oa.modular.attendance.shift.service.ShiftService;
import vip.xiaonuo.oa.modular.attendance.shift.entity.Shift;
import vip.xiaonuo.oa.modular.attendance.shift.param.ShiftAddParam;
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

    @Resource
    private CalendarService calendarService;

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

    @ApiOperationSupport(order = 7)
    @ApiOperation("获取节假日")
//    @SaCheckPermission("/oa/shift/page")
    @GetMapping("/oa/calender/page")
    public CommonResult<Page<Calendar>> calenderPage(CalendarPageParam calendarPageParam) {
        return CommonResult.data(calendarService.page(calendarPageParam));
    }

    @ApiOperationSupport(order = 8)
    @ApiOperation("新增工作日或休息日")
//    @SaCheckPermission("/oa/shift/page")
    @GetMapping("/oa/calender/updateCalendar")
    public CommonResult<String> updateCalendar(CalendarEditParam calendarEditParam) {
        calendarService.updateCalendar(calendarEditParam);
        return CommonResult.ok();
    }
}
