package vip.xiaonuo.biz.modular.orgPerf.goal.enums;

import lombok.Getter;

/**
 * 公司绩效考核-目标登记枚举
 *
 * @author pzh
 * @date  2025/06/18 10:02
 **/
@Getter
public enum BizCompanyGoalEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    BizCompanyGoalEnum(String value) {
        this.value = value;
    }
}
