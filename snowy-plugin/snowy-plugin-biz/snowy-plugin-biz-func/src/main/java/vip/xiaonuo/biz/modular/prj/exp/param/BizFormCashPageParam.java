package vip.xiaonuo.biz.modular.prj.exp.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 费用报销（二期）- 借支单查询参数
 *
 * @author yong.ye
 * @date  2025/02/24 15:12
 **/
@Getter
@Setter
public class BizFormCashPageParam {

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

    /** 公司 */
    @ApiModelProperty(value = "公司")
    private String company;

    /** 部门id */
    @ApiModelProperty(value = "部门id")
    private String orgId;

    /** 部门 */
    @ApiModelProperty(value = "部门")
    private String orgName;

    /** 借支日期开始 */
    @ApiModelProperty(value = "借支日期开始")
    private String startAdvanceDate;

    /** 借支日期结束 */
    @ApiModelProperty(value = "借支日期结束")
    private String endAdvanceDate;

    /** 借支人id */
    @ApiModelProperty(value = "借支人id")
    private String advanceId;

    /** 借支人 */
    @ApiModelProperty(value = "借支人")
    private String advanceName;

    /** 借支类型 */
    @ApiModelProperty(value = "借支类型")
    private String type;

    /** 归还时限开始 */
    @ApiModelProperty(value = "归还时限开始")
    private String startBackDate;

    /** 归还时限结束 */
    @ApiModelProperty(value = "归还时限结束")
    private String endBackDate;

    /** 收款账户名 */
    @ApiModelProperty(value = "收款账户名")
    private String bankUserName;

    /** 开户行 */
    @ApiModelProperty(value = "开户行")
    private String bankName;

    /** 账户号 */
    @ApiModelProperty(value = "账户号")
    private String bankNo;

    /** 付款方式 */
    @ApiModelProperty(value = "付款方式")
    private String payType;

    /** 付款日期开始 */
    @ApiModelProperty(value = "付款日期开始")
    private String startPayDate;

    /** 付款日期结束 */
    @ApiModelProperty(value = "付款日期结束")
    private String endPayDate;

    /** SN */
    @ApiModelProperty(value = "SN")
    private String sn;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态")
    private String status;

    /** 归还状态 - 需要计算 */
    @ApiModelProperty(value = "归还状态 - 需要计算")
    private String backStatus;

}
