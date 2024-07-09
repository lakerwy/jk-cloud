
package vip.xiaonuo.oa.modular.attendance.holiday.enums;

import lombok.Getter;

/**
 * 假期类型配置表枚举
 *
 * @author mayike
 * @date  2024/07/05 09:19
 **/
@Getter
public enum HolidayRuleEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    HolidayRuleEnum(String value) {
        this.value = value;
    }
}
