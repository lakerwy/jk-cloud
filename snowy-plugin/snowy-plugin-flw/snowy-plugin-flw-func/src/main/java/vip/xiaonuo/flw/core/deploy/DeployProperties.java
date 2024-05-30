package vip.xiaonuo.flw.core.deploy;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author hxw
 * @version 1.0
 * @date 2024/5/29 09:22
 */

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "tencent.cloud.deploy")
public class DeployProperties {

    /**
     * epc请求前缀：ip和端口
     */
    private String prefixUrl;
}
