package vip.xiaonuo.biz.modular.crm.subcontract.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.crm.subcontract.entity.SubcontractSettlement;
import vip.xiaonuo.biz.modular.crm.subcontract.entity.SubcontractSettlementItem;

import java.util.List;

/**
 * 分包合同结算审批实体
 *
 * @author pzh
 * @date  2025/04/30 11:19
 **/
@Getter
@Setter
public class SubcontractSettlementVo extends SubcontractSettlement {

    /** 本次申请结算分包合同 */
    @ApiModelProperty(value = "本次申请结算分包合同", position = 10)
    private List<SubcontractSettlementItem> subcontractSettlementItemList;

}
