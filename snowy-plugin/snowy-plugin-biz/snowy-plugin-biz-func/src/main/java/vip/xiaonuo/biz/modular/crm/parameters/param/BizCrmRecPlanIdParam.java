package vip.xiaonuo.biz.modular.crm.parameters.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 合同收款计划Id参数
 *
 * @author pzh
 * @date  2025/05/30 09:35
 **/
@Getter
@Setter
public class BizCrmRecPlanIdParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
