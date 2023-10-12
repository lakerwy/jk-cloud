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
package vip.xiaonuo.sys.modular.hr.userAttendance.controller;

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
import vip.xiaonuo.sys.modular.hr.userAttendance.vo.UserAttendanceCountEntity;
import vip.xiaonuo.sys.modular.hr.userAttendance.param.HrUserAttendanceAddParam;
import vip.xiaonuo.sys.modular.hr.userAttendance.param.HrUserAttendanceEditParam;
import vip.xiaonuo.sys.modular.hr.userAttendance.param.HrUserAttendanceIdParam;
import vip.xiaonuo.sys.modular.hr.userAttendance.param.HrUserAttendancePageParam;
import vip.xiaonuo.sys.modular.hr.userAttendance.service.HrUserAttendanceService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

/**
 * 打卡记录表控制器
 *
 * @author ljy
 * @date  2023/09/07 14:24
 */
@Api(tags = "打卡记录表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class HrUserAttendanceController {

    @Resource
    private HrUserAttendanceService hrUserAttendanceService;

    /**
     * 获取打卡记录表分页
     *
     * @author ljy
     * @date  2023/09/07 14:24
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取打卡记录表分页")
    //@SaCheckPermission("/sys/userAttendance/page")
    @GetMapping("/sys/userAttendance/page")
    public CommonResult<Page<HrUserAttendance>> page(HrUserAttendancePageParam hrUserAttendancePageParam) {
        return CommonResult.data(hrUserAttendanceService.page(hrUserAttendancePageParam));
    }

    /**
     * 添加打卡记录表
     *
     * @author ljy
     * @date  2023/09/07 14:24
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加打卡记录表")
    @CommonLog("添加打卡记录表")
    //@SaCheckPermission("/sys/userAttendance/add")
    @PostMapping("/sys/userAttendance/add")
    public CommonResult<String> add(@RequestBody @Valid HrUserAttendanceAddParam hrUserAttendanceAddParam) {
        hrUserAttendanceService.add(hrUserAttendanceAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑打卡记录表
     *
     * @author ljy
     * @date  2023/09/07 14:24
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑打卡记录表")
    @CommonLog("编辑打卡记录表")
    //@SaCheckPermission("/sys/userAttendance/edit")
    @PostMapping("/sys/userAttendance/edit")
    public CommonResult<String> edit(@RequestBody @Valid HrUserAttendanceEditParam hrUserAttendanceEditParam) {
        hrUserAttendanceService.edit(hrUserAttendanceEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除打卡记录表
     *
     * @author ljy
     * @date  2023/09/07 14:24
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除打卡记录表")
    @CommonLog("删除打卡记录表")
    //@SaCheckPermission("/sys/userAttendance/delete")
    @PostMapping("/sys/userAttendance/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<HrUserAttendanceIdParam> hrUserAttendanceIdParamList) {
        hrUserAttendanceService.delete(hrUserAttendanceIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取打卡记录表详情
     *
     * @author ljy
     * @date  2023/09/07 14:24
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取打卡记录表详情")
    //@SaCheckPermission("/sys/userAttendance/detail")
    @GetMapping("/sys/userAttendance/detail")
    public CommonResult<HrUserAttendance> detail(@Valid HrUserAttendanceIdParam hrUserAttendanceIdParam) {
        return CommonResult.data(hrUserAttendanceService.detail(hrUserAttendanceIdParam));
    }

    /**
     * 打卡
     *
     * @author ljy
     * @date  2023/09/07 14:24
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("用戶打卡接口")
    @CommonLog("用戶打卡接口")
    //@SaCheckPermission("/sys/userAttendance/attendance")
    @PostMapping("/sys/userAttendance/attendance")
    public CommonResult<String> attendance(@RequestBody @Valid HrUserAttendanceAddParam hrUserAttendanceAddParam) {
        return CommonResult.data(hrUserAttendanceService.attendance(hrUserAttendanceAddParam));
    }

    @ApiOperationSupport(order = 7)
    @ApiOperation("预留接口--传入部门ID,获取部门下面人员ID")
    @CommonLog("预留接口--传入部门ID,获取部门下面人员ID")
    //@SaCheckPermission("/sys/userAttendance/orgIdToUserIds")
    @GetMapping("/sys/userAttendance/orgIdToUserIds")
    public CommonResult<String> orgIdToUserIds(@Valid String orgId) {
        return CommonResult.data(hrUserAttendanceService.getOrgAndUserId(orgId));
    }

    @ApiOperationSupport(order = 8)
    @ApiOperation("预留接口--手动缓存考勤配置")
    @CommonLog("预留接口--手动缓存考勤配置")
    //@SaCheckPermission("/sys/userAttendance/attendanceConfigCache")
    @GetMapping("/sys/userAttendance/attendanceConfigCache")
    public CommonResult<String> attendanceConfigCache() {
        hrUserAttendanceService.attendanceConfigCache();
        return CommonResult.ok();
    }

}
