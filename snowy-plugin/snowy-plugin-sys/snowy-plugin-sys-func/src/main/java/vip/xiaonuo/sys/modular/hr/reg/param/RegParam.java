package vip.xiaonuo.sys.modular.hr.reg.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
public class RegParam {

    /** personnelId - 申请人-花名册主键id */
    @ApiModelProperty(value = "personnelId - 申请人-花名册主键id", required = true, position = 1)
    @NotBlank(message = "personnelId不能为空")
    private String personnelId;
}
