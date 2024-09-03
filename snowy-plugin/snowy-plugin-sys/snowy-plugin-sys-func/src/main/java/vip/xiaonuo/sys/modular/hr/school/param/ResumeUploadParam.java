package vip.xiaonuo.sys.modular.hr.school.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 为简历补充附件
 */

@Getter
@Setter
public class ResumeUploadParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 简历附件 */
    @ApiModelProperty(value = "简历附件", required = true, position = 2)
    @NotBlank(message = "简历附件不能为空")
    private String resumeAttach;

}
