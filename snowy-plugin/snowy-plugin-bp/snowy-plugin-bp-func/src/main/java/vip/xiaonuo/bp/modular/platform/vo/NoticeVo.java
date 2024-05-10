package vip.xiaonuo.bp.modular.platform.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.bp.modular.platform.entity.Clarify;
import vip.xiaonuo.bp.modular.platform.entity.Notice;

import java.util.List;

/**
 * 公告列表
 */


@Getter
@Setter
public class NoticeVo extends Notice {

    /** 相关澄清 - 集合 */
    @ApiModelProperty(value = "相关澄清 - 集合", position = 100)
    private List<Clarify> clarifyList;

    /** 相关澄清 - Y|N */
    @ApiModelProperty(value = "相关澄清 - Y|N", position = 100)
    private String hasClarify;

    /** 项目公告状态 - 报名中 | 报名已截止 */
    @ApiModelProperty(value = "项目公告状态 - 报名中 | 报名已截止", position = 100)
    private String bmStatus;

}
