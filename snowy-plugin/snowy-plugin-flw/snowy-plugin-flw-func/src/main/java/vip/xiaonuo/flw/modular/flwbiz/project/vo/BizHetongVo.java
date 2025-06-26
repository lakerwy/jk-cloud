package vip.xiaonuo.flw.modular.flwbiz.project.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BizHetongVo {

    /** 主键ID（历史表Hetong.Id） */
    @ApiModelProperty(value = "主键ID（历史表Hetong.Id）", position = 1)
    private String id;

    /** 合同编号（历史表Hetong.ContractNo） */
    @ApiModelProperty(value = "合同编号（历史表Hetong.ContractNo）", position = 6)
    private String contractNo;

    /** 合同名称（历史表Hetong.ProjectName） */
    @ApiModelProperty(value = "合同名称（历史表Hetong.ProjectName）", position = 7)
    private String projectName;

    /** 合同金额 - 建科所占金额 */
    @ApiModelProperty(value = "合同金额 - 建科所占金额 ", position = 19)
    private BigDecimal contractAmount;

    /** 合同总金额 */
    @ApiModelProperty(value = "合同总金额", position = 63)
    private BigDecimal sumAmount;
}
