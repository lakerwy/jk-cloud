package vip.xiaonuo.sys.modular.hr.social.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InterviewVo {
    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 应聘部门Id */
    @ApiModelProperty(value = "应聘部门Id", position = 2)
    private String deptId;

    /** 应聘部门 */
    @ApiModelProperty(value = "应聘部门", position = 3)
    private String dept;

    /** 应聘岗位 */
    @ApiModelProperty(value = "应聘岗位", position = 4)
    private String position;

    /** SN */
    @ApiModelProperty(value = "SN", position = 5)
    private String sn;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 6)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 7)
    private String status;

    /** 录用结果 */
    @ApiModelProperty(value = "录用结果", position = 8)
    private String reasult;
}
