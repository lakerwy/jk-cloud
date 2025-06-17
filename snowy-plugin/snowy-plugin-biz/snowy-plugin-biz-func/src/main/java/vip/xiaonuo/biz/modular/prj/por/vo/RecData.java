package vip.xiaonuo.biz.modular.prj.por.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.crm.parameters.entity.BizCrmRecPlan;

import java.util.List;

/**
 * 回款计划
 */

@Getter
@Setter
public class RecData {

    /** 合同 - 流程实例Id */
    @ApiModelProperty(value = "合同 - 流程实例Id", position = 2)
    private String processInstanceId;

    /** 合同 - 流程状态 */
    @ApiModelProperty(value = "合同 - 流程状态", position = 3)
    private String status;

    /** 合同 - 合同Id */
    @ApiModelProperty(value = "合同 - 合同Id", position = 4)
    private String htId;

    /** 合同 - 合同名称 */
    @ApiModelProperty(value = "合同 - 合同名称", position = 5)
    private String htProjectName;

    /** 合同 - 合同编号 */
    @ApiModelProperty(value = "合同 - 合同编号", position = 6)
    private String htContractNo;



    /** 回款计划 */
    @ApiModelProperty(value = "回款计划", position = 10)
    private List<BizCrmRecPlan> planList;
}
