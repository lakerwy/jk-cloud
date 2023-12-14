package vip.xiaonuo.biz.modular.cad.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 校审区 -- 审批
 */

@Getter
@Setter
public class AuditParam {

    /** 审批记录Id：主键Id  CadProofread.Id */
    @ApiModelProperty(value = "审批记录Id：主键Id CadProofread.Id ", position = 1)
    private String proofreadId;

    /** 状态：校审|校对|审批 - 通过|退回 */
    @ApiModelProperty(value = "状态：校审|校对|审批 - 通过|退回", position = 7)
    private String status;
}
