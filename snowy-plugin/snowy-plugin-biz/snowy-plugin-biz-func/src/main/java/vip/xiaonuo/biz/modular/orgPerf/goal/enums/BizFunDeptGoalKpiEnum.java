package vip.xiaonuo.biz.modular.orgPerf.goal.enums;

import lombok.Getter;

/**
 * 职能部门绩效考核-考核指标枚举
 *
 * @author pzh
 * @date  2025/06/26 16:36
 **/
@Getter
public enum BizFunDeptGoalKpiEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    BizFunDeptGoalKpiEnum(String value) {
        this.value = value;
    }
}
