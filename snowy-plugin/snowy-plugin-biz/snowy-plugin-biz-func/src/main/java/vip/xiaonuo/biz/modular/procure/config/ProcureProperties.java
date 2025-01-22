package vip.xiaonuo.biz.modular.procure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "procure")
public class ProcureProperties {
    private String prefixUrl;
}
