package vip.xiaonuo.biz.modular.prj.plan.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/***
 * 计划单 - 发票信息 列表（分页）
 */

@Getter
@Setter
public class PlanInvoicePage {
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

    /** 计划付款单编号 */
    @ApiModelProperty(value = "计划付款单编号")
    private String planNo;

    /** 合同编号 */
    @ApiModelProperty(value = "合同编号")
    private String contractNo;

    /** 合同名称 */
    @ApiModelProperty(value = "合同名称")
    private String contractName;

    /** 计划单状态：审核通过|审核中 */
    @ApiModelProperty(value = "计划单状态：审核通过|审核中")
    private String status;

}
