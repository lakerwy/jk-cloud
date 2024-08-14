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
package vip.xiaonuo.biz.modular.mapBiz.AuditLists.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 业务表-设计校审记录表-聊天模式添加参数
 *
 * @author mayike
 * @date  2023/07/03 11:33
 **/
@Getter
@Setter
public class ChatInfoVo {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 消息类型：提问方 - QUESTION   回复方 - REPLAY  */
    @ApiModelProperty(value = "消息类型：提问方 - QUESTION   回复方 - REPLAY", position = 2)
    private String chatType;

    /** 消息内容 */
    @ApiModelProperty(value = "消息内容", position = 3)
    private String content;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 4)
    private String attachIds;

    /** 工程设计校审主表ID */
    @ApiModelProperty(value = "工程设计校审主表ID", position = 5)
    private String auditId;

    /** 操作时间  提出校审|回复时间 */
    @ApiModelProperty(value = "操作时间  提出校审|回复时间", position = 6)
    private Date optTime;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 14)
    private Date createTime;


    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 16)
    private Date updateTime;

}
