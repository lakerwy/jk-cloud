package vip.xiaonuo.biz.modular.prj.por.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目产值台账 - 项目管理 - 责任上缴费用管理实体
 *
 * @author yy
 * @date  2025/06/03 09:55
 **/
@Getter
@Setter
@TableName("BIZ_POR_RPM")
public class PorRpm {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 收入合同- 主键Id */
    @ApiModelProperty(value = "收入合同- 主键Id", position = 2)
    private String hetongId;

    /** 责任上缴比例 */
    @ApiModelProperty(value = "责任上缴比例", position = 3)
    private Double rpmRate;

    /** 责任上缴总金额 */
    @ApiModelProperty(value = "责任上缴总金额", position = 4)
    private BigDecimal rpmTotalAmount;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 5)
    private String rpmAttach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 6)
    private String rpmMemo;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 7)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 8)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 9)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 10)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 预留字段-备注 */
    @ApiModelProperty(value = "预留字段-备注", position = 11)
    private String memo;

    /** 预留字段-附件 */
    @ApiModelProperty(value = "预留字段-附件", position = 12)
    private String attach;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 13)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 14)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 15)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;
}
