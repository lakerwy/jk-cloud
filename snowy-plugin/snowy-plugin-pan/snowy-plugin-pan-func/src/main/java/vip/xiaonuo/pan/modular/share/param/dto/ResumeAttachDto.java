package vip.xiaonuo.biz.modular.recruit.param.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yong.ye
 * @date 2023/9/1 15:27
 */

@Getter
@Setter
public class ResumeAttachDto {

    /** 简历附件 */
    @ApiModelProperty(value = "简历附件", position = 37)
    private String resumeAttach;

    /** 简历附件地址 */
    @ApiModelProperty(value = "简历附件地址", position = 38)
    private String resumeUrl;

    /** 简历附件名称（带后缀） */
    @ApiModelProperty(value = "简历附件名称（带后缀）", position = 38)
    private String resumeName;
}
