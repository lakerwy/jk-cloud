package vip.xiaonuo.iot.modular.group.enums;

import lombok.Getter;

/**
 * 设备分组枚举
 *
 * @author mayike
 * @date  2024/08/06 08:55
 **/
@Getter
public enum IotGroupEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    IotGroupEnum(String value) {
        this.value = value;
    }
}
