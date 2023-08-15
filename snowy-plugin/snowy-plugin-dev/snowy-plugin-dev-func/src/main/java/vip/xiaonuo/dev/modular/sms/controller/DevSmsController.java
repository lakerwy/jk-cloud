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
package vip.xiaonuo.dev.modular.sms.controller;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gexin.rp.sdk.base.IPushResult;
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
import vip.xiaonuo.dev.modular.sms.entity.DevSms;
import vip.xiaonuo.dev.modular.sms.param.*;
import vip.xiaonuo.dev.modular.sms.service.DevSmsService;
import vip.xiaonuo.dev.modular.sms.service.JPushService;
import vip.xiaonuo.dev.modular.sms.service.UniPushService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

/**
 * 短信控制器
 *
 * @author xuyuxiang
 * @date 2022/2/23 18:26
 **/
@Api(tags = "短信控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 5)
@RestController
@Validated
public class DevSmsController {

    @Resource
    private DevSmsService devSmsService;

    @Resource
    UniPushService uniPushService;

    @Resource
    JPushService jPushService;

    /**
     * 发送短信——阿里云
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:47
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("发送阿里云短信")
    @CommonLog("发送阿里云短信")
    @PostMapping("/dev/sms/sendAliyun")
    public CommonResult<String> sendAliyun(@RequestBody @Valid DevSmsSendAliyunParam devSmsSendAliyunParam) {
        devSmsService.sendAliyun(devSmsSendAliyunParam);
        return CommonResult.ok();
    }

    /**
     * 发送短信——腾讯云
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:47
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("发送腾讯云短信")
    @CommonLog("发送腾讯云短信")
    @PostMapping("/dev/sms/sendTencent")
    public CommonResult<String> sendTencent(@RequestBody @Valid DevSmsSendTencentParam devSmsSendTencentParam) {
        devSmsService.sendTencent(devSmsSendTencentParam);
        return CommonResult.ok();
    }

    /**
     * 获取短信分页
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("获取短信分页")
    @GetMapping("/dev/sms/page")
    public CommonResult<Page<DevSms>> page(DevSmsPageParam devSmsPageParam) {
        return CommonResult.data(devSmsService.page(devSmsPageParam));
    }

    /**
     * 删除短信
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除短信")
    @CommonLog("删除短信")
    @PostMapping("/dev/sms/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                               CommonValidList<DevSmsIdParam> devSmsIdParamList) {
        devSmsService.delete(devSmsIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取短信详情
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取短信详情")
    @GetMapping("/dev/sms/detail")
    public CommonResult<DevSms> detail(@Valid DevSmsIdParam devSmsIdParam) {
        return CommonResult.data(devSmsService.detail(devSmsIdParam));
    }

    /**
     * 发送短信——云片(单一发送)
     *
     * @author yong.ye
     * @date 2023/6/27 15:47
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("发送云片短信")
    @CommonLog("发送云片短信")
    @PostMapping("/dev/sms/sendYunPian")
    public CommonResult<String> sendYunPian(@RequestBody @Valid DevSmsSendYunPianParam devSmsSendYunPianParam) {
        devSmsService.sendYunPian(devSmsSendYunPianParam);
        return CommonResult.ok();
    }

    /**
     * 发送短信——云片(单一发送)
     *
     * @author yong.ye
     * @date 2023/6/27 15:47
     */
//    @ApiOperationSupport(order = 6)
//    @ApiOperation("发送unipush推送消息")
//    @CommonLog("发送unipush推送消息")
//    @GetMapping("/dev/sms/sendUniPush")
//    public CommonResult<String> sendUniPush(String cid) {
//        String message = RandomUtil.randomString(16);
//        IPushResult iPushResult = uniPushService.singlePush(cid, message, 1);
//        //IPushResult iPushResult = uniPushService.singlePush(cid, message, 1);
////        List<String> cids = new ArrayList<>();
////        cids.add(cid);
////        jPushService.sendToAliasList(cids,"notification_title"+message,"msg_title"+message,"msg_content"+message,"exts",false);
//        System.out.println(iPushResult.getResponse().toString());
//        return CommonResult.ok();
//    }

    /**
     * 发送短信——云片(单一发送)
     *
     * @author yong.ye
     * @date 2023/6/27 15:47
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("别名绑定")
    @CommonLog("别名绑定")
    @PostMapping("/dev/sms/bindAlias")
    public CommonResult<String> bindAlias(String alias,String cid) {
        uniPushService.bindAlias(alias,cid);
        return CommonResult.ok();
    }
}
