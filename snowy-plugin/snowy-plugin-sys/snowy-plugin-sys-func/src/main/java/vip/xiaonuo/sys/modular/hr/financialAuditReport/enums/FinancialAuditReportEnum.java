package vip.xiaonuo.sys.modular.hr.financialAuditReport.enums;

import lombok.Getter;

/**
 * 财务审计报告枚举
 *
 * @author pzh
 * @date  2024/10/18 14:51
 **/
@Getter
public enum FinancialAuditReportEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    FinancialAuditReportEnum(String value) {
        this.value = value;
    }
}
