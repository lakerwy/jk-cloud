package vip.xiaonuo.sys.modular.hr.sub.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SubFilingCheckParam {

//    /** 用印部门 */
//    @ApiModelProperty(value = "用印部门", position = 2)
//    private String sealDept;

    /** 用印部门ID */
    @ApiModelProperty(value = "用印部门ID", position = 3)
    private String sealDeptId;

    /** 分包单位备案-明细 */
    @ApiModelProperty(value = "分包单位备案-明细", position = 17)
    private List<SubFilingItemEditParam> subFilingItemList;
}
