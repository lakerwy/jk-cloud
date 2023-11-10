///*
// * Copyright [2022] [https://www.xiaonuo.vip]
// *
// * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
// *
// * 1.请不要删除和修改根目录下的LICENSE文件。
// * 2.请不要删除和修改Snowy源码头部的版权声明。
// * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
// * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
// * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
// * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
// */
//package vip.xiaonuo.biz.modular.car.controller;
//
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
//import com.github.xiaoymin.knife4j.annotations.ApiSupport;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//import vip.xiaonuo.biz.modular.car.entity.Drivers;
//import vip.xiaonuo.biz.modular.car.param.DriversAddParam;
//import vip.xiaonuo.biz.modular.car.param.DriversEditParam;
//import vip.xiaonuo.biz.modular.car.param.DriversIdParam;
//import vip.xiaonuo.biz.modular.car.param.DriversPageParam;
//import vip.xiaonuo.biz.modular.car.service.DriversService;
//import vip.xiaonuo.common.annotation.CommonLog;
//import vip.xiaonuo.common.pojo.CommonResult;
//import vip.xiaonuo.common.pojo.CommonValidList;
//
//import javax.annotation.Resource;
//import javax.validation.Valid;
//import javax.validation.constraints.NotEmpty;
//import java.util.List;
//
///**
// * CAR-驾驶员控制器
// *
// * @author yong.ye
// * @date  2023/11/08 15:57
// */
//@Api(tags = "CAR-移动端接口")
//@ApiSupport(author = "SNOWY_TEAM", order = 1)
//@RestController
//@Validated
//public class CarH5Controller {
//
//    @Resource
//    private DriversService driversService;
//
//    /**
//     * 获取CAR-驾驶员分页
//     *
//     * @author yong.ye
//     * @date  2023/11/08 15:57
//     */
//    @ApiOperationSupport(order = 1)
//    @ApiOperation("获取CAR-驾驶员分页")
////    @SaCheckPermission("/biz/car/drivers/getDriversPageList")
//    @GetMapping("/biz/car/drivers/getDriversPageList")
//    public CommonResult<Page<Drivers>> page(DriversPageParam driversPageParam) {
//        return CommonResult.data(driversService.page(driversPageParam));
//    }
//
//    /**
//     * 添加CAR-驾驶员
//     *
//     * @author yong.ye
//     * @date  2023/11/08 15:57
//     */
//    @ApiOperationSupport(order = 2)
//    @ApiOperation("添加CAR-驾驶员")
//    @CommonLog("添加CAR-驾驶员")
////    @SaCheckPermission("/biz/car/drivers/addDrivers")
//    @PostMapping("/biz/car/drivers/addDrivers")
//    public CommonResult<String> add(@RequestBody @Valid DriversAddParam driversAddParam) {
//        driversService.add(driversAddParam);
//        return CommonResult.ok();
//    }
//
//    /**
//     * 编辑CAR-驾驶员
//     *
//     * @author yong.ye
//     * @date  2023/11/08 15:57
//     */
//    @ApiOperationSupport(order = 3)
//    @ApiOperation("编辑CAR-驾驶员")
//    @CommonLog("编辑CAR-驾驶员")
////    @SaCheckPermission("/biz/car/drivers/updateDrivers")
//    @PostMapping("/biz/car/drivers/updateDrivers")
//    public CommonResult<String> edit(@RequestBody @Valid DriversEditParam driversEditParam) {
//        driversService.edit(driversEditParam);
//        return CommonResult.ok();
//    }
//
//    /**
//     * 删除CAR-驾驶员
//     *
//     * @author yong.ye
//     * @date  2023/11/08 15:57
//     */
//    @ApiOperationSupport(order = 4)
//    @ApiOperation("删除CAR-驾驶员")
//    @CommonLog("删除CAR-驾驶员")
////    @SaCheckPermission("/biz/car/drivers/deleteDrivers")
//    @PostMapping("/biz/car/drivers/deleteDrivers")
//    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
//                                                   CommonValidList<DriversIdParam> driversIdParamList) {
//        driversService.delete(driversIdParamList);
//        return CommonResult.ok();
//    }
//
//    /**
//     * 获取CAR-驾驶员详情
//     *
//     * @author yong.ye
//     * @date  2023/11/08 15:57
//     */
//    @ApiOperationSupport(order = 5)
//    @ApiOperation("获取CAR-驾驶员详情")
////    @SaCheckPermission("/biz/car/drivers/getDriversDetail")
//    @GetMapping("/biz/car/drivers/getDriversDetail")
//    public CommonResult<Drivers> detail(@Valid DriversIdParam driversIdParam) {
//        return CommonResult.data(driversService.detail(driversIdParam));
//    }
//
//    /**
//     * 获取CAR-驾驶员列表
//     *
//     * @author yong.ye
//     * @date  2023/11/08 15:57
//     */
//    @ApiOperationSupport(order = 6)
//    @ApiOperation("获取CAR-驾驶员列表")
////    @SaCheckPermission("/biz/car/drivers/getDriversList")
//    @GetMapping("/biz/car/drivers/getDriversList")
//    public CommonResult<List<Drivers>> list() {
//        return CommonResult.data(driversService.list());
//    }
//}
