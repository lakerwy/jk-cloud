package vip.xiaonuo.iot.modular.topic.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 物理网-主题topic 表添加参数
 *
 * @author mayike
 * @date  2024/08/05 10:26
 **/
@Getter
@Setter
public class IotTopicAddParam {

    /** 主题前缀 */
    @ApiModelProperty(value = "主题前缀", position = 2,required = true)
    private String topicPrex;

    /** 设备ID */
    @ApiModelProperty(value = "设备ID", position = 3,required = true)
    private String clientId;

    /** 主题描述 */
    @ApiModelProperty(value = "主题描述", position = 4)
    private String topicDesc;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 6)
    private String extJson;

}
