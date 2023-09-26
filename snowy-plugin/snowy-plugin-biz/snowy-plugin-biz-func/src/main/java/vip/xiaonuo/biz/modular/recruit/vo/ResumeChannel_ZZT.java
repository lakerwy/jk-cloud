package vip.xiaonuo.biz.modular.recruit.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author yong.ye
 * @date 2023/8/29 16:48
 */

@Getter
@Setter
public class ResumeChannel_ZZT {

    /** x轴-名称 */
    @ApiModelProperty(value = "x轴-名称", position = 1)
    private List<String> xAxis;

    /** y轴-值 有效简历占比 试用人数占比 招聘未达成-原因比例  */
    @ApiModelProperty(value = "y轴-值 有效简历占比 试用人数占比 招聘未达成-原因比例", position = 1)
    private List<Double> yAxis;

    /** y轴-值2  试用人数 需求人数 */
    @ApiModelProperty(value = "y轴-值2 试用人数 需求人数", position = 1)
    private List<Long> ZAxis;

    /** y轴-值3 实际招聘人数 */
    @ApiModelProperty(value = "y轴-值3 实际招聘人数", position = 1)
    private List<Long> TAxis;

}
