package vip.xiaonuo.biz.modular.orgPerf.teamAss.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 班子考核编辑参数
 *
 * @author pzh
 * @date  2025/06/17 16:23
 **/
@Getter
@Setter
public class BizTeamAssEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 考核年度（例如：2025） */
    @ApiModelProperty(value = "考核年度（例如：2025）", position = 2)
    private Integer assYear;

    /** 考核人ID */
    @ApiModelProperty(value = "考核人ID", position = 3)
    private String assessorId;

    /** 考核人姓名 */
    @ApiModelProperty(value = "考核人姓名", position = 4)
    private String assessorName;

    /** 加分小计 */
    @ApiModelProperty(value = "加分小计", position = 5)
    private BigDecimal addScore;

    /** 减分小计 */
    @ApiModelProperty(value = "减分小计", position = 6)
    private BigDecimal minusScore;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 7)
    private String memo;

}
