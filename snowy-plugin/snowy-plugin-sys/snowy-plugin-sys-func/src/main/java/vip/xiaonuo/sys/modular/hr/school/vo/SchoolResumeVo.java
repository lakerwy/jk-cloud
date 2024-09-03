package vip.xiaonuo.sys.modular.hr.school.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.sys.modular.hr.school.entity.SchoolResume;

import java.util.List;

/**
 * 简历信息
 */

@Getter
@Setter
public class SchoolResumeVo extends SchoolResume {

    /** 部门推荐审批流程合集 */
    @ApiModelProperty(value = "部门推荐审批流程合集", position = 66)
    private List<SchoolDeptInfo> deptList;

    /** 面试审批流程合集 */
    @ApiModelProperty(value = "面试审批流程合集", position = 66)
    private List<SchoolInterviewInfo> interviewList;

}
