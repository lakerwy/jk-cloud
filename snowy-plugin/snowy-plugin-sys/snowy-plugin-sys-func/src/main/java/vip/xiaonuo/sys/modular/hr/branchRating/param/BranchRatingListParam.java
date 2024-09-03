package vip.xiaonuo.sys.modular.hr.branchRating.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 分公司评级列表查询参数
 *
 * @author pzh
 * @date  2024/09/03 10:03
 **/
@Getter
@Setter
public class BranchRatingListParam {

    /** 年份(如：2024) */
    @ApiModelProperty(value = "年份(如：2024)")
    private String year;

    /** 季度(上半年、下半年) */
    @ApiModelProperty(value = "季度(上半年、下半年)")
    private String quarter;

    /** 区域 */
    @ApiModelProperty(value = "区域")
    private String area;

    /** 级别(A、B、C、D、-) */
    @ApiModelProperty(value = "级别(A、B、C、D、-)")
    private String level;

    /** 公示状态(已公示、未公示) */
    @ApiModelProperty(value = "公示状态(已公示、未公示)")
    private String status;

    /** 关键词 */
    @ApiModelProperty(value = "关键词")
    private String searchKey;

}
