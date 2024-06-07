package vip.xiaonuo.sys.modular.hr.sub.vo;


import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 可视化统计各职称级别的人数（统计已缴纳社保人员和所有人员两种类型）
 */

@Getter
@Setter
public class TitleTotalVo {

    /** 统计类别：已缴社保 | 全部 */
    @ApiModelProperty(value = "统计类别：已缴社保 | 全部 ", position = 1)
    private String type;


    /** 统计结果：职称：数量 */
    @ApiModelProperty(value = "统计结果：职称：数量 ", position = 2)
    private Map<String, Integer> total;
}
