package vip.xiaonuo.sys.modular.hr.registerCert.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AlwAndCertParam {
    @ApiModelProperty(value = "注册补贴-用户姓名",required = true)
    @NotNull(message = "用户姓名不能为空")
    private String userName;

    /** 员工类别：总部|分公司|外部 */
    @ApiModelProperty(value = "员工类别：总部|分公司|外部")
    private String userType;

    @ApiModelProperty(value = "状态")
    private String status;
}
