package vip.xiaonuo.iot.modular.mqtt.client;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import vip.xiaonuo.iot.core.config.MqttProperties;
import vip.xiaonuo.iot.modular.mqtt.callBack.MqttAcceptCallback;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/8/2 15:03
 */
@Component
@Slf4j
public class MqttAcceptClient {

    public static MqttClient mqttClient;

    @Autowired
    @Lazy
    private MqttAcceptCallback mqttAcceptCallback;

    @Resource
    private MqttProperties mqttProperties;

    private final ConcurrentMap<String, Boolean> clientMap = new ConcurrentHashMap<>();

    public static MqttClient getMqttClient() {
        return mqttClient;
    }

    public static void setMqttClient(MqttClient mqttClient) {
        MqttAcceptClient.mqttClient = mqttClient;
    }

    /*
     * 客户端连接
     */
    public void connect() {
        MqttClient client;
        try {
            // clientId 使用服务器 yml里面配置的 clientId
            client = new MqttClient(mqttProperties.getHostUrl(), "iot"+ RandomUtil.getRandom(), new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(mqttProperties.getUsername());
            options.setPassword(mqttProperties.getPassword().toCharArray());
            options.setConnectionTimeout(mqttProperties.getTimeout());
            options.setKeepAliveInterval(mqttProperties.getKeepAlive());
            options.setAutomaticReconnect(mqttProperties.getReconnect());
            options.setCleanSession(mqttProperties.getCleanSession());
            MqttAcceptClient.setMqttClient(client);
            try {
                // 设置回调
                client.setCallback(mqttAcceptCallback);
                client.connect(options);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 重新连接
     */
    public void reconnection() {
        try {
            mqttClient.connect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 订阅某个主题
     *
     * @param topic 主题
     * @param qos   连接方式
     */
    public void subscribe(String topic, int qos) {
        try {
            if (clientMap.containsKey(topic) && clientMap.get(topic)) {
                return;
            }
            log.info("==============开始订阅主题=============={}", topic);
            mqttClient.subscribe(topic, qos);
            clientMap.put(topic, true);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 取消订阅某个主题
     *
     * @param topic
     */
    public void unsubscribe(String topic) {
        log.info("==============开始取消订阅主题=============={}", topic);
        try {
            mqttClient.unsubscribe(topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
