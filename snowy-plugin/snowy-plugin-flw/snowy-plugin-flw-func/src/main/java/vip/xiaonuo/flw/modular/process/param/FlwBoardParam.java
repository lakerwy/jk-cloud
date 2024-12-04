package vip.xiaonuo.flw.modular.process.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;


@Getter
@Setter
public class FlwBoardParam {

    /** 统计类型 1 近七天  2 近一个月 3 今年 */
    @ApiModelProperty(value = "统计类型 1 近七天  2 近一个月 3 今年",  position = 1,required = true)
    @NotBlank(message = "统计类型type不能为空")
    private String type;

    /** 当前页 */
    @ApiModelProperty(value = "当前页码")
    private Integer current;

    /** 每页条数 */
    @ApiModelProperty(value = "每页条数")
    private Integer size;

}
