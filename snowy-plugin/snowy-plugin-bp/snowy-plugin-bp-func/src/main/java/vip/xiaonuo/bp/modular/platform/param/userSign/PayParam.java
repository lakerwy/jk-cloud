package vip.xiaonuo.bp.modular.platform.param.userSign;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * 招采平台 - 报名 - 缴费
 */

@Getter
@Setter
public class PayParam {

    /** 招采用户Id (BP_USER.ID) */
    @ApiModelProperty(value = "招采用户Id (BP_USER.ID)", position = 2)
    private String bpUserId;

    /** 招采公告Id（BP_NOTICE.ID） */
    @ApiModelProperty(value = "招采公告Id（BP_NOTICE.ID）",required = true, position = 3)
    @NotBlank(message = "公告Id不能为空")
    private String noticeId;

    /** 费用类型 */
    @ApiModelProperty(value = "费用类型", position = 4)
    @NotBlank(message = "费用类型不能为空")
    private String type;

    /** 缴纳金额 */
    @ApiModelProperty(value = "缴纳金额", position = 5)
    private BigDecimal amount;

}
