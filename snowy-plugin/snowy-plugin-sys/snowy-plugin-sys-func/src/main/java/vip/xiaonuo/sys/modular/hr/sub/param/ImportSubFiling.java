package vip.xiaonuo.sys.modular.hr.sub.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 导入 分包单位备案明细 集合
 */
@Getter
@Setter
public class ImportSubFiling {

    /** 分包单位备案-明细 */
    @ApiModelProperty(value = "分包单位备案-明细", position = 17)
    private List<SubFilingItemImportParam> importData;

    @Getter
    @Setter
    public static class SubFilingItemImportParam {

        /** 分公司名称(用印部门) */
        @ApiModelProperty(value = "分公司名称(用印部门)", position = 3)
        private String sealDept;

        @ApiModelProperty(value = "分包单位", position = 21)
        private String unitName;

        /** 备注 */
        @ApiModelProperty(value = "备注", position = 22)
        private String memo;

    }

}
