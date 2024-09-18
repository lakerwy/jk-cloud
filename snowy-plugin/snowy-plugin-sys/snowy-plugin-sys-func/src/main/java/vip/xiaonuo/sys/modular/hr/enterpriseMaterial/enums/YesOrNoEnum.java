package vip.xiaonuo.sys.modular.hr.enterpriseMaterial.enums;

import lombok.Getter;

/**
 * 是否
 *
 * @author pzh
 * @date  2024/09/18 15:37
 **/
@Getter
public enum YesOrNoEnum {

    /** 是 */
    YES("1"),
    /** 否 */
    NO("0");

    private final String value;

    YesOrNoEnum(String value) {
        this.value = value;
    }
}
