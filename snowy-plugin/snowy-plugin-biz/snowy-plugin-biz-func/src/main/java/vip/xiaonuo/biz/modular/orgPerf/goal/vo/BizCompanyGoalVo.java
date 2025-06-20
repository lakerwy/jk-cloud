package vip.xiaonuo.biz.modular.orgPerf.goal.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizCompanyGoalKpi;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

/**
 * 公司绩效考核-考核指标添加参数
 *
 * @author pzh
 * @date  2025/06/18 10:15
 **/
@Getter
@Setter
public class BizCompanyGoalVo {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    private String id;

    /** 考核类型 */
    @ApiModelProperty(value = "考核类型", position = 2)
    private String assType;

    /** 考核周期（例如：2025） */
    @ApiModelProperty(value = "考核周期（例如：2025）", position = 3)
    private Integer assYear;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 4)
    private String attach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 5)
    private String memo;

    /** KPI指标 */
    @ApiModelProperty(value = "KPI指标-集合", position = 10)
    private List<BizCompanyGoalKpi> kpiList;

}
