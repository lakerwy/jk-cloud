package vip.xiaonuo.iot.modular.topic.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 物理网-主题topic 表Id参数
 *
 * @author mayike
 * @date  2024/08/05 10:26
 **/
@Getter
@Setter
public class IotTopicIdParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
