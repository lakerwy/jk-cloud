package vip.xiaonuo.sys.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 短信发送相关配置信息
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "sms")
public class SmsConfig {

    /**
     * 是否发送短信
     */
    private Boolean isSend;

    /**
     * 长链接生成短连接的域名
     */
    private String baseUrl;

}
