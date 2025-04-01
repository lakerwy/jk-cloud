package vip.xiaonuo.biz.modular.ledger.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 拨付台账实体
 *
 * @author pzh
 * @date  2025/03/18 10:43
 **/
@Getter
@Setter
@TableName("BIZ_LEDGER_ALLOCATION")
public class BizLedgerAllocation {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 项目名称 */
    @ApiModelProperty(value = "项目名称", position = 2)
    private String projectName;

    /** 合同ID */
    @ApiModelProperty(value = "合同ID", position = 3)
    private String hetongId;

    /** 合同编号 */
    @ApiModelProperty(value = "合同编号", position = 4)
    private String contractNo;

    /** 合作类型（分公司|零星） */
    @ApiModelProperty(value = "合作类型（分公司|零星）", position = 5)
    private String contractType;

    /** 合作单位 */
    @ApiModelProperty(value = "合作单位", position = 6)
    private String unitName;

    /** 合作单位ID */
    @ApiModelProperty(value = "合作单位ID", position = 7)
    private String unitId;

    /** 合作单位组织ID */
    @ApiModelProperty(value = "合作单位组织ID", position = 8)
    private String orgId;

    /** 所属大区 */
    @ApiModelProperty(value = "所属大区", position = 9)
    private String region;

    /** 合同金额-建科所占金额 */
    @ApiModelProperty(value = "合同金额-建科所占金额", position = 10)
    private BigDecimal contractAmount;

    /** 累计回款 */
    @ApiModelProperty(value = "累计回款", position = 11)
    private BigDecimal receiveAmountAcc;

    /** 本次回款 */
    @ApiModelProperty(value = "本次回款", position = 12)
    private BigDecimal receiveAmount;

    /** 本次回款时间 */
    @ApiModelProperty(value = "本次回款时间", position = 13)
    private Date receiveAmountTime;

    /** 本次管理费 */
    @ApiModelProperty(value = "本次管理费", position = 14)
    private BigDecimal manageAmount;

    /** 本次拨付金额 */
    @ApiModelProperty(value = "本次拨付金额", position = 15)
    private BigDecimal payAmount;

    /** 递交行政时间 */
    @ApiModelProperty(value = "递交行政时间", position = 16)
    private Date submitXzTime;

    /** 递交财务时间 */
    @ApiModelProperty(value = "递交财务时间", position = 17)
    private Date submitCwTime;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 18)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 19)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 20)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 21)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 22)
    private String memo;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 23)
    private String attachments;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 24)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 25)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 26)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;

    /** 留痕备注 */
    @ApiModelProperty(value = "留痕备注", position = 42)
    private String note;
}
