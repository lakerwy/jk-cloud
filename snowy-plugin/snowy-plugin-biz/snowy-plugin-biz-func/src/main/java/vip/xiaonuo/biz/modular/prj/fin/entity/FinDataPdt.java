package vip.xiaonuo.biz.modular.prj.fin.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 财务数据汇总台账 - 生产院实体
 *
 * @author yy
 * @date  2025/06/05 17:26
 **/
@Getter
@Setter
@TableName("FIN_DATA_PDT")
public class FinDataPdt {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 合同Id */
    @ApiModelProperty(value = "合同Id", position = 2)
    private String contractId;

    /** 合同编码 */
    @ApiModelProperty(value = "合同编码", position = 3)
    private String contractNo;

    /** 拨付日期 */
    @ApiModelProperty(value = "拨付日期", position = 4)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date bfDate;

    /** 拨付外部产值 */
    @ApiModelProperty(value = "拨付外部产值", position = 5)
    private BigDecimal bfOutput;

    /** 拨付经营费 */
    @ApiModelProperty(value = "拨付经营费", position = 6)
    private BigDecimal bfExpense;

    /** 拨付分包款 */
    @ApiModelProperty(value = "拨付分包款", position = 7)
    private BigDecimal bfSub;

    /** 拨付劳务费 */
    @ApiModelProperty(value = "拨付劳务费", position = 8)
    private BigDecimal bfLabor;

    /** 拨付其他费用 */
    @ApiModelProperty(value = "拨付其他费用", position = 9)
    private BigDecimal bfOther;

    /** 扣税差 */
    @ApiModelProperty(value = "扣税差", position = 10)
    private BigDecimal kkTax;

    /** 其他扣款金额 */
    @ApiModelProperty(value = "其他扣款金额", position = 11)
    private BigDecimal kkOther;

    /** 其他扣款事项 */
    @ApiModelProperty(value = "其他扣款事项", position = 12)
    private String kkOtherContent;

    /** 描述 */
    @ApiModelProperty(value = "描述", position = 13)
    private String content;

    /** 留痕 */
    @ApiModelProperty(value = "留痕", position = 14)
    private String extJson;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 15)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 16)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 17)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 18)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 预留字段-备注 */
    @ApiModelProperty(value = "预留字段-备注", position = 19)
    private String memo;

    /** 预留字段-附件 */
    @ApiModelProperty(value = "预留字段-附件", position = 20)
    private String attach;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 21)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 22)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 23)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;
}
