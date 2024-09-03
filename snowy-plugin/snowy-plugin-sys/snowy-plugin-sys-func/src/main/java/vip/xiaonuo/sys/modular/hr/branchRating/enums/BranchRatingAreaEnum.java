package vip.xiaonuo.sys.modular.hr.branchRating.enums;

import lombok.Getter;

/**
 * 分公司评级-区域枚举
 *
 * @author pzh
 * @date  2024/09/03 10:03
 **/
@Getter
public enum BranchRatingAreaEnum {

    HZ_AREA("华中区域"),
    XN_AREA("西南区域"),
    HD_AREA("华东区域"),
    HN_AREA("华南区域"),
    XB_AREA("西北区域"),
    HB_AREA("华北区域"),
    ;

    private final String value;

    BranchRatingAreaEnum(String value) {
        this.value = value;
    }
}
