package vip.xiaonuo.biz.modular.orgPerf.rating.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 职能部门-绩效评分编辑参数
 *
 * @author pzh
 * @date  2025/06/30 16:37
 **/
@Getter
@Setter
public class BizFunDeptRatingEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 关联-kpi主键ID */
    @ApiModelProperty(value = "关联-kpi主键ID", position = 2)
    private String kpiId;

    /** 部门自评分数 */
    @ApiModelProperty(value = "部门自评分数", position = 3)
    private BigDecimal deptSelfAssScore;

    /** 领导评分 */
    @ApiModelProperty(value = "领导评分", position = 4)
    private BigDecimal leaderAssScore;

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
