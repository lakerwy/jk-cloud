package vip.xiaonuo.sys.modular.dingtalk.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 同步钉钉考勤数据 - 所属月份
 */

@Getter
@Setter
public class SyncAttendDataParam {

    @ApiModelProperty(value = "所属月份（格式：yyyyMM，例如：202501）", position = 1, required = true)
    @NotBlank(message = "所属月份不能为空")
    private String yM;

}
