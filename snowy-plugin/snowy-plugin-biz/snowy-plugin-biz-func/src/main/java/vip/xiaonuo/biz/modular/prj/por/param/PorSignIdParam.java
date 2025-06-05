package vip.xiaonuo.biz.modular.prj.por.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 项目产值台账 - 项目管理 - 签字审图费管理Id参数
 *
 * @author yy
 * @date  2025/06/03 09:54
 **/
@Getter
@Setter
public class PorSignIdParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
