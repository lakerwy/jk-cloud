package vip.xiaonuo.iot.modular.project.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 物联网-
 *
 * @author mayike
 * @date  2024/08/08 14:03
 **/
@Getter
@Setter
public class IotProjectListParam {

    /** 主键ID */
    private String id;

    @ApiModelProperty("分组类型，按网关类型展示，还是按照分组类型展示 0网关 1 群组")
    private int type;

    /** 关键词 */
    @ApiModelProperty(value = "关键词")
    private String searchKey;
}
