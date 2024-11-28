package vip.xiaonuo.sys.modular.hr.reputation.enums;

import lombok.Getter;

/**
 * 信誉情况枚举
 *
 * @author pzh
 * @date  2024/10/18 16:52
 **/
@Getter
public enum ReputationEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    ReputationEnum(String value) {
        this.value = value;
    }
}
