package vip.xiaonuo.biz.modular.emp.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yong.ye
 * @date 2023/9/26 16:49
 */

@Getter
@Setter
public class EmpAssTotalParam {

    /** 年度(如：2023) */
    @ApiModelProperty(value = "年度(如：2023)", position = 3)
    private String year;

    /** 季度(如:1) */
    @ApiModelProperty(value = "季度(如:1)", position = 4)
    private String season;

}
