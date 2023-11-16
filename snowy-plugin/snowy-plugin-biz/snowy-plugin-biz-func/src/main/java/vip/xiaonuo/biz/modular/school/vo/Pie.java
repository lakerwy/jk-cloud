package vip.xiaonuo.biz.modular.school.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 饼图对象
 */
@Getter
@Setter
public class Pie {

    /** 栏目名称 */
    @ApiModelProperty(value = "栏目名称")
    private String name ;

    /** 栏目值 */
    @ApiModelProperty(value = "栏目值")
    private Integer value ;
}
