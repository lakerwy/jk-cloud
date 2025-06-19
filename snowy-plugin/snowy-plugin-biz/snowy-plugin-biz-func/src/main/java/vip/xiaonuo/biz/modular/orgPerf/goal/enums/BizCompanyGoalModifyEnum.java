package vip.xiaonuo.biz.modular.orgPerf.goal.enums;

import lombok.Getter;

/**
 * 公司绩效考核-目标调整枚举
 *
 * @author pzh
 * @date  2025/06/18 14:24
 **/
@Getter
public enum BizCompanyGoalModifyEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    BizCompanyGoalModifyEnum(String value) {
        this.value = value;
    }
}
