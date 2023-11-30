package vip.xiaonuo.biz.modular.cad.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.cad.entity.CadSubItem;

@Getter
@Setter
public class CadSubItemVo extends CadSubItem {

    /** 阶段名称 */
    @ApiModelProperty(value = "阶段名称", position = 20)
    private String stageName;

    /** 阶段排序 */
    @ApiModelProperty(value = "阶段排序", position = 20)
    private Integer stageIndex;

    /** 项目名称 */
    @ApiModelProperty(value = "项目名称", position = 20)
    private String prjName;
}
