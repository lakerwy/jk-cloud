package vip.xiaonuo.sys.modular.hr.financialSheet.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 财务状况表Id参数
 *
 * @author pzh
 * @date  2024/10/18 15:53
 **/
@Getter
@Setter
public class FinancialSheetIdParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
