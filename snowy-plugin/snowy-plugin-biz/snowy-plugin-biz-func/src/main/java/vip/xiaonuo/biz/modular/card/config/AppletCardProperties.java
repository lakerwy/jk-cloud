package vip.xiaonuo.biz.modular.card.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "card")
public class AppletCardProperties {
    /**
     * 【电子名片小程序】唯一标识key
     */
    private String appId;

    /**
     * 【电子名片小程序】应用的密钥
     */
    private String appSecret;
}
