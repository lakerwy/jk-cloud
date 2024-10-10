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
import vip.xiaonuo.biz.modular.car.vo.*;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.car.entity.VehicleApply;
import vip.xiaonuo.biz.modular.car.service.VehicleApplyService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * CAR-用车申请单控制器
 *
 * @author yong.ye
 * @date  2023/11/08 16:04
 */
@Api(tags = "CAR-用车申请单控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class VehicleApplyController {

    @Resource
    private VehicleApplyService vehicleApplyService;

    /**
     * 获取CAR-用车申请单分页
     *
     * @author yong.ye
     * @date  2023/11/08 16:04
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取CAR-用车申请单分页")
//    @SaCheckPermission("/biz/car/vehicleApply/getVehicleApplyPageList")
    @GetMapping("/biz/car/vehicleApply/getVehicleApplyPageList")
    public CommonResult<Page<VehicleApply>> page(VehicleApplyPageParam vehicleApplyPageParam) {
        return CommonResult.data(vehicleApplyService.page(vehicleApplyPageParam));
    }

    /**
     * 添加CAR-用车申请单
     *
     * @author yong.ye
     * @date  2023/11/08 16:04
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加CAR-用车申请单")
    @CommonLog("添加CAR-用车申请单")
//    @SaCheckPermission("/biz/car/vehicleApply/addVehicleApply")
    @PostMapping("/biz/car/vehicleApply/addVehicleApply")
    public CommonResult<String> add(@RequestBody @Valid VehicleApplyAddParam vehicleApplyAddParam) {
        vehicleApplyService.add(vehicleApplyAddParam);
        return CommonResult.ok();
    }

    @ApiOperationSupport(order = 2)
    @ApiOperation("添加CAR-用车申请单")
    @CommonLog("添加CAR-用车申请单")
//    @SaCheckPermission("/biz/car/vehicleApply/addVehicleApply")
    @PostMapping("/biz/car/vehicleApply/add")
    public CommonResult<String> add2(@RequestBody @Valid VehicleApplyAddParam vehicleApplyAddParam) {
        vehicleApplyService.add2(vehicleApplyAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑CAR-用车申请单
     *
     * @author yong.ye
     * @date  2023/11/08 16:04
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑CAR-用车申请单")
    @CommonLog("编辑CAR-用车申请单")
//    @SaCheckPermission("/biz/car/vehicleApply/updateVehicleApply")
    @PostMapping("/biz/car/vehicleApply/updateVehicleApply")
    public CommonResult<String> edit(@RequestBody @Valid VehicleApplyEditParam vehicleApplyEditParam) {
        vehicleApplyService.edit(vehicleApplyEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除CAR-用车申请单
     *
     * @author yong.ye
     * @date  2023/11/08 16:04
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除CAR-用车申请单")
    @CommonLog("删除CAR-用车申请单")
//    @SaCheckPermission("/biz/car/vehicleApply/deleteVehicleApply")
    @PostMapping("/biz/car/vehicleApply/deleteVehicleApply")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<VehicleApplyIdParam> vehicleApplyIdParamList) {
        vehicleApplyService.delete(vehicleApplyIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取CAR-用车申请单详情
     *
     * @author yong.ye
     * @date  2023/11/08 16:04
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取CAR-用车申请单详情")
//    @SaCheckPermission("/biz/car/vehicleApply/getVehicleApplyDetail")
    @GetMapping("/biz/car/vehicleApply/getVehicleApplyDetail")
    public CommonResult<VehicleApplyDetailDto> detail(@Valid VehicleApplyIdParam vehicleApplyIdParam) {
        return CommonResult.data(vehicleApplyService.detail(vehicleApplyIdParam));
    }


    /**
     * 获取CAR-获取所有用车申请集合
     *
     * @author yong.ye
     * @date  2023/11/08 16:04
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("获取CAR-获取所有用车申请集合")
//    @SaCheckPermission("/biz/car/vehicleApply/getVehicleApplyList")
    @GetMapping("/biz/car/vehicleApply/getVehicleApplyList")
    public CommonResult<List<VehicleApply>> list() {
        return CommonResult.data(vehicleApplyService.list());
    }

    /**
     * 调整用车申请单（派车单）的状态
     *
     * @author yong.ye
     * @date  2023/11/08 16:04
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("调整用车申请单（派车单）的状态")
    @CommonLog("调整用车申请单（派车单）的状态")
//    @SaCheckPermission("/biz/car/vehicleApply/updateVehicleApplyStatus")
    @PostMapping("/biz/car/vehicleApply/updateVehicleApplyStatus")
    public CommonResult<String> updateStatus(@RequestBody @Valid UpdateApplyStatusParam updateApplyStatusParam) {
        vehicleApplyService.updateStatus(updateApplyStatusParam);
        return CommonResult.ok();
    }

    /**
     * 给申请单（用车申请单），分派车辆与驾驶员（车辆调度员分配驾驶员和车辆）
     *
     * @author yong.ye
     * @date  2023/11/08 16:04
     */
    @ApiOperationSupport(order = 8)
    @ApiOperation("给申请单（用车申请单），分派车辆与驾驶员（车辆调度员分配驾驶员和车辆）")
    @CommonLog("给申请单（用车申请单），分派车辆与驾驶员（车辆调度员分配驾驶员和车辆）")
//    @SaCheckPermission("/biz/car/vehicleApply/dispatchCarForVehicleApply")
    @PostMapping("/biz/car/vehicleApply/dispatchCarForVehicleApply")
    public CommonResult<String> dispatchCar(@RequestBody @Valid DispatchCarParam dispatchCarParam) {
        vehicleApplyService.dispatchCar(dispatchCarParam);
        return CommonResult.ok();
    }

    /**
     * 根据用户UserId，获取用车申请列表【筛选+分页】
     *
     * @author yong.ye
     * @date  2023/11/08 16:04
     */
    @ApiOperationSupport(order = 9)
    @ApiOperation("根据用户UserId，获取用车申请列表【筛选+分页】")
//    @SaCheckPermission("/biz/car/vehicleApply/getVehicleApplyPageListByUserId")
    @GetMapping("/biz/car/vehicleApply/getVehicleApplyPageListByUserId")
    public CommonResult<Page<VehicleApply>> pageByUserId(VehicleApplyPageParam vehicleApplyPageParam) {
        return CommonResult.data(vehicleApplyService.pageByUserId(vehicleApplyPageParam));
    }

    /**
     * 根据用户UserId，获取派车单待评价列表【筛选+分页】
     *
     * @author yong.ye
     * @date  2023/11/08 16:04
     */
    @ApiOperationSupport(order = 10)
    @ApiOperation("根据用户UserId，获取派车单待评价列表【筛选+分页】")
//    @SaCheckPermission("/biz/car/vehicleApply/getRatingApplyListByUserId")
    @GetMapping("/biz/car/vehicleApply/getRatingApplyListByUserId")
    public CommonResult<Page<VehicleApply>> ratePageByUserId(VehicleApplyPageParam vehicleApplyPageParam) {
        return CommonResult.data(vehicleApplyService.ratePageByUserId(vehicleApplyPageParam));
    }

    /**
     * 用车统计表
     *
     * @author yong.ye
     * @date  2023/11/08 16:04
     */
    @ApiOperationSupport(order = 11)
    @ApiOperation("用车统计表")
//    @SaCheckPermission("/biz/car/vehicleApply/getVehicleChart")
    @GetMapping("/biz/car/vehicleApply/getVehicleChart")
    public CommonResult<VehicleChartViewModel> getVehicleChart(VehicleChartParam vehicleChartParam) {
        return CommonResult.data(vehicleApplyService.getVehicleChart(vehicleChartParam));
    }

    /**
     * 油耗分摊
     *
     * @author yong.ye
     * @date  2023/11/08 16:04
     */
    @ApiOperationSupport(order = 12)
    @ApiOperation("油耗分摊")
//    @SaCheckPermission("/biz/car/vehicleApply/getAvgOilChart")
    @GetMapping("/biz/car/vehicleApply/getAvgOilChart")
    public CommonResult<AvgOilChartViewModel> getAvgOilChart(OilChartParam oilChartParam) {
        return CommonResult.data(vehicleApplyService.getAvgOilChart(oilChartParam));
    }

    /**
     * 用车预定列表
     *
     * @author yong.ye
     * @date  2023/11/08 16:04
     */
    @ApiOperationSupport(order = 13)
    @ApiOperation("用车预定列表")
//    @SaCheckPermission("/biz/car/vehicleApply/getCarPrePageList")
    @GetMapping("/biz/car/vehicleApply/getCarPrePageList")
    public CommonResult<Page<CarPreViewModel>> getCarPrePageList(CarPreParam carPreParam) {
        return CommonResult.data(vehicleApplyService.getCarPrePageList(carPreParam));
    }

    /**
     * 获取当前申请时间段，可用的车辆和司机信息
     *
     * @author yong.ye
     * @date  2023/11/08 16:04
     */
    @ApiOperationSupport(order = 14)
    @ApiOperation("获取当前申请时间段，可用的车辆和司机信息")
//    @SaCheckPermission("/biz/car/vehicleApply/getAvailableInfo")
    @GetMapping("/biz/car/vehicleApply/getAvailableInfo")
    public CommonResult<AvailableInfo> getAvailableInfo(AvailableParam availableParam) {
        return CommonResult.data(vehicleApplyService.getAvailableInfo(availableParam));
    }

}
