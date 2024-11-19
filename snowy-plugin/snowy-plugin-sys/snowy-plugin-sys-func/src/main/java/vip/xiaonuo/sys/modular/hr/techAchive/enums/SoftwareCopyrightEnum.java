package vip.xiaonuo.sys.modular.hr.techAchive.enums;

import lombok.Getter;

/**
 * 软著信息枚举
 *
 * @author pzh
 * @date  2024/10/17 10:53
 **/
@Getter
public enum SoftwareCopyrightEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    SoftwareCopyrightEnum(String value) {
        this.value = value;
    }
}
