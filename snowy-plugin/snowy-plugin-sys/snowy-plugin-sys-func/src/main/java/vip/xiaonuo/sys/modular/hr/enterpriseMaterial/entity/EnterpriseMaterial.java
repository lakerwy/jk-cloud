package vip.xiaonuo.sys.modular.hr.enterpriseMaterial.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 企业宣传资料实体
 *
 * @author pzh
 * @date  2024/09/18 14:57
 **/
@Getter
@Setter
@TableName("ENTERPRISE_MATERIAL")
public class EnterpriseMaterial {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 2)
    private String tenantId;

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 3)
    private String name;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 4)
    private String attach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 5)
    private String memo;

    /** 上传时间 */
    @ApiModelProperty(value = "上传时间", position = 6)
    private Date uploadTime;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 7)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 8)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 9)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 10)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新用户 */
    @ApiModelProperty(value = "更新用户", position = 11)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 12)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;
}
