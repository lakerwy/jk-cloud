package vip.xiaonuo.sys.modular.hr.honorAward.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 企业荣誉实体
 *
 * @author pzh
 * @date  2024/10/16 11:17
 **/
@Getter
@Setter
@TableName("ENTERPRISE_HONOR")
public class EnterpriseHonor {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 持有单位 */
    @ApiModelProperty(value = "持有单位", position = 2)
    private String holdingOrg;

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 2)
    private String name;

    /** 类型 */
    @ApiModelProperty(value = "类型", position = 3)
    private String type;

    /** 证书编号 */
    @ApiModelProperty(value = "证书编号", position = 4)
    private String certNo;

    /** 发奖单位 */
    @ApiModelProperty(value = "发奖单位", position = 5)
    private String awardingOrg;

    /** 获奖时间 */
    @ApiModelProperty(value = "获奖时间", position = 9)
    private Date awardDate;

    /** 有效期至 */
    @ApiModelProperty(value = "有效期至", position = 10)
    private Date invalidDate;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 11)
    private String note;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 12)
    private String attachments;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 13)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 14)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 15)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 16)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 17)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 18)
    private String tenantId;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 19)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;

    /** 有效期状态（正常|已过期|临近1个月） */
    @ApiModelProperty(value = "有效期状态（正常|已过期|临近1个月）", position = 30)
    @TableField(exist = false)
    private String validityStatus;
}
