package vip.xiaonuo.sys.modular.hr.branchRating.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
public class BranchFillParam {

    /** 填报季度 */
    @ApiModelProperty(value = "填报季度",required = true, position = 1)
    @NotBlank(message = "填报季度必填")
    private String fillSeason;

    /** 区域 */
    @ApiModelProperty(value = "区域", position = 2)
    private String area;

    /** 级别 */
    @ApiModelProperty(value = "级别", position = 3)
    private String level;

    /** 填报状态 */
    @ApiModelProperty(value = "填报状态", position = 4)
    private String fillStatus;

    /** 是否公示 1 */
    @ApiModelProperty(value = "是否公示 1 ", position = 4)
    private Integer isShow;

    /** 关键词 */
    @ApiModelProperty(value = "关键词")
    private String searchKey;


}
