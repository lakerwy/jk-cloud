package vip.xiaonuo.biz.modular.prj.mou.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 项目承接备忘录 - 项目生产内部分工Id参数
 *
 * @author yy
 * @date  2025/06/06 11:43
 **/
@Getter
@Setter
public class BizPrjMouItemIdParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
