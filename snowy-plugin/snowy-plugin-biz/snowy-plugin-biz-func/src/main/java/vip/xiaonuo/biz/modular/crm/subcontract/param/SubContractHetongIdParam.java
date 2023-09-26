package vip.xiaonuo.biz.modular.crm.subcontract.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author yong.ye
 * @date 2023/7/3 16:27
 */

@Getter
@Setter
public class SubContractHetongIdParam {
    /** 合同ID */
    @ApiModelProperty(value = "合同ID（历史表SubContract.HetongId）", required = true)
    @NotBlank(message = "hetongId不能为空")
    private String hetongId;
}
