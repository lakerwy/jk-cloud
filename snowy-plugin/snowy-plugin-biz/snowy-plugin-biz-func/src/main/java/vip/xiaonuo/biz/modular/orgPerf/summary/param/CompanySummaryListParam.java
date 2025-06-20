package vip.xiaonuo.biz.modular.orgPerf.summary.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 公司绩效汇总列表参数
 *
 * @author pzh
 * @date  2025/06/17 14:48
 **/
@Getter
@Setter
public class CompanySummaryListParam {

    /** 考核年度（例如：2025） */
    @ApiModelProperty(value = "考核年度（例如：2025）", position = 1)
    private Integer assYear;

}
