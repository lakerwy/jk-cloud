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
import vip.xiaonuo.biz.modular.car.entity.VehicleApply;
import vip.xiaonuo.biz.modular.car.service.VehicleApplyService;
import vip.xiaonuo.biz.modular.car.service.VehicleInformationService;
import vip.xiaonuo.biz.modular.car.vo.*;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 车辆-H5控制器(一些H5端专用接口)
 *
 * @author yong.ye
 * @date  2023/11/08 15:57
 */
@Api(tags = "CAR-H5移动端接口")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class CarH5Controller {

    @Resource
    private VehicleInformationService vehicleInformationService;

    @Resource
    private VehicleApplyService vehicleApplyService;

    /**
     * 首页 - 获取所有车辆信息列表
     *
     * @author yong.ye
     * @date  2023/11/08 15:57
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("首页 - 获取所有车辆信息列表")
//    @SaCheckPermission("/biz/car/carH5/getH5VehicleList")
    @GetMapping("/biz/car/carH5/getH5VehicleList")
    public CommonResult<List<H5CarDto>> getH5VehicleList() {
        return CommonResult.data(vehicleInformationService.getH5VehicleList());
    }

    /**
     * 首页 - 根据车辆Id，获取H5端车辆详情
     *
     * @author yong.ye
     * @date  2023/11/08 15:57
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("首页 - 根据车辆Id，获取H5端车辆详情")
//    @SaCheckPermission("/biz/car/carH5/getH5VehicleDetail")
    @GetMapping("/biz/car/carH5/getH5VehicleDetail")
    public CommonResult<H5VehicleDto> getH5VehicleDetail(String id) {
        return CommonResult.data(vehicleApplyService.getH5VehicleDetail(id));
    }

    /**
     * 派车单 - 获取当前司机名下的所有派车单
     *
     *  功能描述：派车单 - 获取当前司机名下的所有派车单
     *         /// 派车单状态：（1-待审核 2-待派车 3-已派车 4-已完成 5-已取消）
     *         /// DriverUserId：司机在我司系统中的UserId，SysUser.UserId
     *         /// IsShowAll：是否显示所有状态派车单，默认为true；若仅显示 【3-已派车】 的派车单，请传入 false
     *
     * @author yong.ye
     * @date  2023/11/08 15:57
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("派车单 - 获取当前司机名下的所有派车单")
//    @SaCheckPermission("/biz/car/carH5/getH5CarOrderList")
    @GetMapping("/biz/car/carH5/getH5CarOrderList")
    public CommonResult<List<H5CarOrderDto>> getH5CarOrderList(String driverUserId, Boolean IsShowAll) {
        return CommonResult.data(vehicleApplyService.getH5CarOrderList(driverUserId,IsShowAll));
    }

    /**
     * 派车单 - 根据派车单id获取当前派车单详情
     * @author yong.ye
     * @date  2023/11/08 15:57
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("派车单 - 根据派车单id获取当前派车单详情")
//    @SaCheckPermission("/biz/car/carH5/getH5CarOrderDetail")
    @GetMapping("/biz/car/carH5/getH5CarOrderDetail")
    public CommonResult<VehicleApply> getH5CarOrderDetail(String id) {
        return CommonResult.data(vehicleApplyService.getById(id));
    }


    /**
     * 派车单 - 发车登记
     *
     * @author yong.ye
     * @date  2023/11/08 15:57
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("派车单 - 发车登记")
    @CommonLog("派车单 - 发车登记")
//    @SaCheckPermission("/biz/car/carH5/drivingRegistrationStart")
    @PostMapping("/biz/car/carH5/drivingRegistrationStart")
    public CommonResult<String> drivingRegistrationStart(@RequestBody @Valid H5CarRegisterFromDto h5CarRegisterFromDto) {
        vehicleApplyService.drivingRegistrationStart(h5CarRegisterFromDto);
        return CommonResult.ok();
    }


    /**
     * 派车单 - 还车登记
     *
     * @author yong.ye
     * @date  2023/11/08 15:57
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("派车单 - 还车登记")
    @CommonLog("派车单 - 还车登记")
//    @SaCheckPermission("/biz/car/carH5/drivingRegistrationEnd")
    @PostMapping("/biz/car/carH5/drivingRegistrationEnd")
    public CommonResult<String> drivingRegistrationEnd(@RequestBody @Valid H5CarRegisterEndDto h5CarRegisterEndDto) {
        vehicleApplyService.drivingRegistrationEnd(h5CarRegisterEndDto);
        return CommonResult.ok();
    }

    /**
     * 个人设置 - 获取司机（用户Id）详情
     * @author yong.ye
     * @date  2023/11/08 15:57
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("个人设置 - 获取司机（用户Id）详情")
//    @SaCheckPermission("/biz/car/carH5/getH5UserInfo")
    @GetMapping("/biz/car/carH5/getH5UserInfo")
    public CommonResult<H5CarUserInfoDto> getH5UserInfo(String id) {
        return CommonResult.data(vehicleApplyService.getH5UserInfo(id));
    }

}
