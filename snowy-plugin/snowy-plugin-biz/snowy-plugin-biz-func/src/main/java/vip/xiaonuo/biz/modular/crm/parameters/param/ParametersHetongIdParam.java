package vip.xiaonuo.biz.modular.crm.parameters.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author yong.ye
 * @date 2023/7/3 15:23
 */

@Getter
@Setter
public class ParametersHetongIdParam {
    /** 合同ID */
    @ApiModelProperty(value = "合同ID（历史表Parameters.ProjectId）", required = true)
    @NotBlank(message = "hetongId不能为空")
    private String hetongId;
}
