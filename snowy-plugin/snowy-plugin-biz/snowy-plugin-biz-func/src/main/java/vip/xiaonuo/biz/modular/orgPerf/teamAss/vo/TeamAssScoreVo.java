package vip.xiaonuo.biz.modular.orgPerf.teamAss.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author yong.ye
 * @date 2023/8/9 9:28
 */

@Getter
@Setter
public class TeamAssScoreVo {
    /** 班子考核ID */
    @ApiModelProperty(value = "班子考核ID")
    private String assId;

    /** 加分小计 */
    @ApiModelProperty(value = "加分小计")
    private BigDecimal addScore;

    /** 减分小计 */
    @ApiModelProperty(value = "减分小计")
    private BigDecimal minusScore;
}
