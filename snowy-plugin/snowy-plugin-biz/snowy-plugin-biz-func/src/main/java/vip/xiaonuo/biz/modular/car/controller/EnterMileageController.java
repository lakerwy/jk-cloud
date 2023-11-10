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
import vip.xiaonuo.biz.modular.car.entity.EnterMileage;
import vip.xiaonuo.biz.modular.car.param.EnterMileageAddParam;
import vip.xiaonuo.biz.modular.car.param.EnterMileageEditParam;
import vip.xiaonuo.biz.modular.car.param.EnterMileageIdParam;
import vip.xiaonuo.biz.modular.car.param.EnterMileagePageParam;
import vip.xiaonuo.biz.modular.car.service.EnterMileageService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * CAR-里程补登控制器
 *
 * @author yong.ye
 * @date  2023/11/08 16:00
 */
@Api(tags = "CAR-里程补登控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class EnterMileageController {

    @Resource
    private EnterMileageService enterMileageService;

    /**
     * 获取CAR-里程补登分页
     *
     * @author yong.ye
     * @date  2023/11/08 16:00
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取CAR-里程补登分页")
//    @SaCheckPermission("/biz/car/enterMileage/getEnterMileagePageList")
    @GetMapping("/biz/car/enterMileage/getEnterMileagePageList")
    public CommonResult<Page<EnterMileage>> page(EnterMileagePageParam enterMileagePageParam) {
        return CommonResult.data(enterMileageService.page(enterMileagePageParam));
    }

    /**
     * 添加CAR-里程补登
     *
     * @author yong.ye
     * @date  2023/11/08 16:00
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加CAR-里程补登")
    @CommonLog("添加CAR-里程补登")
//    @SaCheckPermission("/biz/car/enterMileage/addEnterMileage")
    @PostMapping("/biz/car/enterMileage/addEnterMileage")
    public CommonResult<String> add(@RequestBody @Valid EnterMileageAddParam enterMileageAddParam) {
        enterMileageService.add(enterMileageAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑CAR-里程补登
     *
     * @author yong.ye
     * @date  2023/11/08 16:00
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑CAR-里程补登")
    @CommonLog("编辑CAR-里程补登")
//    @SaCheckPermission("/biz/car/enterMileage/updateEnterMileage")
    @PostMapping("/biz/car/enterMileage/updateEnterMileage")
    public CommonResult<String> edit(@RequestBody @Valid EnterMileageEditParam enterMileageEditParam) {
        enterMileageService.edit(enterMileageEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除CAR-里程补登
     *
     * @author yong.ye
     * @date  2023/11/08 16:00
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除CAR-里程补登")
    @CommonLog("删除CAR-里程补登")
//    @SaCheckPermission("/biz/car/enterMileage/deleteEnterMileage")
    @PostMapping("/biz/car/enterMileage/deleteEnterMileage")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<EnterMileageIdParam> enterMileageIdParamList) {
        enterMileageService.delete(enterMileageIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取CAR-里程补登详情
     *
     * @author yong.ye
     * @date  2023/11/08 16:00
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取CAR-里程补登详情")
//    @SaCheckPermission("/biz/car/enterMileage/getEnterMileageDetail")
    @GetMapping("/biz/car/enterMileage/getEnterMileageDetail")
    public CommonResult<EnterMileage> detail(@Valid EnterMileageIdParam enterMileageIdParam) {
        return CommonResult.data(enterMileageService.detail(enterMileageIdParam));
    }
}
