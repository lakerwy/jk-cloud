package vip.xiaonuo.biz.modular.recruit.param.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
    @ApiModelProperty(value = "外键（招聘计划主键Id）", position = 4)
    private String planId;

    /** 简历附件批量 */
    @ApiModelProperty(value = "简历附件批量", position = 5)
    private List<ResumeAttachDto> resumeAttachList;

}
