package vip.xiaonuo.biz.modular.branch.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 分公司日常费用拨付 - 申请流程Id参数
 *
 * @author pzh
 * @date  2024/08/06 14:03
 **/
@Getter
@Setter
public class BranchAllotmentApplyIdParam {

    /** 主键ID */
    @ApiModelProperty(value = "分公司日常费用拨付申请-主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
