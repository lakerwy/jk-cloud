package vip.xiaonuo.flw.modular.flwbiz.project.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.flw.modular.flwbiz.project.entity.BizProject;

@Getter
@Setter
public class BizProjectData extends BizProject {

    /** 项目名称 - 去除 空格特殊字符  */
    @ApiModelProperty(value = "项目名称 - 去除 空格特殊字符 ", position = 100)
    private String newName;

}
