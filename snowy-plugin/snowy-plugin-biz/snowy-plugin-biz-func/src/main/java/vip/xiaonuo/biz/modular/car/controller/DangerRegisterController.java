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
import vip.xiaonuo.biz.modular.car.entity.DangerRegister;
import vip.xiaonuo.biz.modular.car.param.DangerRegisterAddParam;
import vip.xiaonuo.biz.modular.car.param.DangerRegisterEditParam;
import vip.xiaonuo.biz.modular.car.param.DangerRegisterIdParam;
import vip.xiaonuo.biz.modular.car.param.DangerRegisterPageParam;
import vip.xiaonuo.biz.modular.car.service.DangerRegisterService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * CAR-出险登记控制器
 *
 * @author yong.ye
 * @date  2023/11/08 15:49
 */
@Api(tags = "CAR-出险登记控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class DangerRegisterController {

    @Resource
    private DangerRegisterService dangerRegisterService;

    /**
     * 获取CAR-出险登记分页
     *
     * @author yong.ye
     * @date  2023/11/08 15:49
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取CAR-出险登记分页")
//    @SaCheckPermission("/biz/car/dangerRegister/getDangerRegisterPageList")
    @GetMapping("/biz/car/dangerRegister/getDangerRegisterPageList")
    public CommonResult<Page<DangerRegister>> page(DangerRegisterPageParam dangerRegisterPageParam) {
        return CommonResult.data(dangerRegisterService.page(dangerRegisterPageParam));
    }

    /**
     * 添加CAR-出险登记
     *
     * @author yong.ye
     * @date  2023/11/08 15:49
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加CAR-出险登记")
    @CommonLog("添加CAR-出险登记")
//    @SaCheckPermission("/biz/car/dangerRegister/addDangerRegister")
    @PostMapping("/biz/car/dangerRegister/addDangerRegister")
    public CommonResult<String> add(@RequestBody @Valid DangerRegisterAddParam dangerRegisterAddParam) {
        dangerRegisterService.add(dangerRegisterAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑CAR-出险登记
     *
     * @author yong.ye
     * @date  2023/11/08 15:49
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑CAR-出险登记")
    @CommonLog("编辑CAR-出险登记")
//    @SaCheckPermission("/biz/car/dangerRegister/updateDangerRegister")
    @PostMapping("/biz/car/dangerRegister/updateDangerRegister")
    public CommonResult<String> edit(@RequestBody @Valid DangerRegisterEditParam dangerRegisterEditParam) {
        dangerRegisterService.edit(dangerRegisterEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除CAR-出险登记
     *
     * @author yong.ye
     * @date  2023/11/08 15:49
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除CAR-出险登记")
    @CommonLog("删除CAR-出险登记")
//    @SaCheckPermission("/biz/car/dangerRegister/deleteDangerRegister")
    @PostMapping("/biz/car/dangerRegister/deleteDangerRegister")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<DangerRegisterIdParam> dangerRegisterIdParamList) {
        dangerRegisterService.delete(dangerRegisterIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取CAR-出险登记详情
     *
     * @author yong.ye
     * @date  2023/11/08 15:49
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取CAR-出险登记详情")
//    @SaCheckPermission("/biz/car/dangerRegister/getDangerRegisterDetail")
    @GetMapping("/biz/car/dangerRegister/getDangerRegisterDetail")
    public CommonResult<DangerRegister> detail(@Valid DangerRegisterIdParam dangerRegisterIdParam) {
        return CommonResult.data(dangerRegisterService.detail(dangerRegisterIdParam));
    }
}
