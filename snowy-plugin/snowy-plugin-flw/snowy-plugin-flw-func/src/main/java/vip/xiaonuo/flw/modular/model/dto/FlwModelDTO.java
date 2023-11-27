package vip.xiaonuo.flw.modular.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/11/27 10:03
 */
@Getter
@Setter
public class FlwModelDTO {
    /** id */
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 6)
    private String name;
}
