package vip.xiaonuo.biz.modular.prj.fin.enums;

import lombok.Getter;

/**
 * 财务数据汇总台账 - 转入管理费枚举
 *
 * @author yy
 * @date  2025/06/05 17:25
 **/
@Getter
public enum FinDataManageEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    FinDataManageEnum(String value) {
        this.value = value;
    }
}
