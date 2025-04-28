package vip.xiaonuo.sys.modular.hr.conference.rez.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 会议模块-预约会议列表查询参数
 *
 * @author pzh
 * @date  2025/04/28 14:30
 **/
@Getter
@Setter
public class ConferenceRezListParam {

    /** 查询条数 */
    @ApiModelProperty(value = "查询条数（默认5条）")
    private Integer limit = 5;

    /** 会议室ID */
    @ApiModelProperty(value = "会议室ID", required = true)
    @NotBlank(message = "roomId不能为空")
    private String roomId;

}
