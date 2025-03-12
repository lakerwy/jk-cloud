package vip.xiaonuo.sys.modular.hr.sub.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 分包单位备案实体
 *
 * @author pzh
 * @date  2025/03/11 14:32
 **/
@Getter
@Setter
@TableName("SUB_FILING")
public class SubFiling {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 用印部门 */
    @ApiModelProperty(value = "用印部门", position = 2)
    private String sealDept;

    /** 用印部门ID */
    @ApiModelProperty(value = "用印部门ID", position = 3)
    private String sealDeptId;

    /** 用印人 */
    @ApiModelProperty(value = "用印人", position = 4)
    private String sealUser;

    /** 用印人ID */
    @ApiModelProperty(value = "用印人ID", position = 5)
    private String sealUserId;

    /** 区域经理 */
    @ApiModelProperty(value = "区域经理", position = 6)
    private String manager;

    /** 区域经理ID */
    @ApiModelProperty(value = "区域经理ID", position = 7)
    private String managerId;

    /** 用印事项 */
    @ApiModelProperty(value = "用印事项", position = 8)
    private String sealItem;

    /** 内容摘要 */
    @ApiModelProperty(value = "内容摘要", position = 9)
    private String content;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 10)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 11)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 12)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 13)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 14)
    private String memo;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 15)
    private String attachments;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 16)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 17)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 18)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;

    /** SN流水号 */
    @ApiModelProperty(value = "SN流水号", position = 19)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 20)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 21)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 22)
    private String status;

    /** 入参信息 */
    @ApiModelProperty(value = "入参信息", position = 23)
    private String extJson;
}
