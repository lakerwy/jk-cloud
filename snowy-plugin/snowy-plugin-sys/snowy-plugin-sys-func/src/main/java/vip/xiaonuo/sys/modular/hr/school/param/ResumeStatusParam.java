package vip.xiaonuo.sys.modular.hr.school.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResumeStatusParam {

    /** 主键ID 集合 */
    @ApiModelProperty(value = "主键ID 集合", position = 1)
    private List<String> ids;

    /** 简历状态 0 未审核 1 合格 -1 不合格 */
    @ApiModelProperty(value = "简历状态 0 未审核 1 合格 -1 不合格", position = 2)
    private String resumeStatus;
}
