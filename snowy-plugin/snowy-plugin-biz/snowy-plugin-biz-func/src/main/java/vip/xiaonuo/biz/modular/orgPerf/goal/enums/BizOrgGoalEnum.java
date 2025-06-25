package vip.xiaonuo.biz.modular.orgPerf.goal.enums;

import lombok.Getter;

/**
 * 组织绩效考核-目标登记枚举
 *
 * @author pzh
 * @date  2025/06/24 16:33
 **/
@Getter
public enum BizOrgGoalEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    BizOrgGoalEnum(String value) {
        this.value = value;
    }
}
