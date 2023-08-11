package vip.xiaonuo.dev.modular.sms.config;

import cn.jpush.api.JPushClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class JPushConfig {

    @Value("${jpush.appKey}")
    private String appKey;

    @Value("${jpush.masterSecret}")
    private String masterSecret;

    @Value("${jpush.apnsProduction}")
    public static boolean apnsProduction;

    @Bean(name = "jkPushClient")
    public JPushClient getJPushClient() {
        return new JPushClient(masterSecret, appKey);
    }
}
