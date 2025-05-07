package vip.xiaonuo.biz.modular.crm.subcontract.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 分包合同结算 - 合同ID参数
 *
 * @author pzh
 * @date  2025/04/30 11:19
 **/
@Getter
@Setter
public class AllotmentHtIdParam {

    /** 主合同ID */
    @ApiModelProperty(value = "主合同ID", required = true, position = 1)
    @NotNull(message = "主合同ID不能为空")
    private String hetongId;

}
