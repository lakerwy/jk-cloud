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
package vip.xiaonuo.sys.modular.hr.jkAttendance.controller;

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
import vip.xiaonuo.sys.modular.hr.jkAttendance.entity.HrAttendanceMachine;
import vip.xiaonuo.sys.modular.hr.jkAttendance.param.HrAttendanceMachineAddParam;
import vip.xiaonuo.sys.modular.hr.jkAttendance.param.HrAttendanceMachineEditParam;
import vip.xiaonuo.sys.modular.hr.jkAttendance.param.HrAttendanceMachineIdParam;
import vip.xiaonuo.sys.modular.hr.jkAttendance.param.HrAttendanceMachinePageParam;
import vip.xiaonuo.sys.modular.hr.jkAttendance.service.HrAttendanceMachineService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 打卡机信息表控制器
 *
 * @author ljy
 * @date  2023/09/08 14:39
 */
@Api(tags = "打卡机信息表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class HrAttendanceMachineController {

    @Resource
    private HrAttendanceMachineService hrAttendanceMachineService;

    /**
     * 获取打卡机信息表分页
     *
     * @author ljy
     * @date  2023/09/08 14:39
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取打卡机信息表分页")
    //@SaCheckPermission("/sys/attendanceMachine/page")
    @GetMapping("/sys/attendanceMachine/page")
    public CommonResult<Page<HrAttendanceMachine>> page(HrAttendanceMachinePageParam hrAttendanceMachinePageParam) {
        return CommonResult.data(hrAttendanceMachineService.page(hrAttendanceMachinePageParam));
    }

    /**
     * 添加打卡机信息表
     *
     * @author ljy
     * @date  2023/09/08 14:39
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加打卡机信息表")
    @CommonLog("添加打卡机信息表")
    //@SaCheckPermission("/sys/attendanceMachine/add")
    @PostMapping("/sys/attendanceMachine/add")
    public CommonResult<String> add(@RequestBody @Valid HrAttendanceMachineAddParam hrAttendanceMachineAddParam) {
        hrAttendanceMachineService.add(hrAttendanceMachineAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑打卡机信息表
     *
     * @author ljy
     * @date  2023/09/08 14:39
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑打卡机信息表")
    @CommonLog("编辑打卡机信息表")
    //@SaCheckPermission("/sys/attendanceMachine/edit")
    @PostMapping("/sys/attendanceMachine/edit")
    public CommonResult<String> edit(@RequestBody @Valid HrAttendanceMachineEditParam hrAttendanceMachineEditParam) {
        hrAttendanceMachineService.edit(hrAttendanceMachineEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除打卡机信息表
     *
     * @author ljy
     * @date  2023/09/08 14:39
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除打卡机信息表")
    @CommonLog("删除打卡机信息表")
    //@SaCheckPermission("/sys/attendanceMachine/delete")
    @PostMapping("/sys/attendanceMachine/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<HrAttendanceMachineIdParam> hrAttendanceMachineIdParamList) {
        hrAttendanceMachineService.delete(hrAttendanceMachineIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取打卡机信息表详情
     *
     * @author ljy
     * @date  2023/09/08 14:39
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取打卡机信息表详情")
    //@SaCheckPermission("/sys/attendanceMachine/detail")
    @GetMapping("/sys/attendanceMachine/detail")
    public CommonResult<HrAttendanceMachine> detail(@Valid HrAttendanceMachineIdParam hrAttendanceMachineIdParam) {
        return CommonResult.data(hrAttendanceMachineService.detail(hrAttendanceMachineIdParam));
    }
}
