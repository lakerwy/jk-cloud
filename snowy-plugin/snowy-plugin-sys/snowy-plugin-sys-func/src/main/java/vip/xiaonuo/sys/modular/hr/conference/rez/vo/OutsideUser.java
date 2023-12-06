package vip.xiaonuo.sys.modular.hr.conference.rez.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OutsideUser {

    /** 会议记录人 */
    @ApiModelProperty(value = "外部人员姓名")
    private String outsideUser;

    @ApiModelProperty(value = "外部人员号码")
    private String phone;
}
