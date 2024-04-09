package vip.xiaonuo.sys.modular.hr.salary.param.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 列表统计参数
 */

@Getter
@Setter
public class TotalParam {
    /** 所属年月 */
    @ApiModelProperty(value = "所属年月",required = true)
    @NotBlank(message = "所属年月不能为空")
    private String yearMonth;

    /** 统计类型：封存列表 : salary  预览列表 : salaryPreview */
    @ApiModelProperty(value = "统计类型：封存列表 : salary  预览列表 : salaryPreview",required = true)
    @NotBlank(message = "统计类型不能为空")
    private String type;
}
