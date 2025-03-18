package vip.xiaonuo.sys.modular.hr.branchRating.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class ImportParam {

    /** 区域经济修正系数 - 集合 */
    @ApiModelProperty(value = "区域经济修正系数 - 集合")
    List<BranchAreaAddParam> branchAreaList;
}
