package vip.xiaonuo.sys.modular.hr.registerCert.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class RegCertDataVo {

    /** 注册等级 */
    @ApiModelProperty(value = "注册等级", position = 2)
    private String registerLever;

    /** 注册类别 */
    @ApiModelProperty(value = "注册类别", position = 3)
    private String registerType;

    /** 注册专业 */
    @ApiModelProperty(value = "注册专业", position = 4)
    private String registerSubject;

    /** 身份证号 */
    /*@ApiModelProperty(value = "身份证号", position = 5)
    private String idCardNumber;*/

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 6)
    private String extJson;
}
