package vip.xiaonuo.biz.modular.orgPerf.goal.enums;

import lombok.Getter;

/**
 * 子公司及生产部门绩效考核-考核部门枚举
 *
 * @author pzh
 * @date  2025/06/24 16:33
 **/
@Getter
public enum BizOrgAssOrgEnum {

    CHILDREN_COMPANY("子公司"),

    PRODUCT_DEPT("生产部门"),
    ;

    private final String value;

    BizOrgAssOrgEnum(String value) {
        this.value = value;
    }
}
