package vip.xiaonuo.sys.modular.hr.school.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 校招 - （应聘者）- 推送部门流程实体
 *
 * @author yong.ye
 * @date  2024/09/03 10:47
 **/
@Getter
@Setter
@TableName("HR_SCHOOL_DEPT")
public class SchoolDept {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 应聘部门Id */
    @ApiModelProperty(value = "应聘部门Id", position = 2)
    private String deptId;

    /** 应聘部门 */
    @ApiModelProperty(value = "应聘部门", position = 3)
    private String dept;

    /** 审核人Id */
    @ApiModelProperty(value = "审核人Id", position = 4)
    private String auditId;

    /** 审核人 */
    @ApiModelProperty(value = "审核人", position = 5)
    private String audit;

    /** 推荐的简历集合JSON */
    @ApiModelProperty(value = "推荐的简历集合JSON", position = 6)
    private String resumeJson;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 7)
    private String memo;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 8)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 9)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

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

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 14)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;

    /** SN */
    @ApiModelProperty(value = "SN", position = 15)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 16)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 17)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 18)
    private String status;
}
