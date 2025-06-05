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
package vip.xiaonuo.biz.modular.crm.parameters.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 业务表-CRM-合同参数编辑参数
 *
 * @author yong.ye
 * @date  2023/06/30 15:50
 **/
@Getter
@Setter
public class ParametersEditParam {

    /** 主键ID（历史表Parameters.Id） */
    @ApiModelProperty(value = "主键ID（历史表Parameters.Id）", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 登记人（历史表Parameters.Opt） */
//    @ApiModelProperty(value = "登记人（历史表Parameters.Opt）", position = 2)
//    private String opt;

    /** 登记日期（历史表Parameters.OptDate） */
//    @ApiModelProperty(value = "登记日期（历史表Parameters.OptDate）", position = 3)
//    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
//    private Date optDate;

    /** SN（历史表Parameters.SN） */
//    @ApiModelProperty(value = "SN（历史表Parameters.SN）", position = 4)
//    private String sn;

    /** 合同ID主键（历史表Parameters.ProjectId） */
    @ApiModelProperty(value = "合同ID主键（历史表Parameters.ProjectId）", position = 5)
    private String projectId;

    /** 建筑面积（历史表Parameters.CoveredArea） */
    @ApiModelProperty(value = "建筑面积（历史表Parameters.CoveredArea）", position = 6)
    private String coveredArea;

    /** 建筑层数（历史表Parameters.BuildStorey） */
    @ApiModelProperty(value = "建筑层数（历史表Parameters.BuildStorey）", position = 7)
    private String buildStorey;

    /** 钢结构单跨跨度（历史表Parameters.SteelSpan） */
    @ApiModelProperty(value = "钢结构单跨跨度（历史表Parameters.SteelSpan）", position = 8)
    private String steelSpan;

    /** 水处理规模（历史表Parameters.WaterScale） */
    @ApiModelProperty(value = "水处理规模（历史表Parameters.WaterScale）", position = 9)
    private String waterScale;

    /** 远期水处理规模（历史表Parameters.WaterFar） */
    @ApiModelProperty(value = "远期水处理规模（历史表Parameters.WaterFar）", position = 10)
    private String waterFar;

    /** 最大管径（历史表Parameters.PDMax） */
    @ApiModelProperty(value = "最大管径（历史表Parameters.PDMax）", position = 11)
    private String pdMax;

    /** 景观面积（历史表Parameters.LandscapeArea） */
    @ApiModelProperty(value = "景观面积（历史表Parameters.LandscapeArea）", position = 12)
    private String landscapeArea;

    /** 垃圾处理规模（历史表Parameters.GarbageDScale） */
    @ApiModelProperty(value = "垃圾处理规模（历史表Parameters.GarbageDScale）", position = 13)
    private String garbageDScale;

    /** 管廊分类及登记（历史表Parameters.PipeType） */
    @ApiModelProperty(value = "管廊分类及登记（历史表Parameters.PipeType）", position = 14)
    private String pipeType;

    /** 管廊断面尺寸 - 宽 */
    @ApiModelProperty(value = "管廊断面尺寸 - 宽", position = 15)
    private String pipeSize;

    /** 管廊断面尺寸 - 高 */
    @ApiModelProperty(value = "管廊断面尺寸 - 高", position = 15)
    private String pipeSizeH;

    /** 桥梁单跨跨度（历史表Parameters.BridgeSpan） */
    @ApiModelProperty(value = "桥梁单跨跨度（历史表Parameters.BridgeSpan）", position = 16)
    private String bridgeSpan;

    /** 桥梁总长度（历史表Parameters.BridgeTLength） */
    @ApiModelProperty(value = "桥梁总长度（历史表Parameters.BridgeTLength）", position = 17)
    private String bridgeTLength;

    /** 道路/公路等级（历史表Parameters.RoadLevels） */
    @ApiModelProperty(value = "道路/公路等级（历史表Parameters.RoadLevels）", position = 18)
    private String roadLevels;

    /** 道路总长度（历史表Parameters.RoadTLength） */
    @ApiModelProperty(value = "道路总长度（历史表Parameters.RoadTLength）", position = 19)
    private String roadTLength;

    /** 道路红线宽度（历史表Parameters.RoadRedWidth） */
    @ApiModelProperty(value = "道路红线宽度（历史表Parameters.RoadRedWidth）", position = 20)
    private String roadRedWidth;

    /** 隧道断面尺寸 - 隧道净宽限界(m) */
    @ApiModelProperty(value = "隧道断面尺寸 - 隧道净宽限界(m)", position = 21)
    private String tunnelSSize;

    /** 隧道断面尺寸 - 隧道建筑净空限界(m) */
    @ApiModelProperty(value = "隧道断面尺寸 - 隧道建筑净空限界(m)", position = 21)
    private String tunnelSSizeJ;

    /** 电力容量（历史表Parameters.Power） */
    @ApiModelProperty(value = "电力容量（历史表Parameters.Power）", position = 22)
    private String power;

    /** 供热面积（历史表Parameters.HeatsupplyArea） */
    @ApiModelProperty(value = "供热面积（历史表Parameters.HeatsupplyArea）", position = 23)
    private String heatsupplyArea;

    /** 热源规模（历史表Parameters.HeatsorceSize） */
    @ApiModelProperty(value = "热源规模（历史表Parameters.HeatsorceSize）", position = 24)
    private String heatsorceSize;

    /** 压力等级（历史表Parameters.PressurRate） */
    @ApiModelProperty(value = "压力等级（历史表Parameters.PressurRate）", position = 25)
    private String pressurRate;

    /** 税率（%） */
    @ApiModelProperty(value = "税率（%）", position = 40)
    private BigDecimal taxRate;

    /** 收费标准及付费方式合同原文 */
    @ApiModelProperty(value = "收费标准及付费方式合同原文", position = 41)
    private String recPayText;

    /** 合同收款计划 */
    @ApiModelProperty(value = "合同收款计划", position = 45)
    private List<BizCrmRecPlanEditParam> recPlanList;

}
