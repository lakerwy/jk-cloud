package vip.xiaonuo.biz.modular.prj.fin.enums;

import lombok.Getter;

/**
 * 财务数据汇总台账 - 拨付明细枚举
 *
 * @author yy
 * @date  2025/06/05 17:23
 **/
@Getter
public enum FinDataDbdEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    FinDataDbdEnum(String value) {
        this.value = value;
    }
}
