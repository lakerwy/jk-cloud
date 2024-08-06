package vip.xiaonuo.biz.modular.branch.enums;

import lombok.Getter;

/**
 * 分公司日常费用拨付 - 付款信息枚举
 *
 * @author pzh
 * @date  2024/08/06 14:03
 **/
@Getter
public enum BranchPaymentInfoEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    BranchPaymentInfoEnum(String value) {
        this.value = value;
    }
}
