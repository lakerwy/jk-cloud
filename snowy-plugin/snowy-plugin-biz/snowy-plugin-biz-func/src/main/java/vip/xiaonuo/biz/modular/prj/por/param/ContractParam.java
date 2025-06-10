package vip.xiaonuo.biz.modular.prj.por.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
public class ContractParam {

    /** 收入合同- 主键Id */
    @ApiModelProperty(value = "收入合同- 主键Id",required = true, position = 1)
    @NotBlank(message = "合同id不能为空")
    private String hetongId;
}
