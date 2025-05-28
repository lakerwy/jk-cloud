package vip.xiaonuo.biz.modular.reception.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 接待申请Id参数
 *
 * @author pzh
 * @date  2025/05/28 10:15
 **/
@Getter
@Setter
public class ReceptionApplyIdParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
