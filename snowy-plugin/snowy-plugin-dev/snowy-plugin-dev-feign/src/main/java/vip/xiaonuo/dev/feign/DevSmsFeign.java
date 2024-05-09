package vip.xiaonuo.dev.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vip.xiaonuo.common.consts.FeignConstant;

@FeignClient(name= FeignConstant.WEB_APP, contextId = "DevSmsFeign")
public interface DevSmsFeign {


    @RequestMapping("/feign/dev/sms/sendSmsAliyun")
    void sendSmsAliyun(@RequestParam String phoneNumbers, @RequestParam String signName, @RequestParam String templateCode, @RequestParam String templateParam);

    @RequestMapping("/feign/dev/sms/sendSmsTencent")
    void sendSmsTencent(@RequestParam String sdkAppId,@RequestParam String phoneNumbers,@RequestParam String signName, @RequestParam String templateCode,@RequestParam String templateParam);

    @RequestMapping("/feign/dev/sms/sendSmsYunPian")
    void sendSmsYunPian(@RequestParam String apiKey, @RequestParam String mobile, @RequestParam String tpl_id, @RequestParam String tpl_value);

}
