package vip.xiaonuo.sys.modular.hr.branchRating.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 分公司评级-公示年份和季度添加参数
 *
 * @author pzh
 * @date  2024/09/04 10:21
 **/
@Getter
@Setter
public class BranchRatingPublicAddParam {

    /** 当前公示年份(如：2024) */
    @ApiModelProperty(value = "当前公示年份(如：2024)", position = 3)
    private String year;

    /** 当前公示季度(上半年、下半年) */
    @ApiModelProperty(value = "当前公示季度(上半年、下半年)", position = 4)
    private String quarter;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 5)
    private String memo;

}
