package vip.xiaonuo.biz.modular.orgPerf.teamAss.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 班子考核记录添加参数
 *
 * @author pzh
 * @date  2025/06/17 17:08
 **/
@Getter
@Setter
public class BizTeamAssRecordAddParam {

    /** 关联-班子考核ID */
    @ApiModelProperty(value = "关联-班子考核ID", position = 2)
    private String assId;

    /** 考核年度（例如：2025） */
    @ApiModelProperty(value = "考核年度（例如：2025）", position = 3)
    private String assYear;

    /** 考核人ID */
    @ApiModelProperty(value = "考核人ID", position = 4)
    private String assessorId;

    /** 考核人姓名 */
    @ApiModelProperty(value = "考核人姓名", position = 5)
    private String assessorName;

    /** 考核字典Id */
    @ApiModelProperty(value = "考核字典Id", position = 6)
    private String dictId;

    /** 实际加|减分值 */
    @ApiModelProperty(value = "实际加|减分值", position = 7)
    private BigDecimal realScore;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 8)
    private String memo;

}
