package vip.xiaonuo.biz.modular.prj.mou.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目承接备忘录 - 项目生产内部分工实体
 *
 * @author yy
 * @date  2025/06/06 11:43
 **/
@Getter
@Setter
@TableName("BIZ_PRJ_MOU_ITEM")
public class BizPrjMouItem {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 项目备忘录Id */
    @ApiModelProperty(value = "项目备忘录Id", position = 2)
    private String bizPrjMouId;

    /** 承包类型 */
    @ApiModelProperty(value = "承包类型", position = 3)
    private String mouType;

    /** 经办部门Id */
    @ApiModelProperty(value = "经办部门Id", position = 4)
    private String deptId;

    /** 经办部门 */
    @ApiModelProperty(value = "经办部门", position = 5)
    private String deptName;

    /** 工作内容 */
    @ApiModelProperty(value = "工作内容", position = 6)
    private String content;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 7)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 8)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 9)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 10)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 11)
    private String memo;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 12)
    private String attachs;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 13)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 14)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 15)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;
}
