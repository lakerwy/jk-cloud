package vip.xiaonuo.biz.modular.bidFee.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 投标费用审批查询参数
 *
 * @author pzh
 * @date  2024/12/26 16:37
 **/
@Getter
@Setter
public class BidFeePageParam {

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

    /** 资金支付类型 */
    @ApiModelProperty(value = "资金支付类型")
    private String paymentType;

    /** 申请时间 */
    @ApiModelProperty(value = "申请时间（格式：yyyy-MM-dd）")
    private String applyDate;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态")
    private String status;


}
