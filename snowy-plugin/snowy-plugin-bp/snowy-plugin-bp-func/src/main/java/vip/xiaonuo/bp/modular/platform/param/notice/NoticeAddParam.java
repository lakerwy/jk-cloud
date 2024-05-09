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
package vip.xiaonuo.bp.modular.platform.param.notice;

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
 * 招采管理 - 竞价公告|询价公告|招标公告添加参数
 *
 * @author yong.ye
 * @date  2024/05/07 16:36
 **/
@Getter
@Setter
public class NoticeAddParam {

    /** 公告类型：竞价公告|询价公告|招标公告 */
    @ApiModelProperty(value = "公告类型：竞价公告|询价公告|招标公告", position = 2)
    private String type;

    /** 标题 */
    @ApiModelProperty(value = "标题", position = 3)
    private String title;

    /** 竞价编号 */
    @ApiModelProperty(value = "竞价编号", position = 4)
    private String no;

    /** 征集主体Id */
    @ApiModelProperty(value = "征集主体Id", position = 5)
    private String objId;

    /** 征集主体 */
    @ApiModelProperty(value = "征集主体", position = 6)
    private String objName;

    /** 开始日期 */
    @ApiModelProperty(value = "开始日期", position = 7)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /** 截止日期 */
    @ApiModelProperty(value = "截止日期", position = 8)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /** 联系人 */
    @ApiModelProperty(value = "联系人", position = 9)
    private String contact;

    /** 联系方式 */
    @ApiModelProperty(value = "联系方式", position = 10)
    private String phone;

    /** 报价次数 */
    @ApiModelProperty(value = "报价次数", position = 11)
    private String bjTimes;

    /** 报价方式 */
    @ApiModelProperty(value = "报价方式", position = 12)
    private String bjType;

    /** 征集内容 */
    @ApiModelProperty(value = "征集内容", position = 13)
    private String content;

    /** 其他要求 */
    @ApiModelProperty(value = "其他要求", position = 14)
    private String demand;

    /** 相关附件 */
    @ApiModelProperty(value = "相关附件", position = 15)
    private String attach;

    /** 附件下载费用 */
    @ApiModelProperty(value = "附件下载费用", position = 16)
    private String price;

    /** 投标保证金 */
    @ApiModelProperty(value = "投标保证金", position = 17)
    private String bidBond;

    /** 投标保证金缴纳时限（天） */
    @ApiModelProperty(value = "投标保证金缴纳时限（天）", position = 18)
    private String bidDeadline;

    /** 征集主体所属供应商类别 */
    @ApiModelProperty(value = "征集主体所属供应商类别", position = 19)
    private String prvType;

    /** 通知供应商渠道(短信：1 邮箱 2 微信公众号 4 ，逻辑或的结果) */
    @ApiModelProperty(value = "通知供应商渠道(短信：1 邮箱 2 微信公众号 4 ，逻辑或的结果)", position = 20)
    private Integer noticeChannel;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 21)
    private String memo;

//    /** 发布时间 */
//    @ApiModelProperty(value = "发布时间", position = 22)
//    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date publishTime;

}
