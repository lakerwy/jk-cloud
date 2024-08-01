package vip.xiaonuo.biz.modular.prj.expense.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 财务管理 - 费用报销 - 财务收单编码库Id参数
 *
 * @author yong.ye
 * @date  2024/08/01 11:21
 **/
@Getter
@Setter
public class BizFinCodeIdParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
