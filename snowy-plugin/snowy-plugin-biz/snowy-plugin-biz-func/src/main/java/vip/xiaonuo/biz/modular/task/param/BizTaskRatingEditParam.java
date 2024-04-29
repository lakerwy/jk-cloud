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
package vip.xiaonuo.biz.modular.task.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 部门考核 - 绩效评估流程业务表编辑参数
 *
 * @author yong.ye
 * @date  2024/04/29 10:08
 **/
@Getter
@Setter
public class BizTaskRatingEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 董事办评级 */
    @ApiModelProperty(value = "董事办评级", position = 2)
    private Double boosScore;

    /** 董事办意见 */
    @ApiModelProperty(value = "董事办意见", position = 3)
    private String bossMemo;

    /** SN流水号 */
    @ApiModelProperty(value = "SN流水号", position = 11)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 12)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 13)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 14)
    private String status;

    /** 入参信息 */
    @ApiModelProperty(value = "入参信息", position = 15)
    private String extJson;

    /** 外键：部门任务Id - BIZ_TASK.Id */
    @ApiModelProperty(value = "外键：部门任务Id - BIZ_TASK.Id", position = 16)
    private String taskId;

}
