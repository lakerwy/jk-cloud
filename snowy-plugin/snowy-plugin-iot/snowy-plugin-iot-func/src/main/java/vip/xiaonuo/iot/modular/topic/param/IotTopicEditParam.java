package vip.xiaonuo.iot.modular.topic.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 物理网-主题topic 表编辑参数
 *
 * @author mayike
 * @date  2024/08/05 10:26
 **/
@Getter
@Setter
public class IotTopicEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 设备ID */
    @ApiModelProperty(value = "设备ID", position = 3,required = true)
    @NotBlank(message = "网关ID不能为空")
    private String clientId;

    @ApiModelProperty(value = "项目ID", position = 4)
    private String projectId;

    /** 主题描述 */
    @ApiModelProperty(value = "主题描述", position = 5,required = true)
    private String topicDesc;

    /** 主题描述 */
    @ApiModelProperty(value = "主题描述", position = 6)
    private String desc;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 7)
    private String extJson;

}
