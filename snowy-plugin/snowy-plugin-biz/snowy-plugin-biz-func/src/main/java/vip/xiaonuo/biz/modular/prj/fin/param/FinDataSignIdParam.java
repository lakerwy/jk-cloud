package vip.xiaonuo.biz.modular.prj.fin.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 财务数据汇总台账 - 转入签字费Id参数
 *
 * @author yy
 * @date  2025/06/05 17:27
 **/
@Getter
@Setter
public class FinDataSignIdParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
