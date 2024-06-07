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
@ConfigurationProperties(prefix = "seal.apply")
public class SealApplyProperties {

    /**
     *
     * 云玺 --访问-ip+端口+接口请求
     */
    private String postUrl;

    /**
     *
     * 云玺 --应用标识
     */
    private String appKey;

    /**
     *
     * 云玺 --租户标识
     */
    private String tenant;

    /**
     *
     * 云玺 --授权码有效期
     */
    private String time;
}
