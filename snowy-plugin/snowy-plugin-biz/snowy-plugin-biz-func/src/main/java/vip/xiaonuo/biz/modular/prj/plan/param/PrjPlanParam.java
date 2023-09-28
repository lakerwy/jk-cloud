package vip.xiaonuo.biz.modular.prj.plan.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import vip.xiaonuo.biz.modular.prj.plan.entity.BizPrjPlanCollection;
import vip.xiaonuo.biz.modular.prj.plan.entity.BizPrjPlanInvoice;

import java.util.Date;
import java.util.List;

/**
 * @author yong.ye
 * @date 2023/9/28 14:25
 */

@Getter
@Setter
public class PrjPlanParam {

    /** 主键Id */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 合作方/分公司编码 */
    @ApiModelProperty(value = "合作方/分公司编码", position = 2)
    private String hzfCode;

    /** 合作方/分公司名称 */
    @ApiModelProperty(value = "合作方/分公司名称", position = 3)
    private String hzfName;

//    /** 申请日期 */
//    @ApiModelProperty(value = "申请日期", position = 4)
//    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    private Date applyDate;

    /** 计划付款单编号 */
    @ApiModelProperty(value = "计划付款单编号", position = 5)
    private String planNo;

    /** 合同编号 */
    @ApiModelProperty(value = "合同编号", position = 6)
    private String contractNo;

    /** 合同金额 */
    @ApiModelProperty(value = "合同金额", position = 7)
    private Double contractAmount;

    /** 合同名称 */
    @ApiModelProperty(value = "合同名称", position = 8)
    private String contractName;

    /** 合同签订日期 */
    @ApiModelProperty(value = "合同签订日期", position = 9)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date sealDate;

    /** 合同归档日期 */
    @ApiModelProperty(value = "合同归档日期", position = 10)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date filingDate;

    /** 开工日期 */
    @ApiModelProperty(value = "开工日期", position = 11)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    /** 完工日期 */
    @ApiModelProperty(value = "完工日期", position = 12)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    /** 总包合同对分包的限制条款 */
    @ApiModelProperty(value = "总包合同对分包的限制条款", position = 13)
    private String clause;

    /** 客户编码 */
    @ApiModelProperty(value = "客户编码", position = 14)
    private String ctmCode;

    /** 客户名称 */
    @ApiModelProperty(value = "客户名称", position = 15)
    private String ctmName;

    /** 合作期限 */
    @ApiModelProperty(value = "合作期限", position = 16)
    private String termLimit;

    /** 项目所在县市 */
    @ApiModelProperty(value = "项目所在县市", position = 17)
    private String prjAddress;

    /** 竣工结算金额 */
    @ApiModelProperty(value = "竣工结算金额", position = 18)
    private Double completeAmount;

    /** 竣工结算资料说明 */
    @ApiModelProperty(value = "竣工结算资料说明", position = 19)
    private String completeMemo;

    /** 竣工结算资料上传 */
    @ApiModelProperty(value = "竣工结算资料上传", position = 20)
    private String completeAttach;

    /** 合同归档率 */
    @ApiModelProperty(value = "合同归档率", position = 21)
    private String filingRate;

    /** 分包合同编号 */
    @ApiModelProperty(value = "分包合同编号", position = 22)
    private String subContractNo;

    /** 分包合同名称 */
    @ApiModelProperty(value = "分包合同名称", position = 23)
    private String subContractName;

    /** 分包合同金额 */
    @ApiModelProperty(value = "分包合同金额", position = 24)
    private Double subContractAmount;

    /** 收款单位 */
    @ApiModelProperty(value = "收款单位", position = 25)
    private String collectingUnit;

    /** 累计提供发票（含本次）- 开票金额(不含税) */
    @ApiModelProperty(value = "累计提供发票（含本次）- 开票金额(不含税)", position = 26)
    private Double fpKpAmount;

    /** 累计提供发票（含本次）- 税额(专票) */
    @ApiModelProperty(value = "累计提供发票（含本次）- 税额(专票)", position = 27)
    private Double fpSeAmount;

    /** 累计提供发票（含本次）- 累计发票金额 */
    @ApiModelProperty(value = "累计提供发票（含本次）- 累计发票金额", position = 28)
    private Double fpLjAmount;

    /** 累计提供发票（含本次）- 累计支付金额 */
    @ApiModelProperty(value = "累计提供发票（含本次）- 累计支付金额", position = 29)
    private Double fpZfAmount;

    /** 累计提供发票（含本次）- 开票未付金额 */
    @ApiModelProperty(value = "累计提供发票（含本次）- 开票未付金额", position = 30)
    private Double fpWfAmount;

    /** 工程总承包事业部意见 */
    @ApiModelProperty(value = "工程总承包事业部意见", position = 31)
    private String gczcIdea;

    /** 财务审定付款金额 */
    @ApiModelProperty(value = "财务审定付款金额", position = 32)
    private Double cwFkAmount;

    /** 资产财务中心意见-审定意见 */
    @ApiModelProperty(value = "资产财务中心意见-审定意见", position = 33)
    private String cwIdea;

    /** SN */
    @ApiModelProperty(value = "SN", position = 34)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 35)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 36)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 42)
    private String status;

//    /** 入参信息 */
//    @ApiModelProperty(value = "入参信息", position = 43)
//    private String extJson;

//    /** 数据权限控制字段 */
//    @ApiModelProperty(value = "数据权限控制字段", position = 44)
//    private String dataScopeId;

    /** 项目计划付款单-本次提供发票列表 */
    @ApiModelProperty(value = "项目计划付款单-本次提供发票列表", position = 50)
    private List<BizPrjPlanInvoice> planInvoiceList;

    /** 项目计划付款单-收款单位列表 */
    @ApiModelProperty(value = "项目计划付款单-收款单位列表", position = 50)
    private List<BizPrjPlanCollection> planCollectionList;

}
