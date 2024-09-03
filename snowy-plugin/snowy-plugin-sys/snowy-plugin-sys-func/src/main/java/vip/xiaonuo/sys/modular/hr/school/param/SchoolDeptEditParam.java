package vip.xiaonuo.sys.modular.hr.school.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 校招 - （应聘者）- 推送部门流程编辑参数
 *
 * @author yong.ye
 * @date  2024/09/03 10:47
 **/
@Getter
@Setter
public class SchoolDeptEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

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

    /** SN */
    @ApiModelProperty(value = "SN", position = 15)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 16)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 17)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 18)
    private String status;

}
