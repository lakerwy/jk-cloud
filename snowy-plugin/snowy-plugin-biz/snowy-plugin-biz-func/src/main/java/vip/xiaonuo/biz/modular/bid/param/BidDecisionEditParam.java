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
package vip.xiaonuo.biz.modular.bid.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 投标决策 - 申请流程编辑参数
 *
 * @author yong.ye
 * @date  2024/06/21 10:30
 **/
@Getter
@Setter
public class BidDecisionEditParam {

    /** 申请部门Id */
    @ApiModelProperty(value = "申请部门Id", position = 1)
    private String deptId;

    /** 申请部门 */
    @ApiModelProperty(value = "申请部门", position = 2)
    private String dept;

    /** 项目承接部门 */
    @ApiModelProperty(value = "项目承接部门", position = 3)
    private String acceptDept;

    /** 主键ID */
    @ApiModelProperty(value = "主键ID",position = 1)
    private String id;

    /** 立项Id：来源于立项 */
    @ApiModelProperty(value = "立项Id：来源于立项", position = 4)
    private String prjId;

    /** 项目名称：来源于立项 */
    @ApiModelProperty(value = "项目名称：来源于立项", position = 5)
    private String prjName;

    /** 项目来源 */
    @ApiModelProperty(value = "项目来源", position = 5)
    private String prjSource;

    /** 立项申请信息：来源于立项 */
    @ApiModelProperty(value = "立项申请信息：来源于立项", position = 6)
    private String prjSn;

    /** 立项申请人：来源于立项 */
    @ApiModelProperty(value = "立项申请人：来源于立项", position = 7)
    private String prjApplyUser;

    /** 顾问联系人(自营)|合作方联系人(零星合作)|分公司联系人(分公司) */
    @ApiModelProperty(value = "顾问联系人(自营)|合作方联系人(零星合作)|分公司联系人(分公司)", position = 6)
    private String contractor;

    /** 项目承接类型(自营|零星合作|分公司) */
    @ApiModelProperty(value = "项目承接类型(自营|零星合作|分公司)", position = 7)
    private String contractType;

    /** 项目承接范围 */
    @ApiModelProperty(value = "项目承接范围", position = 8)
    private String contractRange;

    /** 项目管理模式 */
    @ApiModelProperty(value = "项目管理模式", position = 9)
    private String manageType;

    /** 经营费经手人 */
    @ApiModelProperty(value = "经营费经手人", position = 10)
    private String expenseManager;

    /** 是否专门针对集团业绩/战略发展需要：是|否 */
    @ApiModelProperty(value = "是否专门针对集团业绩/战略发展需要：是|否", position = 11)
    private String isTag;

    /** 是否专门针对集团业绩/战略发展需要 ：针对项目 */
    @ApiModelProperty(value = "是否专门针对集团业绩/战略发展需要 ：针对项目", position = 12)
    private String tag;

    /** 承接工作内容 */
    @ApiModelProperty(value = "承接工作内容", position = 13)
    private String jobContent;

    /** 是否我方牵头：是|否 */
    @ApiModelProperty(value = "是否我方牵头：是|否", position = 13)
    private String isHead;

    /** 是否我方牵头：职责 */
    @ApiModelProperty(value = "是否我方牵头：职责", position = 14)
    private String headInfo;

    /** 牵头方单位 */
    @ApiModelProperty(value = "牵头方单位", position = 15)
    private String headUnit;

    /** 最高投标限价|最低下浮费率：类型 */
    @ApiModelProperty(value = "最高投标限价|最低下浮费率：类型", position = 16)
    private String bidType;

    /** 最高投标限价|最低下浮费率：数值 */
    @ApiModelProperty(value = "最高投标限价|最低下浮费率：数值", position = 17)
    private String bidPrice;

    /** 投标保证金（元，含税） */
    @ApiModelProperty(value = "投标保证金（元，含税）", position = 18)
    private String bidBond;

    /** 履约保证金/担保：类型 */
    @ApiModelProperty(value = "履约保证金/担保：类型", position = 19)
    private String suretyType;

    /** 履约保证金/担保：金额 */
    @ApiModelProperty(value = "履约保证金/担保：金额", position = 20)
    private String suretyAmount;

    /** 开标日期（投标截止日期） */
    @ApiModelProperty(value = "开标日期（投标截止日期）", position = 21)
    private String bidDate;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 22)
    private String attach;

    /** 项目生产分工JSON */
    @ApiModelProperty(value = "项目生产分工JSON", position = 22)
    private String planDivideJson;

    /** 项目产值划分及成本测算JSON */
    @ApiModelProperty(value = "项目产值划分及成本测算JSON", position = 23)
    private String esSummaryJson;

    /** 进度计划JSON */
    @ApiModelProperty(value = "进度计划JSON", position = 24)
    private String esDetailJson;

    /** 招采计划JSON */
    @ApiModelProperty(value = "招采计划JSON", position = 25)
    private String riskJson;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 26)
    private String memo;

    /** SN流水号 */
    @ApiModelProperty(value = "SN流水号", position = 34)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 35)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 36)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 37)
    private String status;

    /** 入参信息 */
    @ApiModelProperty(value = "入参信息", position = 38)
    private String extJson;

    /**************************** 投标决策二期 *********************************/

    /** 责任上缴比例 */
    @ApiModelProperty(value = "责任上缴比例", position = 44)
    private String rate;

}
