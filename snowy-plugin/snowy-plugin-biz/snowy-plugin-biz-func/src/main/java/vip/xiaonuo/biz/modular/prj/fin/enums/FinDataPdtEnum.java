package vip.xiaonuo.biz.modular.prj.fin.enums;

import lombok.Getter;

/**
 * 财务数据汇总台账 - 生产院枚举
 *
 * @author yy
 * @date  2025/06/05 17:26
 **/
@Getter
public enum FinDataPdtEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    FinDataPdtEnum(String value) {
        this.value = value;
    }
}
