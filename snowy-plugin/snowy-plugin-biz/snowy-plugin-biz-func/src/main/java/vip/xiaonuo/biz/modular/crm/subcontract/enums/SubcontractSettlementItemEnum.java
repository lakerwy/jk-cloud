package vip.xiaonuo.biz.modular.crm.subcontract.enums;

import lombok.Getter;

/**
 * 分包合同结算明细枚举
 *
 * @author pzh
 * @date  2025/04/30 11:20
 **/
@Getter
public enum SubcontractSettlementItemEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    SubcontractSettlementItemEnum(String value) {
        this.value = value;
    }
}
