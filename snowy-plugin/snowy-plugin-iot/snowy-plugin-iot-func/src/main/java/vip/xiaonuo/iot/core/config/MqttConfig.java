package vip.xiaonuo.iot.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vip.xiaonuo.iot.modular.mqtt.client.MqttAcceptClient;

import javax.annotation.Resource;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/8/2 15:17
 */
@Configuration
public class MqttConfig {

    @Resource
    private MqttAcceptClient mqttAcceptClient;

    @Bean
    public MqttAcceptClient getMqttPushClient() {
        mqttAcceptClient.connect();
        return mqttAcceptClient;
    }
}
