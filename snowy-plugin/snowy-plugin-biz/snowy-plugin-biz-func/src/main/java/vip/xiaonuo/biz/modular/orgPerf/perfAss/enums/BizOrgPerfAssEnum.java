package vip.xiaonuo.biz.modular.orgPerf.perfAss.enums;

import lombok.Getter;

/**
 * 子公司及生产部门绩效考核-绩效评估枚举
 *
 * @author pzh
 * @date  2025/06/25 15:01
 **/
@Getter
public enum BizOrgPerfAssEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    BizOrgPerfAssEnum(String value) {
        this.value = value;
    }
}
