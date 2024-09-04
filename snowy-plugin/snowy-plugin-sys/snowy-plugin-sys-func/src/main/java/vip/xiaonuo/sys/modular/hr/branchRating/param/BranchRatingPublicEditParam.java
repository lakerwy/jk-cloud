package vip.xiaonuo.sys.modular.hr.branchRating.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 分公司评级-公示年份和季度编辑参数
 *
 * @author pzh
 * @date  2024/09/04 10:21
 **/
@Getter
@Setter
public class BranchRatingPublicEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

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
