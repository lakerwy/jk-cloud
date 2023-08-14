package vip.xiaonuo.biz.modular.report.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 描述：分公司超过3个月未归档合同数统计表  （盖章日期2017.1.1-2023.3.31）                                                                                                         统计日期:2023.7.4
 * @author yong.ye
 * @date 2023/7/25 15:45
 */

@Getter
@Setter
public class HTThreeMonthUnFiling {
    /** 伙伴名称|责任单位|分公司 */
    @ApiModelProperty(value = "伙伴名称|责任单位|分公司", position = 1)
    private String partnerName;

    /** 未归档数 */
    @ApiModelProperty(value = "未归档数", position = 2)
    private Integer threeUnFilingNum;
}
