package vip.xiaonuo.im.modular.openIm.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/7/22 14:34
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "openim")
public class OpenImProperties {

    public String url;

    public String secret;

}
