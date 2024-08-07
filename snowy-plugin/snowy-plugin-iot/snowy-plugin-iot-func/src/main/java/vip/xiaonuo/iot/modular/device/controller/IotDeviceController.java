package vip.xiaonuo.iot.modular.device.controller;

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
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.iot.modular.device.entity.IotDevice;
import vip.xiaonuo.iot.modular.device.param.*;
import vip.xiaonuo.iot.modular.device.service.IotDeviceService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 物联网-设备表控制器
 *
 * @author mayike
 * @date  2024/08/05 09:51
 */
@Api(tags = "物联网-设备表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class IotDeviceController {

    @Resource
    private IotDeviceService iotDeviceService;

    /**
     * 获取物联网-设备表分页
     *
     * @author mayike
     * @date  2024/08/05 09:51
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取物联网-设备表分页")
//    @SaCheckPermission("/iot/device/page")
    @GetMapping("/iot/device/page")
    public CommonResult<Page<IotDevice>> page(IotDevicePageParam iotDevicePageParam) {
        return CommonResult.data(iotDeviceService.page(iotDevicePageParam));
    }

    /**
     * 添加物联网-设备表
     *
     * @author mayike
     * @date  2024/08/05 09:51
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加物联网-设备表")
    @CommonLog("添加物联网-设备表")
//    @SaCheckPermission("/iot/device/add")
    @PostMapping("/iot/device/add")
    public CommonResult<String> add(@RequestBody @Valid IotDeviceAddParam iotDeviceAddParam) {
        iotDeviceService.add(iotDeviceAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑物联网-设备表
     *
     * @author mayike
     * @date  2024/08/05 09:51
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑物联网-设备表")
    @CommonLog("编辑物联网-设备表")
//    @SaCheckPermission("/iot/device/edit")
    @PostMapping("/iot/device/edit")
    public CommonResult<String> edit(@RequestBody @Valid IotDeviceEditParam iotDeviceEditParam) {
        iotDeviceService.edit(iotDeviceEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除物联网-设备表
     *
     * @author mayike
     * @date  2024/08/05 09:51
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除物联网-设备表")
    @CommonLog("删除物联网-设备表")
//    @SaCheckPermission("/iot/device/delete")
    @PostMapping("/iot/device/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<IotDeviceIdParam> iotDeviceIdParamList) {
        iotDeviceService.delete(iotDeviceIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取物联网-设备表详情
     *
     * @author mayike
     * @date  2024/08/05 09:51
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取物联网-设备表详情")
//    @SaCheckPermission("/iot/device/detail")
    @GetMapping("/iot/device/detail")
    public CommonResult<IotDevice> detail(@Valid IotDeviceIdParam iotDeviceIdParam) {
        return CommonResult.data(iotDeviceService.detail(iotDeviceIdParam));
    }

    @ApiOperationSupport(order = 6)
    @ApiOperation("开关设备-设备表")
    @CommonLog("开关设备-设备表")
//    @SaCheckPermission("/iot/device/edit")
    @PostMapping("/iot/device/operate")
    public CommonResult<String> operate(@RequestBody @Valid IotDeviceSwitchParam iotDeviceSwitchParam){
        iotDeviceService.operate(iotDeviceSwitchParam);
        return CommonResult.ok();
    }

    @ApiOperationSupport(order = 6)
    @ApiOperation("设置分组-设备表")
    @CommonLog("设置分组-设备表")
//    @SaCheckPermission("/iot/device/edit")
    @PostMapping("/iot/device/setGroup")
    public CommonResult<String> setDeviceGroup(@RequestBody @Valid IotDeviceGroupParam iotDeviceGroupParam){
        iotDeviceService.setDeviceGroup(iotDeviceGroupParam);
        return CommonResult.ok();
    }

    @ApiOperationSupport(order = 6)
    @ApiOperation("批量开关设备-设备表")
    @CommonLog("批量设备-设备表")
//    @SaCheckPermission("/iot/device/edit")
    @PostMapping("/iot/device/batchOperate")
    public CommonResult<String> batchOperate(@RequestBody @Valid IotDeviceOperateParam iotDeviceOperateParam){
        if(iotDeviceOperateParam.getIds().size()==0 && iotDeviceOperateParam.getGroupIds().size() == 0){
            throw new CommonException("{}参数错误","ids或groupIds");
        }
        iotDeviceService.batchOperate(iotDeviceOperateParam);
        return CommonResult.ok();
    }
}
