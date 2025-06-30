package vip.xiaonuo.biz.modular.orgPerf.goal.enums;

import lombok.Getter;

/**
 * 职能部门绩效考核-目标登记枚举
 *
 * @author pzh
 * @date  2025/06/26 16:25
 **/
@Getter
public enum BizFunDeptGoalEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    BizFunDeptGoalEnum(String value) {
        this.value = value;
    }
}
