package vip.xiaonuo.biz.modular.prj.por.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目产值台账 - 项目管理 - 经营费管理实体
 *
 * @author yy
 * @date  2025/06/03 09:56
 **/
@Getter
@Setter
@TableName("BIZ_POR_OEM")
public class PorOem {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 收入合同- 主键Id */
    @ApiModelProperty(value = "收入合同- 主键Id", position = 2)
    private String hetongId;

    /** 经营费比例 */
    @ApiModelProperty(value = "经营费比例", position = 3)
    private Double oemRate;

    /** 应收经营费总金额 */
    @ApiModelProperty(value = "应收经营费总金额", position = 4)
    private BigDecimal oemTotalAmount;

    /** 累计结算金额 */
    @ApiModelProperty(value = "累计结算金额", position = 5)
    private BigDecimal oemSettleAmount;

    /** 累计付款金额 */
    @ApiModelProperty(value = "累计付款金额", position = 5)
    private BigDecimal oemPayAmount;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 6)
    private String oemAttach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 7)
    private String oemMemo;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 8)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 9)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 10)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 11)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 预留字段-备注 */
    @ApiModelProperty(value = "预留字段-备注", position = 12)
    private String memo;

    /** 预留字段-附件 */
    @ApiModelProperty(value = "预留字段-附件", position = 13)
    private String attach;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 14)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 15)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 16)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;
}
