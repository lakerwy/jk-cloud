package vip.xiaonuo.flw.modular.process.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FlwBoardParam {

    /** 统计类型 1 近七天  2 近一个月 3 今年 */
    @ApiModelProperty(value = "统计类型 1 近七天  2 近一个月 3 今年",  position = 1)
    private String type;
}
