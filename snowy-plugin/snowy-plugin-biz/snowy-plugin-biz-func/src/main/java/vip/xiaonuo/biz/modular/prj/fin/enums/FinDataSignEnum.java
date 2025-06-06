package vip.xiaonuo.biz.modular.prj.fin.enums;

import lombok.Getter;

/**
 * 财务数据汇总台账 - 转入签字费枚举
 *
 * @author yy
 * @date  2025/06/05 17:27
 **/
@Getter
public enum FinDataSignEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    FinDataSignEnum(String value) {
        this.value = value;
    }
}
