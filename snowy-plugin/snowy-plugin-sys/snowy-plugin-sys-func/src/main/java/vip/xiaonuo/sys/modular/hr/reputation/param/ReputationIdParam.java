package vip.xiaonuo.sys.modular.hr.reputation.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 信誉情况Id参数
 *
 * @author pzh
 * @date  2024/10/18 16:52
 **/
@Getter
@Setter
public class ReputationIdParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
