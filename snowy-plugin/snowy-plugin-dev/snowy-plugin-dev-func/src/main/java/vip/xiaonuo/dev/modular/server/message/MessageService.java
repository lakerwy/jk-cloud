package vip.xiaonuo.dev.modular.server.message;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.messaging.Message;
import vip.xiaonuo.common.consts.RabbitConstant;

import javax.annotation.Resource;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/6/16 23:27
 */
@Service
public class MessageService {

    @Resource
    StreamBridge streamBridge;

    /**
     * 采用路由的方式，不同的消息发送到不同的路由，这样在消息消费系统中，可以共用通道和函数，只需要修改路由即可
     * @param payload
     * @param routingKey
     */
    public boolean sendMessage(Object payload, String routingKey) {
        Message<Object> message = MessageBuilder
                .withPayload(payload)
                .setHeader("routingKey", routingKey)
                .build();
       return streamBridge.send(RabbitConstant.DEFAULT_EXT_CHANNEL, message);
    }
}
