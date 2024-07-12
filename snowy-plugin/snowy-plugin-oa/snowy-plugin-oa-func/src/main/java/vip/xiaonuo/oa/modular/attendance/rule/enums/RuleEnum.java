package vip.xiaonuo.oa.modular.attendance.rule.enums;

import lombok.Getter;

/**
 * 考勤规则配置表枚举
 *
 * @author mayike
 * @date  2024/07/10 16:29
 **/
@Getter
public enum RuleEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    RuleEnum(String value) {
        this.value = value;
    }
}
