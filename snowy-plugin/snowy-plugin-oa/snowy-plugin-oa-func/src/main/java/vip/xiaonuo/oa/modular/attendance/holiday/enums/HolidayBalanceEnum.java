package vip.xiaonuo.oa.modular.attendance.holiday.enums;

import lombok.Getter;

/**
 * 假期余额管理表枚举
 *
 * @author mayike
 * @date  2024/07/05 10:57
 **/
@Getter
public enum HolidayBalanceEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    HolidayBalanceEnum(String value) {
        this.value = value;
    }
}
