package vip.xiaonuo.biz.modular.prj.por.enums;

import lombok.Getter;

/**
 * 分包费用性质
 *
 * @author yy
 * @date  2025/06/03 09:40
 **/
@Getter
public enum ExpenseNatureEnum {

    /** 经营费 */
    JYF("1"),
    /** 分包费 */
    FBF("2"),
    /** 居间费 */
    JJF("3"),
    /** 其他 */
    QT("4");

    private final String value;

    ExpenseNatureEnum(String value) {
        this.value = value;
    }
}
