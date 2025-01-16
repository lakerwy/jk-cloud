package vip.xiaonuo.sys.modular.hr.reg.enums;

import lombok.Getter;

/**
 * 注册补贴（新版）- 结果反馈枚举
 *
 * @author yong.ye
 * @date  2025/01/15 09:08
 **/
@Getter
public enum RegSubsidyResultEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    RegSubsidyResultEnum(String value) {
        this.value = value;
    }
}
