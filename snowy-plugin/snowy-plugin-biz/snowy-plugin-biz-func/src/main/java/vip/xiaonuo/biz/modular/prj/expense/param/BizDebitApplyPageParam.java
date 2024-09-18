package vip.xiaonuo.biz.modular.prj.expense.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 财务管理 - 借支单 - 申请流程查询参数
 *
 * @author yong.ye
 * @date  2024/07/30 15:33
 **/
@Getter
@Setter
public class BizDebitApplyPageParam {

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

    /** 发起人 */
    @ApiModelProperty(value = "发起人")
    private String initor;

    /** 财务编码 */
    @ApiModelProperty(value = "财务编码")
    private String finCode;

    /** 单位 */
    @ApiModelProperty(value = "单位")
    private String unit;

    /** 部门 */
    @ApiModelProperty(value = "部门")
    private String dept;

    /** 日期开始 */
    @ApiModelProperty(value = "日期开始")
    private String startExpDate;

    /** 日期结束 */
    @ApiModelProperty(value = "日期结束")
    private String endExpDate;

    /** 借支人 */
    @ApiModelProperty(value = "借支人")
    private String borrower;

    /** SN流水号 */
    @ApiModelProperty(value = "SN流水号")
    private String sn;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态")
    private String status;

    /** 借支 - 还款状态 0：待归还  1：已归还 */
    @ApiModelProperty(value = "借支 - 还款状态 0：待归还  1：已归还")
    private String debitStatus;

    /** 收款账户信息-账户名 */
    @ApiModelProperty(value = "收款账户信息-账户名")
    private String bankName;

    /** 收款账户信息-账户号 */
    @ApiModelProperty(value = "收款账户信息-账户号")
    private String bankNo;

    /** 收款账户信息-开户行 */
    @ApiModelProperty(value = "收款账户信息-开户行")
    private String bankAccount;
}
