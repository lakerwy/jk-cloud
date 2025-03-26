package vip.xiaonuo.biz.modular.prj.exp.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 费用报销（二期）- 差旅费用报销单查询参数
 *
 * @author yong.ye
 * @date  2025/02/24 15:17
 **/
@Getter
@Setter
public class BizFormTravelPageParam {

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

    /** 部门 */
    @ApiModelProperty(value = "部门")
    private String orgName;

    /** 日期开始 */
    @ApiModelProperty(value = "日期开始")
    private String startExpDate;

    /** 日期结束 */
    @ApiModelProperty(value = "日期结束")
    private String endExpDate;

    /** 报销人id */
    @ApiModelProperty(value = "报销人id")
    private String expId;

    /** 报销人 */
    @ApiModelProperty(value = "报销人")
    private String expName;

    /** 借支单id(BIZ_FORM_CASH.ID) */
    @ApiModelProperty(value = "借支单id(BIZ_FORM_CASH.ID)")
    private String cashId;

    /** 借支单(BIZ_FORM_CASH.SN) */
    @ApiModelProperty(value = "借支单(BIZ_FORM_CASH.SN)")
    private String cashSn;

    /** 项目合同编号 */
    @ApiModelProperty(value = "项目合同编号")
    private String contractNo;

    /** 研发编号 */
    @ApiModelProperty(value = "研发编号")
    private String devNo;

    /** SN */
    @ApiModelProperty(value = "SN")
    private String sn;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态")
    private String status;


    /** 核定借支单编码 */
    @ApiModelProperty(value = "核定借支单编码")
    private String verifyCashSn;

    /** 核定借支单ID */
    @ApiModelProperty(value = "核定借支单ID")
    private String verifyCashId;

}
