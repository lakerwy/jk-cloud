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
package vip.xiaonuo.biz.modular.car.controller;

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
import vip.xiaonuo.biz.modular.car.param.*;
import vip.xiaonuo.biz.modular.car.vo.FeeChartViewModel;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.car.entity.VehicleFees;
import vip.xiaonuo.biz.modular.car.service.VehicleFeesService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * CAR-费用管理控制器
 *
 * @author yong.ye
 * @date  2023/11/08 16:06
 */
@Api(tags = "CAR-费用管理控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class VehicleFeesController {

    @Resource
    private VehicleFeesService vehicleFeesService;

    /**
     * 获取CAR-费用管理分页
     *
     * @author yong.ye
     * @date  2023/11/08 16:06
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取CAR-费用管理分页")
//    @SaCheckPermission("/biz/car/vehicleFees/getVehicleFeesPageList")
    @GetMapping("/biz/car/vehicleFees/getVehicleFeesPageList")
    public CommonResult<Page<VehicleFees>> page(VehicleFeesPageParam vehicleFeesPageParam) {
        return CommonResult.data(vehicleFeesService.page(vehicleFeesPageParam));
    }

    /**
     * 添加CAR-费用管理
     *
     * @author yong.ye
     * @date  2023/11/08 16:06
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加CAR-费用管理")
    @CommonLog("添加CAR-费用管理")
//    @SaCheckPermission("/biz/car/vehicleFees/addVehicleFees")
    @PostMapping("/biz/car/vehicleFees/addVehicleFees")
    public CommonResult<String> add(@RequestBody @Valid VehicleFeesAddParam vehicleFeesAddParam) {
        vehicleFeesService.add(vehicleFeesAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑CAR-费用管理
     *
     * @author yong.ye
     * @date  2023/11/08 16:06
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑CAR-费用管理")
    @CommonLog("编辑CAR-费用管理")
//    @SaCheckPermission("/biz/car/vehicleFees/updateVehicleFees")
    @PostMapping("/biz/car/vehicleFees/updateVehicleFees")
    public CommonResult<String> edit(@RequestBody @Valid VehicleFeesEditParam vehicleFeesEditParam) {
        vehicleFeesService.edit(vehicleFeesEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除CAR-费用管理
     *
     * @author yong.ye
     * @date  2023/11/08 16:06
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除CAR-费用管理")
    @CommonLog("删除CAR-费用管理")
//    @SaCheckPermission("/biz/car/vehicleFees/deleteVehicleFees")
    @PostMapping("/biz/car/vehicleFees/deleteVehicleFees")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<VehicleFeesIdParam> vehicleFeesIdParamList) {
        vehicleFeesService.delete(vehicleFeesIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取CAR-费用管理详情
     *
     * @author yong.ye
     * @date  2023/11/08 16:06
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取CAR-费用管理详情")
//    @SaCheckPermission("/biz/car/vehicleFees/getVehicleFeesDetail")
    @GetMapping("/biz/car/vehicleFees/getVehicleFeesDetail")
    public CommonResult<VehicleFees> detail(@Valid VehicleFeesIdParam vehicleFeesIdParam) {
        return CommonResult.data(vehicleFeesService.detail(vehicleFeesIdParam));
    }

    /**
     * 费用统计表
     *
     * @author yong.ye
     * @date  2023/11/08 16:06
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("费用统计表")
//    @SaCheckPermission("/biz/car/vehicleFees/getFeeChart")
    @GetMapping("/biz/car/vehicleFees/getFeeChart")
    public CommonResult<FeeChartViewModel> getFeeChart(@Valid FeeChartParam feeChartParam) {
        return CommonResult.data(vehicleFeesService.getFeeChart(feeChartParam));
    }
}
