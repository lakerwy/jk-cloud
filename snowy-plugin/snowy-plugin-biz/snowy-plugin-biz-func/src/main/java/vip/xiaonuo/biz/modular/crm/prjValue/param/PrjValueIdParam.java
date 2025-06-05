package vip.xiaonuo.biz.modular.crm.prjValue.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 生产院及子公司产值Id参数
 *
 * @author pzh
 * @date  2025/06/05 10:04
 **/
@Getter
@Setter
public class PrjValueIdParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
