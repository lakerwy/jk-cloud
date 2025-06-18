package vip.xiaonuo.biz.modular.orgPerf.finance.enums;

import lombok.Getter;

/**
 * 财务情况枚举
 *
 * @author pzh
 * @date  2025/06/17 11:47
 **/
@Getter
public enum BizFinanceEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    BizFinanceEnum(String value) {
        this.value = value;
    }
}
