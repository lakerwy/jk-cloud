package vip.xiaonuo.sys.modular.hr.enterpriseMaterial.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 企业宣传资料Id参数
 *
 * @author pzh
 * @date  2024/09/18 14:57
 **/
@Getter
@Setter
public class EnterpriseMaterialIdParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
