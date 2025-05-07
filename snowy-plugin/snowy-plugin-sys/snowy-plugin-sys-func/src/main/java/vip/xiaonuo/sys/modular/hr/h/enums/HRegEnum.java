package vip.xiaonuo.sys.modular.hr.h.enums;

import lombok.Getter;

/**
 * 招待事项登记枚举
 *
 * @author yy
 * @date  2025/05/06 16:11
 **/
@Getter
public enum HRegEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    HRegEnum(String value) {
        this.value = value;
    }
}
