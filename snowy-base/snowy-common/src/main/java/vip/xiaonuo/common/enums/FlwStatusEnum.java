package vip.xiaonuo.common.enums;

import lombok.Getter;

/**
 * 流程状态
 */
@Getter
public enum FlwStatusEnum {
    ACTIVE_STATUS("0","审批中"),

    COMPLETED_STATUS("1","已完成"),

    REVOKE_STATUS("2","已撤回"),

    REJECT_STATUS("3","已拒绝"),

    END_STATUS("4","已终止");

    private final String value;

    private final String name;

    FlwStatusEnum(String value, String name) {
        this.value =value;
        this.name = name;
    }
}
