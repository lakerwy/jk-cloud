package vip.xiaonuo.biz.modular.orgPerf.goal.enums;

import lombok.Getter;

/**
 * 公司绩效考核-考核指标枚举
 *
 * @author pzh
 * @date  2025/06/18 10:15
 **/
@Getter
public enum BizCompanyGoalKpiEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    BizCompanyGoalKpiEnum(String value) {
        this.value = value;
    }
}
