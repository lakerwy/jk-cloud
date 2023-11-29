package vip.xiaonuo.biz.modular.cad.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.cad.entity.CadProject;

@Getter
@Setter
public class CadProjectVo extends CadProject {
    /** 项目管理者|项目负责人 姓名 */
    @ApiModelProperty(value = "项目管理者|项目负责人 姓名 ", position = 20)
    private String managerName;
}
