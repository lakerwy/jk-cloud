package vip.xiaonuo.biz.modular.report.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.hpsf.Decimal;

import java.math.BigDecimal;

/**
 * @author yong.ye
 * @date 2023/7/10 16:56
 */

@Getter
@Setter
public class HetongReportData {
    /** 正表头名称 - 正表头1  */
    @ApiModelProperty(value = "责任单位|部门")
    private String companyOrDepartment;

    /** 工程类别：岩土、建筑...  */
    @ApiModelProperty(value = "工程类别：岩土、建筑...")
    private String secondLevel;

    /** 项目数量 */
    @ApiModelProperty(value = "项目数量")
    private Integer projsNum;

    /** 合同归档  */
    @ApiModelProperty(value = "合同归档")
    private Integer hetongNum;

    /** 合同金额 */
    @ApiModelProperty(value = "合同金额")
    private BigDecimal hetongAmount;

}
