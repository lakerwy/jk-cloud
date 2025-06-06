package vip.xiaonuo.biz.modular.prj.fin.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 财务数据汇总台账 - 项目回款查询参数
 *
 * @author yy
 * @date  2025/06/05 17:26
 **/
@Getter
@Setter
public class FinDataPpcPageParam {

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

    /** 回款单位 */
    @ApiModelProperty(value = "回款单位")
    private String unitName;

    /** 回款日期开始 */
    @ApiModelProperty(value = "回款日期开始")
    private String startHkDate;

    /** 回款日期结束 */
    @ApiModelProperty(value = "回款日期结束")
    private String endHkDate;

    /** 合同Id */
    @ApiModelProperty(value = "合同Id")
    private String contractId;

    /** 回款单位id */
    @ApiModelProperty(value = "回款单位id")
    private String unitId;
}
