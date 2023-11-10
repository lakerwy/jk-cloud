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
import vip.xiaonuo.biz.modular.car.entity.DriverBreaks;
import vip.xiaonuo.biz.modular.car.param.DriverBreaksAddParam;
import vip.xiaonuo.biz.modular.car.param.DriverBreaksEditParam;
import vip.xiaonuo.biz.modular.car.param.DriverBreaksIdParam;
import vip.xiaonuo.biz.modular.car.param.DriverBreaksPageParam;
import vip.xiaonuo.biz.modular.car.service.DriverBreaksService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * CAR-违章记录控制器
 *
 * @author yong.ye
 * @date  2023/11/08 15:58
 */
@Api(tags = "CAR-违章记录控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class DriverBreaksController {

    @Resource
    private DriverBreaksService driverBreaksService;

    /**
     * 获取CAR-违章记录分页
     *
     * @author yong.ye
     * @date  2023/11/08 15:58
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取CAR-违章记录分页")
//    @SaCheckPermission("/biz/car/driverBreaks/getDriverBreaksPageList")
    @GetMapping("/biz/car/driverBreaks/getDriverBreaksPageList")
    public CommonResult<Page<DriverBreaks>> page(DriverBreaksPageParam driverBreaksPageParam) {
        return CommonResult.data(driverBreaksService.page(driverBreaksPageParam));
    }

    /**
     * 添加CAR-违章记录
     *
     * @author yong.ye
     * @date  2023/11/08 15:58
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加CAR-违章记录")
    @CommonLog("添加CAR-违章记录")
//    @SaCheckPermission("/biz/car/driverBreaks/addDriverBreaks")
    @PostMapping("/biz/car/driverBreaks/addDriverBreaks")
    public CommonResult<String> add(@RequestBody @Valid DriverBreaksAddParam driverBreaksAddParam) {
        driverBreaksService.add(driverBreaksAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑CAR-违章记录
     *
     * @author yong.ye
     * @date  2023/11/08 15:58
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑CAR-违章记录")
    @CommonLog("编辑CAR-违章记录")
//    @SaCheckPermission("/biz/car/driverBreaks/updateDriverBreaks")
    @PostMapping("/biz/car/driverBreaks/updateDriverBreaks")
    public CommonResult<String> edit(@RequestBody @Valid DriverBreaksEditParam driverBreaksEditParam) {
        driverBreaksService.edit(driverBreaksEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除CAR-违章记录
     *
     * @author yong.ye
     * @date  2023/11/08 15:58
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除CAR-违章记录")
    @CommonLog("删除CAR-违章记录")
//    @SaCheckPermission("/biz/car/driverBreaks/deleteDriverBreaks")
    @PostMapping("/biz/car/driverBreaks/deleteDriverBreaks")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<DriverBreaksIdParam> driverBreaksIdParamList) {
        driverBreaksService.delete(driverBreaksIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取CAR-违章记录详情
     *
     * @author yong.ye
     * @date  2023/11/08 15:58
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取CAR-违章记录详情")
//    @SaCheckPermission("/biz/car/driverBreaks/getDriverBreaksDetail")
    @GetMapping("/biz/car/driverBreaks/getDriverBreaksDetail")
    public CommonResult<DriverBreaks> detail(@Valid DriverBreaksIdParam driverBreaksIdParam) {
        return CommonResult.data(driverBreaksService.detail(driverBreaksIdParam));
    }
}
