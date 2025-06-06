package vip.xiaonuo.biz.modular.prj.fin.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 财务数据汇总台账 - 销售发票查询参数
 *
 * @author yy
 * @date  2025/06/05 17:24
 **/
@Getter
@Setter
public class FinDataInvoicePageParam {

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

    /** 合同编码 */
    @ApiModelProperty(value = "合同编码")
    private String contractNo;

    /** 开票单位 */
    @ApiModelProperty(value = "开票单位")
    private String unitName;

    /** 开票日期开始 */
    @ApiModelProperty(value = "开票日期开始")
    private String startKpDate;

    /** 开票日期结束 */
    @ApiModelProperty(value = "开票日期结束")
    private String endKpDate;

    /** 合同Id */
    @ApiModelProperty(value = "合同Id")
    private String contractId;

    /** 开票单位id */
    @ApiModelProperty(value = "开票单位id")
    private String unitId;
}
