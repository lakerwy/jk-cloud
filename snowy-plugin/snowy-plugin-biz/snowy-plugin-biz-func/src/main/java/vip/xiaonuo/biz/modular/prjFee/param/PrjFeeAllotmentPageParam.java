package vip.xiaonuo.biz.modular.prjFee.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 工程款拨付审批查询参数
 *
 * @author pzh
 * @date  2025/01/15 09:22
 **/
@Getter
@Setter
public class PrjFeeAllotmentPageParam {

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

    /** 款项性质 */
    @ApiModelProperty(value = "款项性质")
    private String feeType;

    /** 是否出图（是|否） */
    @ApiModelProperty(value = "是否出图（是|否）")
    private String isMap;

    /** 日期 */
    @ApiModelProperty(value = "日期（格式：yyyy-MM-dd）")
    private String feeDate;

}
