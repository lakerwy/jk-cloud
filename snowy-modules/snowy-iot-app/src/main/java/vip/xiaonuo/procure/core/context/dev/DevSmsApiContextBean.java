//package vip.xiaonuo.procure.core.context.dev;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import vip.xiaonuo.dev.api.DevSmsApi;
//import vip.xiaonuo.dev.feign.DevSmsFeign;
//
//@Slf4j
//@RequiredArgsConstructor
//@Component
//public class DevSmsApiContextBean implements DevSmsApi {
//
//    private final DevSmsFeign devSmsFeign;
//
//    @Override
//    public void sendSmsAliyun(String phoneNumbers, String signName, String templateCode, String templateParam) {
//        devSmsFeign.sendSmsAliyun(phoneNumbers,signName,templateCode,templateParam);
//    }
//
//    @Override
//    public void sendSmsTencent(String sdkAppId, String phoneNumbers, String signName, String templateCode, String templateParam) {
//        devSmsFeign.sendSmsTencent(sdkAppId,phoneNumbers,signName,templateCode,templateParam);
//    }
//
//    @Override
//    public void sendSmsYunPian(String apiKey, String mobile, String tpl_id, String tpl_value) {
//        devSmsFeign.sendSmsYunPian(apiKey,mobile,tpl_id,tpl_value);
//    }
//}
