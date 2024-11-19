package vip.xiaonuo.sys.modular.hr.techAchive.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 标准规范实体
 *
 * @author pzh
 * @date  2024/10/17 17:05
 **/
@Getter
@Setter
@TableName("STANDARD")
public class Standard {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 标准名称 */
    @ApiModelProperty(value = "标准名称", position = 2)
    private String name;

    /** 类型 */
    @ApiModelProperty(value = "类型", position = 3)
    private String type;

    /** 标准类型 */
    @ApiModelProperty(value = "标准类型", position = 4)
    private String standardType;

    /** 参编人员 */
    @ApiModelProperty(value = "参编人员", position = 5)
    private String participants;

    /** 发布日期 */
    @ApiModelProperty(value = "发布日期", position = 6)
    private Date publishDate;

    /** 实施日期 */
    @ApiModelProperty(value = "实施日期", position = 7)
    private Date effectiveDate;

    /** 附件信息 */
    @ApiModelProperty(value = "附件信息", position = 8)
    private String attachments;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 9)
    private String memo;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 10)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

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

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 15)
    private String tenantId;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 16)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;
}
