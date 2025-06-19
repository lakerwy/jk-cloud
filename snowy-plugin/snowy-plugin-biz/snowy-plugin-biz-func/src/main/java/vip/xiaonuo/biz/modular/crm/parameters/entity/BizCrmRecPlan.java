package vip.xiaonuo.biz.modular.crm.parameters.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 合同收款计划实体
 *
 * @author pzh
 * @date  2025/05/30 09:35
 **/
@Getter
@Setter
@TableName("BIZ_CRM_REC_PLAN")
public class BizCrmRecPlan {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 关联-合同参数主键ID */
    @ApiModelProperty(value = "关联-合同参数主键ID", position = 2)
    private String parametersId;

    @ApiModelProperty(value = "合同ID", position = 2)
    private String hetongId;

    /** 款项性质 */
    @ApiModelProperty(value = "款项性质", position = 3)
    private String feeType;

    /** 收款节点 */
    @ApiModelProperty(value = "收款节点", position = 4)
    private String recNode;

    /** 计划收款日期（格式：yyyy-MM-dd） */
    @ApiModelProperty(value = "计划收款日期（格式：yyyy-MM-dd）", position = 5)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date plannedRecDate;

    /** 缓冲期 */
    @ApiModelProperty(value = "缓冲期", position = 6)
    private Integer gracePeriod;

    /** 收款比例（按总价计算） */
    @ApiModelProperty(value = "收款比例（按总价计算）", position = 7)
    private BigDecimal recRatio;

    /** 计划收款金额 */
    @ApiModelProperty(value = "计划收款金额", position = 8)
    private BigDecimal plannedRecAmount;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 9)
    private String memo;

    /** 回款情况 */
    @ApiModelProperty(value = "回款情况", position = 10)
    private String recStatus;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 15)
    private String attach;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 16)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 17)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 18)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 19)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 20)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 21)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 22)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;


    /** （若均存在）计划收款日期+缓冲期（格式：yyyy-MM-dd） */
    @ApiModelProperty(value = "（若均存在）计划收款日期+缓冲期（计划收款日期（格式：yyyy-MM-dd）", position = 5)
    @TableField(exist = false)
    private Date recDate;
}
