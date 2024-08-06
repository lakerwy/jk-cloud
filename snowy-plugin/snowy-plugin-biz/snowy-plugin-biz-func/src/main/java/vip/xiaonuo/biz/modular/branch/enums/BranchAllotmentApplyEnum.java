package vip.xiaonuo.biz.modular.branch.enums;

import lombok.Getter;

/**
 * 分公司日常费用拨付 - 申请流程枚举
 *
 * @author pzh
 * @date  2024/08/06 14:03
 **/
@Getter
public enum BranchAllotmentApplyEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    BranchAllotmentApplyEnum(String value) {
        this.value = value;
    }
}
