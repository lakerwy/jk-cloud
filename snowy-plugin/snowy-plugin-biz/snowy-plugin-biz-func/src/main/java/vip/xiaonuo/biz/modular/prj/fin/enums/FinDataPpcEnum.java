package vip.xiaonuo.biz.modular.prj.fin.enums;

import lombok.Getter;

/**
 * 财务数据汇总台账 - 项目回款枚举
 *
 * @author yy
 * @date  2025/06/05 17:26
 **/
@Getter
public enum FinDataPpcEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    FinDataPpcEnum(String value) {
        this.value = value;
    }
}
