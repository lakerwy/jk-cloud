package vip.xiaonuo.sys.modular.hr.branchRating.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 导入 分公司银行账户信息 集合
 */
@Getter
@Setter
public class ImportBranchRating {

    /** 年份(如：2024) */
    @ApiModelProperty(value = "年份(如：2024)", position = 1)
    private String year;

    /** 季度(上半年、下半年) */
    @ApiModelProperty(value = "季度(上半年、下半年)", position = 2)
    private String quarter;

    /** 导入集合 */
    @ApiModelProperty(value = "导入集合", position = 3)
    private List<BranchRatingImportParam> importData;
}
