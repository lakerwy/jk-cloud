package vip.xiaonuo.biz.modular.rule.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@TableName("BIZ_RULE_MANAGE")
public class BizRuleManage {


    /**
     * 主键ID
     */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /**
     * 租户Id
     */
    @ApiModelProperty(value = "租户Id", position = 2)
    private String tenantId;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题", position = 3)
    private String title;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称", position = 4)
    private String orgName;

    /**
     * 发布时间
     */
    @ApiModelProperty(value = "发布时间", position = 5)
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date getDate;

    /**
     * 部门ID
     */
    @ApiModelProperty(value = "部门ID", position = 6)
    private String orgId;

    /**
     * 负责人
     */
    @ApiModelProperty(value = "负责人", position = 7)
    private String manager;

    /**
     * 附件
     */
    @ApiModelProperty(value = "附件", position = 8)
    private String attachment;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", position = 9)
    private String memo;

    /**
     * 删除状态标记
     */
    @ApiModelProperty(value = "删除状态标记", position = 10)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", position = 11)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", position = 12)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", position = 13)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人", position = 14)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

}
