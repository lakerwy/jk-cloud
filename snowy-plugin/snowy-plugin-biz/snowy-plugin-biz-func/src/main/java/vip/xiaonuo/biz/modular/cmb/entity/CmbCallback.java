package vip.xiaonuo.biz.modular.cmb.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 招商银行 - 回调记录实体
 *
 * @author pzh
 * @date  2025/02/08 10:09
 **/
@Getter
@Setter
@TableName("BIZ_CMB_CALLBACK")
public class CmbCallback {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 通知类型 */
    @ApiModelProperty(value = "通知类型", position = 2)
    private String type;

    /** 原始报文 */
    @ApiModelProperty(value = "原始报文", position = 3)
    private String orinResponse;

    /** 解密后报文 */
    @ApiModelProperty(value = "解密后报文", position = 4)
    private String decryptResponse;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 5)
    private String memo;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 6)
    private String tenantId;

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

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 10)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 11)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 12)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;
}
