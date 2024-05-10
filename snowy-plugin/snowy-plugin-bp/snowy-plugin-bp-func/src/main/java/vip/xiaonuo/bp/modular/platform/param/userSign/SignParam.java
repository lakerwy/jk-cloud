package vip.xiaonuo.bp.modular.platform.param.userSign;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 招采平台 - 报名
 */

@Getter
@Setter
public class SignParam {

    /** 招采用户Id (BP_USER.ID) */
    @ApiModelProperty(value = "招采用户Id (BP_USER.ID)", position = 2)
    private String bpUserId;

    /** 招采公告Id（BP_NOTICE.ID） */
    @ApiModelProperty(value = "招采公告Id（BP_NOTICE.ID）",required = true, position = 3)
    @NotBlank(message = "公告Id不能为空")
    private String noticeId;
}
