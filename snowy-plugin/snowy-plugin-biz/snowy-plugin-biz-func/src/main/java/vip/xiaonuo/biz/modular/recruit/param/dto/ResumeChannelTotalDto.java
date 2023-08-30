package vip.xiaonuo.biz.modular.recruit.param.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yong.ye
 * @date 2023/8/29 14:49
 */

@Getter
@Setter
public class ResumeChannelTotalDto {

    /** 所属部门 */
    @ApiModelProperty(value = "所属部门")
    private String orgId;

    /** 开始时间 */
    @ApiModelProperty(value = "开始时间")
    private String startDate;

    /** 结束时间 */
    @ApiModelProperty(value = "结束时间")
    private String endDate;

}
