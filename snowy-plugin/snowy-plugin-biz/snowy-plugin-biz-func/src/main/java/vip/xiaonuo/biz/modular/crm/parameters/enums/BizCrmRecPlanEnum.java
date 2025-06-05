package vip.xiaonuo.biz.modular.crm.parameters.enums;

import lombok.Getter;

/**
 * 合同收款计划枚举
 *
 * @author pzh
 * @date  2025/05/30 09:35
 **/
@Getter
public enum BizCrmRecPlanEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    BizCrmRecPlanEnum(String value) {
        this.value = value;
    }
}
