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
package vip.xiaonuo.biz.modular.teambition.taskInfoComment.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 任务管理平台-任务评论编辑参数
 *
 * @author ljy
 * @date  2023/12/13 17:51
 **/
@Getter
@Setter
public class BizTaskCommentEditParam {

    /** ID */
    @ApiModelProperty(value = "ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 任务ID */
    @ApiModelProperty(value = "任务ID", position = 2)
    private String taskInfoId;

    /** 任务变更记录 */
    @ApiModelProperty(value = "任务变更记录", position = 3)
    private String infoStatus;

    /** 任务评论 */
    @ApiModelProperty(value = "任务评论", position = 4)
    private String infoComment;

    /** 回复评论 */
    @ApiModelProperty(value = "回复评论", position = 5)
    private String replyComment;

    /** 回复人，艾特人 */
    @ApiModelProperty(value = "回复人，艾特人", position = 6)
    private String replyUser;

    /** 附件ID */
    @ApiModelProperty(value = "附件ID", position = 7)
    private String fileId;

    /** 父id */
    @ApiModelProperty(value = "父id", position = 8)
    private String parentId;
}
