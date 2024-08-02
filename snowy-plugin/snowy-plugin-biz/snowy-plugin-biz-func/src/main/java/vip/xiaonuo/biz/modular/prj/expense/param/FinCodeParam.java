package vip.xiaonuo.biz.modular.prj.expense.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
public class FinCodeParam {


    /** 公司 */
    @ApiModelProperty(value = "公司", position = 2)
    private String company;

    /** 公司码 */
    @ApiModelProperty(value = "公司码", required = true, position = 3)
    @NotBlank(message = "公司码不能为空")
    private String codePre;

    /** 年 */
    @ApiModelProperty(value = "年",required = true, position = 4)
    @NotBlank(message = "年不能为空")
    private String codeYear;

    /** 月 */
    @ApiModelProperty(value = "月", required = true,position = 5)
    @NotBlank(message = "月不能为空")
    private String codeMonth;
}
