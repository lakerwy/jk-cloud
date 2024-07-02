package vip.xiaonuo.sys.modular.hr.social.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.sys.modular.hr.social.entity.SocialResume;

import java.util.List;

@Getter
@Setter
public class SocialResumeVo extends SocialResume {

    /** 面试|入职审批流程合集 */
    @ApiModelProperty(value = "面试|入职审批流程合集", position = 66)
    private List<InterviewVo> interviewList;

}



