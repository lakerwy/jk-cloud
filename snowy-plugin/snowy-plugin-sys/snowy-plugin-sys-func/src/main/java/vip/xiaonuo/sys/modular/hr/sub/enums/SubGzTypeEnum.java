package vip.xiaonuo.sys.modular.hr.sub.enums;

import lombok.Getter;

/**
 * 分公司-工资发放 - 工资|奖金|工资+奖金 类型枚举
 *
 * @author yong.ye
 * @date  2025/02/06 09:11
 **/
@Getter
public enum SubGzTypeEnum {

    /** 工资 */
    GZ("工资"),
    /** 奖金 */
    JJ("奖金"),
    /** 工资+奖金 */
    GZ_JJ("工资+奖金");

    private final String value;

    SubGzTypeEnum(String value) {
        this.value = value;
    }
}
