package vip.xiaonuo.biz.modular.teambition.ThirdParam;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class ThirdTaskPageParam {
    /** 当前页 */
    @ApiModelProperty(value = "当前页码")
    private Integer current;

    /** 每页条数 */
    @ApiModelProperty(value = "每页条数")
    private Integer size;

    /** 排序字段 */
    @ApiModelProperty(value = "排序字段，字段驼峰名称，如：userName")
    private String sortField;

    /** 排序方式 */
    @ApiModelProperty(value = "排序方式，升序：ASCEND；降序：DESCEND")
    private String sortOrder;

    /** 关键词 */
    @ApiModelProperty(value = "关键词")
    private String searchKey;

    /** 父任务ID */
    @ApiModelProperty(value = "父任务ID")
    private String parentId;

    /** 任务名称 */
    @ApiModelProperty(value = "任务名称")
    private String taskName;

    @ApiModelProperty(value = "开始时间", required = true)
    @NotNull(message = "开始时间不能为空")
    private String taskStartTime;

    @ApiModelProperty(value = "结束时间", required = true)
    @NotNull(message = "结束时间不能为空")
    private String taskEndTime;

    @ApiModelProperty(value = "任务类型：0-返回所有能查看任务，1 - 我创建的，2 - 我执行的，3 - 我参与的",required = true)
    @NotNull(message = "任务类型不能为空")
    private Integer infoType;

    /** 任务状态 */
    @ApiModelProperty(value = "任务状态，0 未确认，1 已确认，2 已取消，3 进行中，4 已完成")
    private String taskStatus;

    /** 项目源主键ID */
    @ApiModelProperty(value = "项目源主键ID",required = true)
    @NotNull(message = "第三方来源code不能为空")
    private String proSourceId;

    /** 第三方来源code：1 会议，2 审批流程，3  */
    @ApiModelProperty(value = "第三方来源code：1 会议，2 审批流程，3 ",required = true)
    @NotNull(message = "第三方来源code不能为空")
    private Integer thirdCode;
}
