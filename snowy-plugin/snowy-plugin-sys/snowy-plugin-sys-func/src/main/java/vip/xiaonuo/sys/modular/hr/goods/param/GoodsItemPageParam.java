package vip.xiaonuo.sys.modular.hr.goods.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 物资领用申请（附表） - 物资清单+退回清单 查询参数
 *
 * @author yy
 * @date  2025/05/23 14:35
 **/
@Getter
@Setter
public class GoodsItemPageParam {

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

    /** 物资清单|退回清单 */
    @ApiModelProperty(value = "物资清单|退回清单")
    private String type;

    /** 物资名称|退回物资名称 */
    @ApiModelProperty(value = "物资名称|退回物资名称")
    private String goodName;

}
