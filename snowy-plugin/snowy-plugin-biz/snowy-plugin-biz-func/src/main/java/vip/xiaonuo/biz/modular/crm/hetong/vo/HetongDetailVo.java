package vip.xiaonuo.biz.modular.crm.hetong.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yong.ye
 * @date 2023/7/13 13:57
 */
@Getter
@Setter
public class HetongDetailVo {
    /** 主键ID（历史表Hetong.Id） */
    @ApiModelProperty(value = "主键ID（历史表Hetong.Id）", position = 1)
    private String id;

    /** 登记人（历史表Hetong.Opt） */
    @ApiModelProperty(value = "登记人（历史表Hetong.Opt）", position = 2)
    private String opt;

    /** 登记日期（历史表Hetong.RegisterDate） */
    @ApiModelProperty(value = "登记日期（历史表Hetong.RegisterDate）", position = 3)
    private Date registerDate;

    /** 是否归档（历史表Hetong.IsFiling） */
    @ApiModelProperty(value = "是否归档（历史表Hetong.IsFiling）", position = 4)
    private String isFiling;

    /** 工程类别（历史表Hetong.SecondLevel） */
    @ApiModelProperty(value = "工程类别（历史表Hetong.SecondLevel）", position = 5)
    private String secondLevel;

    /** 合同编号（历史表Hetong.ContractNo） */
    @ApiModelProperty(value = "合同编号（历史表Hetong.ContractNo）", position = 6)
    private String contractNo;

    /** 合同名称（历史表Hetong.ProjectName） */
    @ApiModelProperty(value = "合同名称（历史表Hetong.ProjectName）", position = 7)
    private String projectName;

    /** 甲方（历史表Hetong.Counterparty） */
    @ApiModelProperty(value = "甲方（历史表Hetong.Counterparty）", position = 8)
    private String counterparty;

    /** 乙方（历史表Hetong.Secondparty） */
    @ApiModelProperty(value = "乙方（历史表Hetong.Secondparty）", position = 9)
    private String secondparty;

    /** 合作伙伴ID（历史表Hetong.PartnerId） */
    @ApiModelProperty(value = "合作伙伴ID（历史表Hetong.PartnerId）", position = 10)
    private String partnerId;

    /** 合作伙伴类型（历史表Hetong.PartnerType） */
    @ApiModelProperty(value = "合作伙伴类型（历史表Hetong.PartnerType）", position = 11)
    private String partnerType;

    /** 合作伙伴责任单位（历史表Hetong.CompanyOrDepartment） */
    @ApiModelProperty(value = "合作伙伴责任单位（历史表Hetong.CompanyOrDepartment）", position = 12)
    private String companyOrDepartment;

    /** 合作伙伴责任人（历史表Hetong.Owner） */
    @ApiModelProperty(value = "合作伙伴责任人（历史表Hetong.Owner）", position = 13)
    private String owner;

    /** 项目备忘日期（历史表Hetong.MemoDate） */
    @ApiModelProperty(value = "项目备忘日期（历史表Hetong.MemoDate）", position = 14)
    private String memoDate;

    /** 设计委托书日期（历史表Hetong.DesignDate） */
    @ApiModelProperty(value = "设计委托书日期（历史表Hetong.DesignDate）", position = 15)
    private String designDate;

    /** 中标日期（历史表Hetong.BidDate） */
    @ApiModelProperty(value = "中标日期（历史表Hetong.BidDate）", position = 16)
    private Date bidDate;

    /** 合同盖章日期（历史表Hetong.SealDate） */
    @ApiModelProperty(value = "合同盖章日期（历史表Hetong.SealDate）", position = 17)
    private Date sealDate;

    /** 归档日期（历史表Hetong.FilingDate） */
    @ApiModelProperty(value = "归档日期（历史表Hetong.FilingDate）", position = 18)
    private Date filingDate;

    /** 合同金额 - 建科所占金额 */
    @ApiModelProperty(value = "合同金额 - 建科所占金额 ", position = 19)
    private BigDecimal contractAmount;

    /** 投资规模（历史表Hetong.InvestScale） */
    @ApiModelProperty(value = "投资规模（历史表Hetong.InvestScale）", position = 20)
    private String investScale;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 21)
    private String status;

    /** 页次（历史表Hetong.Page） */
    @ApiModelProperty(value = "页次（历史表Hetong.Page）", position = 22)
    private String page;

    /** 原件份数（历史表Hetong.Copys） */
    @ApiModelProperty(value = "原件份数（历史表Hetong.Copys）", position = 23)
    private String copys;

    /** 项目规模描述（历史表Hetong.Description） */
    @ApiModelProperty(value = "项目规模描述（历史表Hetong.Description）", position = 24)
    private String description;

    /** 备注（历史表Hetong.Note） */
    @ApiModelProperty(value = "备注（历史表Hetong.Note）", position = 25)
    private String note;

    /** 中标通知书（历史表Hetong.BidAttaches） */
    @ApiModelProperty(value = "中标通知书（历史表Hetong.BidAttaches）", position = 26)
    private String bidAttaches;

    /** 中标负责人（历史表Hetong.BidOwner） */
    @ApiModelProperty(value = "中标负责人（历史表Hetong.BidOwner）", position = 27)
    private String bidOwner;

    /** 电子合同（历史表Hetong.Attachments） */
    @ApiModelProperty(value = "电子合同（历史表Hetong.Attachments）", position = 28)
    private String attachments;

    /** 出图日期（历史表Hetong.FigureDate） */
    @ApiModelProperty(value = "出图日期（历史表Hetong.FigureDate）", position = 29)
    private Date figureDate;

    /** 出图阶段（历史表Hetong.FigureStage） */
    @ApiModelProperty(value = "出图阶段（历史表Hetong.FigureStage）", position = 30)
    private String figureStage;

    /** 图纸存档（历史表Hetong.FigureAttaches） */
    @ApiModelProperty(value = "图纸存档（历史表Hetong.FigureAttaches）", position = 31)
    private String figureAttaches;

    /** 图审合格证（历史表Hetong.Certificate） */
    @ApiModelProperty(value = "图审合格证（历史表Hetong.Certificate）", position = 32)
    private String certificate;

    /** 竣工日期（历史表Hetong.CompleDate） */
    @ApiModelProperty(value = "竣工日期（历史表Hetong.CompleDate）", position = 33)
    private Date compleDate;

    /** 竣工材料（历史表Hetong.CompleData） */
    @ApiModelProperty(value = "竣工材料（历史表Hetong.CompleData）", position = 34)
    private String compleData;

    /** 原件存放位置（历史表Hetong.Location） */
    @ApiModelProperty(value = "原件存放位置（历史表Hetong.Location）", position = 35)
    private String location;

    /** 核定应收金额（历史表Hetong.ShouldAmount） */
    @ApiModelProperty(value = "核定应收金额（历史表Hetong.ShouldAmount）", position = 36)
    private BigDecimal shouldAmount;

    /** 已收金额（历史表Hetong.HaveAmount） */
    @ApiModelProperty(value = "已收金额（历史表Hetong.HaveAmount）", position = 37)
    private BigDecimal haveAmount;

    /** 已开票金额（历史表Hetong.InvoiceAmount） */
    @ApiModelProperty(value = "已开票金额（历史表Hetong.InvoiceAmount）", position = 38)
    private BigDecimal invoiceAmount;

    /** 其他附件（历史表Hetong.AttachmentsNote） */
    @ApiModelProperty(value = "其他附件（历史表Hetong.AttachmentsNote）", position = 39)
    private String attachmentsNote;

    /** 项目负责人（历史表Hetong.ProjectLeader） */
    @ApiModelProperty(value = "项目负责人（历史表Hetong.ProjectLeader）", position = 40)
    private String projectLeader;

    /** 管理费（历史表Hetong.ManageFee） */
    @ApiModelProperty(value = "管理费（历史表Hetong.ManageFee）", position = 41)
    private BigDecimal manageFee;

    /** 合同签订人（历史表Hetong.Register） */
    @ApiModelProperty(value = "合同签订人（历史表Hetong.Register）", position = 42)
    private String register;

    /** 删除状态标记 */
    @ApiModelProperty(value = "删除状态标记", position = 43)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 44)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 45)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 46)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 47)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 丙方 */
    @ApiModelProperty(value = "丙方", position = 48)
    private String partyc;

    /** 合同状态：对应历史表的status */
    @ApiModelProperty(value = "合同状态：对应历史表的status", position = 50)
    private String htstatus;

    /** 零星单位名称 */
    @ApiModelProperty(value = "零星单位名称", position = 53)
    private String unitsName;

    /** 模型id */
    @ApiModelProperty(value = "模型id", position = 52)
    private String modelId;

    /** 流程实例id */
    @ApiModelProperty(value = "流程实例id", position = 53)
    private String processInstanceId;

    /** 合同总金额 */
    @ApiModelProperty(value = "合同总金额", position = 63)
    private BigDecimal sumAmount;

    /** 用印审批-流程实例id，多个英文逗号分割 */
    @ApiModelProperty(value = "用印审批-流程实例id，多个英文逗号分割", position = 64)
    private String sealProcessInstanceId;




    /** 立项-主键ID */
    @ApiModelProperty(value = "立项-主键ID", position = 66)
    private String bizProjectId;

    /** 立项-名称 */
    @ApiModelProperty(value = "立项-名称", position = 66)
    private String bizProjectName;

    /** 立项-编号 */
    @ApiModelProperty(value = "立项-编号", position = 66)
    private String bizProjectSn;

    /** 立项-是否EPC */
    @ApiModelProperty(value = "立项-是否EPC", position = 66)
    private String isEpc;

    /** 立项-是否EPC+F */
    @ApiModelProperty(value = "立项-是否EPC+F", position = 66)
    private String isEpcf;

    /** 立项-是否牵头 */
    @ApiModelProperty(value = "立项-是否牵头", position = 66)
    private String isLead;

    /** 立项-项目类型 */
    @ApiModelProperty(value = "立项-项目类型", position = 66)
    private String bizProjectType;

    /** 立项-行业分类 */
    @ApiModelProperty(value = "立项-行业分类", position = 66)
    private String industryClassification;

    /** 立项-是否联合体 */
    @ApiModelProperty(value = "立项-是否联合体", position = 66)
    private String isConsortium;

    /** 立项-所属大区 */
    @ApiModelProperty(value = "立项-所属大区", position = 66)
    private String region;

    /** 立项-建设地点 */
    @ApiModelProperty(value = "立项-建设地点", position = 66)
    private String bizProjectLocation;

    /** 申请人单位 */
    @ApiModelProperty(value = "申请人单位", position = 70)
    private String htMainType;



    /** 立项 - 经营部门 */
    @ApiModelProperty(value = "立项 - 经营部门", position = 200)
    private String manageDept;

    /** 合同类型 */
    @ApiModelProperty(value = "合同类型", position = 210)
    private String contractType;

    /** 质量保证金 */
    @ApiModelProperty(value = "质量保证金", position = 211)
    private BigDecimal qualityBondAmount;

    /** 合同生效日期 */
    @ApiModelProperty(value = "合同生效日期（格式：yyyy-MM-dd）", position = 212)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date effectiveDate;


    /** 项目来源 （BizProject.head）*/
    @ApiModelProperty(value = "项目来源（BizProject.head）", position = 203)
    private String bizPrjHead;
}
