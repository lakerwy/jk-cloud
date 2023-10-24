package vip.xiaonuo.biz.modular.visit.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yong.ye
 * @date 2023/10/24 10:24
 */

@Getter
@Setter
public class SummaryParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 总结：外访目的 */
    @ApiModelProperty(value = "总结：外访目的", position = 32)
    private String sumPurpose;

    /** 总结：工作计划完成情况 */
    @ApiModelProperty(value = "总结：工作计划完成情况", position = 33)
    private String sumPlanComplete;

    /** 总结：工作开展情况 */
    @ApiModelProperty(value = "总结：工作开展情况", position = 34)
    private String sumImplComplete;

    /** 总结：工作总结 */
    @ApiModelProperty(value = "总结：工作总结", position = 35)
    private String sumSummary;
}
