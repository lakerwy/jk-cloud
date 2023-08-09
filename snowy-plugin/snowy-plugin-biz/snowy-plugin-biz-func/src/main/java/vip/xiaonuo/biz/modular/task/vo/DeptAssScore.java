package vip.xiaonuo.biz.modular.task.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yong.ye
 * @date 2023/8/9 9:28
 */

@Getter
@Setter
public class DeptAssScore {
    /** (外键)部门考核表主键Id */
    @ApiModelProperty(value = "(外键)部门考核表主键Id")
    private String assId;

    /** 加分小计 */
    @ApiModelProperty(value = "加分小计")
    private Double addScore;

    /** 减分小计 */
    @ApiModelProperty(value = "减分小计")
    private Double minusScore;
}
