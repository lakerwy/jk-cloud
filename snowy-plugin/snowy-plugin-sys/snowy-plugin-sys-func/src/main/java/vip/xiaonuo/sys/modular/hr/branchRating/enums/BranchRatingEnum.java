package vip.xiaonuo.sys.modular.hr.branchRating.enums;

import lombok.Getter;

/**
 * 分公司评级枚举
 *
 * @author pzh
 * @date  2024/09/03 10:03
 **/
@Getter
public enum BranchRatingEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    BranchRatingEnum(String value) {
        this.value = value;
    }
}
