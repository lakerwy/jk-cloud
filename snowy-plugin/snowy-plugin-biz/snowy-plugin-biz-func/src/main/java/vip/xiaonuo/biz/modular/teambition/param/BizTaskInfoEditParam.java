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
package vip.xiaonuo.biz.modular.teambition.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 任务主表编辑参数
 *
 * @author ljy
 * @date  2023/12/08 09:03
 **/
@Getter
@Setter
public class BizTaskInfoEditParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 父任务ID */
    @ApiModelProperty(value = "父任务ID", position = 3)
    private String parentId;

    /** 任务名称 */
    @ApiModelProperty(value = "任务名称", position = 4)
    private String taskName;

    /** 项目备注 */
    @ApiModelProperty(value = "任务备注", position = 5)
    private String taskRemark;

    /** 项目ID */
    @ApiModelProperty(value = "项目ID", position = 6)
    private String proId;

    /** 任务开始时间 */
    @ApiModelProperty(value = "任务开始时间", position = 7)
    private Date taskStartTime;

    /** 任务结束时间 */
    @ApiModelProperty(value = "任务结束时间", position = 8)
    private Date taskEndTime;

    /** 任务执行者 */
    @ApiModelProperty(value = "任务执行者", position = 9)
    private String belonger;

    /** 任务状态 */
    @ApiModelProperty(value = "任务状态", position = 10)
    private String taskStatus;

    /** 任务优先级 */
    @ApiModelProperty(value = "任务优先级", position = 11)
    private String taskPriority;

    /** 参与人集合 */
    @ApiModelProperty(value = "参与人集合", position = 12)
    private String taskParticipants;

    /** 任务模式（公开或私密） */
    @ApiModelProperty(value = "任务模式（公开或私密）", position = 13)
    private String taskModel;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 14)
    private String dataScopeId;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 15)
    private String extJson;

}
