package vip.xiaonuo.iot.modular.group.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 设备分组编辑参数
 *
 * @author mayike
 * @date  2024/08/06 08:55
 **/
@Getter
@Setter
public class IotGroupEditParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 2)
    @NotBlank(message = "名称不能为空")
    private String name;

    @ApiModelProperty(value = "项目ID", position = 3)
    private String projectId;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 4)
    private String extJson;

}
