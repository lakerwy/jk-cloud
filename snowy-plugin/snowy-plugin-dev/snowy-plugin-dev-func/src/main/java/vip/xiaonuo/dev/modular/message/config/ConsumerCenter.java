package vip.xiaonuo.dev.modular.message.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.MessageBuilder;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.consts.RabbitConstant;
import vip.xiaonuo.common.pojo.RabbitDelayedMessage;
import vip.xiaonuo.common.pojo.RabbitMsg;
import vip.xiaonuo.dev.api.IDingDingService;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/12/7 10:58
 */
@Configuration
@Slf4j
public class ConsumerCenter {

    @Resource
    IDingDingService iDingDingService;

    @Resource
    CommonCacheOperator commonCacheOperator;

    @Resource
    StreamBridge streamBridge;


    @Bean
    public Consumer<RabbitMsg> sink(){
        return rabbitMsg -> {
            boolean flag = commonCacheOperator.isValueInSet(RabbitConstant.RABBIT_MESSAGE_ID, rabbitMsg.getUuid());
//            if(!flag){
//                log.info("------消息已被消费-----------"+rabbitMsg.getUuid()+rabbitMsg.getTitle());
//                commonCacheOperator.removeFromSet(RabbitConstant.RABBIT_MESSAGE_ID,rabbitMsg.getUuid());
//            }else {
//                log.info("------接受到消息----- 开始发送-----------" + rabbitMsg.getTitle());
//                iDingDingService.commonSend(rabbitMsg);
//            }
            log.info("------接受到消息----- 开始发送-----------" + rabbitMsg.getTitle());
            iDingDingService.commonSend(rabbitMsg);
        };
    }

    @Bean
    public Consumer<Message<RabbitDelayedMessage>> delayedMessage() {
        return message -> {
            RabbitDelayedMessage rabbitDelayedMessage = message.getPayload();
            String strRabbitMsg = "";
            try {
                strRabbitMsg = rabbitDelayedMessage.getRabbitMsg().toString();
            }catch (Exception e){
                strRabbitMsg = e.getMessage();
            }
            log.info("接受到延迟队列的消息: " + rabbitDelayedMessage.getTaskKey() + " 【消息体】：" + strRabbitMsg);

            String taskKey = rabbitDelayedMessage.getTaskKey();
            Object o = commonCacheOperator.get(taskKey);
            if (ObjectUtil.isNotEmpty(o) && !Boolean.parseBoolean(String.valueOf(o))) {
                //未办理，发送消息，继续放入缓存中
                Message<RabbitDelayedMessage> newMessage = MessageBuilder
                        .withPayload(rabbitDelayedMessage)
                        .setHeader("x-delay", rabbitDelayedMessage.getLimit() *60*60*1000)
//                .copyHeaders(customHeaders)
//                        .setHeader("x-delay", 2*60*1000)
                        .build();

                streamBridge.send(RabbitConstant.DEFAULT_DELAYED_CHANNEL, newMessage);
                rabbitDelayedMessage.getRabbitMsg().setUuid(UUID.randomUUID(Boolean.FALSE).toString());
                commonCacheOperator.addToSet(RabbitConstant.RABBIT_MESSAGE_ID,rabbitDelayedMessage.getRabbitMsg().getUuid());
                iDingDingService.commonSend(rabbitDelayedMessage.getRabbitMsg());
            } else {
                // 处理接收到的延迟消息
                log.info("Received delayed message: " + rabbitDelayedMessage.getTaskKey());
            }
//            boolean flag = commonCacheOperator.isValueInSet(RabbitConstant.RABBIT_MESSAGE_ID, rabbitDelayedMessage.getRabbitMsg().getUuid());
//            log.info("消息信息----"+rabbitDelayedMessage.getRabbitMsg().toString());
//            if(!flag){
//                log.info("------消息已被消费-----------"+rabbitDelayedMessage.getRabbitMsg()+rabbitDelayedMessage.getRabbitMsg().getTitle());
//                commonCacheOperator.removeFromSet(RabbitConstant.RABBIT_MESSAGE_ID,rabbitDelayedMessage.getRabbitMsg().getUuid());
//            }else {
//                log.info("接受到延迟队列的消息: " + message.getPayload().getTaskKey());
//
//                String taskKey = rabbitDelayedMessage.getTaskKey();
//                Object o = commonCacheOperator.get(taskKey);
//                if (ObjectUtil.isNotEmpty(o) && !Boolean.parseBoolean(String.valueOf(o))) {
//                    //未办理，发送消息，继续放入缓存中
//                    Message<RabbitDelayedMessage> newMessage = MessageBuilder
//                            .withPayload(rabbitDelayedMessage)
//                            .setHeader("x-delay", rabbitDelayedMessage.getLimit() *60*60*1000)
////                .copyHeaders(customHeaders)
////                        .setHeader("x-delay", 2*60*1000)
//                            .build();
//
//                    streamBridge.send(RabbitConstant.DEFAULT_DELAYED_CHANNEL, newMessage);
//                    rabbitDelayedMessage.getRabbitMsg().setUuid(UUID.randomUUID(Boolean.FALSE).toString());
//                    commonCacheOperator.addToSet(RabbitConstant.RABBIT_MESSAGE_ID,rabbitDelayedMessage.getRabbitMsg().getUuid());
//                    iDingDingService.commonSend(rabbitDelayedMessage.getRabbitMsg());
//                } else {
//                    // 处理接收到的延迟消息
//                    log.info("Received delayed message: " + rabbitDelayedMessage.getTaskKey());
//                }
//            }

        };
    }


}
