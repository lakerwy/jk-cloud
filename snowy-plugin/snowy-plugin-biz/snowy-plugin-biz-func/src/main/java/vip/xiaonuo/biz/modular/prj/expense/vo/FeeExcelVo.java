package vip.xiaonuo.biz.modular.prj.expense.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 费用报销单 - excel - 实体
 */

@Getter
@Setter
public class FeeExcelVo {
    /** 费用报销单 - 单位 */
    @ApiModelProperty(value = "费用报销单 - 单位", position = 1)
    private String unit;

    /** 费用报销单 - 部门 */
    @ApiModelProperty(value = "费用报销单 - 部门", position = 2)
    private String dept;

    /** 费用报销单 - 年 */
    @ApiModelProperty(value = "费用报销单 - 年", position = 3)
    private String year;

    /** 费用报销单 - 月 */
    @ApiModelProperty(value = "费用报销单 - 月", position = 4)
    private String month;

    /** 费用报销单 - 日 */
    @ApiModelProperty(value = "费用报销单 - 日", position = 5)
    private String day;

    /** 费用报销单 - 财务收单编码 */
    @ApiModelProperty(value = "费用报销单 - 财务收单编码", position = 6)
    private String sn;

    /** 费用报销单 - 总计金额 （大写） */
    @ApiModelProperty(value = "费用报销单 - 总计金额 （大写）", position = 7)
    private String upFeeTotal;

    /** 费用报销单 - 总计金额（小写） */
    @ApiModelProperty(value = "费用报销单 - 总计金额（小写）", position = 8)
    private String lowFeeTotal;

    /** 费用报销单 - 是否冲借支 */
    @ApiModelProperty(value = "费用报销单 - 是否冲借支", position = 9)
    private String isCharge;

    /** 费用报销单 - 借支时间 */
    @ApiModelProperty(value = "费用报销单 - 借支时间", position = 10)
    private String chargeDate;

    /** 费用报销单 - 借支金额 */
    @ApiModelProperty(value = "费用报销单 - 借支金额", position = 11)
    private String chargeAmount;

    /** 费用报销单 - 是否研发 */
    @ApiModelProperty(value = "费用报销单 - 是否研发", position = 12)
    private String isDev;

    /** 费用报销单 - 研发编号 */
    @ApiModelProperty(value = "费用报销单 - 研发编号", position = 13)
    private String devNo;

    /** 费用报销单 - 研发课题 */
    @ApiModelProperty(value = "费用报销单 - 研发课题", position = 14)
    private String devSubject;

    /** 费用报销单 - 费用明细 */
    @ApiModelProperty(value = "费用报销单 - 费用明细", position = 15)
    private List<FeeItemVo> feeItems;

    /****************************************************************/

    /** 成本一览表 - 总计金额 （大写） */
    @ApiModelProperty(value = "成本一览表 - 总计金额 （大写）", position = 51)
    private String upCostTotal;

    /** 成本一览表 - 总计进项税额 */
    @ApiModelProperty(value = "成本一览表 - 总计进项税额", position = 52)
    private String taxCostTotal;

    /** 成本一览表 - 总计金额（小写） */
    @ApiModelProperty(value = "成本一览表 - 总计金额（小写）", position = 53)
    private String lowCostTotal;

    /** 成本一览表 - 成本明细 */
    @ApiModelProperty(value = "成本一览表 - 成本明细", position = 54)
    private List<CostItemVo> costItems;




    public FeeExcelVo(){
        this.unit = "湖北建科国际";
        this.dept = "市场部门-华东地区";
        this.year = "2024";
        this.month = "07";
        this.day = "12";
        this.sn = "FY2024070012";
        this.upFeeTotal = "壹仟捌佰叁拾玖万柒仟叁佰叁拾贰元整";
        this.lowFeeTotal = "18397332";
        this.isCharge = "是";
        this.chargeDate = "2024-05-21";
        this.chargeAmount = "3408903";
        this.isDev = "是";
        this.devNo = "DEV2024001";
        this.devSubject = "人为啥要吃饭";
        List<FeeItemVo> feeItems = new ArrayList<>();
        for(int i=0;i<10;i++){
            FeeItemVo feeItemVo = new FeeItemVo();
            feeItemVo.setItem("费用"+(i+1));
            feeItemVo.setAbstractDesc("费用摘要"+(i+1));
            feeItemVo.setAmount("费用金额"+(i+1));
            feeItemVo.setMemo("费用备注"+(i+1));
            feeItems.add(feeItemVo);
        }
        this.feeItems = feeItems;


        this.upCostTotal = "壹仟捌佰叁拾玖万柒仟叁佰叁拾贰元整";
        this.lowCostTotal = "18397332";
        this.taxCostTotal = "197332";
        List<CostItemVo> costItems = new ArrayList<>();
        for(int i=0;i<10;i++){
            CostItemVo costItemVo = new CostItemVo();
            costItemVo.setPrjName("项目|合同名称"+(i+1));
            costItemVo.setItem("明细科目"+(i+1));
            costItemVo.setAbstractDesc("摘要"+(i+1));
            costItemVo.setAmount("金额（含税）"+(i+1));
            costItemVo.setTax("进项税额"+(i+1));
            costItemVo.setMemo("备注"+(i+1));
            costItems.add(costItemVo);
        }
        this.costItems = costItems;
    }

}
