package vip.xiaonuo.biz.modular.crm.prjValue.enums;

import lombok.Getter;

/**
 * 项目产值划分明细枚举
 *
 * @author pzh
 * @date  2025/06/04 16:48
 **/
@Getter
public enum PrjValueAllocationItemEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    PrjValueAllocationItemEnum(String value) {
        this.value = value;
    }
}
