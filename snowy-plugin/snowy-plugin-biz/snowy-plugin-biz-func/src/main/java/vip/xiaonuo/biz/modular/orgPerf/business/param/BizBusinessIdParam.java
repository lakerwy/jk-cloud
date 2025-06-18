package vip.xiaonuo.biz.modular.orgPerf.business.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 业务情况Id参数
 *
 * @author pzh
 * @date  2025/06/17 14:48
 **/
@Getter
@Setter
public class BizBusinessIdParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
