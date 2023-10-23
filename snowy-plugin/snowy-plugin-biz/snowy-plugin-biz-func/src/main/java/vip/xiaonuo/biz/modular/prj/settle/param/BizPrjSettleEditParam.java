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
package vip.xiaonuo.biz.modular.prj.settle.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务表-项目计划付款单编辑参数
 *
 * @author yong.ye
 * @date  2023/09/28 11:36
 **/
@Getter
@Setter
public class BizPrjSettleEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 合作方/分公司编码 */
    @ApiModelProperty(value = "合作方/分公司编码", position = 2)
    private String hzfCode;

    /** 合作方/分公司名称 */
    @ApiModelProperty(value = "合作方/分公司名称", position = 3)
    private String hzfName;

    /** 申请日期 */
//    @ApiModelProperty(value = "申请日期", position = 4)
//    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date applyDate;

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




//    /** 累计开票（含本次）- 累计税额  -- 删除字段，暂不用 */
//    @ApiModelProperty(value = "累计开票（含本次）- 累计税额  -- 删除字段，暂不用", position = 25)
//    private Double kpLjsAmount;
//
//    /** 累计开票（含本次）- 累计不含税金额  -- 删除字段，暂不用*/
//    @ApiModelProperty(value = "累计开票（含本次）- 累计不含税金额  -- 删除字段，暂不用", position = 26)
//    private Double kpLjnsAmount;
//
//    /** 累计开票（含本次）- 累计金额（含税） -- 删除字段，暂不用*/
//    @ApiModelProperty(value = "累计开票（含本次）- 累计金额（含税） -- 删除字段，暂不用", position = 27)
//    private Double kpLjAmount;
//
//    /** 累计开票（含本次）- 小计 */
//    @ApiModelProperty(value = "累计开票（含本次）- 小计", position = 28)
//    private Double kpTotalAmount;
//
//    /** 累计开票（含本次）- 累计回款金额 */
//    @ApiModelProperty(value = "累计开票（含本次）- 累计回款金额", position = 28)
//    private Double kpLjhkAmount;
//
//    /** 累计开票（含本次）- 本次申请内部结算金额 */
//    @ApiModelProperty(value = "累计开票（含本次）- 本次申请内部结算金额", position = 29)
//    private Double kpLjjsAmount;



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

    /** 财务部-本次实际收取税差 */
    @ApiModelProperty(value = "财务部-本次实际收取税差", position = 41)
    private Double cwbSsThisAmount;

    /** 财务部-累计实际收取税差 */
    @ApiModelProperty(value = "财务部-累计实际收取税差", position = 42)
    private Double cwbSsLjAmount;

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

    /** 入参信息 */
    @ApiModelProperty(value = "入参信息", position = 53)
    private String extJson;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 54)
    private String dataScopeId;

}
