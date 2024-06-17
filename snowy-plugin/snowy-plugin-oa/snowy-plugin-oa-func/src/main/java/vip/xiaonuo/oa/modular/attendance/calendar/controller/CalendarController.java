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
package vip.xiaonuo.oa.modular.attendance.calendar.controller;

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
import vip.xiaonuo.oa.modular.attendance.calendar.entity.Calendar;
import vip.xiaonuo.oa.modular.attendance.calendar.param.CalendarAddParam;
import vip.xiaonuo.oa.modular.attendance.calendar.param.CalendarEditParam;
import vip.xiaonuo.oa.modular.attendance.calendar.param.CalendarIdParam;
import vip.xiaonuo.oa.modular.attendance.calendar.param.CalendarPageParam;
import vip.xiaonuo.oa.modular.attendance.calendar.service.CalendarService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 日历表控制器
 *
 * @author mayike
 * @date  2024/06/15 12:59
 */
@Api(tags = "日历表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class CalendarController {

    @Resource
    private CalendarService calendarService;

    /**
     * 获取日历表分页
     *
     * @author mayike
     * @date  2024/06/15 12:59
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取日历表分页")
    @SaCheckPermission("/oa/calendar/page")
    @GetMapping("/oa/calendar/page")
    public CommonResult<Page<Calendar>> page(CalendarPageParam calendarPageParam) {
        return CommonResult.data(calendarService.page(calendarPageParam));
    }

    /**
     * 添加日历表
     *
     * @author mayike
     * @date  2024/06/15 12:59
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加日历表")
    @CommonLog("添加日历表")
    @SaCheckPermission("/oa/calendar/add")
    @PostMapping("/oa/calendar/add")
    public CommonResult<String> add(@RequestBody @Valid CalendarAddParam calendarAddParam) {
        calendarService.add(calendarAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑日历表
     *
     * @author mayike
     * @date  2024/06/15 12:59
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑日历表")
    @CommonLog("编辑日历表")
    @SaCheckPermission("/oa/calendar/edit")
    @PostMapping("/oa/calendar/edit")
    public CommonResult<String> edit(@RequestBody @Valid CalendarEditParam calendarEditParam) {
        calendarService.edit(calendarEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除日历表
     *
     * @author mayike
     * @date  2024/06/15 12:59
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除日历表")
    @CommonLog("删除日历表")
    @SaCheckPermission("/oa/calendar/delete")
    @PostMapping("/oa/calendar/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<CalendarIdParam> calendarIdParamList) {
        calendarService.delete(calendarIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取日历表详情
     *
     * @author mayike
     * @date  2024/06/15 12:59
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取日历表详情")
    @SaCheckPermission("/oa/calendar/detail")
    @GetMapping("/oa/calendar/detail")
    public CommonResult<Calendar> detail(@Valid CalendarIdParam calendarIdParam) {
        return CommonResult.data(calendarService.detail(calendarIdParam));
    }
}
