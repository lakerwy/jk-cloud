package vip.xiaonuo.biz.modular.prj.expense.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import vip.xiaonuo.biz.modular.prj.expense.entity.BizExpCost;
import vip.xiaonuo.biz.modular.prj.expense.entity.BizExpItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 差旅费用报销单 - excel - 实体
 */
@Getter
@Setter
public class TripExcelData {

    /** 发起人 */
    @ApiModelProperty(value = "发起人", position = 3)
    private String initor;

    /** 报销人 */
    @ApiModelProperty(value = "报销人", position = 4)
    private String exptor;

    /** 财务编码 */
    @ApiModelProperty(value = "财务编码", position = 5)
    private String finCode;

    /** 单位 */
    @ApiModelProperty(value = "单位", position = 6)
    private String unit;

    /** 部门 */
    @ApiModelProperty(value = "部门", position = 7)
    private String dept;

    /** 日期 */
    @ApiModelProperty(value = "日期", position = 8)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expDate;

    /** 是否冲借支 */
    @ApiModelProperty(value = "是否冲借支", position = 9)
    private String isCharge;

    /** 借支时间 */
    @ApiModelProperty(value = "借支时间", position = 10)
    private String chargeDate;

    /** 借支金额 */
    @ApiModelProperty(value = "借支金额", position = 11)
    private String chargeAmount;

    /** 是否研发 */
    @ApiModelProperty(value = "是否研发", position = 12)
    private String isDev;

    /** 研发编号 */
    @ApiModelProperty(value = "研发编号", position = 13)
    private String devNo;

    /** 研发课题 */
    @ApiModelProperty(value = "研发课题", position = 14)
    private String devSubject;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 15)
    private String memo;

    /** 费用明细-总计金额（大写） */
    @ApiModelProperty(value = "费用明细-总计金额（大写）", position = 16)
    private String upFeeTotal;

    /** 费用明细-总计金额（小写） */
    @ApiModelProperty(value = "费用明细-总计金额（小写）", position = 17)
    private String lowFeeTotal;

    /** 成本一览表-总计进项税额 */
    @ApiModelProperty(value = "成本一览表-总计进项税额", position = 18)
    private String taxCostTotal;

    /** 成本一览表-总计金额（小写） */
    @ApiModelProperty(value = "成本一览表-总计金额（小写）", position = 19)
    private String upCostTotal;

    /** 成本一览表-总计金额（小写） */
    @ApiModelProperty(value = "成本一览表-总计金额（小写）", position = 20)
    private String lowCostTotal;

    /****************************************************************/

    /** 费用明细 - 集合 */
    @ApiModelProperty(value = "费用明细 - 集合", position = 34)
    private List<BizExpItem> itemList;

    /** 成本一览表 - 集合 */
    @ApiModelProperty(value = "成本一览表 - 集合", position = 35)
    private List<BizExpCost> costList;


    /** 费用报销单 - 年 */
    @ApiModelProperty(value = "费用报销单 - 年", position = 36)
    private String year;

    /** 费用报销单 - 月 */
    @ApiModelProperty(value = "费用报销单 - 月", position = 37)
    private String month;

    /** 费用报销单 - 日 */
    @ApiModelProperty(value = "费用报销单 - 日", position = 38)
    private String day;

    /** 费用报销单 - 报销人 */
    @ApiModelProperty(value = "费用报销单 - 报销人", position = 39)
    private String expSign;

}
