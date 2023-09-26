package vip.xiaonuo.biz.modular.mapBiz.Chartreviews.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 业务表-公共更新接口
 *
 * @author mayike
 * @date  2023/06/30 16:39
 **/
@Getter
@Setter
public class BizChartReviewsStatusParam{

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 更新字段值 */
    @ApiModelProperty(value = "更新字段值")
    private String status;
}
