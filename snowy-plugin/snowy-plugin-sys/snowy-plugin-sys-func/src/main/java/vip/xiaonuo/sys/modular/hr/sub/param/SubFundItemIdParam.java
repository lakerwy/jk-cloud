package vip.xiaonuo.sys.modular.hr.sub.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 分公司-五险一金 - 社保|公积金明细Id参数
 *
 * @author yong.ye
 * @date  2025/02/06 09:13
 **/
@Getter
@Setter
public class SubFundItemIdParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
