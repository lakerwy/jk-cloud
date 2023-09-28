package vip.xiaonuo.biz.modular.prj.settle.param;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import vip.xiaonuo.biz.modular.prj.plan.entity.BizPrjPlanInvoice;
import vip.xiaonuo.biz.modular.prj.settle.entity.BizPrjSettleInvoice;

import java.util.Date;
import java.util.List;

/**
 * @author yong.ye
 * @date 2023/9/28 15:42
 */

@Getter
@Setter
public class PrjSettleParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 合作方/分公司编码 */
    @ApiModelProperty(value = "合作方/分公司编码", position = 2)
    private String hzfCode;

    /** 合作方/分公司名称 */
    @ApiModelProperty(value = "合作方/分公司名称", position = 3)
    private String hzfName;

    /** 申请日期 */
    @ApiModelProperty(value = "申请日期", position = 4)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyDate;

    /** 结算单编号 */
    @ApiModelProperty(value = "结算单编号", position = 5)
    private String settleNo;

    /** 合作协议基本信息-合同编号 */
    @ApiModelProperty(value = "合作协议基本信息-合同编号", position = 6)
    private String xyContractNo;

    /** 合作协议基本信息-合同名称 */
    @ApiModelProperty(value = "合作协议基本信息-合同名称", position = 7)
    private String xyContractName;

    /** 合作协议基本信息-合作期限 */
    @ApiModelProperty(value = "合作协议基本信息-合作期限", position = 8)
    private String xyTermLimit;

    /** 合作协议基本信息-当期约定的回款额 */
    @ApiModelProperty(value = "合作协议基本信息-当期约定的回款额", position = 9)
    private Double xyCurrentAppointAmount;

    /** 合作协议基本信息-当期约定的保底额 */
    @ApiModelProperty(value = "合作协议基本信息-当期约定的保底额", position = 10)
    private Double xyCurrentAppointLowAmount;

    /** 合作协议基本信息-管理费费率 */
    @ApiModelProperty(value = "合作协议基本信息-管理费费率", position = 11)
    private String xyMangeRate;

    /** 总包-合同编号 */
    @ApiModelProperty(value = "总包-合同编号", position = 12)
    private String zbContractNo;

    /** 总包-合同名称 */
    @ApiModelProperty(value = "总包-合同名称", position = 13)
    private String zbContractName;

    /** 总包-合同金额 */
    @ApiModelProperty(value = "总包-合同金额", position = 14)
    private Double zbContractAmount;

    /** 总包-合同签订日期 */
    @ApiModelProperty(value = "总包-合同签订日期", position = 15)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date zbSealDate;

    /** 总包-合同归档日期 */
    @ApiModelProperty(value = "总包-合同归档日期", position = 16)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date zbFilingDate;

    /** 总包-开工日期 */
    @ApiModelProperty(value = "总包-开工日期", position = 17)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date zbStartDate;

    /** 总包-完工日期 */
    @ApiModelProperty(value = "总包-完工日期", position = 18)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date zbEndDate;

    /** 总包-客户编码 */
    @ApiModelProperty(value = "总包-客户编码", position = 19)
    private String zbCtmCode;

    /** 总包-客户名称 */
    @ApiModelProperty(value = "总包-客户名称", position = 20)
    private String zbCtmName;

    /** 总包-项目所在县市 */
    @ApiModelProperty(value = "总包-项目所在县市", position = 21)
    private String zbPrjAddress;

    /** 总包-竣工结算金额 */
    @ApiModelProperty(value = "总包-竣工结算金额", position = 22)
    private Double zbCompleteAmount;

    /** 总包-竣工结算资料说明 */
    @ApiModelProperty(value = "总包-竣工结算资料说明", position = 23)
    private String zbCompleteMemo;

    /** 总包-竣工结算资料上传 */
    @ApiModelProperty(value = "总包-竣工结算资料上传", position = 24)
    private String zbCompleteAttach;

    /** 累计开票（含本次）- 累计税额 */
    @ApiModelProperty(value = "累计开票（含本次）- 累计税额", position = 25)
    private Double kpLjsAmount;

    /** 累计开票（含本次）- 累计不含税金额 */
    @ApiModelProperty(value = "累计开票（含本次）- 累计不含税金额", position = 26)
    private Double kpLjnsAmount;

    /** 累计开票（含本次）- 累计金额（含税） */
    @ApiModelProperty(value = "累计开票（含本次）- 累计金额（含税）", position = 27)
    private Double kpLjAmount;

    /** 累计开票（含本次）- 累计回款金额 */
    @ApiModelProperty(value = "累计开票（含本次）- 累计回款金额", position = 28)
    private Double kpLjhkAmount;

    /** 累计开票（含本次）- 本次申请内部结算金额 */
    @ApiModelProperty(value = "累计开票（含本次）- 本次申请内部结算金额", position = 29)
    private Double kpLjjsAmount;

    /** 行政部-意见 */
    @ApiModelProperty(value = "行政部-意见", position = 30)
    private String xzbIdea;

    /** 总师办-注明受理或者出图情况 */
    @ApiModelProperty(value = "总师办-注明受理或者出图情况", position = 31)
    private String zsbCondition;

    /** 总师办-意见 */
    @ApiModelProperty(value = "总师办-意见", position = 32)
    private String zsbIdea;

    /** 总师办-是否有签字费 */
    @ApiModelProperty(value = "总师办-是否有签字费", position = 33)
    private String zsbIsSign;

    /** 总师办-签字费总金额 */
    @ApiModelProperty(value = "总师办-签字费总金额", position = 34)
    private Double zsbSignAmount;

    /** 总师办-附件上传（签字费盖章收款确认单） */
    @ApiModelProperty(value = "总师办-附件上传（签字费盖章收款确认单）", position = 35)
    private String zsbAttach;

    /** 总师办-本次收取签字费金额 */
    @ApiModelProperty(value = "总师办-本次收取签字费金额", position = 36)
    private Double zsbCurrentSignAmount;

    /** 总师办-质量情况或签字费用说明 */
    @ApiModelProperty(value = "总师办-质量情况或签字费用说明", position = 37)
    private String zsbSignCondition;

    /** 财务部-情况说明 */
    @ApiModelProperty(value = "财务部-情况说明", position = 38)
    private String cwbCondition;

    /** 财务部-本次异地预缴税款 */
    @ApiModelProperty(value = "财务部-本次异地预缴税款", position = 39)
    private Double cwbYdThisAmount;

    /** 财务部-累计异地预缴税款 */
    @ApiModelProperty(value = "财务部-累计异地预缴税款", position = 40)
    private Double cwbYdLjAmount;

    /** 财务部-本次应收税差 */
    @ApiModelProperty(value = "财务部-本次应收税差", position = 41)
    private Double cwbScThisAmount;

    /** 财务部-累计应收税差 */
    @ApiModelProperty(value = "财务部-累计应收税差", position = 42)
    private Double cwbScLjAmount;

    /** 财务部-税差计算方式 */
    @ApiModelProperty(value = "财务部-税差计算方式", position = 43)
    private String cwbScType;

    /** SN */
    @ApiModelProperty(value = "SN", position = 44)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 45)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 46)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 52)
    private String status;

    /** 项目款结算单-发票列表 */
    @ApiModelProperty(value = "项目款结算单-发票列表", position = 50)
    private List<BizPrjSettleInvoice> settleInvoiceList;
}
