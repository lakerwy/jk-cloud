package vip.xiaonuo.biz.modular.emp.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.emp.entity.BizEmpTaskFlw;

import java.util.List;

/**
 * @author yong.ye
 * @date 2023/9/21 10:00
 */

@Getter
@Setter
public class BizEmpTaskFlwVo extends BizEmpTaskFlw {

    /** 绩效评分 - 主键 - Id  (来源：BIZ_EMP_PERFORMANCE.Id)*/
    @ApiModelProperty(value = "绩效评分 -主键 - Id (来源：BIZ_EMP_PERFORMANCE.Id)", position = 6)
    private String epId;

    /** 绩效评分 - 流程 - 模型Id modelId (来源：BIZ_EMP_PERFORMANCE.modelId)*/
    @ApiModelProperty(value = "绩效评分 - 流程 - modelId (来源：BIZ_EMP_PERFORMANCE.modelId)", position = 6)
    private String epModelId;

    /** 绩效评分 - 流程 - 实例Id processInstanceId (来源：BIZ_EMP_PERFORMANCE.processInstanceId)*/
    @ApiModelProperty(value = "绩效评分 - 流程 - modelId (来源：BIZ_EMP_PERFORMANCE.processInstanceId)", position = 6)
    private String epProcessInstanceId;

    /** 绩效评分 - 流程 - SN (来源：BIZ_EMP_PERFORMANCE.SN)*/
    @ApiModelProperty(value = "绩效评分 - 流程 - SN (来源：BIZ_EMP_PERFORMANCE.SN)", position = 6)
    private String epSn;

    /** 合计-员工季度任务-自评 */
    @ApiModelProperty(value = "合计-员工季度任务-自评", position = 12)
    private Double epEmpTaskSelfScore;

    /** 合计-员工季度表现-自评 */
    @ApiModelProperty(value = "合计-员工季度表现-自评", position = 13)
    private Double epEmpExpressionSelfScore;

    /** 合计-员工季度任务-评分 */
    @ApiModelProperty(value = "合计-员工季度任务-评分", position = 14)
    private Double epEmpTaskScore;

    /** 合计-员工季度表现-评分 */
    @ApiModelProperty(value = "合计-员工季度表现-评分", position = 15)
    private Double epEmpExpressionScore;

    /** 员工季度-评估得分 */
    @ApiModelProperty(value = "员工季度-评估得分", position = 16)
    private Double epEmpSeasonScore;

    /** 员工季度-绩效等级 */
    @ApiModelProperty(value = "员工季度-绩效等级", position = 17)
    private String epEmpSeasonLevel;

    /** 流程状态(绩效评估流程) */
    @ApiModelProperty(value = "流程状态(绩效评估流程)", position = 23)
    private String epStatus;

    /**一级部门id*/
    @ApiModelProperty(value = "一级部门Id", position = 19)
    private String firstOrgId;

    /**一级部门名称*/
    @ApiModelProperty(value = "一级部门名称", position = 20)
    private String firstOrgName;
}
