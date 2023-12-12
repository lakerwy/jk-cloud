package vip.xiaonuo.biz.modular.ezviz.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "ezviz")
public class EzvizProperties {
    /**
     * 【EZVIZ监控】唯一标识key
     */
    private String appKey;

    /**
     * 【EZVIZ监控】应用的密钥
     */
    private String appSecret;
}
