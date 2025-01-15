package vip.xiaonuo.biz.modular.prjFee.enums;

import lombok.Getter;

/**
 * 工程款拨付 - 款项拨付枚举
 *
 * @author pzh
 * @date  2025/01/15 09:33
 **/
@Getter
public enum PrjFeeAllotmentItemEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    PrjFeeAllotmentItemEnum(String value) {
        this.value = value;
    }
}
