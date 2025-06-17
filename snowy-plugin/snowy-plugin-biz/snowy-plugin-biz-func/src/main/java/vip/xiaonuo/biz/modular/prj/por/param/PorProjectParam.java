package vip.xiaonuo.biz.modular.prj.por.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PorProjectParam {

    /** 项目产值台账 - 项目管理 - 主键Id */
    @ApiModelProperty(value = "项目产值台账 - 项目管理 - 主键Id",required = true, position = 2)
    @NotBlank(message = "项目Id不能为空")
    private String porProjectId;


    /** 项目周报类型：周报|月报 (可选参数) */
    @ApiModelProperty(value = "项目周报类型：周报|月报(可选参数)",position = 5)
    private String reportType;

}
