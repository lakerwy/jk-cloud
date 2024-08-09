package vip.xiaonuo.iot.modular.topic.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 物理网-主题topic 表实体
 *
 * @author mayike
 * @date  2024/08/05 10:26
 **/
@Getter
@Setter
@TableName("IOT_TOPIC")
public class IotTopic {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 主题前缀 */
    @ApiModelProperty(value = "主题前缀", position = 2)
    private String topicPrex;

    /** 设备ID */
    @ApiModelProperty(value = "设备ID", position = 3)
    private String clientId;

    /** 主题描述 */
    @ApiModelProperty(value = "主题描述", position = 4)
    private String topicDesc;

    @ApiModelProperty(value = "项目ID", position = 5)
    private String projectId;

    /** 租户ID */
    @ApiModelProperty(value = "租户ID", position = 6)
    private String tenantId;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 7)
    private String extJson;

    /** 删除标识 */
    @ApiModelProperty(value = "删除标识", position = 8)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 9)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 10)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 11)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 12)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    @ApiModelProperty(value = "网关",position = 13)
    @TableField(exist = false)
    private String topic;

    @ApiModelProperty(value = "项目名称",position = 14)
    @TableField(exist = false)
    private String projectName;

    public String getTopic() {
        return topicPrex+"/"+clientId;
    }
}
