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
package vip.xiaonuo.dev.modular.message.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.messaging.MessageHeaders;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.common.pojo.RabbitDelayedMessage;
import vip.xiaonuo.common.pojo.RabbitMsg;
import vip.xiaonuo.dev.modular.message.entity.DevMessage;
import vip.xiaonuo.dev.modular.message.param.DevMessageIdParam;
import vip.xiaonuo.dev.modular.message.param.DevMessagePageParam;
import vip.xiaonuo.dev.modular.message.param.DevMessageSendParam;
import vip.xiaonuo.dev.modular.message.result.DevMessageResult;
import vip.xiaonuo.dev.modular.message.service.DevMessageService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Map;

/**
 * 站内信控制器
 *
 * @author xuyuxiang
 * @date 2022/6/21 14:57
 **/
@Api(tags = "站内信控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 6)
@RestController
@Validated
public class DevMessageController {

    @Resource
    private DevMessageService devMessageService;

    @Resource
    private StreamBridge streamBridge;
    /**
     * 发送站内信
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:47
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("发送站内信")
    @CommonLog("发送站内信")
    @PostMapping("/dev/message/send")
    public CommonResult<String> send(@RequestBody @Valid DevMessageSendParam devMessageSendParam) {
        devMessageService.send(devMessageSendParam);
        return CommonResult.ok();
    }

    /**
     * 获取站内信分页
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("获取站内信分页")
    @GetMapping("/dev/message/page")
    public CommonResult<Page<DevMessage>> page(DevMessagePageParam devMessagePageParam) {
        return CommonResult.data(devMessageService.page(devMessagePageParam));
    }

    /**
     * 删除站内信
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("删除站内信")
    @CommonLog("删除站内信")
    @PostMapping("/dev/message/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                               CommonValidList<DevMessageIdParam> devMessageIdParamList) {
        devMessageService.delete(devMessageIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取站内信详情
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("获取站内信详情")
    @GetMapping("/dev/message/detail")
    public CommonResult<DevMessageResult> detail(@Valid DevMessageIdParam devMessageIdParam) {
        return CommonResult.data(devMessageService.detail(devMessageIdParam));
    }

    /**
     * 获取站内信详情
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("测试发送消息到延迟队列")
    @GetMapping("/dev/message/sendDelayMessage")
    public CommonResult<String> sendDelayMessage() {
        RabbitDelayedMessage rabbitDelayedMessage = new RabbitDelayedMessage();
        rabbitDelayedMessage.setTaskKey("12121212");
//        rabbitDelayedMessage.setContent("测试哈哈哈哈----deplay");
//        Map<String, Object> customHeaders = new HashMap<>();
//        customHeaders.put("x-delay", 30);

        // 使用 MessageBuilder 创建带有负载和自定义头部的消息
        Message<RabbitDelayedMessage> message = MessageBuilder
                .withPayload(rabbitDelayedMessage)
                .setHeader("x-delay", 60000)
//                .copyHeaders(customHeaders)
                .build();
        streamBridge.send("delayedMessage-out-0", message);
        return CommonResult.ok();
    }

    @ApiOperationSupport(order = 5)
    @ApiOperation("测试发送消息到普通队列")
    @GetMapping("/dev/message/sendMessage")
    public CommonResult<String> sendMessage() {
        RabbitMsg rabbitMsg = new RabbitMsg();
        rabbitMsg.setTitle("测hi是hi  哈哈哈哈哈哈--rabbitmsg");
//        Message<RabbitMsg> message = MessageBuilder
//                .withPayload(rabbitMsg)
//                .setHeader("x-delay", 60000)
////                .copyHeaders(customHeaders)
//                .build();
        streamBridge.send("jkYunOutPut-test", rabbitMsg);
        return CommonResult.ok();
    }
}
