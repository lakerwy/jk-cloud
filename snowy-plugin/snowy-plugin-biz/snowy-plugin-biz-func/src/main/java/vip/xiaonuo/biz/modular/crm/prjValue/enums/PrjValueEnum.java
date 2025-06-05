package vip.xiaonuo.biz.modular.crm.prjValue.enums;

import lombok.Getter;

/**
 * 生产院及子公司产值枚举
 *
 * @author pzh
 * @date  2025/06/05 10:04
 **/
@Getter
public enum PrjValueEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    PrjValueEnum(String value) {
        this.value = value;
    }
}
