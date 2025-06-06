package vip.xiaonuo.biz.modular.prj.fin.enums;

import lombok.Getter;

/**
 * 财务数据汇总台账 - 施工枚举
 *
 * @author yy
 * @date  2025/06/05 17:22
 **/
@Getter
public enum FinDataConEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    FinDataConEnum(String value) {
        this.value = value;
    }
}
