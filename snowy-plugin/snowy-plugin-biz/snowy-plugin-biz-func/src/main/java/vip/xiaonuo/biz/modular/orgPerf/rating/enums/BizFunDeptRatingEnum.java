package vip.xiaonuo.biz.modular.orgPerf.rating.enums;

import lombok.Getter;

/**
 * 职能部门-绩效评分枚举
 *
 * @author pzh
 * @date  2025/06/30 16:37
 **/
@Getter
public enum BizFunDeptRatingEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    BizFunDeptRatingEnum(String value) {
        this.value = value;
    }
}
