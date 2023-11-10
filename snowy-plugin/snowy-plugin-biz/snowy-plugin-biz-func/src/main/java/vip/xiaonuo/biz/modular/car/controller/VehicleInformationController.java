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
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.car.entity.VehicleInformation;
import vip.xiaonuo.biz.modular.car.param.VehicleInformationAddParam;
import vip.xiaonuo.biz.modular.car.param.VehicleInformationEditParam;
import vip.xiaonuo.biz.modular.car.param.VehicleInformationIdParam;
import vip.xiaonuo.biz.modular.car.param.VehicleInformationPageParam;
import vip.xiaonuo.biz.modular.car.service.VehicleInformationService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 车辆管理控制器
 *
 * @author yong.ye
 * @date  2023/11/08 17:17
 */
@Api(tags = "车辆管理-控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class VehicleInformationController {

    @Resource
    private VehicleInformationService vehicleInformationService;

    /**
     * 获取车辆管理-分页
     *
     * @author yong.ye
     * @date  2023/11/08 17:17
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取车辆管理-分页")
//    @SaCheckPermission("/biz/car/vehicle/getVehicleInformationPageList")
    @GetMapping("/biz/car/vehicle/getVehicleInformationPageList")
    public CommonResult<Page<VehicleInformation>> page(VehicleInformationPageParam vehicleInformationPageParam) {
        return CommonResult.data(vehicleInformationService.page(vehicleInformationPageParam));
    }

    /**
     * 添加车辆管理-
     *
     * @author yong.ye
     * @date  2023/11/08 17:17
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加车辆管理-")
    @CommonLog("添加车辆管理-")
//    @SaCheckPermission("/biz/car/vehicle/addVehicleInformation")
    @PostMapping("/biz/car/vehicle/addVehicleInformation")
    public CommonResult<String> add(@RequestBody @Valid VehicleInformationAddParam vehicleInformationAddParam) {
        vehicleInformationService.add(vehicleInformationAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑车辆管理-
     *
     * @author yong.ye
     * @date  2023/11/08 17:17
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑车辆管理-")
    @CommonLog("编辑车辆管理-")
//    @SaCheckPermission("/biz/car/vehicle/updateVehicleInformation")
    @PostMapping("/biz/car/vehicle/updateVehicleInformation")
    public CommonResult<String> edit(@RequestBody @Valid VehicleInformationEditParam vehicleInformationEditParam) {
        vehicleInformationService.edit(vehicleInformationEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除车辆管理-
     *
     * @author yong.ye
     * @date  2023/11/08 17:17
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除车辆管理-")
    @CommonLog("删除车辆管理-")
//    @SaCheckPermission("/biz/car/vehicle/deleteVehicleInformation")
    @PostMapping("/biz/car/vehicle/deleteVehicleInformation")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<VehicleInformationIdParam> vehicleInformationIdParamList) {
        vehicleInformationService.delete(vehicleInformationIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取车辆管理-详情
     *
     * @author yong.ye
     * @date  2023/11/08 17:17
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取车辆管理-详情")
//    @SaCheckPermission("/biz/car/vehicle/getVehicleInformationDetail")
    @GetMapping("/biz/car/vehicle/getVehicleInformationDetail")
    public CommonResult<VehicleInformation> detail(@Valid VehicleInformationIdParam vehicleInformationIdParam) {
        return CommonResult.data(vehicleInformationService.detail(vehicleInformationIdParam));
    }

    /**
     * 获取所有车辆信息集合
     *
     * @author yong.ye
     * @date  2023/11/08 17:17
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("获取所有车辆信息集合")
//    @SaCheckPermission("/biz/car/vehicle/getVehicleInformationList")
    @GetMapping("/biz/car/vehicle/getVehicleInformationList")
    public CommonResult<List<VehicleInformation>> list() {
        return CommonResult.data(vehicleInformationService.list());
    }
}
