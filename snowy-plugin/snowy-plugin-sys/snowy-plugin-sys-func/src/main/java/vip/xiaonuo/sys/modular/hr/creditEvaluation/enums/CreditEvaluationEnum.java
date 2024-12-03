package vip.xiaonuo.sys.modular.hr.creditEvaluation.enums;

import lombok.Getter;

/**
 * 信用评价枚举
 *
 * @author pzh
 * @date  2024/10/18 10:49
 **/
@Getter
public enum CreditEvaluationEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    CreditEvaluationEnum(String value) {
        this.value = value;
    }
}
