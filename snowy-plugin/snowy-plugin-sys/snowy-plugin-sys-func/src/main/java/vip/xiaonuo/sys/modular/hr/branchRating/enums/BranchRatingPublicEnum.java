package vip.xiaonuo.sys.modular.hr.branchRating.enums;

import lombok.Getter;

/**
 * 分公司评级-公示年份和季度枚举
 *
 * @author pzh
 * @date  2024/09/04 10:21
 **/
@Getter
public enum BranchRatingPublicEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    BranchRatingPublicEnum(String value) {
        this.value = value;
    }
}
