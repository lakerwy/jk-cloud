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
package vip.xiaonuo.biz.modular.crm.notes.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务表-CRM-备忘录登记添加参数
 *
 * @author yong.ye
 * @date  2023/06/30 16:49
 **/
@Getter
@Setter
public class NotesAddParam {

    /** 流水号SN（历史表Notes.SN） */
    @ApiModelProperty(value = "流水号SN（历史表Notes.SN）", position = 2)
    private String sn;

    /** 登记人（历史表Notes.Opt） */
    @ApiModelProperty(value = "登记人（历史表Notes.Opt）", position = 3)
    private String opt;

    /** 登记日期（历史表Notes.OptDate） */
    @ApiModelProperty(value = "登记日期（历史表Notes.OptDate）", position = 4)
    private Date optDate;

    /** 类别（历史表Notes.Type） */
    @ApiModelProperty(value = "类别（历史表Notes.Type）", position = 5)
    private String type;

    /** 伙伴编号（历史表Notes.PartnerNo） */
    @ApiModelProperty(value = "伙伴编号（历史表Notes.PartnerNo）", position = 6)
    private String partnerNo;

    /** 合同编号（历史表Notes.ContractNo） */
    @ApiModelProperty(value = "合同编号（历史表Notes.ContractNo）", position = 7)
    private String contractNo;

    /** 申请日期（历史表Notes.ApplyDate） */
    @ApiModelProperty(value = "申请日期（历史表Notes.ApplyDate）", position = 8)
    private Date applyDate;

    /** 最后签批日期（历史表Notes.FinalDate） */
    @ApiModelProperty(value = "最后签批日期（历史表Notes.FinalDate）", position = 9)
    private Date finalDate;

    /** 财务签收日期（历史表Notes.SignDate） */
    @ApiModelProperty(value = "财务签收日期（历史表Notes.SignDate）", position = 10)
    private Date signDate;

    /** 执行注销标记（历史表Notes.CancelMark） */
    @ApiModelProperty(value = "执行注销标记（历史表Notes.CancelMark）", position = 11)
    private String cancelMark;

    /** 备注（历史表Notes.Note） */
    @ApiModelProperty(value = "备注（历史表Notes.Note）", position = 12)
    private String note;

    /** 合作人（历史表Notes.Partner） */
    @ApiModelProperty(value = "合作人（历史表Notes.Partner）", position = 13)
    private String partner;

}
