package vip.xiaonuo.sys.modular.hr.financialSheet.enums;

import lombok.Getter;

/**
 * 财务状况表枚举
 *
 * @author pzh
 * @date  2024/10/18 15:53
 **/
@Getter
public enum FinancialSheetEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    FinancialSheetEnum(String value) {
        this.value = value;
    }
}
