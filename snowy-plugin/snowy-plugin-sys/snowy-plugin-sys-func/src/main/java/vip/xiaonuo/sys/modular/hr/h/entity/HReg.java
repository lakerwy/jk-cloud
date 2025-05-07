package vip.xiaonuo.sys.modular.hr.h.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 招待事项登记实体
 *
 * @author yy
 * @date  2025/05/06 16:11
 **/
@Getter
@Setter
@TableName("HR_H_REG")
public class HReg {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 填报部门Id */
    @ApiModelProperty(value = "填报部门Id", position = 2)
    private String deptId;

    /** 填报部门 */
    @ApiModelProperty(value = "填报部门", position = 3)
    private String deptName;

    /** 填报人Id */
    @ApiModelProperty(value = "填报人Id", position = 4)
    private String optId;

    /** 填报人 */
    @ApiModelProperty(value = "填报人", position = 5)
    private String optName;

    /** 登记日期 */
    @ApiModelProperty(value = "登记日期", position = 6)
    private Date optTime;

    /** 招待日期 */
    @ApiModelProperty(value = "招待日期", position = 7)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hDate;

    /** 招待事由 */
    @ApiModelProperty(value = "招待事由", position = 8)
    private String descMemo;

    /** 陪同人员 */
    @ApiModelProperty(value = "陪同人员", position = 9)
    private String joinJosn;

    /** 总人数 */
    @ApiModelProperty(value = "总人数", position = 10)
    private String joinNum;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 11)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 12)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 13)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 14)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 15)
    private String memo;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 16)
    private String attachs;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 17)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 18)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

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


    /** 招待方式 */
    @ApiModelProperty(value = "招待方式", position = 25)
    private String type;
}
