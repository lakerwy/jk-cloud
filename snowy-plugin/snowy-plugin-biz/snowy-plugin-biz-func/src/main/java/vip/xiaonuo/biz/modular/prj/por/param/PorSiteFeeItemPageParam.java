package vip.xiaonuo.biz.modular.prj.por.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目产值台账 - 项目管理 - 项目现场管理费用 - 分类明细查询参数
 *
 * @author yy
 * @date  2025/06/03 09:47
 **/
@Getter
@Setter
public class PorSiteFeeItemPageParam {

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

    /** 项目现场管理费用- 主键Id */
    @ApiModelProperty(value = "项目现场管理费用- 主键Id")
    private String porSiteFeeId;

    /** 归属月份 */
    @ApiModelProperty(value = "归属月份")
    private String feeMonth;

    /** 费用类型 */
    @ApiModelProperty(value = "费用类型")
    private String feeType;

}
