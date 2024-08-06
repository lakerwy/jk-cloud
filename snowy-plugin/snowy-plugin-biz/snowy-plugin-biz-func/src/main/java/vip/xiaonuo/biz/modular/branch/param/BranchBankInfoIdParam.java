package vip.xiaonuo.biz.modular.branch.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 分公司银行账户信息Id参数
 *
 * @author pzh
 * @date  2024/08/05 11:46
 **/
@Getter
@Setter
public class BranchBankInfoIdParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
