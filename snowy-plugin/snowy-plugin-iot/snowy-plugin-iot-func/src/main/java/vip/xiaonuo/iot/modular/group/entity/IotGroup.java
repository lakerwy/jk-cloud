package vip.xiaonuo.iot.modular.group.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 设备分组实体
 *
 * @author mayike
 * @date  2024/08/06 08:55
 **/
@Getter
@Setter
@TableName("IOT_GROUP")
public class IotGroup {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 2)
    private String name;

    /** 租户ID */
    @ApiModelProperty(value = "租户ID", position = 3)
    private String tenantId;

    @ApiModelProperty(value = "项目ID", position = 4)
    private String projectId;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 5)
    private String extJson;

    /** 删除标识 */
    @ApiModelProperty(value = "删除标识", position = 6)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 7)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 8)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 9)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 10)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;
}
