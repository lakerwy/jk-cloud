package vip.xiaonuo.sys.modular.hr.school.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 推荐部门，简历信息实体
 */

@Getter
@Setter
public class DeptInfo {

    /** 简历id主键 */
    @ApiModelProperty(value = "简历id主键", position = 1)
    private String resumeId;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 2)
    private String name;

    /** 学历 */
    @ApiModelProperty(value = "学历", position = 3)
    private String education;

    /** 学校 */
    @ApiModelProperty(value = "学校", position = 4)
    private String school;

    /** 岗位类别 */
    @ApiModelProperty(value = "岗位类别", position = 6)
    private String postType;

    /** 简历附件 */
    @ApiModelProperty(value = "简历附件", position = 5)
    private String resumeAttach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 7)
    private String memo;

    /** 是否面试 */
    @ApiModelProperty(value = "是否面试", position = 10)
    private String isPass;

}
