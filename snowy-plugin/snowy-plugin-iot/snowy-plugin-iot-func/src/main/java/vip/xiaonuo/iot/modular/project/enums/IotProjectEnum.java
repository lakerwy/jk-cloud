package vip.xiaonuo.iot.modular.project.enums;

import lombok.Getter;

/**
 * 物联网-项目枚举
 *
 * @author mayike
 * @date  2024/08/08 14:03
 **/
@Getter
public enum IotProjectEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    IotProjectEnum(String value) {
        this.value = value;
    }
}
