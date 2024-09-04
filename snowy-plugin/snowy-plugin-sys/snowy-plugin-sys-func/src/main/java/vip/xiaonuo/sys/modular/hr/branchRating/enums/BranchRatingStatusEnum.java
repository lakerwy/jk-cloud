package vip.xiaonuo.sys.modular.hr.branchRating.enums;

import lombok.Getter;

/**
 * 分公司评级-公示状态枚举
 *
 * @author pzh
 * @date  2024/09/03 10:03
 **/
@Getter
public enum BranchRatingStatusEnum {

    PUBLIC("已公示"),
    NOT_PUBLIC("未公示"),;

    private final String value;

    BranchRatingStatusEnum(String value) {
        this.value = value;
    }
}
