package vip.xiaonuo.sys.modular.hr.techAchive.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 其他（科技成果）实体
 *
 * @author pzh
 * @date  2024/10/18 09:10
 **/
@Getter
@Setter
@TableName("OTHER_TECH_ACHIVE")
public class OtherTechAchive {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 2)
    private String name;

    /** 类型 */
    @ApiModelProperty(value = "类型", position = 3)
    private String type;

    /** 完成人员 */
    @ApiModelProperty(value = "完成人员", position = 4)
    private String completedPersonnel;

    /** 完成人员 */
    @ApiModelProperty(value = "完成人员Id", position = 4)
    private String completedPersonnelId;

    /** 发证单位 */
    @ApiModelProperty(value = "发证单位", position = 5)
    private String issueOrg;

    /** 证书时间 */
    @ApiModelProperty(value = "证书时间", position = 6)
    private Date certDate;

    /** 附件信息 */
    @ApiModelProperty(value = "附件信息", position = 7)
    private String attachments;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 8)
    private String memo;

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

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 14)
    private String tenantId;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 15)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;
}
