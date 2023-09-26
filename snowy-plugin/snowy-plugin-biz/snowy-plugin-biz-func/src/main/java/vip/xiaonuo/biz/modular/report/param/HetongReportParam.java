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
    @ApiModelProperty(value = "年度", position = 1)
    private Integer year;

    /** 开始时间 */
    @ApiModelProperty(value = "开始时间", position = 2)
    private String startDate;

    /** 结束时间 */
    @ApiModelProperty(value = "结束时间", position = 3)
    private String endDate;

    /** 合同类型：分公司、零星、机构、公司部门  */
    @ApiModelProperty(value = "报表类型", position = 4)
    private String type;
}
