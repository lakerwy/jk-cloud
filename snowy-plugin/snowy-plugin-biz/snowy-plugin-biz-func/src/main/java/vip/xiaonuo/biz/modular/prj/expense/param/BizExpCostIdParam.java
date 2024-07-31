package vip.xiaonuo.biz.modular.prj.expense.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 财务管理 - 费用报销|差旅费用报销 - 成本一览表Id参数
 *
 * @author yong.ye
 * @date  2024/07/30 15:32
 **/
@Getter
@Setter
public class BizExpCostIdParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
