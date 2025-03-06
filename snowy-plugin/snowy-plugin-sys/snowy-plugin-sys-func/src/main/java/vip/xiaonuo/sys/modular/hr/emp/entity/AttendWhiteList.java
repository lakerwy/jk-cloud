package vip.xiaonuo.sys.modular.hr.emp.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 钉钉考勤-员工白名单实体
 *
 * @author pzh
 * @date  2025/03/06 11:34
 **/
@Getter
@Setter
@TableName("ATTEND_WHITE_LIST")
public class AttendWhiteList {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 类型（高管|返聘） */
    @ApiModelProperty(value = "类型（高管|返聘）", position = 2)
    private String type;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 3)
    private String userName;

    /** 建科云用户Id */
    @ApiModelProperty(value = "建科云用户Id", position = 4)
    private String userId;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 5)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 6)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 7)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 8)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 9)
    private String memo;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 10)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 11)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 12)
    private String dataScopeId;
}
