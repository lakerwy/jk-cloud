package vip.xiaonuo.sys.modular.hr.school.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class SchoolDeptInfo {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 应聘部门 */
    @ApiModelProperty(value = "应聘部门", position = 3)
    private String dept;

    /** SN */
    @ApiModelProperty(value = "SN", position = 15)
    private String sn;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 17)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 18)
    private String status;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 10)
    private Date createTime;

    /** 是否面试 - (当前简历) */
    @ApiModelProperty(value = "是否面试(当前简历)", position = 18)
    private String isPass;
}
