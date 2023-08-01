package vip.xiaonuo.biz.modular.report.result;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yong.ye
 * @date 2023/7/26 16:16
 */

@Getter
@Setter
public class HTFilingPageResult {
    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

//    /** 登记人 */
//    @ApiModelProperty(value = "登记人", position = 2)
//    private String opt;
//
//    /** 登记日期 */
//    @ApiModelProperty(value = "登记日期", position = 3)
//    private Date registerDate;

    /** 是否归档 */
    @ApiModelProperty(value = "是否归档", position = 4)
    private String isFiling;

    /** 工程类别 */
    @ApiModelProperty(value = "工程类别", position = 5)
    private String secondLevel;

    /** 合同编号 */
    @ApiModelProperty(value = "合同编号", position = 6)
    private String contractNo;

    /** 合同名称 */
    @ApiModelProperty(value = "合同名称", position = 7)
    private String projectName;

    /** 甲方 */
    @ApiModelProperty(value = "甲方", position = 8)
    private String counterparty;

    /** 乙方 */
    @ApiModelProperty(value = "乙方", position = 9)
    private String secondparty;

    /** 合作伙伴ID */
    @ApiModelProperty(value = "合作伙伴ID", position = 10)
    private String partnerId;

//    /** 合作伙伴类型 */
//    @ApiModelProperty(value = "合作伙伴类型", position = 11)
//    private String partnerType;

    /** 合作伙伴责任单位 */
    @ApiModelProperty(value = "合作伙伴责任单位", position = 12)
    private String companyOrDepartment;

//    /** 合作伙伴责任人 */
//    @ApiModelProperty(value = "合作伙伴责任人", position = 13)
//    private String owner;
//
//    /** 项目备忘日期（历史表Hetong.MemoDate） */
//    @ApiModelProperty(value = "项目备忘日期", position = 14)
//    private String memoDate;
//
//    /** 设计委托书日期 */
//    @ApiModelProperty(value = "设计委托书日期", position = 15)
//    private String designDate;
//
//    /** 中标日期 */
//    @ApiModelProperty(value = "中标日期", position = 16)
//    private Date bidDate;

    /** 合同盖章日期 */
    @ApiModelProperty(value = "合同盖章日期", position = 17)
    private Date sealDate;

    /** 归档日期 */
    @ApiModelProperty(value = "归档日期", position = 18)
    private Date filingDate;

    /** 合同金额 */
    @ApiModelProperty(value = "合同金额", position = 19)
    private BigDecimal contractAmount;
//
//    /** 投资规模 */
//    @ApiModelProperty(value = "投资规模", position = 20)
//    private String investScale;

//    /** 流程状态（0 - 已提交  1- 已审批） */
//    @ApiModelProperty(value = "流程状态（0 - 已提交  1- 已审批）", position = 21)
//    private String status;
//
//    /** 页次 */
//    @ApiModelProperty(value = "页次", position = 22)
//    private String page;
//
//    /** 原件份数 */
//    @ApiModelProperty(value = "原件份数", position = 23)
//    private String copys;
//
//    /** 项目规模描述 */
//    @ApiModelProperty(value = "项目规模描述", position = 24)
//    private String description;
//
//    /** 备注 */
//    @ApiModelProperty(value = "备注", position = 25)
//    private String note;
//
//    /** 中标通知书 */
//    @ApiModelProperty(value = "中标通知书", position = 26)
//    private String bidAttaches;
//
//    /** 中标负责人 */
//    @ApiModelProperty(value = "中标负责人", position = 27)
//    private String bidOwner;
//
//    /** 电子合同 */
//    @ApiModelProperty(value = "电子合同", position = 28)
//    private String attachments;
//
//    /** 出图日期 */
//    @ApiModelProperty(value = "出图日期", position = 29)
//    private Date figureDate;
//
//    /** 出图阶段 */
//    @ApiModelProperty(value = "出图阶段", position = 30)
//    private String figureStage;
//
//    /** 图纸存档 */
//    @ApiModelProperty(value = "图纸存档", position = 31)
//    private String figureAttaches;
//
//    /** 图审合格证 */
//    @ApiModelProperty(value = "图审合格证", position = 32)
//    private String certificate;
//
//    /** 竣工日期 */
//    @ApiModelProperty(value = "竣工日期", position = 33)
//    private Date compleDate;
//
//    /** 竣工材料 */
//    @ApiModelProperty(value = "竣工材料", position = 34)
//    private String compleData;
//
//    /** 原件存放位置 */
//    @ApiModelProperty(value = "原件存放位置", position = 35)
//    private String location;
//
//    /** 核定应收金额 */
//    @ApiModelProperty(value = "核定应收金额", position = 36)
//    private BigDecimal shouldAmount;
//
//    /** 已收金额 */
//    @ApiModelProperty(value = "已收金额", position = 37)
//    private BigDecimal haveAmount;
//
//    /** 已开票金额 */
//    @ApiModelProperty(value = "已开票金额", position = 38)
//    private BigDecimal invoiceAmount;
//
//    /** 其他附件 */
//    @ApiModelProperty(value = "其他附件", position = 39)
//    private String attachmentsNote;
//
//    /** 项目负责人 */
//    @ApiModelProperty(value = "项目负责人", position = 40)
//    private String projectLeader;
//
//    /** 管理费 */
//    @ApiModelProperty(value = "管理费", position = 41)
//    private BigDecimal manageFee;
//
//    /** 合同签订人*/
//    @ApiModelProperty(value = "合同签订人", position = 42)
//    private String register;

//    /** 删除状态标记 */
//    @ApiModelProperty(value = "删除状态标记", position = 43)
//    @TableLogic
//    @TableField(fill = FieldFill.INSERT)
//    private String deleteFlag;
//
//    /** 创建时间 */
//    @ApiModelProperty(value = "创建时间", position = 44)
//    @TableField(fill = FieldFill.INSERT)
//    private Date createTime;
//
//    /** 创建人 */
//    @ApiModelProperty(value = "创建人", position = 45)
//    @TableField(fill = FieldFill.INSERT)
//    private String createUser;
//
//    /** 更新时间 */
//    @ApiModelProperty(value = "更新时间", position = 46)
//    @TableField(fill = FieldFill.UPDATE)
//    private Date updateTime;
//
//    /** 更新人 */
//    @ApiModelProperty(value = "更新人", position = 47)
//    @TableField(fill = FieldFill.UPDATE)
//    private String updateUser;

//    /** 丙方 */
//    @ApiModelProperty(value = "丙方", position = 48)
//    private String partyc;
//
//    /** 立项-主键ID */
//    @ApiModelProperty(value = "立项-主键ID", position = 49)
//    private String bizProjectId;
//
//    /** 合同状态：对应历史表的status */
//    @ApiModelProperty(value = "合同状态：对应历史表的status", position = 50)
//    private String htstatus;
//
//    /** 零星单位名称 */
//    @ApiModelProperty(value = "零星单位名称", position = 51)
//    private String unitsName;


    /** 所属责任单位： 合同总数 */
    @ApiModelProperty(value = "所属责任单位：合同总数", position = 52)
    private Integer totalNum;

    /** 所属责任单位：归档数 */
    @ApiModelProperty(value = "所属责任单位：归档数", position = 53)
    private Integer filingNum;

    /** 所属责任单位：未归档数 */
    @ApiModelProperty(value = "所属责任单位：未归档数", position = 54)
    private Integer unFilingNum;

    /** 所属责任单位：归档率 */
    @ApiModelProperty(value = "所属责任单位：归档率", position = 55)
    private Double filingRate;

    /** 所属责任单位：超过3个月未归档数 */
    @ApiModelProperty(value = "所属责任单位：超过3个月未归档数", position = 56)
    private Integer threeUnFilingNum;
}
