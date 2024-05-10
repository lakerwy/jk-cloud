package vip.xiaonuo.bp.modular.platform.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 招采平台 - 登录后，用户查看公告详情信息
 */


@Getter
@Setter
public class UserNoticeVo extends NoticeVo{

    /** 若已登录： 是否已缴纳 附件查看费用  YES|NO */
    @ApiModelProperty(value = "若已登录：是否已缴纳 附件查看费用 YES|NO ", position = 100)
    private String isPayForAttach;

    /** 若已登录：是否已报名 */
    @ApiModelProperty(value = "若已登录：是否已报名", position = 100)
    private String isSign;

}
