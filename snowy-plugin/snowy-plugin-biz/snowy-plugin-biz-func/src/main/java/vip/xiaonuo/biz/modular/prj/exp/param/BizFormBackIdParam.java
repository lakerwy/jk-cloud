package vip.xiaonuo.biz.modular.prj.exp.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 费用报销（二期）- 归还登记Id参数
 *
 * @author yong.ye
 * @date  2025/02/25 09:15
 **/
@Getter
@Setter
public class BizFormBackIdParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
