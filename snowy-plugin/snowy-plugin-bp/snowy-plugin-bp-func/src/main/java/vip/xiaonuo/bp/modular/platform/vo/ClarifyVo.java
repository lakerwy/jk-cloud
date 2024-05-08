package vip.xiaonuo.bp.modular.platform.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import vip.xiaonuo.bp.modular.platform.entity.Clarify;

import java.util.Date;


@Getter
@Setter
public class ClarifyVo extends Clarify {
    /** 上级公告 - 标题 */
    @ApiModelProperty(value = "上级公告 - 标题", position = 100)
    private String noticeTitle;

    /** 上级公告 - 征集主体 */
    @ApiModelProperty(value = "上级公告 - 征集主体", position = 100)
    private String noticeObjName;

    /** 上级公告 - 发布时间 */
    @ApiModelProperty(value = "上级公告 - 发布时间", position = 100)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date noticePublishTime;

    /** 上级公告 - 相关附件 */
    @ApiModelProperty(value = "上级公告 - 相关附件", position = 100)
    private String noticeAttach;

}
