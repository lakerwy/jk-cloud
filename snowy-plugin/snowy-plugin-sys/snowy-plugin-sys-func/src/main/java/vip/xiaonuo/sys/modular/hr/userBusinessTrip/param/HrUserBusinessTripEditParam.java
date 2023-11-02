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
package vip.xiaonuo.sys.modular.hr.userBusinessTrip.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务表-考勤-用户出差申请实体数据表编辑参数
 *
 * @author ljy
 * @date  2023/11/02 09:03
 **/
@Getter
@Setter
public class HrUserBusinessTripEditParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 申请人ID */
    @ApiModelProperty(value = "申请人ID", position = 3)
    private String applyUserId;

    /** 申请人名称 */
    @ApiModelProperty(value = "申请人名称", position = 4)
    private String applyUserName;

    /** 申请出差开始时间 */
    @ApiModelProperty(value = "申请出差开始时间", position = 5)
    private Date applyBeginTime;

    /** 申请出差结束时间 */
    @ApiModelProperty(value = "申请出差结束时间", position = 6)
    private Date applyEndTime;

    /** 申请理由 */
    @ApiModelProperty(value = "申请理由", position = 7)
    private String applyReason;

    /** 申请出差天数 */
    @ApiModelProperty(value = "申请出差天数", position = 8)
    private Double businessTripDays;

    /** 交通工具类别 */
    @ApiModelProperty(value = "交通工具类别", position = 9)
    private Integer trafficType;

    /** 交通工具名称 */
    @ApiModelProperty(value = "交通工具名称", position = 10)
    private String trafficName;

    /** 出差出发地 */
    @ApiModelProperty(value = "出差出发地", position = 11)
    private String basePlace;

    /** 出差目的地 */
    @ApiModelProperty(value = "出差目的地", position = 12)
    private String destPlace;

    /** 往返类别；1 单程，2 往返 */
    @ApiModelProperty(value = "往返类别；1 单程，2 往返", position = 13)
    private Integer returnType;

    /** 项目编号 */
    @ApiModelProperty(value = "项目编号", position = 14)
    private String projectId;

    /** 项目名称 */
    @ApiModelProperty(value = "项目名称", position = 15)
    private String projectName;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 16)
    private String extJson;

    /** 外键（流程单主键Id） */
    @ApiModelProperty(value = "外键（流程单主键Id）", position = 22)
    private String flwId;

}
