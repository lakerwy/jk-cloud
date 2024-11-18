package vip.xiaonuo.sys.modular.hr.honorAward.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 荣誉奖项实体
 *
 * @author pzh
 * @date  2024/10/16 15:18
 **/
@Getter
@Setter
@TableName("HONOR_AWARD")
public class HonorAward {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 荣誉奖项类型（项目成果|科技奖|数字化成果） */
    @ApiModelProperty(value = "荣誉奖项类型（项目成果|科技奖|数字化成果）", position = 2)
    private String type;

    /** 项目名称 */
    @ApiModelProperty(value = "名称", position = 3)
    private String prjName;

    /** 活动名称 */
    @ApiModelProperty(value = "活动名称", position = 4)
    private String activityName;

    /** 发奖单位 */
    @ApiModelProperty(value = "发奖单位", position = 5)
    private String awardingOrg;

    /** 专业 */
    @ApiModelProperty(value = "专业", position = 6)
    private String profession;

    /** 获奖等级 */
    @ApiModelProperty(value = "获奖等级", position = 7)
    private String awardLevel;

    /** 级别 */
    @ApiModelProperty(value = "级别", position = 8)
    private String level;

    /** 完成人员 */
    @ApiModelProperty(value = "完成人员", position = 9)
    private String completedPersonnel;

    /** 完成人员 */
    @ApiModelProperty(value = "完成人员Id", position = 10)
    private String completedPersonnelId;

    /** 申报时间 */
    @ApiModelProperty(value = "申报时间", position = 11)
    private Date declareDate;

    /** 证书时间 */
    @ApiModelProperty(value = "证书时间", position = 12)
    private Date certDate;

    /** 证书编号 */
    @ApiModelProperty(value = "证书编号", position = 13)
    private String certNo;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 14)
    private String note;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 15)
    private String attachments;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 16)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 17)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 18)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 19)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 20)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 21)
    private String tenantId;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 22)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;
}
