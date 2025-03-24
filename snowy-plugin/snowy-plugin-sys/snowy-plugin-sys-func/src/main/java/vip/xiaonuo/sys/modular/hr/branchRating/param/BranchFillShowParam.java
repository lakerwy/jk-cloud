package vip.xiaonuo.sys.modular.hr.branchRating.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Getter
@Setter
public class BranchFillShowParam {

    /** 全部公示：1 否则 0 */
    @ApiModelProperty(value = "全部公示：1 否则 0", required = true, position = 1)
    @NotNull(message = "公示类别：是否全部公示必填")
    private Integer isAll;

    /**  公示：1 取消公示： 0 */
    @ApiModelProperty(value = "公示：1 取消公示：0", required = true, position = 2)
    @NotNull(message = "是否公示必填")
    private Integer isShow;

    /** 填报季度 */
    @ApiModelProperty(value = "填报季度", position = 3)
    private String fillSeason;

    /** 填报记录 - 主键ID */
    @ApiModelProperty(value = "填报记录 - 主键ID", position = 4)
    private String id;

}
