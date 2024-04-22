/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.biz.modular.plan.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 合约规划 - 申请流程实体
 *
 * @author yong.ye
 * @date  2024/04/22 11:41
 **/
@Getter
@Setter
@TableName("BIZ_CONTRACT_PLAN")
public class ContractPlan {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 项目对接人 */
    @ApiModelProperty(value = "项目对接人", position = 2)
    private String prjContact;

    /** 立项主体 */
    @ApiModelProperty(value = "立项主体", position = 3)
    private String prjSubject;

    /** 招标人（建设单位） */
    @ApiModelProperty(value = "招标人（建设单位）", position = 4)
    private String bidder;

    /** 招标联系人 */
    @ApiModelProperty(value = "招标联系人", position = 5)
    private String biddContact;

    /** 招标联系人电话 */
    @ApiModelProperty(value = "招标联系人电话", position = 6)
    private String biddContactPhone;

    /** 招标代理机构 */
    @ApiModelProperty(value = "招标代理机构", position = 7)
    private String biddUnit;

    /** 招标代理机构联系人 */
    @ApiModelProperty(value = "招标代理机构联系人", position = 8)
    private String biddUnitContact;

    /** 招标代理机构联系人电话 */
    @ApiModelProperty(value = "招标代理机构联系人电话", position = 9)
    private String biddUnitContactPhone;

    /** 立项Id */
    @ApiModelProperty(value = "立项Id", position = 10)
    private String prjId;

    /** 项目名称(通过审批的立项) */
    @ApiModelProperty(value = "项目名称(通过审批的立项)", position = 11)
    private String prjName;

    /** 招标方式 */
    @ApiModelProperty(value = "招标方式", position = 12)
    private String biddType;

    /** 项目资金来源及落实情况 */
    @ApiModelProperty(value = "项目资金来源及落实情况", position = 13)
    private String prjFundSource;

    /** 项目内容 */
    @ApiModelProperty(value = "项目内容", position = 14)
    private String prjContent;

    /** 项目管理模式 */
    @ApiModelProperty(value = "项目管理模式", position = 15)
    private String prjManageType;

    /** 招标范围 */
    @ApiModelProperty(value = "招标范围", position = 16)
    private String biddRange;

    /** 计划工期 */
    @ApiModelProperty(value = "计划工期", position = 17)
    private String planDuration;

    /** 质量标准 */
    @ApiModelProperty(value = "质量标准", position = 18)
    private String qualityStandard;

    /** 是否接受联合体投标 */
    @ApiModelProperty(value = "是否接受联合体投标", position = 19)
    private String isAccept;

    /** 建设地点 */
    @ApiModelProperty(value = "建设地点", position = 20)
    private String constructAddress;

    /** 建设规模 */
    @ApiModelProperty(value = "建设规模", position = 21)
    private String constructScale;

    /** 所属大区 */
    @ApiModelProperty(value = "所属大区", position = 22)
    private String belongRegion;

    /** 最高投标限价或其计算方法/最低下浮费率 */
    @ApiModelProperty(value = "最高投标限价或其计算方法/最低下浮费率", position = 23)
    private String bidLimit;

    /** 投标保证金 */
    @ApiModelProperty(value = "投标保证金", position = 24)
    private String bidSecurity;

    /** 履约担保 */
    @ApiModelProperty(value = "履约担保", position = 25)
    private String guarantee;

    /** 开标日期（投标截止时间） */
    @ApiModelProperty(value = "开标日期（投标截止时间）", position = 26)
    private String bidOpenTime;

    /** 开标地点 */
    @ApiModelProperty(value = "开标地点", position = 27)
    private String bidOpenAddress;

    /** 评标结果公示媒介/平台 */
    @ApiModelProperty(value = "评标结果公示媒介/平台", position = 28)
    private String publicPlatform;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 29)
    private String attach;

    /** 是否专门针对集团业绩/战略发展需要 */
    @ApiModelProperty(value = "是否专门针对集团业绩/战略发展需要", position = 30)
    private String isSpecial;

    /** 经营费经手人 */
    @ApiModelProperty(value = "经营费经手人", position = 31)
    private String expenseHandler;

    /** 投标主体 */
    @ApiModelProperty(value = "投标主体", position = 32)
    private String bidSubject;

    /** 是否主投 */
    @ApiModelProperty(value = "是否主投", position = 33)
    private String isMain;

    /** 我方联系人 */
    @ApiModelProperty(value = "我方联系人", position = 34)
    private String selfContact;

    /** 集团投标专员 */
    @ApiModelProperty(value = "集团投标专员", position = 35)
    private String jtBidSale;

    /** 合作方联系人 */
    @ApiModelProperty(value = "合作方联系人", position = 36)
    private String hzContact;

    /** 联系电话 */
    @ApiModelProperty(value = "联系电话", position = 37)
    private String hzContactPhone;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 38)
    private String memo;

    /** 项目总产值-收入：产值划分(含税，元） */
    @ApiModelProperty(value = "项目总产值-收入：产值划分(含税，元）", position = 39)
    private BigDecimal prjOutput;

    /** 项目总产值-收入：产值划分比例（%） */
    @ApiModelProperty(value = "项目总产值-收入：产值划分比例（%）", position = 40)
    private Float prjOutputRate;

    /** 项目总产值-成本-费用金额（含税，元） */
    @ApiModelProperty(value = "项目总产值-成本-费用金额（含税，元）", position = 41)
    private BigDecimal prjExpense;

    /** 项目总产值-成本-费用占比（%） */
    @ApiModelProperty(value = "项目总产值-成本-费用占比（%）", position = 42)
    private Float prjExpenseRate;

    /** 项目总产值-效益：利润额(元） */
    @ApiModelProperty(value = "项目总产值-效益：利润额(元）", position = 43)
    private BigDecimal prjProfit;

    /** 项目总产值-效益：利润率（%） */
    @ApiModelProperty(value = "项目总产值-效益：利润率（%）", position = 44)
    private Float prjProfitRate;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 45)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 46)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 47)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 48)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 49)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 50)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 51)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;

    /** SN流水号 */
    @ApiModelProperty(value = "SN流水号", position = 52)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 53)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 54)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 55)
    private String status;

    /** 入参信息 */
    @ApiModelProperty(value = "入参信息", position = 56)
    private String extJson;
}
