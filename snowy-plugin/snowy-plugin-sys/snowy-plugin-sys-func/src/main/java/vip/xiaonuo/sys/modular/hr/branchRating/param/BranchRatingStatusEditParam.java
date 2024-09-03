package vip.xiaonuo.sys.modular.hr.branchRating.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 分公司评级 设置公示状态参数
 *
 * @author pzh
 * @date  2024/09/03 10:03
 **/
@Getter
@Setter
public class BranchRatingStatusEditParam {

    /** 年份(如：2024) */
    @ApiModelProperty(value = "年份(如：2024)", position = 6)
    private String year;

    /** 季度(上半年、下半年) */
    @ApiModelProperty(value = "季度(上半年、下半年)", position = 7)
    private String quarter;

}
