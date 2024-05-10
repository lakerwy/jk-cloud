package vip.xiaonuo.bp.modular.platform.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import vip.xiaonuo.bp.modular.platform.entity.Clarify;
import vip.xiaonuo.bp.modular.platform.entity.Notice;
import vip.xiaonuo.bp.modular.platform.entity.UserPay;
import vip.xiaonuo.bp.modular.platform.entity.UserSign;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UserSignVo extends UserSign {

    /** 项目公告 - 集合 */
    @ApiModelProperty(value = "项目公告 - 集合", position = 100)
    private Notice noticeInfo;

    /** 澄清公告 - 集合 */
    @ApiModelProperty(value = "澄清公告 - 集合", position = 100)
    private List<Clarify> clarifyList;

    /** 保证金缴纳 - 集合 */
    @ApiModelProperty(value = "保证金缴纳 - 集合", position = 100)
    private UserPay userPayInfo;

    /** 报名项目 */
    @ApiModelProperty(value = "报名项目", position = 100)
    private String noticeTitle;

    /** 报名截止日期 */
    @ApiModelProperty(value = "报名截止日期", position = 100)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date noticeEndTime;

    /** 投标保证金缴纳时限（天） */
    @ApiModelProperty(value = "投标保证金缴纳时限（天）", position = 100)
    private String noticeBidDeadline;

    /** 是否已缴纳 保证金 YES|NO */
    @ApiModelProperty(value = "是否已缴纳 保证金 YES|NO", position = 100)
    private String isPayForBond;

    /** 保证金缴纳截止日期 */
    @ApiModelProperty(value = "保证金缴纳截止日期", position = 100)
    private String noticeBondEndTime;
}
