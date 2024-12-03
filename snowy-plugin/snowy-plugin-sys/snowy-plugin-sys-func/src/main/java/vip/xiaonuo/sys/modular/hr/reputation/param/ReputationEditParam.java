package vip.xiaonuo.sys.modular.hr.reputation.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 信誉情况编辑参数
 *
 * @author pzh
 * @date  2024/10/18 16:52
 **/
@Getter
@Setter
public class ReputationEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 被拉黑单位 */
    @ApiModelProperty(value = "被拉黑单位", position = 2)
    private String unitName;

    /** 被拉黑原因 */
    @ApiModelProperty(value = "被拉黑原因", position = 3)
    private String reason;

    /** 被拉黑时间 */
    @ApiModelProperty(value = "被拉黑时间", position = 4)
    private Date blacklistDate;

    /** 状态（已拉黑，已解除黑名单） */
    @ApiModelProperty(value = "状态（已拉黑，已解除黑名单）", position = 5)
    private String blacklistStatus;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 6)
    private String attachments;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 7)
    private String memo;

}
