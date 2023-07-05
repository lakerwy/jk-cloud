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
package vip.xiaonuo.biz.modular.crm.contractrr.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务表-CRM-合同收款登记添加参数
 *
 * @author yong.ye
 * @date  2023/06/30 16:47
 **/
@Getter
@Setter
public class ContractrrAddParam {

    /** 流水号SN（历史表ContractRR.SN） */
    @ApiModelProperty(value = "流水号SN（历史表ContractRR.SN）", position = 2)
    private String sn;

    /** 登记人（历史表ContractRR.Opt） */
    @ApiModelProperty(value = "登记人（历史表ContractRR.Opt）", position = 3)
    private String opt;

    /** 登记日期（历史表ContractRR.OptDate） */
    @ApiModelProperty(value = "登记日期（历史表ContractRR.OptDate）", position = 4)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date optDate;

    /** 合同编号（历史表ContractRR.ContractNo） */
    @ApiModelProperty(value = "合同编号（历史表ContractRR.ContractNo）", position = 5)
    private String contractNo;

    /** 收款时间（历史表ContractRR.CollectDate） */
    @ApiModelProperty(value = "收款时间（历史表ContractRR.CollectDate）", position = 6)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date collectDate;

    /** 收款金额（历史表ContractRR.CollectAmount） */
    @ApiModelProperty(value = "收款金额（历史表ContractRR.CollectAmount）", position = 7)
    private BigDecimal collectAmount;

    /** 银行回单（历史表ContractRR.Attachment） */
    @ApiModelProperty(value = "银行回单（历史表ContractRR.Attachment）", position = 8)
    private String attachments;

}
