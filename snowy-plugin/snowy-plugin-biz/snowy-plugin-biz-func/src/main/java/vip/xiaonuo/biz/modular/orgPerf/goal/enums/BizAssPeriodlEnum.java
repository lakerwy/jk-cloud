package vip.xiaonuo.biz.modular.orgPerf.goal.enums;

import lombok.Getter;

/**
 * 组织绩效考核-考核时段枚举
 *
 * @author pzh
 * @date  2025/06/24 16:33
 **/
@Getter
public enum BizAssPeriodlEnum {

    SEASON("季度考核"),

    YEAR("年度考核"),
    ;

    private final String value;

    BizAssPeriodlEnum(String value) {
        this.value = value;
    }
}
