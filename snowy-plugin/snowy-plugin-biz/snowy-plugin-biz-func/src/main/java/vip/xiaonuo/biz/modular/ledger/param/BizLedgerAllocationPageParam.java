package vip.xiaonuo.biz.modular.ledger.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 拨付台账查询参数
 *
 * @author pzh
 * @date  2025/03/18 10:43
 **/
@Getter
@Setter
public class BizLedgerAllocationPageParam {

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

    /** 合作类型（分公司|零星） */
    @ApiModelProperty(value = "合作类型（分公司|零星）")
    private String contractType;

    /** 合作单位 */
    @ApiModelProperty(value = "合作单位")
    private String unitName;

    /** 合作单位ID */
    @ApiModelProperty(value = "合作单位ID")
    private String unitId;

    /** 所属大区 */
    @ApiModelProperty(value = "所属大区")
    private String region;

    /** 登记时间开始 */
    @ApiModelProperty(value = "登记时间开始")
    private String startCreateTime;

    /** 登记时间结束 */
    @ApiModelProperty(value = "登记时间结束")
    private String endCreateTime;

    /** 回款时间开始 */
    @ApiModelProperty(value = "回款时间开始")
    private String startReceiveAmountTime;

    /** 回款时间结束 */
    @ApiModelProperty(value = "回款时间结束")
    private String endReceiveAmountTime;

}
