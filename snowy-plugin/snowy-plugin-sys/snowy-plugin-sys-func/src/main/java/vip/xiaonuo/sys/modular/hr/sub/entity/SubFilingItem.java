package vip.xiaonuo.sys.modular.hr.sub.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 分包单位备案明细实体
 *
 * @author pzh
 * @date  2025/03/11 14:32
 **/
@Getter
@Setter
@TableName("SUB_FILING_ITEM")
public class SubFilingItem {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 关联-分包单位备案ID */
    @ApiModelProperty(value = "关联-分包单位备案ID", position = 2)
    private String subFilingId;

    /** 分包单位名称 */
    @ApiModelProperty(value = "分包单位名称", position = 3)
    private String unitName;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 4)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 5)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 6)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 7)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 8)
    private String memo;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 9)
    private String attachments;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 10)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 11)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 12)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;
}
