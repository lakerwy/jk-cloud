package vip.xiaonuo.dev.modular.sms.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author yong.ye
 * @date 2023/6/27 11:47
 */

@Getter
@Setter
public class DevSmsSendYunPianParam {

    /** ApiKey 用户唯一标识，在"账号设置"-"子帐号管理"中查看 */
    @ApiModelProperty(value = "用户唯一标识", position = 1)
    private String apiKey;

    /** 手机号 */
    @ApiModelProperty(value = "手机号", required = true, position = 2)
    @NotBlank(message = "phoneNumbers不能为空")
    private String phoneNumbers;

    /** 模板编码 */
    @ApiModelProperty(value = "模板Id", required = true, position = 3)
    @NotBlank(message = "templateCode不能为空")
    private String templateCode;

    /** 发送参数 */
    @ApiModelProperty(value = "变量名和变量值对", position = 4)
    private String templateParam;
}
