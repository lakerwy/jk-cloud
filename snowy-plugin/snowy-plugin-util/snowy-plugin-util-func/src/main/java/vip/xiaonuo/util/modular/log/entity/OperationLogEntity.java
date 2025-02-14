package vip.xiaonuo.util.modular.log.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 操作日志实体
 *
 * @author pzh
 * @date  2025/02/13 16:48
 **/
@Getter
@Setter
@ToString
@TableName("OPERATION_LOG")
public class OperationLogEntity {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 日志分类 */
    @ApiModelProperty(value = "日志分类", position = 2)
    private String category;

    /** 功能名称 */
    @ApiModelProperty(value = "功能名称", position = 3)
    private String name;

    /** 请求参数 */
    @ApiModelProperty(value = "请求参数", position = 7)
    private String paramJson;

    /** 数据变化记录 */
    @ApiModelProperty(value = "数据变化记录", position = 10)
    private String updateJson;

    /** 操作时间 */
    @ApiModelProperty(value = "操作时间", position = 11)
    private Date opTime;

    /** 操作人姓名 */
    @ApiModelProperty(value = "操作人姓名", position = 12)
    private String opUser;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 13)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 14)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 15)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 16)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 17)
    private String memo;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 18)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 19)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;


}
