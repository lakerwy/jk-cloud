package vip.xiaonuo.sys.modular.hr.sub.enums;

import lombok.Getter;

/**
 * 分公司-工资发放 - 工资|奖金明细枚举
 *
 * @author yong.ye
 * @date  2025/02/06 09:11
 **/
@Getter
public enum SubGzItemTypeEnum {

    /** 工资 */
    GZ("工资"),
    /** 奖金 */
    JJ("奖金"),
    /** 经济补偿金 */
    BCJ("经济补偿金");

    private final String value;

    SubGzItemTypeEnum(String value) {
        this.value = value;
    }
}
