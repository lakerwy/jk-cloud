package vip.xiaonuo.sys.modular.hr.school.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 校招 - （应聘者）- 面试入职流程编辑参数
 *
 * @author yong.ye
 * @date  2024/09/03 10:46
 **/
@Getter
@Setter
public class SchoolInterviewEditParam {

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

    /** 外键：应聘者简历Id */
    @ApiModelProperty(value = "外键：应聘者简历Id", position = 4)
    private String resumeId;

    /** 匹配岗位说明书任职资格要求JSON */
    @ApiModelProperty(value = "匹配岗位说明书任职资格要求JSON", position = 5)
    private String matchJson;

    /** 建议薪酬JSON */
    @ApiModelProperty(value = "建议薪酬JSON", position = 6)
    private String salaryJson;

    /** 人力资源意见JSON */
    @ApiModelProperty(value = "人力资源意见JSON", position = 7)
    private String hrJson;

    /** 用人部门意见JSON */
    @ApiModelProperty(value = "用人部门意见JSON", position = 8)
    private String deptJson;

    /** 董事长意见JSON */
    @ApiModelProperty(value = "董事长意见JSON", position = 9)
    private String bossJson;

    /** 录用结果 */
    @ApiModelProperty(value = "录用结果", position = 10)
    private String reasult;

    /** 其他附件 */
    @ApiModelProperty(value = "其他附件", position = 11)
    private String otherAttach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 12)
    private String memo;

    /** SN */
    @ApiModelProperty(value = "SN", position = 20)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 21)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 22)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 23)
    private String status;

}
