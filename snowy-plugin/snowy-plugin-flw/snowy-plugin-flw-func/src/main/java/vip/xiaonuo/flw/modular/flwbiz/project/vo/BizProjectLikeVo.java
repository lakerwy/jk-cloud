package vip.xiaonuo.flw.modular.flwbiz.project.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BizProjectLikeVo {

    /** 主键 */
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** sn值 */
    @ApiModelProperty(value = "sn值", position = 2)
    private String sn;

    /** 项目名称 */
    @ApiModelProperty(value = "项目名称", position = 6)
    private String name;

    /** 项目名称 - 去除 空格特殊字符  */
    @ApiModelProperty(value = "项目名称 - 去除 空格特殊字符 ", position = 100)
    private String newName;
}
