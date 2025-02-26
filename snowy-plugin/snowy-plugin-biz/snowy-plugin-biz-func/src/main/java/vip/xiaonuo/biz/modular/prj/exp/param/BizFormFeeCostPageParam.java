package vip.xiaonuo.biz.modular.prj.exp.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 费用报销（二期）- 费用报销单 - 成本明细查询参数
 *
 * @author yong.ye
 * @date  2025/02/24 15:15
 **/
@Getter
@Setter
public class BizFormFeeCostPageParam {

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

    /** 合同id */
    @ApiModelProperty(value = "合同id")
    private String htId;

    /** 合同名称 */
    @ApiModelProperty(value = "合同名称")
    private String htName;

    /** 合同SN */
    @ApiModelProperty(value = "合同SN")
    private String htSn;

    /** 费用类型（一级） */
    @ApiModelProperty(value = "费用类型（一级）")
    private String expItem;

    /** 费用类型（二级） */
    @ApiModelProperty(value = "费用类型（二级）")
    private String secondExpItem;

}
