package vip.xiaonuo.biz.modular.prj.expense.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 财务管理 - 费用报销|差旅费用报销 - 申请流程Id参数
 *
 * @author yong.ye
 * @date  2024/07/30 15:26
 **/
@Getter
@Setter
public class BizExpApplyIdParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
