package vip.xiaonuo.biz.modular.cmb.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 招商银行 - 回调记录Id参数
 *
 * @author pzh
 * @date  2025/02/08 10:09
 **/
@Getter
@Setter
public class CmbCallbackIdParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
