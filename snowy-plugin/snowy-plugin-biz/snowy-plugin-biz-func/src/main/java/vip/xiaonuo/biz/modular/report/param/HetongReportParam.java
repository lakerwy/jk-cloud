package vip.xiaonuo.biz.modular.report.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yong.ye
 * @date 2023/7/10 9:17
 */

@Getter
@Setter
public class HetongReportParam {
    /** 年度 */
    @ApiModelProperty(value = "年度")
    private Integer year;

    /** 开始时间 */
    @ApiModelProperty(value = "开始时间")
    private String startDate;

    /** 结束时间 */
    @ApiModelProperty(value = "结束时间")
    private String endDate;

    /** 报表类型  */
    @ApiModelProperty(value = "报表类型")
    private String type;
}
