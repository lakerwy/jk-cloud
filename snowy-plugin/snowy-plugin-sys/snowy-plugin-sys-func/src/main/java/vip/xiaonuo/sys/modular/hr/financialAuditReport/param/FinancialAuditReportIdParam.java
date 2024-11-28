package vip.xiaonuo.sys.modular.hr.financialAuditReport.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 财务审计报告Id参数
 *
 * @author pzh
 * @date  2024/10/18 14:51
 **/
@Getter
@Setter
public class FinancialAuditReportIdParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
