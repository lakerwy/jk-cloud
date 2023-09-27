package vip.xiaonuo.biz.modular.emp.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yong.ye
 * @date 2023/9/21 11:29
 */

@Getter
@Setter
public class EmpCheckParam {

    /** 考核对象-组织Id */
    @ApiModelProperty(value = "考核对象-组织Id", position = 2)
    private String orgId;

    /** 用户Id */
    @ApiModelProperty(value = "用户Id", position = 1)
    private String userId;

    /** 年度(如：2023) */
    @ApiModelProperty(value = "年度(如：2023)", position = 3)
    private String year;

    /** 季度(如:1) */
    @ApiModelProperty(value = "季度(如:1)", position = 4)
    private String season;

}
