package vip.xiaonuo.dev.modular.message.config;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.RabbitConstant;
import vip.xiaonuo.common.pojo.RabbitDelayedMessage;
import vip.xiaonuo.common.pojo.RabbitMsg;
import vip.xiaonuo.dev.api.IDingDingService;

import javax.annotation.Resource;
import java.util.function.Consumer;

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
           // boolean flag = commonCacheOperator.isValueInSet(RabbitConstant.RABBIT_MESSAGE_ID, rabbitMsg.getUuid());
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
            String taskKey = rabbitDelayedMessage.getTaskKey();
            Object o = commonCacheOperator.get(taskKey);
            Boolean nextFlag = ObjectUtil.isNotEmpty(o) && !Boolean.parseBoolean(String.valueOf(o)); //标记是否需要继续
            //【无设置，走历史逻辑，有设置，走新逻辑】
            Integer limitCount = rabbitDelayedMessage.getLimitCount();
            Integer limitNum = rabbitDelayedMessage.getLimitNum();
            if(ObjectUtil.isNotEmpty(limitCount)){ //新
                if(nextFlag && limitNum <= limitCount){
                    log.info("【发送延迟队列消息-通知上限】: " + taskKey + "【超时提醒次数：" + limitNum + "】【消息体】：" + JSONUtil.toJsonStr(rabbitDelayedMessage));
                    //未办理，生成消息，继续放入缓存中【未达次数上线】
                    if(limitNum < limitCount) {
                        Message<RabbitDelayedMessage> newMessage = MessageBuilder
                                .withPayload(rabbitDelayedMessage)
                                .setHeader("x-delay", rabbitDelayedMessage.getLimit() * 60 * 60 * 1000)
                                .build();
                        newMessage.getPayload().setLimitNum(limitNum+1);
                        streamBridge.send(RabbitConstant.DEFAULT_DELAYED_CHANNEL, newMessage);
                    }
                    //消息
                    rabbitDelayedMessage.getRabbitMsg().setTitle("【超时提醒次数" + limitNum + "】"+rabbitDelayedMessage.getRabbitMsg().getTitle());
                    iDingDingService.commonSend(rabbitDelayedMessage.getRabbitMsg());
                }
            }else{ //历史
                if (nextFlag) {
                    log.info("【发送延迟队列消息-历史通知】: " + taskKey + "【超时提醒次数：" + limitNum + "】【消息体】：" + JSONUtil.toJsonStr(rabbitDelayedMessage));
                    //未办理，生成消息，继续放入缓存中
                    Message<RabbitDelayedMessage> newMessage = MessageBuilder
                            .withPayload(rabbitDelayedMessage)
                            .setHeader("x-delay", rabbitDelayedMessage.getLimit() *60*60*1000)
                            .build();
                    newMessage.getPayload().setLimitNum(limitNum+1);
                    streamBridge.send(RabbitConstant.DEFAULT_DELAYED_CHANNEL, newMessage);
                    //消息
                    rabbitDelayedMessage.getRabbitMsg().setTitle("【超时提醒次数" + limitNum + "】"+rabbitDelayedMessage.getRabbitMsg().getTitle());
                    iDingDingService.commonSend(rabbitDelayedMessage.getRabbitMsg());
                }
            }
        };
    }

}
