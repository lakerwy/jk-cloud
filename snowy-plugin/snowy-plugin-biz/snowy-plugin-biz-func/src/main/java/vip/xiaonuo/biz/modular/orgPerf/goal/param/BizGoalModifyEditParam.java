package vip.xiaonuo.biz.modular.orgPerf.goal.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 公司绩效考核-目标调整编辑参数
 *
 * @author pzh
 * @date  2025/06/18 14:24
 **/
@Getter
@Setter
public class BizGoalModifyEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 目标登记主键ID */
    @ApiModelProperty(value = "目标登记主键ID", required = true, position = 3)
    @NotBlank(message = "目标登记主键ID不能为空")
    private String goalId;

    /** 目标登记主键ID */
    @ApiModelProperty(value = "绩效目标类型(公司|班子|子公司|生产部门|职能部门)", required = true, position = 4)
    @NotBlank(message = "绩效目标类型perfGoalType必填")
    private String perfGoalType;

    /** 目标登记主键ID */
    @ApiModelProperty(value = "绩效评估类别(业务类|职能类)", position = 5)
    private String perfAssType;

    /** 目标登记主键ID */
    @ApiModelProperty(value = "考核类型（季度考核|年度考核）", position = 7)
    private String assType;

    /** 流程表单数据JSON */
    @ApiModelProperty(value = "流程表单数据JSON", position = 11)
    private String dataJson;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 13)
    private String memo;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 15)
    private String status;

    /** SN流水号 */
    @ApiModelProperty(value = "SN流水号", position = 17)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 19)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 21)
    private String processInstanceId;

}
