package vip.xiaonuo.biz.modular.cmb.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "cmb")
public class CmbProperties {
    private Boolean enabled;
    private String url;
    private String publicKey;
    private String privateKey;
    private String symKey;
    private String uid;
}
