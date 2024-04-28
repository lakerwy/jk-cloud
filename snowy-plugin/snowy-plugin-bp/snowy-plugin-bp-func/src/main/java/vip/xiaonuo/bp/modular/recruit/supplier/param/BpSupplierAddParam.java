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
package vip.xiaonuo.bp.modular.recruit.supplier.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.bp.modular.recruit.bankinformation.param.BpBankInformationAddParam;
import vip.xiaonuo.bp.modular.recruit.businessscope.param.BpBusinessScopeAddParam;
import vip.xiaonuo.bp.modular.recruit.finance.param.BpFinanceAddParam;
import vip.xiaonuo.bp.modular.recruit.relation.param.BpRelationAddParam;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * 供应商基本信息表添加参数
 *
 * @author hxw
 * @date  2024/04/17 15:18
 **/
@Getter
@Setter
public class BpSupplierAddParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID")
    private String id;

    /** 营业执照扫描件图像 */
    @ApiModelProperty(value = "营业执照扫描件图像")
    @NotBlank(message = "营业执照扫描件图像不能为空")
    private String businessLicenseImage;

    /** 法人名片图像 */
    @ApiModelProperty(value = "法人名片图像")
    private String legalPersonImage;

    /** 法定代表人 */
    @ApiModelProperty(value = "法定代表人")
    @NotBlank(message = "法定代表人不能为空")
    private String legalRepresentative;

    /** 法人身份证号 */
    @ApiModelProperty(value = "法人身份证")
    @NotBlank(message = "法人身份证不能为空")
    private String idNumber;

    /** 注册资本(元) */
    @ApiModelProperty(value = "注册资本(元)")
    @NotNull(message = "注册资本不能为空")
    private BigDecimal registeredCapital;

    /** 公司logo */
    @ApiModelProperty(value = "公司logo")
    private String companylogo;

    /** 成立日期 */
    @ApiModelProperty(value = "成立日期")
    @NotBlank(message = "成立日期不能为空")
    private String establishTime;

    /** 公司邮箱 */
    @ApiModelProperty(value = "公司邮箱")
    private String mailbox;

    /** 公司名称 */
    @ApiModelProperty(value = "公司名称")
    @NotBlank(message = "公司名称不能为空")
    private String companyName;

    /** 企业组织形式 */
    @ApiModelProperty(value = "企业组织形式")
    @NotBlank(message = "企业组织形式不能为空")
    private String companyType;

    /** 统一社会信用代码 */
    @ApiModelProperty(value = "统一社会信用代码")
    @NotBlank(message = "统一社会信用代码不能为空")
    private String corpCode;

    /** 住所省市区 */
    @ApiModelProperty(value = "住所省市区")
    @NotBlank(message = "住所省市区不能为空")
    private String home;

    /** 住所详细地址 */
    @ApiModelProperty(value = "住所详细地址")
    private String homeAddress;

    /** 办公地址省市区 */
    @ApiModelProperty(value = "办公地址省市区")
    @NotBlank(message = "办公地址省市区不能为空")
    private String officeAddress;

    /** 办公详细地址 */
    @ApiModelProperty(value = "办公详细地址")
    private String workAddress;

    /** 服务地址省 */
    @ApiModelProperty(value = "服务地址省")
    private String serviceAreaDart;

    /** 服务地址市 */
    @ApiModelProperty(value = "服务地址市")
    private String serviceAreaCity;

    /** 经营范围 */
    @ApiModelProperty(value = "经营范围")
    private String businessScope;

    /** 来源类型 */
    @ApiModelProperty(value = "来源类型")
    private String source;

    /** 合作承诺函 */
    @ApiModelProperty(value = "合作承诺函")
    @NotBlank(message = "合作承诺函不能为空")
    private String commitmentLetter;

    /** 宣读视频 */
    @ApiModelProperty(value = "宣读视频")
    private String video;

    /** 审核状态 */
    @ApiModelProperty(value = "审核状态")
    private String status;

    /** SN流水号 */
    @ApiModelProperty(value = "SN流水号")
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id")
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id")
    private String processInstanceId;

    /** 备注 */
    @ApiModelProperty(value = "备注")
    private String memo;

    /** 黑名单标记 */
    @ApiModelProperty(value = "黑名单标记")
    private String isBlacklist;

    /** 黑名单原因 */
    @ApiModelProperty(value = "黑名单原因")
    private String blacklistReason;

    /** 营业范围集合 */
    private List<BpBusinessScopeAddParam> classifyList;

    /** 人员信息集合 */
    private List<BpRelationAddParam> relationAddParamList;

    /** 供应商银行开户集合 */
    private List<BpBankInformationAddParam> bankInformationList;

    /** 供应商财务信息集合 */
    private List<BpFinanceAddParam> bpFinanceList;
}
