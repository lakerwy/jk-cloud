package vip.xiaonuo.biz.modular.orgPerf.goal.enums;

import lombok.Getter;

/**
 * 子公司及生产部门绩效考核-考核指标枚举
 *
 * @author pzh
 * @date  2025/06/24 16:45
 **/
@Getter
public enum BizOrgGoalItemEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    BizOrgGoalItemEnum(String value) {
        this.value = value;
    }
}
