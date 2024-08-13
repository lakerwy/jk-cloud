package vip.xiaonuo.biz.modular.branch.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 导入 分公司银行账户信息 集合
 */
@Getter
@Setter
public class ImportBranchBankInfo {

    /** 导入集合 */
    @ApiModelProperty(value = "导入集合", position = 1)
    private List<BranchBankInfoAddParam> importData;
}
