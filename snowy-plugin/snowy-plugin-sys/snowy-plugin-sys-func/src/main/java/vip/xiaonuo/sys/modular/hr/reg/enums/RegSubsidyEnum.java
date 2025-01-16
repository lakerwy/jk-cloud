package vip.xiaonuo.sys.modular.hr.reg.enums;

import lombok.Getter;

/**
 * 注册补贴（新版）-审批流枚举
 *
 * @author yong.ye
 * @date  2025/01/15 09:07
 **/
@Getter
public enum RegSubsidyEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    RegSubsidyEnum(String value) {
        this.value = value;
    }
}
