package vip.xiaonuo.sys.modular.hr.school.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 校招 - （应聘者）- 面试入职流程实体
 *
 * @author yong.ye
 * @date  2024/09/03 10:46
 **/
@Getter
@Setter
@TableName("HR_SCHOOL_INTERVIEW")
public class SchoolInterview {

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

    /** 外键：应聘者简历Id */
    @ApiModelProperty(value = "外键：应聘者简历Id", position = 4)
    private String resumeId;

    /** 匹配岗位说明书任职资格要求JSON */
    @ApiModelProperty(value = "匹配岗位说明书任职资格要求JSON", position = 5)
    private String matchJson;

    /** 建议薪酬JSON */
    @ApiModelProperty(value = "建议薪酬JSON", position = 6)
    private String salaryJson;

    /** 人力资源意见JSON */
    @ApiModelProperty(value = "人力资源意见JSON", position = 7)
    private String hrJson;

    /** 用人部门意见JSON */
    @ApiModelProperty(value = "用人部门意见JSON", position = 8)
    private String deptJson;

    /** 董事长意见JSON */
    @ApiModelProperty(value = "董事长意见JSON", position = 9)
    private String bossJson;

    /** 录用结果 */
    @ApiModelProperty(value = "录用结果", position = 10)
    private String reasult;

    /** 其他附件 */
    @ApiModelProperty(value = "其他附件", position = 11)
    private String otherAttach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 12)
    private String memo;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 13)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 14)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

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

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 19)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;

    /** SN */
    @ApiModelProperty(value = "SN", position = 20)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 21)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 22)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 23)
    private String status;
}
