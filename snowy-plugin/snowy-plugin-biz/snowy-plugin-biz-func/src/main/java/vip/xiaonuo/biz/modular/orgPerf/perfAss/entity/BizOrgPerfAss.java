package vip.xiaonuo.biz.modular.orgPerf.perfAss.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 子公司及生产部门绩效考核-绩效评估实体
 *
 * @author pzh
 * @date  2025/06/25 15:01
 **/
@Getter
@Setter
@TableName("BIZ_ORG_PERF_ASS")
public class BizOrgPerfAss {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 关联-目标登记主键ID */
    @ApiModelProperty(value = "关联-目标登记主键ID", position = 2)
    private String goalId;

    /** 基本项目标得分（合计） */
    @ApiModelProperty(value = "基本项目标得分（合计）", position = 3)
    private BigDecimal itemScoreTotal;

    /** 部门履职情况得分 */
    @ApiModelProperty(value = "部门履职情况得分", position = 4)
    private BigDecimal orgDutyScore;

    /** 实际加分分值（合计） */
    @ApiModelProperty(value = "实际加分分值（合计）", position = 5)
    private BigDecimal addScoreTotal;

    /** 实际减分分值（合计） */
    @ApiModelProperty(value = "实际减分分值（合计）", position = 6)
    private BigDecimal minusScoreTotal;

    /** 总结 */
    @ApiModelProperty(value = "总结", position = 7)
    private String summary;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 8)
    private String attach;

    /** 总分 */
    @ApiModelProperty(value = "总分", position = 9)
    private BigDecimal totalScore;

    /** 评定等级 */
    @ApiModelProperty(value = "评定等级", position = 10)
    private String assLevel;

    /** 评定系数 */
    @ApiModelProperty(value = "评定系数", position = 11)
    private String assFactor;

    /** 上级评价意见 */
    @ApiModelProperty(value = "上级评价意见", position = 12)
    private String superiorOpinion;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 13)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 14)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 15)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 16)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 17)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 18)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 19)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 20)
    private String status;

    /** SN流水号 */
    @ApiModelProperty(value = "SN流水号", position = 21)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 22)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 23)
    private String processInstanceId;
}
