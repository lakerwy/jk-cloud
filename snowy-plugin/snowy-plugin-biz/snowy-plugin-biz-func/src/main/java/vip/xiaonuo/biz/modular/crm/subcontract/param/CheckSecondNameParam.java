package vip.xiaonuo.biz.modular.crm.subcontract.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author yong.ye
 * @date 2023/11/3 8:57
 */


@Getter
@Setter
public class CheckSecondNameParam {

    /** 主合同Id  */
    @ApiModelProperty(value = "主合同Id", position = 1,required = true)
    @NotBlank(message = "主合同Id不能为空")
    private String hetongId;

    /** 乙方名称 */
    @ApiModelProperty(value = "乙方名称", position = 2,required = true)
    @NotBlank(message = "乙方名称不能为空")
    private String counterparty;

}
