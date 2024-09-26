package vip.xiaonuo.sys.modular.dingtalk.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 是否开启钉钉组织架构同步
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "ding")
public class DingSyncProperties {

    /**
     * 是否开启钉钉组织架构同步
     */
    private Boolean isSync;

}
