package vip.xiaonuo.bp.modular.recruit.businessscope.entity;/**
 * @Author hxw
 * @Date 2024/4/28 9:52
 */

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 供应商分类混合搜索 实体结构
 *@Author hxw
 *@Date 2024/4/28 9:52
 */
@Getter
@Setter
public class BpBusinessScopeSearchParam {
    /** 一级分类ID */
    @ApiModelProperty(value = "一级分类ID")
    private String stairclassifyId;

    /** 二级分类ID */
    @ApiModelProperty(value = "二级分类ID")
    private String accessclassifyId;

    /** 三级分类ID */
    @ApiModelProperty(value = "三级分类ID")
    private String reclassifyId;
}
