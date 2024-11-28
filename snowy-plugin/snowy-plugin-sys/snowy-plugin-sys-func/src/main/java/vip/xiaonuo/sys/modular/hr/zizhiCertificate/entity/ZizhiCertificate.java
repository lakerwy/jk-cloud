package vip.xiaonuo.sys.modular.hr.zizhiCertificate.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 资质证照实体
 *
 * @author pzh
 * @date  2024/10/16 16:39
 **/
@Getter
@Setter
@TableName("ZIZHI_CERTIFICATE")
public class ZizhiCertificate {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 资质名称 */
    @ApiModelProperty(value = "资质名称", position = 2)
    private String ziZhiName;

    /** 资质类别 */
    @ApiModelProperty(value = "资质类别", position = 3)
    private String type;

    /** 资质证书号 */
    @ApiModelProperty(value = "资质证书号", position = 4)
    private String certNo;

    /** 颁发机构 */
    @ApiModelProperty(value = "颁发机构", position = 5)
    private String issueOrg;

    /** 资质/业务范围 */
    @ApiModelProperty(value = "资质/业务范围", position = 6)
    private String businessScope;

    /** 有效期至 */
    @ApiModelProperty(value = "有效期至", position = 7)
    private Date validity;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 8)
    private String attachment;

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
