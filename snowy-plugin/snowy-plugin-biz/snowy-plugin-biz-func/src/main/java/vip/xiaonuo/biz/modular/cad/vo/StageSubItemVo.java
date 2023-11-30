package vip.xiaonuo.biz.modular.cad.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/***
 * 根据项目Id,获取 阶段/子项 二级树状结构
 */

@Getter
@Setter
public class StageSubItemVo {

    /** 阶段 - 主键ID */
    @ApiModelProperty(value = " 阶段 - 主键ID ", position = 1)
    private String stageId;

    /** 阶段名称 */
    @ApiModelProperty(value = "阶段名称", position = 3)
    private String stageName;

    /** 当前项目prjId 当前阶段 下的子项集合 */
    @ApiModelProperty(value = "当前项目prjId 当前阶段 下的子项集合", position = 5)
    private List<CadSubItemVo> subItemList;

}
