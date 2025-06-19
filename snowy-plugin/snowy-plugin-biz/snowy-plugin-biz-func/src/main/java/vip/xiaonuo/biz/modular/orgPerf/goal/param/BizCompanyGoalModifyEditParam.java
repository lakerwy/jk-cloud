package vip.xiaonuo.biz.modular.orgPerf.goal.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 公司绩效考核-目标调整编辑参数
 *
 * @author pzh
 * @date  2025/06/18 14:24
 **/
@Getter
@Setter
public class BizCompanyGoalModifyEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 目标登记主键ID */
    @ApiModelProperty(value = "目标登记主键ID", position = 2)
    private String goalId;

    /** 流程表单数据JSON */
    @ApiModelProperty(value = "流程表单数据JSON", position = 3)
    private String dataJson;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 4)
    private String memo;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 12)
    private String status;

    /** SN流水号 */
    @ApiModelProperty(value = "SN流水号", position = 13)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 14)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 15)
    private String processInstanceId;

}
