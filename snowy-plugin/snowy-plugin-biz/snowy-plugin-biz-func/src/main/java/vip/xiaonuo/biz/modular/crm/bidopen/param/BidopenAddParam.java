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
package vip.xiaonuo.biz.modular.crm.bidopen.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务表-CRM-开标登记添加参数
 *
 * @author yong.ye
 * @date  2023/06/30 16:31
 **/
@Getter
@Setter
public class BidopenAddParam {

    /** 登记人（历史表BidOpen.Opt） */
    @ApiModelProperty(value = "登记人（历史表BidOpen.Opt）", position = 2)
    private String opt;

    /** 登记日期（历史表BidOpen.OptDate） */
    @ApiModelProperty(value = "登记日期（历史表BidOpen.OptDate）", position = 3)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date optDate;

    /** 流水号SN（历史表BidOpen.SN） */
    @ApiModelProperty(value = "流水号SN（历史表BidOpen.SN）", position = 4)
    private String sn;

    /** 立项-主键ID（历史表BidOpen.ProjectId） */
    @ApiModelProperty(value = "立项-主键ID（历史表BidOpen.ProjectId）", position = 5)
    private String projectId;

    /** 立项-项目名称（历史表BidOpen.ProjectName） */
    @ApiModelProperty(value = "立项-项目名称（历史表BidOpen.ProjectName）", position = 6)
    private String projectName;

    /** 立项-招标编号（历史表BidOpen.BidNo） */
    @ApiModelProperty(value = "立项-招标编号（历史表BidOpen.BidNo）", position = 7)
    private String bidNo;

    /** 立项-项目类型（历史表BidOpen.Type） */
    @ApiModelProperty(value = "立项-项目类型（历史表BidOpen.Type）", position = 8)
    private String type;

    /** 中标情况（历史表BidOpen.States） */
    @ApiModelProperty(value = "中标情况（历史表BidOpen.States）", position = 9)
    private String states;

    /** 中标金额（万元）（历史表BidOpen.BidPrice） */
    @ApiModelProperty(value = "中标金额（万元）（历史表BidOpen.BidPrice）", position = 10)
    private String bidPrice;

    /** 中标通知书（历史表BidOpen.Attachments） */
    @ApiModelProperty(value = "中标通知书（历史表BidOpen.Attachments）", position = 11)
    private String attachments;

    /** 备注（历史表BidOpen.Note） */
    @ApiModelProperty(value = "备注（历史表BidOpen.Note）", position = 12)
    private String note;

    /** 中标公示网址（历史表BidOpen.URL） */
    @ApiModelProperty(value = "中标公示网址（历史表BidOpen.URL）", position = 13)
    private String url;

    /** 中标时间（历史表BidOpen.BidDate） */
    @ApiModelProperty(value = "中标时间（历史表BidOpen.BidDate）", position = 14)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date bidDate;

    /** 立项-所属部门（历史表BidOpen.Dept） */
    @ApiModelProperty(value = "立项-所属部门（历史表BidOpen.Dept）", position = 15)
    private String dept;

    /** 联合体金额 */
    @ApiModelProperty(value = "联合体金额（万元）", position = 23)
    private String amount;

}
