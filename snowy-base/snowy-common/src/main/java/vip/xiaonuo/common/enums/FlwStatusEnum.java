package vip.xiaonuo.common.enums;

import lombok.Getter;

/**
 * 流程状态
 */
@Getter
public enum FlwStatusEnum {

    //参数状态
    ACTIVE_STATUS("0", "审批中"),

    COMPLETED_STATUS("1", "审批通过"),

    REVOKE_STATUS("2", "已撤回"),

    REJECT_STATUS("3", "已拒绝"),

    END_STATUS("4", "已终止"),
    ;

    private final String value;

    private final String name;

    FlwStatusEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public static String fromValue(String value) {
        for (FlwStatusEnum enumConstant : FlwStatusEnum.values()) {
            if (enumConstant.value.equals(value)) {
                return enumConstant.getName();
            }
        }
        throw new IllegalArgumentException("Invalid value: " + value);
    }
}
