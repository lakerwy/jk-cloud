package vip.xiaonuo.sys.modular.hr.reg.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 注册补贴（新版）- 结果反馈Id参数
 *
 * @author yong.ye
 * @date  2025/01/15 09:08
 **/
@Getter
@Setter
public class RegSubsidyResultIdParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
