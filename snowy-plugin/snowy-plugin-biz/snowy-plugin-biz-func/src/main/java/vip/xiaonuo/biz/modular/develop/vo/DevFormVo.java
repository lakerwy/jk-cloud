package vip.xiaonuo.biz.modular.develop.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.develop.entity.DevForm;
import vip.xiaonuo.biz.modular.develop.entity.DevFormItem;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
public class DevFormVo extends DevForm {

    /** 需求拆解 - 集合 */
    @ApiModelProperty(value = "需求拆解 - 集合", position = 100)
    private List<DevFormItem> formItemList;

    /** 需求确认 **/

    /** 需求确认单 - 实际成本（元） */
    @ApiModelProperty(value = "需求确认单 - 实际成本（元）", position = 101)
    private BigDecimal actualCost;

    /** 需求确认单 - 需求结算类型 */
    @ApiModelProperty(value = "需求确认单 - 需求结算类型", position = 102)
    private String settlementType;

    /** 需求确认单 - 需求情况说明 */
    @ApiModelProperty(value = "需求确认单 - 需求情况说明", position = 103)
    private String situationDesc;

    /** 需求确认单 - 需求终止原因 */
    @ApiModelProperty(value = "需求确认单 - 需求终止原因", position = 104)
    private String readjustReason;

    /** 需求确认单 - 需求证明附件 */
    @ApiModelProperty(value = "需求确认单 - 需求证明附件", position = 105)
    private String proofAttach;

    /** 需求确认单 - SN流水号 */
    @ApiModelProperty(value = "需求确认单 - SN流水号", position = 106)
    private String confirmSn;

    /** 需求确认单 - 模型Id */
    @ApiModelProperty(value = "需求确认单 - 模型Id", position = 107)
    private String confirmModelId;

    /** 需求确认单 - 流程实例Id */
    @ApiModelProperty(value = "需求确认单 - 流程实例Id", position = 108)
    private String confirmProcessInstanceId;

    /** 需求确认单 - 流程状态 */
    @ApiModelProperty(value = "需求确认单 - 流程状态", position = 109)
    private String confirmStatus;
}
