package vip.xiaonuo.sys.modular.hr.branchRating.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
public class BranchAreaParam {

    /** 分公司id */
    @ApiModelProperty(value = "分公司id", required = true, position = 2)
    @NotBlank(message = "分公司id不可为空")
    private String branchId;

    /** 分公司名称 */
    @ApiModelProperty(value = "分公司名称", required = true, position = 3)
    @NotBlank(message = "分公司名称不可为空")
    private String branchName;


    /** 分公司对应的组织orgId */
    @ApiModelProperty(value = "分公司对应的组织orgId", position = 4)
    private String orgId;
}
