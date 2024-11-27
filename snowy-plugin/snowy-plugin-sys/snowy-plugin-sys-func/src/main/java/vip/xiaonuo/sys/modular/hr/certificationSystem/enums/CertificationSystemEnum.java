package vip.xiaonuo.sys.modular.hr.certificationSystem.enums;

import lombok.Getter;

/**
 * 认证体系枚举
 *
 * @author pzh
 * @date  2024/10/18 10:01
 **/
@Getter
public enum CertificationSystemEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    CertificationSystemEnum(String value) {
        this.value = value;
    }
}
