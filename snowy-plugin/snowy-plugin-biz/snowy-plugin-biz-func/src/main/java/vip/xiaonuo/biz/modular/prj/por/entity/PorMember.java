package vip.xiaonuo.biz.modular.prj.por.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目产值台账 - 项目管理 - 项目成员管理实体
 *
 * @author yy
 * @date  2025/06/03 09:40
 **/
@Getter
@Setter
@TableName("BIZ_POR_MEMBER")
public class PorMember {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 项目产值台账 - 项目管理 - 主键Id */
    @ApiModelProperty(value = "项目产值台账 - 项目管理 - 主键Id", position = 2)
    private String porProjectId;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 3)
    private String userName;

    /** 用户id */
    @ApiModelProperty(value = "用户id", position = 4)
    private String userId;

    /** 手机号 */
    @ApiModelProperty(value = "手机号", position = 5)
    private String phone;

    /** 所属部门 */
    @ApiModelProperty(value = "所属部门", position = 6)
    private String orgName;

    /** 所属部门Id */
    @ApiModelProperty(value = "所属部门Id", position = 7)
    private String orgId;

    /** 担任角色 */
    @ApiModelProperty(value = "担任角色", position = 8)
    private String roleName;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 9)
    private String memberMemo;

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

    /** 预留字段-备注 */
    @ApiModelProperty(value = "预留字段-备注", position = 14)
    private String memo;

    /** 预留字段-附件 */
    @ApiModelProperty(value = "预留字段-附件", position = 15)
    private String attach;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 16)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 17)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;
}
