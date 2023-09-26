package vip.xiaonuo.biz.modular.recruit.param.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author yong.ye
 * @date 2023/8/23 9:18
 */

@Getter
@Setter
public class RecruitPlanFlwDto {

    /** 模型id */
    @ApiModelProperty(value = "模型id", position = 1)
    private String modelId;

    /** 流程实例id */
    @ApiModelProperty(value = "流程实例id", position = 2)
    private String processInstanceId;

    /** 招聘计划集合 */
    @ApiModelProperty(value = "招聘计划集合", position = 3)
    private List<RecruitPlanFlwAddDto> recruitPlanList;
}
