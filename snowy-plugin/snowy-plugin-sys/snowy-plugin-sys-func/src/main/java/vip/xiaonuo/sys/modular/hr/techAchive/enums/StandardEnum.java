package vip.xiaonuo.sys.modular.hr.techAchive.enums;

import lombok.Getter;

/**
 * 标准规范枚举
 *
 * @author pzh
 * @date  2024/10/17 17:05
 **/
@Getter
public enum StandardEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    StandardEnum(String value) {
        this.value = value;
    }
}
