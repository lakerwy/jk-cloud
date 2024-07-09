package vip.xiaonuo.oa.modular.attendance.apply.enums;

import lombok.Getter;

/**
 * 请假管理表枚举
 *
 * @author mayike
 * @date  2024/07/09 12:00
 **/
@Getter
public enum HolidayApplyEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    HolidayApplyEnum(String value) {
        this.value = value;
    }
}
