package vip.xiaonuo.biz.modular.task.param;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author yong.ye
 * @date 2023/8/4 14:27
 */

@Getter
@Setter
public class TaskFlwParam {

    /** 组织Id */
    @ApiModelProperty(value = "组织Id", position = 2)
    private String orgId;

    /** 责任人|部门负责人 */
    @ApiModelProperty(value = "责任人|部门负责人", position = 3)
    private String ower;

    /** 责任人Id */
    @ApiModelProperty(value = "责任人Id", position = 3)
    private String owerId;

    /** 年度 */
    @ApiModelProperty(value = "年度", position = 3)
    private String year;

    /** 模型id */
    @ApiModelProperty(value = "模型id", position = 4)
    private String modelId;

    /** 流程实例id */
    @ApiModelProperty(value = "流程实例id", position = 5)
    private String processInstanceId;

    /** 年度任务集合 */
    @ApiModelProperty(value = "年度任务集合", position = 6)
    private List<FlwParam> flwParamList;
}
