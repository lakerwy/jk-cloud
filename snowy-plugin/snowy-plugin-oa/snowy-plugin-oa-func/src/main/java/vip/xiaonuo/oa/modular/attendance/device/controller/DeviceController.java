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
package vip.xiaonuo.oa.modular.attendance.device.controller;

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
import vip.xiaonuo.oa.modular.attendance.device.entity.Device;
import vip.xiaonuo.oa.modular.attendance.device.param.DeviceAddParam;
import vip.xiaonuo.oa.modular.attendance.device.param.DeviceEditParam;
import vip.xiaonuo.oa.modular.attendance.device.param.DeviceIdParam;
import vip.xiaonuo.oa.modular.attendance.device.param.DevicePageParam;
import vip.xiaonuo.oa.modular.attendance.device.service.DeviceService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 考勤设备控制器
 *
 * @author mayike
 * @date  2024/06/04 15:30
 */
@Api(tags = "考勤设备控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class DeviceController {

    @Resource
    private DeviceService deviceService;

    /**
     * 获取考勤设备分页
     *
     * @author mayike
     * @date  2024/06/04 15:30
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取考勤设备分页")
    @SaCheckPermission("/oa/device/page")
    @GetMapping("/oa/device/page")
    public CommonResult<Page<Device>> page(DevicePageParam devicePageParam) {
        return CommonResult.data(deviceService.page(devicePageParam));
    }

    /**
     * 添加考勤设备
     *
     * @author mayike
     * @date  2024/06/04 15:30
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加考勤设备")
    @CommonLog("添加考勤设备")
    @SaCheckPermission("/oa/device/add")
    @PostMapping("/oa/device/add")
    public CommonResult<String> add(@RequestBody @Valid DeviceAddParam deviceAddParam) {
        deviceService.add(deviceAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑考勤设备
     *
     * @author mayike
     * @date  2024/06/04 15:30
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑考勤设备")
    @CommonLog("编辑考勤设备")
    @SaCheckPermission("/oa/device/edit")
    @PostMapping("/oa/device/edit")
    public CommonResult<String> edit(@RequestBody @Valid DeviceEditParam deviceEditParam) {
        deviceService.edit(deviceEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除考勤设备
     *
     * @author mayike
     * @date  2024/06/04 15:30
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除考勤设备")
    @CommonLog("删除考勤设备")
    @SaCheckPermission("/oa/device/delete")
    @PostMapping("/oa/device/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                       CommonValidList<DeviceIdParam> deviceIdParamList) {
        deviceService.delete(deviceIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取考勤设备详情
     *
     * @author mayike
     * @date  2024/06/04 15:30
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取考勤设备详情")
    @SaCheckPermission("/oa/device/detail")
    @GetMapping("/oa/device/detail")
    public CommonResult<Device> detail(@Valid DeviceIdParam deviceIdParam) {
        return CommonResult.data(deviceService.detail(deviceIdParam));
    }
}
