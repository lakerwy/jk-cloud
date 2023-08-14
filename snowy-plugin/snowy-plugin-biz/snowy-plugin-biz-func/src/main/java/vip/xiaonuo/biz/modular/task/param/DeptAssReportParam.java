package vip.xiaonuo.biz.modular.task.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author yong.ye
 * @date 2023/8/9 14:38
 */

@Getter
@Setter
public class DeptAssReportParam {

    /** 考核年月 */
    @ApiModelProperty(value = "考核年月", required = true, position = 2)
    @NotBlank(message = "assYear不能为空")
    private String assYear;

    /** 组织Id */
    @ApiModelProperty(value = "组织Id", position = 3)
    private String orgId;

}
