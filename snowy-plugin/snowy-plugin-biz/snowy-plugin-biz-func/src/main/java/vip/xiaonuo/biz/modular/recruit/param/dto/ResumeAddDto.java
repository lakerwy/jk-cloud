package vip.xiaonuo.biz.modular.recruit.param.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yong.ye
 * @date 2023/8/23 17:38
 *
 * 页面新增简历
 */

@Getter
@Setter
public class ResumeAddDto {

    /** 简历来源 */
    @ApiModelProperty(value = "简历来源", position = 3)
    private String resumeResource;

    /** 外键（招聘计划主键Id） */
    @ApiModelProperty(value = "外键（招聘计划主键Id）", position = 34)
    private String planId;

    /** 简历附件 */
    @ApiModelProperty(value = "简历附件", position = 37)
    private String resumeAttach;

    /** 简历附件地址 */
    @ApiModelProperty(value = "简历附件地址", position = 38)
    private String resumeUrl;
}
