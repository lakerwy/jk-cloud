package vip.xiaonuo.sys.modular.hr.salary.param.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 根据 所属年月
 */

@Getter
@Setter
public class SearchParam {

    /** 所属年月 */
    @ApiModelProperty(value = "所属年月",required = true)
    @NotBlank(message = "所属年月不能为空")
    private String yearMonth;

}
