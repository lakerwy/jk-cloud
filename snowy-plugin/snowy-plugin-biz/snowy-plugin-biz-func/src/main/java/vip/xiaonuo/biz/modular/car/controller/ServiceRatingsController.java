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
import vip.xiaonuo.biz.modular.car.vo.ServiceRatingsViewModel;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.car.entity.ServiceRatings;
import vip.xiaonuo.biz.modular.car.param.ServiceRatingsAddParam;
import vip.xiaonuo.biz.modular.car.param.ServiceRatingsEditParam;
import vip.xiaonuo.biz.modular.car.param.ServiceRatingsIdParam;
import vip.xiaonuo.biz.modular.car.param.ServiceRatingsPageParam;
import vip.xiaonuo.biz.modular.car.service.ServiceRatingsService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * CAR-用车服务评价控制器
 *
 * @author yong.ye
 * @date  2023/11/08 16:03
 */
@Api(tags = "CAR-用车服务评价控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class ServiceRatingsController {

    @Resource
    private ServiceRatingsService serviceRatingsService;

    /**
     * 获取CAR-用车服务评价分页
     *
     * @author yong.ye
     * @date  2023/11/08 16:03
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取CAR-用车服务评价分页")
//    @SaCheckPermission("/biz/car/serviceRatings/getServiceRatingsPageList")
    @GetMapping("/biz/car/serviceRatings/getServiceRatingsPageList")
    public CommonResult<Page<ServiceRatings>> page(ServiceRatingsPageParam serviceRatingsPageParam) {
        return CommonResult.data(serviceRatingsService.page(serviceRatingsPageParam));
    }

    /**
     * 添加CAR-用车服务评价
     *
     * @author yong.ye
     * @date  2023/11/08 16:03
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加CAR-用车服务评价")
    @CommonLog("添加CAR-用车服务评价")
//    @SaCheckPermission("/biz/car/serviceRatings/addServiceRating")
    @PostMapping("/biz/car/serviceRatings/addServiceRating")
    public CommonResult<String> add(@RequestBody @Valid ServiceRatingsAddParam serviceRatingsAddParam) {
        serviceRatingsService.add(serviceRatingsAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑CAR-用车服务评价
     *
     * @author yong.ye
     * @date  2023/11/08 16:03
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑CAR-用车服务评价")
    @CommonLog("编辑CAR-用车服务评价")
//    @SaCheckPermission("/biz/car/serviceRatings/edit")
    @PostMapping("/biz/car/serviceRatings/edit")
    public CommonResult<String> edit(@RequestBody @Valid ServiceRatingsEditParam serviceRatingsEditParam) {
        serviceRatingsService.edit(serviceRatingsEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除CAR-用车服务评价
     *
     * @author yong.ye
     * @date  2023/11/08 16:03
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除CAR-用车服务评价")
    @CommonLog("删除CAR-用车服务评价")
//    @SaCheckPermission("/biz/car/serviceRatings/deleteServiceRating")
    @PostMapping("/biz/car/serviceRatings/deleteServiceRating")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<ServiceRatingsIdParam> serviceRatingsIdParamList) {
        serviceRatingsService.delete(serviceRatingsIdParamList);
        return CommonResult.ok();
    }

    /**
     * 根据评价Id（ServiceRating.Id）获取评论详情
     *
     * @author yong.ye
     * @date  2023/11/08 16:03
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("根据评价Id（ServiceRating.Id）获取评论详情")
//    @SaCheckPermission("/biz/car/serviceRatings/getCarRatingById")
    @GetMapping("/biz/car/serviceRatings/getCarRatingById")
    public CommonResult<ServiceRatingsViewModel> detail(@Valid ServiceRatingsIdParam serviceRatingsIdParam) {
        return CommonResult.data(serviceRatingsService.detail(serviceRatingsIdParam));
    }

    /**
     * 根据申请单Id（派车单VehicleApply.Id）获取本派车单的评论详情
     *
     * @author yong.ye
     * @date  2023/11/08 16:03
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("根据申请单Id（派车单VehicleApply.Id）获取本派车单的评论详情")
//    @SaCheckPermission("/biz/car/serviceRatings/getCarRatingByVehicleApplyId")
    @GetMapping("/biz/car/serviceRatings/getCarRatingByVehicleApplyId")
    public CommonResult<ServiceRatingsViewModel> detailByApplyId(String applyId) {
        return CommonResult.data(serviceRatingsService.detailByApplyId(applyId));
    }

    /**
     * 根据司机用户Id（SysUser.UserId = 派车单VehicleApply.DriverId），获取司机名下所有派车单评论
     *
     * @author yong.ye
     * @date  2023/11/08 16:03
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("根据司机用户Id（SysUser.UserId = 派车单VehicleApply.DriverId），获取司机名下所有派车单评论")
//    @SaCheckPermission("/biz/car/serviceRatings/getCarRatingListByDriverId")
    @GetMapping("/biz/car/serviceRatings/getCarRatingListByDriverId")
    public CommonResult<List<ServiceRatingsViewModel>> detailByDriverId(String driverId) {
        return CommonResult.data(serviceRatingsService.detailByDriverId(driverId));
    }
}
