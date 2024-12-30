package vip.xiaonuo.biz.modular.bidFee.enums;

import lombok.Getter;

/**
 * 投标费用审批枚举
 *
 * @author pzh
 * @date  2024/12/26 16:37
 **/
@Getter
public enum BidFeeEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    BidFeeEnum(String value) {
        this.value = value;
    }
}
