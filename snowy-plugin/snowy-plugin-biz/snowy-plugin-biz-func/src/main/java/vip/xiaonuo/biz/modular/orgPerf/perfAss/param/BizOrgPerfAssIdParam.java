package vip.xiaonuo.biz.modular.orgPerf.perfAss.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 子公司及生产部门绩效考核-绩效评估Id参数
 *
 * @author pzh
 * @date  2025/06/25 15:01
 **/
@Getter
@Setter
public class BizOrgPerfAssIdParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
