package vip.xiaonuo.iot.modular.project.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 物联网-项目添加参数
 *
 * @author mayike
 * @date  2024/08/08 14:03
 **/
@Getter
@Setter
public class IotProjectAddParam {

    /** 项目编号 */
    @ApiModelProperty(value = "项目编号", position = 2)
    private String projectCode;

    /** 项目名称 */
    @ApiModelProperty(value = "项目名称", position = 3)
    private String projectName;

    /** 描述 */
    @ApiModelProperty(value = "描述", position = 4)
    private String projectDesc;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 6)
    private String extJson;

}
