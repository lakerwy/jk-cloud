package vip.xiaonuo.iot.modular.mqtt.callBack;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.iot.core.config.MqttProperties;
import vip.xiaonuo.iot.modular.device.constant.CmdConstant;
import vip.xiaonuo.iot.modular.device.entity.IotDevice;
import vip.xiaonuo.iot.modular.device.service.IotDeviceService;
import vip.xiaonuo.iot.modular.mqtt.client.MqttAcceptClient;
import vip.xiaonuo.iot.modular.mqtt.client.MqttSendClient;
import vip.xiaonuo.iot.modular.mqtt.param.Message;
import vip.xiaonuo.iot.modular.mqtt.param.MqttData;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/8/2 15:00
 */
@Component
@Slf4j
public class MqttAcceptCallback implements MqttCallbackExtended {

    @Resource
    private MqttAcceptClient mqttAcceptClient;

    @Resource
    private MqttProperties mqttProperties;

    @Resource
    private IotDeviceService iotDeviceService;

    @Resource
    private MqttSendClient mqttSendClient;

   // private final ConcurrentMap<String, Boolean> clientMap = new ConcurrentHashMap<>();

    /**
     * 连接成功后事件
     * @param reconnect  是否需要重新连接
     * @param serverURI  mqtt 服务的url
     */
    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
//        log.info("--------------------ClientId:{}客户端连接成功！--------------------", MqttAcceptClient.mqttClient.getClientId());
        // 以/#结尾表示订阅所有以test开头的主题
        // 订阅所有机构主题
        mqttAcceptClient.subscribe(mqttProperties.getDeviceTopic(), 1);
//        mqttAcceptClient.subscribe(mqttProperties.getDeviceTopic(), 1);
    }

    /**
     * 客户端与 MQTT 服务器的连接意外丢失时，该方法会被调用
     * @param throwable
     */
    @Override
    public void connectionLost(Throwable throwable) {
        log.warn("MQTT 连接丢失，原因: {}", throwable.getMessage());
        MqttClient mqttClient = MqttAcceptClient.getMqttClient();
        boolean connected = mqttClient.isConnected();
        if (!connected) {
            try {
                mqttClient.reconnect();
            } catch (MqttException e) {
                throw new RuntimeException(e);
            }
        }

//        // 延迟重试机制
//        int maxRetries = 5;
//        int retryCount = 0;
//        int retryDelay = 5000; // 初始延迟时间（毫秒）
//
//        while ((MqttAcceptClient.mqttClient == null || !MqttAcceptClient.mqttClient.isConnected()) && retryCount < maxRetries) {
//            try {
//                log.info("尝试重新连接，重试次数: {}/{}", retryCount + 1, maxRetries);
//                mqttAcceptClient.reconnection();
//                if (MqttAcceptClient.mqttClient.isConnected()) {
//                    log.info("重新连接成功！");
//                    break;
//                }
//            } catch (Exception e) {
//                log.error("重新连接失败: {}", e.getMessage(), e);
//            }
//
//            retryCount++;
//            try {
//                // 随着重试次数增加，延长延迟时间
//                Thread.sleep(retryDelay + (retryCount * 2000));
//            } catch (InterruptedException e) {
//                log.error("重连延迟被打断: {}", e.getMessage(), e);
//                Thread.currentThread().interrupt();
//            }
//        }
//
//        if (retryCount >= maxRetries) {
//            log.error("已达到最大重试次数，无法重新连接 MQTT 服务器");
//            // 可选择停止重试，或者进行更长时间的延迟后再试
//            scheduleReconnectAttempt();
//        }
    }

    private void scheduleReconnectAttempt() {
        int extendedRetryDelay = 60000; // 延迟 60 秒后再次尝试连接
        log.warn("将在 {} 毫秒后尝试再次连接", extendedRetryDelay);

        new Thread(() -> {
            try {
                Thread.sleep(extendedRetryDelay);
                mqttAcceptClient.reconnection();
            } catch (InterruptedException e) {
                log.error("延迟后重连失败: {}", e.getMessage(), e);
            }
        }).start();
    }


    /**
     * 在消息到达客户端时被调用
     * @param topic
     * @param mqttMessage
     */
    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) {
        log.info("接收消息回调:  接收消息主题 : {}", topic);
        //处理设备消息
        if (topic.startsWith("device")) {
            String message = new String(mqttMessage.getPayload());
            log.info("接收消息回调:  接收消息内容 : {}", message);
            JSONObject jsonObject = JSONUtil.parseObj(message);
            Integer cmd = jsonObject.getInt("cmd");
            if (cmd == CmdConstant.CMD_109 || cmd == CmdConstant.CMD_108) {
                String  clientId = jsonObject.getStr("clientId");
                Object o = jsonObject.get("data");
                IotDevice iotDevice = BeanUtil.copyProperties(o, IotDevice.class);
//                if(iotDevice.getDeviceCode().equals("083b7c19")){
//                    System.out.println("测试");
//                }
                iotDeviceService.deviceUpdate(iotDevice,clientId);
            }else if(cmd == CmdConstant.CMD_100){
                //心跳检测
                String  clientId = jsonObject.getStr("clientId");
                jsonObject = jsonObject.getJSONObject("data");
                //String id = jsonObject.getStr("id");
                Message message1 = new Message();
                message1.setCmd(CmdConstant.CMD_200);
                message1.setClientId(clientId);
                message1.setState(1);
                message1.setTime(System.currentTimeMillis());
                MqttData data = new MqttData();
                String id = iotDeviceService.getId(CmdConstant.CMD_200);
                data.setId(id);
                data.setIspush(1);
                data.setPollTime(2);
                message1.setData(data);
                mqttSendClient.publish(false,mqttProperties.getGateWayPre()+"/"+clientId,message1);
            }
        }
    }

    /**
     * 消息发送成功后回调
     * @param token
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        String[] topics = token.getTopics();
        for (String topic : topics) {
            log.info("接收消息回调:  向主题：{}发送消息成功！", topic);
        }
        try {
            MqttMessage message = token.getMessage();
            if(message == null){
                return;
            }
            byte[] payload = message.getPayload();
            String s = new String(payload, "UTF-8");
            log.info("接收消息回调:  消息的内容是：{}", s);
        } catch (MqttException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
