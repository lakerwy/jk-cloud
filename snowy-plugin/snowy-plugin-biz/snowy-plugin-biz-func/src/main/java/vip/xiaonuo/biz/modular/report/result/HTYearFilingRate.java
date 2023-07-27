package vip.xiaonuo.biz.modular.report.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 描述：分公司近一年收入合同清单 （盖章日期，一年归档率）
 * @author yong.ye
 * @date 2023/7/25 15:36
 */

@Getter
@Setter
public class HTYearFilingRate {

    /** 责任单位Id（分公司） */
    @ApiModelProperty(value = "责任单位Id（分公司）", position = 1)
    private String partnerId;

    /** 合同总数 */
    @ApiModelProperty(value = "合同总数", position = 2)
    private Integer totalNum;

    /** 归档数 */
    @ApiModelProperty(value = "归档数", position = 3)
    private Integer filingNum;

    /** 未归档数 */
    @ApiModelProperty(value = "未归档数", position = 4)
    private Integer unFilingNum;

    /** 归档率 */
    @ApiModelProperty(value = "归档率", position = 5)
    private Double filingRate;

    /** 超过3个月未归档数 */
    @ApiModelProperty(value = "超过3个月未归档数", position = 6)
    private Integer threeUnFilingNum;
}
