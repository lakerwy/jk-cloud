package vip.xiaonuo.sys.modular.hr.financialAuditReport.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 财务审计报告添加参数
 *
 * @author pzh
 * @date  2024/10/18 14:51
 **/
@Getter
@Setter
public class FinancialAuditReportAddParam {

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 2)
    private String name;

    /** 年度 */
    @ApiModelProperty(value = "年度", position = 3)
    private String year;

    /** 审计单位 */
    @ApiModelProperty(value = "审计单位", position = 4)
    private String auditorCompany;

    /** 财务报表摘要 */
    @ApiModelProperty(value = "财务报表摘要", position = 5)
    private String summary;

    /** 附件信息 */
    @ApiModelProperty(value = "附件信息", position = 6)
    private String attachments;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 7)
    private String memo;


}
