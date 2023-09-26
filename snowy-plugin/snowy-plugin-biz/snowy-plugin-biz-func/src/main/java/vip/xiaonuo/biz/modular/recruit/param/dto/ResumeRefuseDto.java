package vip.xiaonuo.biz.modular.recruit.param.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author yong.ye
 * @date 2023/8/30 9:09
 */

@Getter
@Setter
public class ResumeRefuseDto {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 拒绝Offer的原因 */
    @ApiModelProperty(value = "拒绝Offer的原因", position = 2)
    private String reason;

}
