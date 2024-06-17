package vip.xiaonuo.dev.modular.server.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/6/17 11:47
 * 系统模板类
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TemplateInfo {

    @ApiModelProperty(value = "公司名称", position = 1)
    private String companyName;

    @ApiModelProperty(value = "部门名称", position = 2)
    private String departmentName;

    @ApiModelProperty(value = "父部门名称", position = 3)
    private String parentDepartmentName;

    @ApiModelProperty(value = "岗位或角色名称", position = 4)
    private String positionOrRole;

    @ApiModelProperty(value = "类型(0是岗位 1 是角色)", position = 5)
    private String type;

    @ApiModelProperty(value = "岗位或角色编码", position = 6)
    private String code;

}
