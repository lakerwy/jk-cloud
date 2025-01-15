package vip.xiaonuo.biz.modular.prjFee.enums;

import lombok.Getter;

/**
 * 工程款拨付审批枚举
 *
 * @author pzh
 * @date  2025/01/15 09:22
 **/
@Getter
public enum PrjFeeAllotmentEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    PrjFeeAllotmentEnum(String value) {
        this.value = value;
    }
}
