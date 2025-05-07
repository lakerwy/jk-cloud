package vip.xiaonuo.biz.modular.crm.subcontract.enums;

import lombok.Getter;

/**
 * 分包合同结算审批枚举
 *
 * @author pzh
 * @date  2025/04/30 11:19
 **/
@Getter
public enum SubcontractSettlementEnum {

    NOT_START("未完结"),

    ACTIVE_STATUS("审核中"),

    COMPLETED_STATUS("已完结");

    private final String value;

    SubcontractSettlementEnum(String value) {
        this.value = value;
    }
}
