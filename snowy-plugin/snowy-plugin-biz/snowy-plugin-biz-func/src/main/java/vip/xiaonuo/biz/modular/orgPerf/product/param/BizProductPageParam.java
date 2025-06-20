package vip.xiaonuo.biz.modular.orgPerf.product.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 产值情况查询参数
 *
 * @author pzh
 * @date  2025/06/17 09:03
 **/
@Getter
@Setter
public class BizProductPageParam {

    /** 当前页 */
    @ApiModelProperty(value = "当前页码")
    private Integer current;

    /** 每页条数 */
    @ApiModelProperty(value = "每页条数")
    private Integer size;

    /** 排序字段 */
    @ApiModelProperty(value = "排序字段，字段驼峰名称，如：userName")
    private String sortField;

    /** 排序方式 */
    @ApiModelProperty(value = "排序方式，升序：ASCEND；降序：DESCEND")
    private String sortOrder;

    /** 关键词 */
    @ApiModelProperty(value = "关键词")
    private String searchKey;

    /** 年份（例如：2025） */
    @ApiModelProperty(value = "年份（例如：2025）")
    private Integer year;

    /** 季度（例如：1） */
    @ApiModelProperty(value = "季度（例如：1）")
    private Integer season;

}
