package vip.xiaonuo.iot.modular.project.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 物联网-项目实体
 *
 * @author mayike
 * @date  2024/08/08 14:03
 **/
@Getter
@Setter
@TableName("IOT_PROJECT")
public class IotProject {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 项目编号 */
    @ApiModelProperty(value = "项目编号", position = 2)
    private String projectCode;

    /** 项目名称 */
    @ApiModelProperty(value = "项目名称", position = 3)
    private String projectName;

    /** 描述 */
    @ApiModelProperty(value = "描述", position = 4)
    private String projectDesc;

    /** 租户ID */
    @ApiModelProperty(value = "租户ID", position = 5)
    private String tenantId;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 6)
    private String extJson;

    /** 删除标识 */
    @ApiModelProperty(value = "删除标识", position = 7)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 8)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 9)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 10)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 11)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;
}
