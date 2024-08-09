package vip.xiaonuo.iot.modular.mqtt.callBack;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
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

    private final ConcurrentMap<String, Boolean> clientMap = new ConcurrentHashMap<>();

    @Override
    public void connectComplete(boolean b, String s) {
        log.info("--------------------ClientId:{}客户端连接成功！--------------------", MqttAcceptClient.mqttClient.getClientId());
        // 以/#结尾表示订阅所有以test开头的主题
        // 订阅所有机构主题
        mqttAcceptClient.subscribe(mqttProperties.getDeviceTopic(), 1);
//        mqttAcceptClient.subscribe(mqttProperties.getDeviceTopic(), 1);
    }

    @Override
    public void connectionLost(Throwable throwable) {
        log.info("接收消息回调:  连接断开，可以做重连");
        if (MqttAcceptClient.mqttClient == null || !MqttAcceptClient.mqttClient.isConnected()) {
            log.info("接收消息回调:  emqx重新连接....................................................");
            mqttAcceptClient.reconnection();
        }
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) {
        log.info("接收消息回调:  接收消息主题 : {}", topic);
        //处理设备消息
        if (topic.startsWith("device")) {
            String message = new String(mqttMessage.getPayload());
            log.info("接收消息回调:  接收消息内容 : {}", message);
            JSONObject jsonObject = JSONUtil.parseObj(message);
            Integer cmd = jsonObject.getInt("cmd");
            if (cmd == CmdConstant.CMD_109) {
                String  clientId = jsonObject.getStr("clientId");
                Object o = jsonObject.get("data");
                IotDevice iotDevice = BeanUtil.copyProperties(o, IotDevice.class);
                iotDeviceService.deviceUpdate(iotDevice,clientId);
            }else if(cmd == CmdConstant.CMD_100){
                String  clientId = jsonObject.getStr("clientId");
                Boolean flag = clientMap.get(clientId);
                if(flag!=null && flag){
                    return;
                }
                jsonObject = jsonObject.getJSONObject("data");
                String id = jsonObject.getStr("id");
                Message message1 = new Message();
                message1.setCmd(CmdConstant.CMD_200);
                message1.setClientId(clientId);
                message1.setState(1);
                message1.setTime(System.currentTimeMillis());
                MqttData data = new MqttData();
                data.setId(id);
                message1.setData(data);
                mqttSendClient.publish(false,mqttProperties.getGateWayPre()+"/"+clientId,message1);
                clientMap.put(clientId,true);
            }
        }
    }

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
