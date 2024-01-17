package vip.xiaonuo.biz.modular.prj.settle.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/***
 * 结算单 - 发票信息 列表（分页）
 */

@Getter
@Setter
public class SettleInvoicePage {
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

    /** 结算单编号 */
    @ApiModelProperty(value = "结算单编号")
    private String settleNo;

    /** 合同编号 */
    @ApiModelProperty(value = "合同编号")
    private String contractNo;

    /** 合同名称 */
    @ApiModelProperty(value = "合同名称")
    private String contractName;

    /** 结算单状态：审核通过|审核中 */
    @ApiModelProperty(value = "结算单状态：审核通过|审核中")
    private String status;

}
