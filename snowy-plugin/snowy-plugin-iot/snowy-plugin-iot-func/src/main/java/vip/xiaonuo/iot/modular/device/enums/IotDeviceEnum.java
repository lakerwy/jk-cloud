package vip.xiaonuo.iot.modular.device.enums;

import lombok.Getter;

/**
 * 物联网-设备表枚举
 *
 * @author mayike
 * @date  2024/08/05 09:51
 **/
@Getter
public enum IotDeviceEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    IotDeviceEnum(String value) {
        this.value = value;
    }
}
