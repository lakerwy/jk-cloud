package vip.xiaonuo.biz.modular.school.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 简历报表视图模型
 */
@Getter
@Setter
public class ReportModel {

    /** collect：1  简历收集-柱状图 */
    @ApiModelProperty(value = "collect：1  简历收集-柱状图")
    private BarChart collect ;

    /** classification：2  专业类别分析-饼状图 */
    @ApiModelProperty(value = "classification：2  专业类别分析-饼状图")
    private PieChart classification ;

    /** levels：3  有效简历学历层次分析-柱状图 */
    @ApiModelProperty(value = "levels：3  有效简历学历层次分析-柱状图")
    private BarChart levels ;

    /** filter：4  有效简历&初筛合格-柱状图 */
    @ApiModelProperty(value = "filter：4  有效简历&初筛合格-柱状图")
    private BarChart filter ;

    /** passing：5  到面率&通过率-折线图 */
    @ApiModelProperty(value = "passing：5  到面率&通过率-折线图")
    private LineChart2 passing ;

    /** refuse：6  offer拒绝率-折线图 */
    @ApiModelProperty(value = "refuse：6  offer拒绝率-折线图")
    private LineChart<Float> refuse ;

    /** passPerson：7  面试通过人数及接受offer人数-柱状图 */
    @ApiModelProperty(value = "passPerson：7  面试通过人数及接受offer人数-柱状图")
    private BarChart passPerson ;

    /** refuseReson：8   offer拒绝原因-饼状图 */
    @ApiModelProperty(value = "refuseReson：8   offer拒绝原因-饼状图")
    private PieChart refuseReson ;

}
