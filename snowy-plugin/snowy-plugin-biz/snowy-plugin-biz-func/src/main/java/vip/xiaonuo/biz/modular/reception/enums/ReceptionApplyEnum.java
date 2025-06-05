package vip.xiaonuo.biz.modular.reception.enums;

import lombok.Getter;

/**
 * 接待申请枚举
 *
 * @author pzh
 * @date  2025/05/28 10:15
 **/
@Getter
public enum ReceptionApplyEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    ReceptionApplyEnum(String value) {
        this.value = value;
    }
}
