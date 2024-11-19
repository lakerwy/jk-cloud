package vip.xiaonuo.sys.modular.hr.techAchive.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 专利信息实体
 *
 * @author pzh
 * @date  2024/10/17 10:22
 **/
@Getter
@Setter
@TableName("PATENT_INFO")
public class PatentInfo {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 专利名称 */
    @ApiModelProperty(value = "专利名称", position = 2)
    private String name;

    /** 申请类型 */
    @ApiModelProperty(value = "申请类型", position = 3)
    private String applyType;

    /** 申请号 */
    @ApiModelProperty(value = "申请号", position = 4)
    private String applyNo;

    /** 专利号 */
    @ApiModelProperty(value = "专利号", position = 5)
    private String patentNo;

    /** 发明人 */
    @ApiModelProperty(value = "发明人", position = 6)
    private String inventor;

    /** 申请日期 */
    @ApiModelProperty(value = "申请日期", position = 7)
    private Date applyDate;

    /** 授权日期 */
    @ApiModelProperty(value = "授权日期", position = 8)
    private Date authDate;

    /** 专利状态 */
    @ApiModelProperty(value = "专利状态", position = 9)
    private String patentStatus;

    /** 有效期至 */
    @ApiModelProperty(value = "有效期至", position = 10)
    private Date invalidDate;

    /** 附件信息 */
    @ApiModelProperty(value = "附件信息", position = 11)
    private String attachments;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 12)
    private String memo;

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
}
