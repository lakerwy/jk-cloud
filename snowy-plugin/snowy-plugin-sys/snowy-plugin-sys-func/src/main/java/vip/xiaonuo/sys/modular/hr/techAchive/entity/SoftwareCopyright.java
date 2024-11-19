package vip.xiaonuo.sys.modular.hr.techAchive.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 软著信息实体
 *
 * @author pzh
 * @date  2024/10/17 10:53
 **/
@Getter
@Setter
@TableName("SOFTWARE_COPYRIGHT")
public class SoftwareCopyright {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 软著名称 */
    @ApiModelProperty(value = "软著名称", position = 2)
    private String name;

    /** 著作权人 */
    @ApiModelProperty(value = "著作权人", position = 3)
    private String owner;

    /** 登记号 */
    @ApiModelProperty(value = "登记号", position = 4)
    private String registerNo;

    /** 下证日期 */
    @ApiModelProperty(value = "下证日期", position = 8)
    private Date authDate;

    /** 首次发表日期 */
    @ApiModelProperty(value = "首次发表日期", position = 9)
    private Date firstPublishDate;

    /** 软著状态 */
    @ApiModelProperty(value = "软著状态", position = 10)
    private String copyrightStatus;

    /** 有效期至 */
    @ApiModelProperty(value = "有效期至", position = 11)
    private Date invalidDate;

    /** 附件信息 */
    @ApiModelProperty(value = "附件信息", position = 12)
    private String attachments;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 13)
    private String memo;

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

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 19)
    private String tenantId;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 20)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;
}
