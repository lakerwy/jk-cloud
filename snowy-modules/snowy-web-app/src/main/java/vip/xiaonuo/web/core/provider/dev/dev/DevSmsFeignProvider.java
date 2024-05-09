package vip.xiaonuo.web.core.provider.dev.dev;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.dev.feign.DevSmsFeign;
import vip.xiaonuo.dev.modular.sms.provider.DevSmsApiProvider;

@Slf4j
@RequiredArgsConstructor
@RestController
@Configuration
public class DevSmsFeignProvider implements DevSmsFeign {

    private final DevSmsApiProvider devSmsApiProvider;
    @Override
    @RequestMapping("/feign/dev/sms/sendSmsAliyun")
    public void sendSmsAliyun(String phoneNumbers, String signName, String templateCode, String templateParam) {
        devSmsApiProvider.sendSmsAliyun(phoneNumbers,signName,templateCode,templateParam);
    }

    @Override
    @RequestMapping("/feign/dev/sms/sendSmsTencent")
    public void sendSmsTencent(String sdkAppId, String phoneNumbers, String signName, String templateCode, String templateParam) {
        devSmsApiProvider.sendSmsTencent(sdkAppId,phoneNumbers,signName,templateCode,templateParam);
    }

    @Override
    @RequestMapping("/feign/dev/sms/sendSmsYunPian")
    public void sendSmsYunPian(String apiKey, String mobile, String tpl_id, String tpl_value) {
        devSmsApiProvider.sendSmsYunPian(apiKey,mobile,tpl_id,tpl_value);
    }
}
