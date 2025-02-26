package vip.xiaonuo.sys.modular.hr.icManage.enums;

import lombok.Getter;

/**
 * 工商管理枚举
 *
 * @author pzh
 * @date  2025/02/25 16:33
 **/
@Getter
public enum IcManageEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    IcManageEnum(String value) {
        this.value = value;
    }
}
