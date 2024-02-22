package vip.xiaonuo.biz.modular.card.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 生成微信电子名片-二维码请求参数
 */

@Getter
@Setter
public class QRCodeParam {

    /** 用户Id */
    @ApiModelProperty(value = "用户Id", position = 1)
    private String userId;

    /** 用户名 */
    @ApiModelProperty(value = "用户名", position = 2)
    private String userName;

    /** 部门名称 */
    @ApiModelProperty(value = "部门名称", position = 3)
    private String deptName;

    /** 手机号 */
    @ApiModelProperty(value = "手机号", position = 4)
    private String phone;
}
