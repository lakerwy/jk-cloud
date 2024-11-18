package vip.xiaonuo.sys.modular.hr.honorAward.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 项目成果Id参数
 *
 * @author pzh
 * @date  2024/10/16 15:18
 **/
@Getter
@Setter
public class HonorAwardIdParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
