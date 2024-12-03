package vip.xiaonuo.sys.modular.hr.taxGradeEvaluation.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 纳税等级评价Id参数
 *
 * @author pzh
 * @date  2024/11/27 14:45
 **/
@Getter
@Setter
public class TaxGradeEvaluationIdParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
