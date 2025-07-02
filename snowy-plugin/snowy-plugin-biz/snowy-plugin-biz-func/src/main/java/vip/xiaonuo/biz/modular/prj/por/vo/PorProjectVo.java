package vip.xiaonuo.biz.modular.prj.por.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import vip.xiaonuo.biz.modular.prj.por.entity.PorProject;

import java.math.BigDecimal;
import java.util.Date;


@Getter
@Setter
public class PorProjectVo extends PorProject {

    /*********************************** 来源-立项 ******************************************/

    /** 项目名称（BizProject.name） */
    @ApiModelProperty(value = "项目名称", position = 201)
    private String bizPrjName;

    /** 项目类型（BizProject.type） */
    @ApiModelProperty(value = "项目类型（BizProject.type）", position = 202)
    private String bizPrjType;

    /** 项目编号（BizProject.sn） */
    @ApiModelProperty(value = "项目编号（BizProject.sn）", position = 203)
    private String bizPrjSn;

    /** 建设地点（BizProject.location） */
    @ApiModelProperty(value = "建设地点（BizProject.location）", position = 204)
    private String bizPrjLocation;

    /** 项目属性 （BizProject.source）*/
    @ApiModelProperty(value = "项目属性（BizProject.source）", position = 205)
    private String bizPrjSource;

    /** 立项 - 创建时间 */
    @ApiModelProperty(value = "立项 - 创建时间", position = 206)
    private Date bizCreateTime;


    /*********************************** 来源-计算或统计 ******************************************/

    /** 项目整体进度(最近一次周报|日报) */
    @ApiModelProperty(value = "项目整体进度(最近一次周报|日报)", position = 301)
    private Double reportRate;

    /** 预计完工时间(最近一次周报|日报) */
    @ApiModelProperty(value = "预计完工时间(最近一次周报|日报)", position = 302)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date reportPreEndDate;



    /** 投资额 */
    @ApiModelProperty(value = "投资额", position = 400)
    private BigDecimal prjScaleSum;

    /** 项目合同总金额 */
    @ApiModelProperty(value = "项目合同总金额", position = 401)
    private BigDecimal prjHtSum;

    /** 项目合同/协议数 */
    @ApiModelProperty(value = "项目合同/协议数", position = 402)
    private Long prjHtNum;

    /** 建科所占金额 */
    @ApiModelProperty(value = "建科所占金额", position = 403)
    private BigDecimal prjJkSum;

    /** 建科所占金额-合同总金额占比 */
    @ApiModelProperty(value = "建科所占金额-合同总金额占比", position = 404)
    private Double prjJkRate;

    /** 应收款金额 */
    @ApiModelProperty(value = "应收款金额", position = 405)
    private BigDecimal prjYsSum;

    /** 应收款金额 - 已收 (项目回款总额) */
    @ApiModelProperty(value = "应收款金额 - 已收 (项目回款总额)", position = 406)
    private BigDecimal prjHsSum;

    /** 应收款金额-已收-占比 */
    @ApiModelProperty(value = "应收款金额-已收-占比", position = 407)
    private Double prjHsRate;

    /** 待收款金额 */
    @ApiModelProperty(value = "待收款金额", position = 408)
    private BigDecimal prjDsSum;

    /** 待收款金额-占比 */
    @ApiModelProperty(value = "待收款金额-占比", position = 409)
    private Double prjDsRate;

    /** 分包合同总额 */
    @ApiModelProperty(value = "分包合同总额", position = 410)
    private BigDecimal prjSubSum;

    /** 分包合同数 */
    @ApiModelProperty(value = "分包合同数", position = 411)
    private Long prjSubNum;

    /** 应付金额合计 */
    @ApiModelProperty(value = "应付金额合计", position = 412)
    private BigDecimal prjYfSum;

    /** 应付金额合计 - 已付 */
    @ApiModelProperty(value = "应付金额合计 - 已付", position = 413)
    private BigDecimal prjHfSum;

    /** 应付金额合计 - 已付 -占比 */
    @ApiModelProperty(value = "应付金额合计 - 已付 -占比", position = 414)
    private Double prjHfRate;

    /** 责任上缴费用 */
    @ApiModelProperty(value = "责任上缴费用", position = 415)
    private BigDecimal prjRpmSum;

    /** 签字审图费 */
    @ApiModelProperty(value = "签字审图费", position = 416)
    private BigDecimal prjSignYfSum;

    /** 签字审图费 - 已付 */
    @ApiModelProperty(value = "签字审图费 - 已付", position = 417)
    private BigDecimal prjSignHfSum;

    /** 签字审图费 - 已付 -占比 */
    @ApiModelProperty(value = "签字审图费 - 已付 -占比", position = 418)
    private Double prjSignHfRate;

    /** 分包费应付 */
    @ApiModelProperty(value = "分包费应付", position = 419)
    private BigDecimal prjSubYfSum;

    /** 分包费应付 - 已付 */
    @ApiModelProperty(value = "分包费应付 - 已付", position = 420)
    private BigDecimal prjSubHfSum;

    /** 分包费应付 - 已付 -占比 */
    @ApiModelProperty(value = "分包费应付 - 已付 -占比", position = 421)
    private Double prjSubHfRate;

    /** 经营费应付 */
    @ApiModelProperty(value = "经营费应付", position = 422)
    private BigDecimal prjOemYfSum;

    /** 经营费应付 - 已付 */
    @ApiModelProperty(value = "经营费应付 - 已付", position = 423)
    private BigDecimal prjOemHfSum;

    /** 经营费应付 - 已付 -占比 */
    @ApiModelProperty(value = "经营费应付 - 已付 -占比", position = 424)
    private Double prjOemHfRate;

    /** 项目现场管理费用累计 */
    @ApiModelProperty(value = "项目现场管理费用累计", position = 425)
    private BigDecimal prjSiteSum;

    /** 税费实付 */
    @ApiModelProperty(value = "税费实付", position = 426)
    private BigDecimal prjTaxSum;

    /** 项目回款进度 */
    @ApiModelProperty(value = "项目回款进度", position = 427)
    private Double prjRcmRate;

    /** 项目回款进度缺口 */
    @ApiModelProperty(value = "项目回款进度缺口", position = 428)
    private Double prjRcmLostRate;
}
