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
package vip.xiaonuo.biz.modular.ezviz.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import vip.xiaonuo.biz.modular.ezviz.service.EzvizService;
import vip.xiaonuo.common.pojo.CommonResult;

import javax.annotation.Resource;

/**
 * 【EZVIZ】控制器-监控
 *
 * @author yong.ye
 * @date  2023/11/29 10:35
 */
@Api(tags = "【EZVIZ】控制器-监控")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class EzvizController {
    @Resource
    private EzvizService ezvizService;

    /**
     * 【EZVIZ】控制器-监控 获取token
     *
     * @author yong.ye
     * @date  2023/12/01 10:35
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("【EZVIZ】控制器-监控 获取token")
    @GetMapping("/biz/ezviz/token")
    public CommonResult<String> token() {
        return CommonResult.data(ezvizService.getToken());
    }


    /**
     * 【EZVIZ】控制器-监控 获取设备列表
     *
     * @author yong.ye
     * @date  2023/12/01 10:35
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("【EZVIZ】控制器-监控 获取设备列表")
    @GetMapping("/biz/ezviz/device/list")
    public CommonResult<Object> deviceList(Integer pageStart, Integer pageSize) {
        return CommonResult.data(ezvizService.deviceList(pageStart,pageSize));
    }


    /**
     * 【EZVIZ】控制器-监控 获取单个设备信息
     *
     * @author yong.ye
     * @date  2023/12/01 10:35
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("【EZVIZ】控制器-监控 获取单个设备信息")
    @GetMapping("/biz/ezviz/device/info")
    public CommonResult<Object> deviceInfo(String deviceSerial) {
        return CommonResult.data(ezvizService.deviceInfo(deviceSerial));
    }


    /**
     * 【EZVIZ】控制器-监控 获取视频通道列表
     *
     * @author yong.ye
     * @date  2023/12/01 10:35
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("【EZVIZ】控制器-监控 获取视频通道列表")
    @GetMapping("/biz/ezviz/camera/list")
    public CommonResult<Object> cameraList(Integer pageStart, Integer pageSize) {
        return CommonResult.data(ezvizService.cameraList(pageStart,pageSize));
    }


    /**
     * 【EZVIZ】控制器-监控 获取单个设备信息
     *
     * @author yong.ye
     * @date  2023/12/01 10:35
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("【EZVIZ】控制器-监控 获取单个设备信息")
    @GetMapping("/biz/ezviz/device/camera/list")
    public CommonResult<Object> deviceCameraList(String deviceSerial) {
        return CommonResult.data(ezvizService.deviceCameraList(deviceSerial));
    }

}
