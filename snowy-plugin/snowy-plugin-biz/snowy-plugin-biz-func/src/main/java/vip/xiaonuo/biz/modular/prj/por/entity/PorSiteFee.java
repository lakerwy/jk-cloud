package vip.xiaonuo.biz.modular.prj.por.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目产值台账 - 项目管理 - 项目现场管理费用实体
 *
 * @author yy
 * @date  2025/06/03 09:45
 **/
@Getter
@Setter
@TableName("BIZ_POR_SITE_FEE")
public class PorSiteFee {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 收入合同- 主键Id */
    @ApiModelProperty(value = "收入合同- 主键Id", position = 2)
    private String hetongId;

    /** 费用归属月份 */
    @ApiModelProperty(value = "费用归属月份", position = 3)
    private String feeMonth;

    /** 本月费用合计 */
    @ApiModelProperty(value = "本月费用合计", position = 4)
    private BigDecimal feeMonthTotal;

    /** 预算金额 */
    @ApiModelProperty(value = "预算金额", position = 5)
    private BigDecimal feePreAmount;

    /** 超支比例 */
    @ApiModelProperty(value = "超支比例", position = 6)
    private Double feeOutRate;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 7)
    private String feeAttach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 8)
    private String feeMemo;

    /** 记录人id */
    @ApiModelProperty(value = "记录人id", position = 9)
    private String feeOptId;

    /** 记录人 */
    @ApiModelProperty(value = "记录人", position = 10)
    private String feeOptName;

    /** 记录时间 */
    @ApiModelProperty(value = "记录时间", position = 11)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date feeOptTime;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 12)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 13)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 14)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 15)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 预留字段-备注 */
    @ApiModelProperty(value = "预留字段-备注", position = 16)
    private String memo;

    /** 预留字段-附件 */
    @ApiModelProperty(value = "预留字段-附件", position = 17)
    private String attach;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 18)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 19)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 20)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;
}
