package vip.xiaonuo.biz.modular.orgPerf.summary.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 子公司及生产部门绩效汇总列表参数
 *
 * @author pzh
 * @date  2025/06/26 08:54
 **/
@Getter
@Setter
public class FunDeptDetailParam {

    @ApiModelProperty(value = "目标登记主键ID", required = true, position = 1)
    @NotBlank(message = "目标登记主键ID不能为空")
    private String id;

}
