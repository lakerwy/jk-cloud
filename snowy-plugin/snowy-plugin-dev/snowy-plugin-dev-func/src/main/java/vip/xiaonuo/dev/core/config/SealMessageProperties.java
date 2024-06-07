package vip.xiaonuo.dev.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author hxw
 * @version 1.0
 * @date 2024/5/29 09:22
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "seal.message")
public class SealMessageProperties {

    /**
     *
     * 群杰前缀 --访问-ip+端口
     */
    private String requestUrl;
}
