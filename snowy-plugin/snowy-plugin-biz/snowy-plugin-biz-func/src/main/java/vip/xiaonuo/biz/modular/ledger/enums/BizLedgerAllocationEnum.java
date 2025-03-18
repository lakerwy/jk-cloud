package vip.xiaonuo.biz.modular.ledger.enums;

import lombok.Getter;

/**
 * 拨付台账枚举
 *
 * @author pzh
 * @date  2025/03/18 10:43
 **/
@Getter
public enum BizLedgerAllocationEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    BizLedgerAllocationEnum(String value) {
        this.value = value;
    }
}
