package vip.xiaonuo.dev.modular.userRelation.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/2/29 14:12
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "sync")
public class SyncProperties {
    /** 是否开启同步 */
    private boolean enable;

    /** 同步接口地址 */
    private String url;
}
