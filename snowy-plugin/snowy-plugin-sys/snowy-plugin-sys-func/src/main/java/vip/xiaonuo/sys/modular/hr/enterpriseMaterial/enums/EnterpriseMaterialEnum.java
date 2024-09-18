package vip.xiaonuo.sys.modular.hr.enterpriseMaterial.enums;

import lombok.Getter;

/**
 * 企业宣传资料枚举
 *
 * @author pzh
 * @date  2024/09/18 14:57
 **/
@Getter
public enum EnterpriseMaterialEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    EnterpriseMaterialEnum(String value) {
        this.value = value;
    }
}
