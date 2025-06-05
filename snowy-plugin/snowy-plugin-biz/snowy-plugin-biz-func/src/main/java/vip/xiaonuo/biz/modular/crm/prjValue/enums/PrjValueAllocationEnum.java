package vip.xiaonuo.biz.modular.crm.prjValue.enums;

import lombok.Getter;

/**
 * 项目产值划分枚举
 *
 * @author pzh
 * @date  2025/06/04 16:35
 **/
@Getter
public enum PrjValueAllocationEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    PrjValueAllocationEnum(String value) {
        this.value = value;
    }
}
