package vip.xiaonuo.biz.modular.orgPerf.rating.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 职能部门-绩效评分Id参数
 *
 * @author pzh
 * @date  2025/06/30 16:37
 **/
@Getter
@Setter
public class BizFunDeptRatingIdParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
