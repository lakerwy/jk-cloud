package vip.xiaonuo.biz.modular.prj.por.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目产值台账 - 项目管理 - 项目现场管理费用 - 分类明细实体
 *
 * @author yy
 * @date  2025/06/03 09:47
 **/
@Getter
@Setter
@TableName("BIZ_POR_SITE_FEE_ITEM")
public class PorSiteFeeItem {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 项目现场管理费用- 主键Id */
    @ApiModelProperty(value = "项目现场管理费用- 主键Id", position = 2)
    private String porSiteFeeId;

    /** 归属月份 */
    @ApiModelProperty(value = "归属月份", position = 3)
    private String feeMonth;

    /** 费用类型 */
    @ApiModelProperty(value = "费用类型", position = 4)
    private String feeType;

    /** 金额 */
    @ApiModelProperty(value = "金额", position = 5)
    private BigDecimal feeAmount;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 6)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 7)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 8)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 9)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 10)
    private String memo;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 11)
    private String attach;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 12)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 13)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;
}
