package vip.xiaonuo.biz.modular.orgPerf.perfAss.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 职能部门-绩效评估Id参数
 *
 * @author pzh
 * @date  2025/06/30 10:41
 **/
@Getter
@Setter
public class BizFunDeptPerfAssIdParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
