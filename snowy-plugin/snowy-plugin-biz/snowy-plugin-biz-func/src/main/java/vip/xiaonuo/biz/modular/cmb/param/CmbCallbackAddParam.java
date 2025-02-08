package vip.xiaonuo.biz.modular.cmb.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 招商银行 - 回调记录添加参数
 *
 * @author pzh
 * @date  2025/02/08 10:09
 **/
@Getter
@Setter
public class CmbCallbackAddParam {

    /** 通知类型 */
    @ApiModelProperty(value = "通知类型", position = 2)
    private String type;

    /** 原始报文 */
    @ApiModelProperty(value = "原始报文", position = 3)
    private String orinResponse;

    /** 解密后报文 */
    @ApiModelProperty(value = "解密后报文", position = 4)
    private String decryptResponse;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 5)
    private String memo;

}
