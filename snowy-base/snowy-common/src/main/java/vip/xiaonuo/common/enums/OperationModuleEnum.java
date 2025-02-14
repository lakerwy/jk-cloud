package vip.xiaonuo.common.enums;

import lombok.Getter;

/**
 * 操作模块枚举
 *
 * @author pzh
 * @date  2025/02/13 16:48
 **/
@Getter
public enum OperationModuleEnum {
    DINGTALK_ATTEND("员工月度考勤(钉钉)");

    private final String value;

    OperationModuleEnum(String value) {
        this.value = value;
    }
}
