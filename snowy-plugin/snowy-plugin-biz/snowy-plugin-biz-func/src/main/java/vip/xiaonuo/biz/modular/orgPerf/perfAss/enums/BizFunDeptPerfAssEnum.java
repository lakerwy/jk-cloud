package vip.xiaonuo.biz.modular.orgPerf.perfAss.enums;

import lombok.Getter;

/**
 * 职能部门-绩效评估枚举
 *
 * @author pzh
 * @date  2025/06/30 10:41
 **/
@Getter
public enum BizFunDeptPerfAssEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    BizFunDeptPerfAssEnum(String value) {
        this.value = value;
    }
}
