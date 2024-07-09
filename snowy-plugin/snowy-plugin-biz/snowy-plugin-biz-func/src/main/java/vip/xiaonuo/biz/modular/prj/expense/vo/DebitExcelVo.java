package vip.xiaonuo.biz.modular.prj.expense.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 借支单 - excel - 实体
 */

@Getter
@Setter
public class DebitExcelVo {
    /** 借支单 - 单位 */
    @ApiModelProperty(value = "借支单 - 单位", position = 1)
    private String unit;

    /** 借支单 - 部门 */
    @ApiModelProperty(value = "借支单 - 部门", position = 2)
    private String dept;

    /** 借支单 - 年 */
    @ApiModelProperty(value = "借支单 - 年", position = 3)
    private String year;

    /** 借支单 - 月 */
    @ApiModelProperty(value = "借支单 - 月", position = 4)
    private String month;

    /** 借支单 - 日 */
    @ApiModelProperty(value = "借支单 - 日", position = 5)
    private String day;

    /** 借支单 - 财务收单编码 */
    @ApiModelProperty(value = "借支单 - 财务收单编码", position = 6)
    private String sn;

    /** 借支单 - 借支人 */
    @ApiModelProperty(value = "借支单 - 借支人", position = 7)
    private String borrower;

    /** 借支单 - 职务 */
    @ApiModelProperty(value = "借支单 - 职务", position = 8)
    private String duty;

    /** 借支单 - 借支事由 */
    @ApiModelProperty(value = "借支单 - 借支事由", position = 9)
    private String reason;

    /** 借支单 - 人民币 （大写） */
    @ApiModelProperty(value = "借支单 - 人民币 （大写）", position = 10)
    private String upAmount;

    /** 借支单 - 人民币（小写） */
    @ApiModelProperty(value = "借支单 - 人民币（小写）", position = 11)
    private String lowAmount;

    /** 借支单 - 预计归还时间 */
    @ApiModelProperty(value = "借支单 - 预计归还时间", position = 12)
    private String returnDate;




    public DebitExcelVo(){
        this.unit = "湖北建科国际";
        this.dept = "市场部门-华东地区";
        this.year = "2024";
        this.month = "07";
        this.day = "12";
        this.sn = "JZ2024070012";
        this.borrower = "小人物";
        this.duty = "职务A";
        this.reason = "借支事由111";
        this.upAmount = "壹仟捌佰叁拾玖万柒仟叁佰叁拾贰元整";
        this.lowAmount = "18397332";
        this.returnDate = "2025-1-1";
    }
}
