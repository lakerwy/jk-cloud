package vip.xiaonuo.sys.modular.hr.conference.rez.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WbyQinFoUser {

    /** 姓名  */
    @ApiModelProperty(value = "姓名")
    private String xmname;

    /** 电话  */
    @ApiModelProperty(value = "电话")
    private String phone;

    /** 邮件  */
    @ApiModelProperty(value = "邮件")
    private String email;
}
