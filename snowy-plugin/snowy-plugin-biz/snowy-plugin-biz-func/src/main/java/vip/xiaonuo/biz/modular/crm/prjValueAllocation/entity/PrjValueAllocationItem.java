package vip.xiaonuo.biz.modular.crm.prjValueAllocation.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目产值划分明细实体
 *
 * @author pzh
 * @date  2025/06/04 16:48
 **/
@Getter
@Setter
@TableName("BIZ_PRJ_VALUE_ALLOCATION_ITEM")
public class PrjValueAllocationItem {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 关联-项目产值划分主键ID */
    @ApiModelProperty(value = "关联-项目产值划分主键ID", position = 2)
    private String allocationId;

    /** 主合同ID */
    @ApiModelProperty(value = "主合同ID", position = 3)
    private String hetongId;

    /** 单位类型 */
    @ApiModelProperty(value = "单位类型", position = 4)
    private String unitType;

    /** 单位名称 */
    @ApiModelProperty(value = "单位名称", position = 5)
    private String unitName;

    /** 负责人 */
    @ApiModelProperty(value = "负责人", position = 6)
    private String leader;

    /** 负责人ID */
    @ApiModelProperty(value = "负责人ID", position = 7)
    private String leaderId;

    /** 产值划分比例 */
    @ApiModelProperty(value = "产值划分比例", position = 8)
    private BigDecimal allocationRate;

    /** 产值划分金额 */
    @ApiModelProperty(value = "产值划分金额", position = 9)
    private BigDecimal allocationAmount;

    /** 费用类型 */
    @ApiModelProperty(value = "费用类型", position = 10)
    private String feeType;

    /** 费用比例 */
    @ApiModelProperty(value = "费用比例", position = 11)
    private BigDecimal feeRate;

    /** 费用金额 */
    @ApiModelProperty(value = "费用金额", position = 12)
    private BigDecimal feeAmount;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 13)
    private String memo;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 14)
    private String attach;

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

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 19)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 20)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 21)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;
}
