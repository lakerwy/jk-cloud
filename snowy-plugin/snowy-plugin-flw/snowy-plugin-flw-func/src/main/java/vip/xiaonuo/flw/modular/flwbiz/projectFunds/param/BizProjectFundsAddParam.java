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
package vip.xiaonuo.flw.modular.flwbiz.projectFunds.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务表-工程款拨付单添加参数
 *
 * @author mayike
 * @date  2023/06/19 17:26
 **/
@Getter
@Setter
public class BizProjectFundsAddParam {

    /** sn值 */
    @ApiModelProperty(value = "sn值", position = 2)
    private String sn;

    /** 关联合同编号 */
    @ApiModelProperty(value = "关联合同编号", position = 3)
    private String chance;

    /** 项目名称 */
    @ApiModelProperty(value = "项目名称", position = 4)
    private String name;

    /** 合同金额 */
    @ApiModelProperty(value = "合同金额", position = 5)
    private String amount;

    /** 相关费用 */
    @ApiModelProperty(value = "相关费用", position = 6)
    private String costs;

    /** 项目类型 */
    @ApiModelProperty(value = "项目类型", position = 7)
    private String type;

    /** 项目来源 */
    @ApiModelProperty(value = "项目来源", position = 8)
    private String head;

    /** 项目属性 */
    @ApiModelProperty(value = "项目属性", position = 9)
    private String source;

    /** 立项编号 */
    @ApiModelProperty(value = "立项编号", position = 10)
    private String approvalNo;

    /** 项目介绍 */
    @ApiModelProperty(value = "项目介绍", position = 11)
    private String introduce;

    /** 附件上传 */
    @ApiModelProperty(value = "附件上传", position = 12)
    private String attachment;

    /** 开标日期 */
    @ApiModelProperty(value = "开标日期", position = 13)
    private String startDate;

    /** 项目负责人 */
    @ApiModelProperty(value = "项目负责人", position = 14)
    private String projectHeader;

    /** 部门 */
    @ApiModelProperty(value = "部门", position = 15)
    private String dept;

    /** 责任人 */
    @ApiModelProperty(value = "责任人", position = 16)
    private String leader;

    /** 区域负责人 */
    @ApiModelProperty(value = "区域负责人", position = 17)
    private String areaLeader;

    /** 是否主投 */
    @ApiModelProperty(value = "是否主投", position = 18)
    private String isMain;

    /** 投标保证金 */
    @ApiModelProperty(value = "投标保证金", position = 19)
    private String bond;

    /** 投标限价 */
    @ApiModelProperty(value = "投标限价", position = 20)
    private String priceLimit;

    /** 招标代理机构 */
    @ApiModelProperty(value = "招标代理机构", position = 21)
    private String biddingAgency;

    /** 联系人 */
    @ApiModelProperty(value = "联系人", position = 22)
    private String biddingHeader;

    /** 联系电话 */
    @ApiModelProperty(value = "联系电话", position = 23)
    private String biddingPhone;

    /** 建设单位 */
    @ApiModelProperty(value = "建设单位", position = 24)
    private String project;

    /** 联系人 */
    @ApiModelProperty(value = "联系人", position = 25)
    private String projectContacts;

    /** 联系电话 */
    @ApiModelProperty(value = "联系电话", position = 26)
    private String projectPhone;

    /** 设计单位 */
    @ApiModelProperty(value = "设计单位", position = 27)
    private String designUnit;

    /** 监理单位 */
    @ApiModelProperty(value = "监理单位", position = 28)
    private String controlUnit;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 29)
    private String note;

    /** 计划附件 */
    @ApiModelProperty(value = "计划附件", position = 30)
    private String subAttachment;

    /** 合同编号 */
    @ApiModelProperty(value = "合同编号", position = 31)
    private String contractNo;

    /** 款项性质 */
    @ApiModelProperty(value = "款项性质", position = 32)
    private String costType;

    /** 回款额 */
    @ApiModelProperty(value = "回款额", position = 33)
    private String repayment;

    /** 税金 */
    @ApiModelProperty(value = "税金", position = 34)
    private String taxes;

    /** 管理费 */
    @ApiModelProperty(value = "管理费", position = 35)
    private String managementFees;

    /** 划拨金额 */
    @ApiModelProperty(value = "划拨金额", position = 36)
    private String transferAmount;

    /** 开户银行 */
    @ApiModelProperty(value = "开户银行", position = 37)
    private String bankName;

    /** 账号 */
    @ApiModelProperty(value = "账号", position = 38)
    private String bankNo;

    /** 收款单位 */
    @ApiModelProperty(value = "收款单位", position = 39)
    private String payeeDept;

    /** 领款人 */
    @ApiModelProperty(value = "领款人", position = 40)
    private String payeeName;

    /** 业主名称 */
    @ApiModelProperty(value = "业主名称", position = 41)
    private String projectLeader;

    /** 累计收款 */
    @ApiModelProperty(value = "累计收款", position = 42)
    private String haveAmount;

    /** 状态 */
    @ApiModelProperty(value = "状态", position = 43)
    private String status;

    /** 模型id */
    @ApiModelProperty(value = "模型id", required = false, position = 44)
    private String modelId;

}
