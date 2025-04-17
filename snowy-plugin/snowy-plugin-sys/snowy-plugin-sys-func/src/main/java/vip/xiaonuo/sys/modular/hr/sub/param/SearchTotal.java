package vip.xiaonuo.sys.modular.hr.sub.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SearchTotal {

    /** 关键词 */
    @ApiModelProperty(value = "关键词")
    private String searchKey;

    /** 分公司Id */
    @ApiModelProperty(value = "分公司Id")
    private String orgId;

    /** 分公司 */
    @ApiModelProperty(value = "分公司")
    private String orgName;

    /** 时间 */
    @ApiModelProperty(value = "时间",required = true)
    @NotBlank(message = "时间必填")
    private String yM;

    /** 所属大区 */
    @ApiModelProperty(value = "所属大区")
    private String region;


    /** 【*** 年度统计专用 ***】：【年度统计 - 报表类型】：1 应发工资人数  2 实发工资人数  3 应发工资总额  4 实发工资总额  5 实缴五险人数  6 实缴五险总额  7 实缴公积金人数  8 实缴公积金金额  9 奖金  10 应发补偿金人数  11 实发补偿金人数  12 应发补偿金总额  13 实发补偿金总额 */
    @ApiModelProperty(value = "【*** 年度统计专用 ***】：【年度统计 - 报表类型】：1 应发工资人数  2 实发工资人数  3 应发工资总额  4 实发工资总额  5 实缴五险人数  6 实缴五险总额  7 实缴公积金人数  8 实缴公积金金额  9 奖金  10 应发补偿金人数  11 实发补偿金人数  12 应发补偿金总额  13 实发补偿金总额")
    private String reportType;

    /** 【*** 打印Excel专用 ***】：【Excel打印报表】：month:月统计表  year:年汇总表 */
    @ApiModelProperty(value = "【*** 打印Excel专用 ***】：【Excel打印报表】：month:月统计表  year:年汇总表 ")
    private String excelType;

}
