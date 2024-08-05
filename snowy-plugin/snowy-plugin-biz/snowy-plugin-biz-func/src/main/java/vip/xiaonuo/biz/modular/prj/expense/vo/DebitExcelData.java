package vip.xiaonuo.biz.modular.prj.expense.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 借支单 - excel - 实体
 */

@Getter
@Setter
public class DebitExcelData {
    /** 发起人 */
    @ApiModelProperty(value = "发起人", position = 2)
    private String initor;

    /** 财务编码 */
    @ApiModelProperty(value = "财务编码", position = 3)
    private String finCode;

    /** 单位 */
    @ApiModelProperty(value = "单位", position = 4)
    private String unit;

    /** 部门 */
    @ApiModelProperty(value = "部门", position = 5)
    private String dept;

    /** 日期 */
    @ApiModelProperty(value = "日期", position = 6)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expDate;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 7)
    private String memo;

    /** 借支人 */
    @ApiModelProperty(value = "借支人", position = 8)
    private String borrower;

    /** 职务 */
    @ApiModelProperty(value = "职务", position = 9)
    private String duty;

    /** 借支事由 */
    @ApiModelProperty(value = "借支事由", position = 10)
    private String reason;

    /** 人民币（大写） */
    @ApiModelProperty(value = "人民币（大写）", position = 11)
    private String upAmount;

    /** 人民币（小写） */
    @ApiModelProperty(value = "人民币（小写）", position = 12)
    private String lowAmount;

    /** 预计归还时间 */
    @ApiModelProperty(value = "预计归还时间", position = 13)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date returnDate;

    /****************************************************************/

    /** 借支单 - 年月日 */
    @ApiModelProperty(value = "借支单 - 年月日", position = 36)
    private String yearMonthDay;

    /** 借支单 - 报销人 */
    @ApiModelProperty(value = "借支单 - 报销人", position = 37)
    private String debitSign;

    /** 人民币（小写）-千分位 */
    @ApiModelProperty(value = "人民币（小写）-千分位 ", position = 38)
    private String lowAmountQian;

    /** 预计归还时间-年月日格式化 */
    @ApiModelProperty(value = "预计归还时间-年月日格式化", position = 39)
    private String returnDateStr;
}
