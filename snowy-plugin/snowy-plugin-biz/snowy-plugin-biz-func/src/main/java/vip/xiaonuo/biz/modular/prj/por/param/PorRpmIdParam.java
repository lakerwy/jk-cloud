package vip.xiaonuo.biz.modular.prj.por.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 项目产值台账 - 项目管理 - 责任上缴费用管理Id参数
 *
 * @author yy
 * @date  2025/06/03 09:55
 **/
@Getter
@Setter
public class PorRpmIdParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
