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
package vip.xiaonuo.bp.modular.platform.param.clarify;

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
 * 招采管理 - 竞价澄清|询价澄清|招标澄清添加参数
 *
 * @author yong.ye
 * @date  2024/05/07 16:37
 **/
@Getter
@Setter
public class ClarifyAddParam {

    /** 澄清类型：竞价澄清|询价澄清|招标澄清 */
    @ApiModelProperty(value = "澄清类型：竞价澄清|询价澄清|招标澄清", position = 2)
    private String type;

    /** 标题 */
    @ApiModelProperty(value = "标题", position = 3)
    private String title;

    /** 上级公告Id（BP_NOTICE.ID） */
    @ApiModelProperty(value = "上级公告Id（BP_NOTICE.ID）", position = 4)
    private String noticeId;

    /** 澄清内容 */
    @ApiModelProperty(value = "澄清内容", position = 5)
    private String content;

    /** 其他要求 */
    @ApiModelProperty(value = "其他要求", position = 6)
    private String demand;

    /** 澄清公告相关附件 */
    @ApiModelProperty(value = "澄清公告相关附件", position = 7)
    private String clarifyAttach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 8)
    private String memo;

//    /** 发布时间 */
//    @ApiModelProperty(value = "发布时间", position = 9)
//    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date publishTime;

}
