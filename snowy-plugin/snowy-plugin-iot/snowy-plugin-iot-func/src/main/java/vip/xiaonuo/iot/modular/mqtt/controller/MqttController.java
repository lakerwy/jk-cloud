package vip.xiaonuo.iot.modular.mqtt.controller;

import cn.hutool.json.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.iot.modular.mqtt.client.MqttSendClient;
import vip.xiaonuo.iot.modular.mqtt.param.SendParam;

import javax.annotation.Resource;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/8/2 15:25
 * mqtt 控制器
 */
@Api(tags = "物理网-mqtt服务")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class MqttController {

    @Resource
    private MqttSendClient mqttSendClient;

    @ApiOperationSupport(order =2)
    @ApiOperation("发布消息")
    @CommonLog("发布消息")
    @PostMapping("/mqtt/sendMessage")
    public CommonResult<String> sendMessage(@RequestBody SendParam sendParam) {
        mqttSendClient.publish(false,sendParam.getTopic(),sendParam.getMessage());
        return CommonResult.ok();
    }

    @ApiOperationSupport(order =2)
    @ApiOperation("网关状态查询")
    @CommonLog("网关状态查询")
    @PostMapping("/mqtt/topicStatus")
    public CommonResult<String> topicStatus(@RequestBody SendParam sendParam) {
        mqttSendClient.publish(false,sendParam.getTopic(),sendParam.getMessage());
        return CommonResult.ok();
    }
}
