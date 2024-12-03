package vip.xiaonuo.sys.modular.hr.zizhiCertificate.enums;

import lombok.Getter;

/**
 * 资质证照枚举
 *
 * @author pzh
 * @date  2024/10/16 16:39
 **/
@Getter
public enum ZizhiCertificateEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    ZizhiCertificateEnum(String value) {
        this.value = value;
    }
}
