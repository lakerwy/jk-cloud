package vip.xiaonuo.biz.modular.emp.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author yong.ye
 * @date 2023/9/26 11:06
 */

@Getter
@Setter
public class EmpAssListBatchParam {

    /** 年度(如：2023) */
    @ApiModelProperty(value = "年度(如：2023)", position = 12)
    private String year;

    /** 季度(如:1) */
    @ApiModelProperty(value = "季度(如:1)", position = 13)
    private String season;

    /** 本年本季度 考核人员名单 */
    @ApiModelProperty(value = "本年本季度 考核人员名单", position = 13)
    private List<EmpAssInfo> EmpAssInfoList;

}
