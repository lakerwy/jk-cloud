package vip.xiaonuo.sys.modular.hr.taxGradeEvaluation.enums;

import lombok.Getter;

/**
 * 纳税等级评价枚举
 *
 * @author pzh
 * @date  2024/11/27 14:45
 **/
@Getter
public enum TaxGradeEvaluationEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    TaxGradeEvaluationEnum(String value) {
        this.value = value;
    }
}
