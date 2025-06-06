package vip.xiaonuo.biz.modular.prj.fin.enums;

import lombok.Getter;

/**
 * 财务数据汇总台账 - 销售发票枚举
 *
 * @author yy
 * @date  2025/06/05 17:24
 **/
@Getter
public enum FinDataInvoiceEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    FinDataInvoiceEnum(String value) {
        this.value = value;
    }
}
