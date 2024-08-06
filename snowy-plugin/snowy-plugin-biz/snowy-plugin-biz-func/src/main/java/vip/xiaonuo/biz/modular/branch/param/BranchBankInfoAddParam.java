package vip.xiaonuo.biz.modular.branch.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 分公司银行账户信息添加参数
 *
 * @author pzh
 * @date  2024/08/05 11:46
 **/
@Getter
@Setter
public class BranchBankInfoAddParam {

    /** 分公司名称 */
    @ApiModelProperty(value = "分公司名称", position = 3)
    private String branchName;

    /** 开户行名称 */
    @ApiModelProperty(value = "开户行名称", position = 4)
    private String bankName;

    /** 银行账户 */
    @ApiModelProperty(value = "银行账户", position = 5)
    private String bankAccount;

    /** 账户余额（元） */
    @ApiModelProperty(value = "账户余额（元）", position = 6)
    private BigDecimal accountBalance;

}
