package vip.xiaonuo.iot.modular.project.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 物联网-项目Id参数
 *
 * @author mayike
 * @date  2024/08/08 14:03
 **/
@Getter
@Setter
public class IotProjectIdParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
