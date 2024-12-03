package vip.xiaonuo.sys.modular.hr.reputation.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 信誉情况实体
 *
 * @author pzh
 * @date  2024/10/18 16:52
 **/
@Getter
@Setter
@TableName("REPUTATION")
public class Reputation {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 被拉黑单位 */
    @ApiModelProperty(value = "被拉黑单位", position = 2)
    private String unitName;

    /** 被拉黑原因 */
    @ApiModelProperty(value = "被拉黑原因", position = 3)
    private String reason;

    /** 被拉黑时间 */
    @ApiModelProperty(value = "被拉黑时间", position = 4)
    private Date blacklistDate;

    /** 状态（已拉黑，已解除黑名单） */
    @ApiModelProperty(value = "状态（已拉黑，已解除黑名单）", position = 5)
    private String blacklistStatus;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 6)
    private String attachments;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 7)
    private String memo;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 8)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 9)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 10)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 11)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 12)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 13)
    private String tenantId;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 14)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;
}
