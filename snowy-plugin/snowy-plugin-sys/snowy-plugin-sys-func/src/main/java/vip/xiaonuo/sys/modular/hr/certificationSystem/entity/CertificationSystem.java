package vip.xiaonuo.sys.modular.hr.certificationSystem.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 认证体系实体
 *
 * @author pzh
 * @date  2024/10/18 10:01
 **/
@Getter
@Setter
@TableName("CERTIFICATION_SYSTEM")
public class CertificationSystem {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 2)
    private String name;

    /** 证书编号 */
    @ApiModelProperty(value = "证书编号", position = 3)
    private String certNo;

    /** 符合标准 */
    @ApiModelProperty(value = "符合标准", position = 4)
    private String standard;

    /** 通过的认证范围 */
    @ApiModelProperty(value = "通过的认证范围", position = 5)
    private String scope;

    /** 有效期至 */
    @ApiModelProperty(value = "有效期至", position = 6)
    private Date invalidDate;

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

    /** 有效期状态（正常|已过期|临近1个月） */
    @ApiModelProperty(value = "有效期状态（正常|已过期|临近1个月）", position = 30)
    @TableField(exist = false)
    private String validityStatus;
}
