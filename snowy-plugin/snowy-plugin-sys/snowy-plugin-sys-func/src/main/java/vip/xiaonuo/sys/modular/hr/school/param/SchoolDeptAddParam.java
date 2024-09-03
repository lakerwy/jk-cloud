package vip.xiaonuo.sys.modular.hr.school.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 校招 - （应聘者）- 推送部门流程添加参数
 *
 * @author yong.ye
 * @date  2024/09/03 10:47
 **/
@Getter
@Setter
public class SchoolDeptAddParam {

    /** 应聘部门Id */
    @ApiModelProperty(value = "应聘部门Id", position = 2)
    private String deptId;

    /** 应聘部门 */
    @ApiModelProperty(value = "应聘部门", position = 3)
    private String dept;

    /** 审核人Id */
    @ApiModelProperty(value = "审核人Id", position = 4)
    private String auditId;

    /** 审核人 */
    @ApiModelProperty(value = "审核人", position = 5)
    private String audit;

    /** 推荐的简历集合JSON */
    @ApiModelProperty(value = "推荐的简历集合JSON", position = 6)
    private String resumeJson;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 7)
    private String memo;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 17)
    private String processInstanceId;

}
