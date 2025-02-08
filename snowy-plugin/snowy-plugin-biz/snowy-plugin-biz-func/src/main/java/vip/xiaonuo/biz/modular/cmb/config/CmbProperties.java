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
    // API启用开关
    private Boolean enabled;
    // 接口地址
    private String url;
    // 银行公钥
    private String publicKey;
    // 客户私钥
    private String privateKey;
    // 协商的对称密钥
    private String symKey;
    // 企业网银用户号
    private String uid;

    // 解密对称公钥
    private String corpKey;
    // 验签公钥
    private String cmbSignaturePublicKey;
}