package vip.xiaonuo.sys.modular.hr.social.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.sys.modular.hr.social.entity.SocialInterview;

/**
 * 面试流程 - 信息
 */

@Getter
@Setter
public class SocialInterviewVo extends SocialInterview {
    /** 社招 - （应聘者）简历 - 姓名 */
    @ApiModelProperty(value = " 社招 - （应聘者）简历 - 姓名", position = 101)
    private String name;

    /**  社招 - （应聘者）简历 - 性别 */
    @ApiModelProperty(value = " 社招 - （应聘者）简历 - 性别", position = 102)
    private String sex;

    /**  社招 - （应聘者）简历 - 出生年月 */
    @ApiModelProperty(value = " 社招 - （应聘者）简历 - 出生年月", position = 103)
    private String birthDay;

    /**  社招 - （应聘者）简历 - 身份证号 */
    @ApiModelProperty(value = " 社招 - （应聘者）简历 - 身份证号", position = 104)
    private String idNumber;

    /**  社招 - （应聘者）简历 - 手机 */
    @ApiModelProperty(value = " 社招 - （应聘者）简历 - 手机", position = 105)
    private String phone;

    /**  社招 - （应聘者）简历 - 应聘岗位 */
    @ApiModelProperty(value = " 社招 - （应聘者）简历 - 应聘岗位", position = 106)
    private String applyPost;
}
