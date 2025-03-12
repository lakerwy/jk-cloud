package vip.xiaonuo.sys.modular.hr.sub.enums;

import lombok.Getter;

/**
 * 分包单位备案枚举
 *
 * @author pzh
 * @date  2025/03/11 14:32
 **/
@Getter
public enum SubFilingEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    SubFilingEnum(String value) {
        this.value = value;
    }
}
