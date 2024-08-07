package vip.xiaonuo.iot.modular.mqtt.callBack;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.stereotype.Component;
import vip.xiaonuo.iot.modular.mqtt.client.MqttAcceptClient;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/8/2 14:38
 */
@Component
@Slf4j
public class MqttSendCallBack implements MqttCallbackExtended {

    @Override
    public void connectComplete(boolean b, String s) {
        log.info("--------------------ClientId:{}客户端连接成功！--------------------", MqttAcceptClient.mqttClient.getClientId());
    }

    /**
     * 客户端断开后触发
     * @param throwable
     */
    @Override
    public void connectionLost(Throwable throwable) {
        log.info("发送消息回调: 连接断开，可以做重连");
    }

    /**
     * 客户端收到消息触发
     * @param topic 主题
     * @param mqttMessage  消息
     * @throws Exception
     */
    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        log.info("发送消息回调:  接收消息主题 : {}", topic);
        log.info("发送消息回调:  接收消息内容 : {}", new String(mqttMessage.getPayload()));
    }

    /**
     * 发布消息成功
     * @param token
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        String[] topics = token.getTopics();
        for (String topic : topics) {
            log.info("发送消息回调:  向主题：{}发送消息成功！", topic);
        }
        try {
            MqttMessage message = token.getMessage();
            if(message== null){
                return;
            }
            byte[] payload = message.getPayload();
            String s = new String(payload, "UTF-8");
            log.info("发送消息回调:  消息的内容是：{}", s);
        } catch (MqttException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
