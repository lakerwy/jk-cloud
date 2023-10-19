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
package vip.xiaonuo.sys.modular.hr.userAttendanceStat.controller;

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
import vip.xiaonuo.sys.modular.hr.userAttendance.entity.HrUserAttendance;
import vip.xiaonuo.sys.modular.hr.userAttendance.param.HrUserAttendancePageParam;
import vip.xiaonuo.sys.modular.hr.userAttendance.vo.UserAttendanceCountEntity;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.entity.HrUserAttendanceStat;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.param.HrUserAttendanceStatAddParam;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.param.HrUserAttendanceStatEditParam;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.param.HrUserAttendanceStatIdParam;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.param.HrUserAttendanceStatPageParam;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.service.HrUserAttendanceStatService;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.vo.AttendanceDayStatParam;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.vo.AttendanceMonthStatParam;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.vo.AttendanceStatNumData;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 考勤统计表控制器
 *
 * @author ljy
 * @date  2023/10/07 10:28
 */
@Api(tags = "考勤统计表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class HrUserAttendanceStatController {

    @Resource
    private HrUserAttendanceStatService hrUserAttendanceStatService;

    /**
     * 获取考勤统计表分页
     *
     * @author ljy
     * @date  2023/10/07 10:28
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取考勤统计表分页")
    //@SaCheckPermission("/sys/userAttendanceStat/page")
    @GetMapping("/sys/userAttendanceStat/page")
    public CommonResult<Page<HrUserAttendanceStat>> page(HrUserAttendanceStatPageParam hrUserAttendanceStatPageParam) {
        return CommonResult.data(hrUserAttendanceStatService.page(hrUserAttendanceStatPageParam));
    }

    /**
     * 添加考勤统计表
     *
     * @author ljy
     * @date  2023/10/07 10:28
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加考勤统计表")
    @CommonLog("添加考勤统计表")
    //@SaCheckPermission("/sys/userAttendanceStat/add")
    @PostMapping("/sys/userAttendanceStat/add")
    public CommonResult<String> add(@RequestBody @Valid HrUserAttendanceStatAddParam hrUserAttendanceStatAddParam) {
        hrUserAttendanceStatService.add(hrUserAttendanceStatAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑考勤统计表
     *
     * @author ljy
     * @date  2023/10/07 10:28
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑考勤统计表")
    @CommonLog("编辑考勤统计表")
    //@SaCheckPermission("/sys/userAttendanceStat/edit")
    @PostMapping("/sys/userAttendanceStat/edit")
    public CommonResult<String> edit(@RequestBody @Valid HrUserAttendanceStatEditParam hrUserAttendanceStatEditParam) {
        hrUserAttendanceStatService.edit(hrUserAttendanceStatEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除考勤统计表
     *
     * @author ljy
     * @date  2023/10/07 10:28
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除考勤统计表")
    @CommonLog("删除考勤统计表")
    //@SaCheckPermission("/sys/userAttendanceStat/delete")
    @PostMapping("/sys/userAttendanceStat/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<HrUserAttendanceStatIdParam> hrUserAttendanceStatIdParamList) {
        hrUserAttendanceStatService.delete(hrUserAttendanceStatIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取考勤统计表详情
     *
     * @author ljy
     * @date  2023/10/07 10:28
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取考勤统计表详情")
    //@SaCheckPermission("/sys/userAttendanceStat/detail")
    @GetMapping("/sys/userAttendanceStat/detail")
    public CommonResult<HrUserAttendanceStat> detail(@Valid HrUserAttendanceStatIdParam hrUserAttendanceStatIdParam) {
        return CommonResult.data(hrUserAttendanceStatService.detail(hrUserAttendanceStatIdParam));
    }

    /**
     * 获取考勤统计天数据
     * @author ljy
     * @date  2023/10/10 16:28
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("获取考勤统计天数据")
    //@SaCheckPermission("/sys/userAttendanceStat/detail")
    @GetMapping("/sys/userAttendanceStat/dayStatInfo")
    public CommonResult<Map<String,Object>> dayStatInfo() {
        return CommonResult.data(hrUserAttendanceStatService.dayStatInfo());
    }

    /**
     * 用戶每天打卡详情
     * @author ljy
     * @date  2023/09/07 14:24
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("用戶每天打卡详情数据")
    @CommonLog("用戶每天打卡详情数据")
    //@SaCheckPermission("/sys/userAttendance/attendancedayinfo")
    @GetMapping("/sys/userAttendance/attendancedayinfo")
    public CommonResult<Map<String, List<HrUserAttendance>>> attendanceDayInfo(@Valid HrUserAttendancePageParam hrUserAttendancePageParam) {
        return CommonResult.data(hrUserAttendanceStatService.attendanceDayInfo(hrUserAttendancePageParam));
    }

    /**
     * 用戶打卡月统计接口
     *
     * @author ljy
     * @date  2023/09/07 14:24
     */
    @ApiOperationSupport(order = 8)
    @ApiOperation("用戶打卡月统计接口")
    @CommonLog("用戶打卡月统计接口")
    //@SaCheckPermission("/sys/userAttendance/attendancemonthcount")
    @GetMapping("/sys/userAttendance/attendancemonthcount")
    public CommonResult<List<UserAttendanceCountEntity>> attendanceMonthCount(@Valid HrUserAttendancePageParam hrUserAttendancePageParam) {
        return CommonResult.data(hrUserAttendanceStatService.attendanceMonthCount(hrUserAttendancePageParam));
    }


    /**
     * 用户考勤概览接口
     *
     * @author ljy
     * @date  2023/09/07 15:24
     * */
    @ApiOperationSupport(order = 9)
    @ApiOperation("用户考勤概览接口")
    @CommonLog("用户考勤概览接口")
    //@SaCheckPermission("/sys/userAttendance/attendanceDayPreview")
    @GetMapping("/sys/userAttendance/attendanceDayPreview")
    public CommonResult<AttendanceStatNumData> attendanceDayPreview() {
        return CommonResult.data(hrUserAttendanceStatService.attendanceDayPreview());
    }

    /**
     * 用户考勤概览每日统计接口
     *
     * @author ljy
     * @date  2023/10/18 14:24
     * */
    @ApiOperationSupport(order = 10)
    @ApiOperation("用户考勤概览-每日统计接口日期列表")
    @CommonLog("用户考勤概览-每日统计接口日期列表")
    //@SaCheckPermission("/sys/userAttendance/dayStatPreview")
    @GetMapping("/sys/userAttendance/statPreviewDateList")
    public CommonResult<LinkedHashMap<String,Object>> statPreviewDateList(@Valid AttendanceDayStatParam attendanceDayStatParam) {
        return CommonResult.data(hrUserAttendanceStatService.statPreviewDateList(attendanceDayStatParam));
    }

    /**
     * 用户考勤概览每日统计接口
     *
     * @author ljy
     * @date  2023/10/16 16:24
     * */
    @ApiOperationSupport(order = 11)
    @ApiOperation("用户考勤概览-每日统计接口")
    @CommonLog("用户考勤概览-每日统计接口")
    //@SaCheckPermission("/sys/userAttendance/dayStatPreview")
    @GetMapping("/sys/userAttendance/dayStatPreview")
    public CommonResult<Page<AttendanceDayStatParam>> dayStatPreview(@Valid AttendanceDayStatParam attendanceDayStatParam) {
        return CommonResult.data(hrUserAttendanceStatService.dayStatPreview(attendanceDayStatParam));
    }

    /**
     * 用户考勤概览- 月统计接口
     *
     * @author ljy
     * @date  2023/10/16 16:24
     * */
    @ApiOperationSupport(order = 12)
    @ApiOperation("用户考勤概览-月统计接口")
    @CommonLog("用户考勤概览-月统计接口")
    //@SaCheckPermission("/sys/userAttendance/dayStatPreview")
    @GetMapping("/sys/userAttendance/monthStatPreview")
    public CommonResult<List<AttendanceMonthStatParam>> monthStatPreview(@Valid AttendanceMonthStatParam attendanceMonthStatParam) {
        return CommonResult.data(hrUserAttendanceStatService.monthStatPreview(attendanceMonthStatParam));
    }
}
