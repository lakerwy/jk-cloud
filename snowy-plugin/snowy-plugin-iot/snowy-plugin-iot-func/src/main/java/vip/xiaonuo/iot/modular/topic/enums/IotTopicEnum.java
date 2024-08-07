package vip.xiaonuo.iot.modular.topic.enums;

import lombok.Getter;

/**
 * 物理网-主题topic 表枚举
 *
 * @author mayike
 * @date  2024/08/05 10:26
 **/
@Getter
public enum IotTopicEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    IotTopicEnum(String value) {
        this.value = value;
    }
}
