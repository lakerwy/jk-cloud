package vip.xiaonuo.biz.modular.deptFee.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ImportParam {

    /** 部门费用-集合 */
    @ApiModelProperty(value = "部门费用-集合", position = 100)
    private List<DeptFeeImportParam> importData;

}
