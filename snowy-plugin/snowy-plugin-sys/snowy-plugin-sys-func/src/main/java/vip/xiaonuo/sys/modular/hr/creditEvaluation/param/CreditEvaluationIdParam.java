package vip.xiaonuo.sys.modular.hr.creditEvaluation.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 信用评价Id参数
 *
 * @author pzh
 * @date  2024/10/18 10:49
 **/
@Getter
@Setter
public class CreditEvaluationIdParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
