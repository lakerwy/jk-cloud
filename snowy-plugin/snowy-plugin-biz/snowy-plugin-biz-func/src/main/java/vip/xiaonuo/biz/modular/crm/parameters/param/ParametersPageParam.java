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

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务表-CRM-合同参数查询参数
 *
 * @author yong.ye
 * @date  2023/06/30 15:50
 **/
@Getter
@Setter
public class ParametersPageParam {

    /** 当前页 */
    @ApiModelProperty(value = "当前页码")
    private Integer current;

    /** 每页条数 */
    @ApiModelProperty(value = "每页条数")
    private Integer size;

    /** 排序字段 */
    @ApiModelProperty(value = "排序字段，字段驼峰名称，如：userName")
    private String sortField;

    /** 排序方式 */
    @ApiModelProperty(value = "排序方式，升序：ASCEND；降序：DESCEND")
    private String sortOrder;

    /** 关键词 */
    @ApiModelProperty(value = "关键词")
    private String searchKey;

    /** 建筑面积（历史表Parameters.CoveredArea） */
    @ApiModelProperty(value = "建筑面积（历史表Parameters.CoveredArea）")
    private String coveredArea;

    /** 建筑层数（历史表Parameters.BuildStorey） */
    @ApiModelProperty(value = "建筑层数（历史表Parameters.BuildStorey）")
    private String buildStorey;

    /** 钢结构单跨跨度（历史表Parameters.SteelSpan） */
    @ApiModelProperty(value = "钢结构单跨跨度（历史表Parameters.SteelSpan）")
    private String steelSpan;

    /** 水处理规模（历史表Parameters.WaterScale） */
    @ApiModelProperty(value = "水处理规模（历史表Parameters.WaterScale）")
    private String waterScale;

    /** 远期水处理规模（历史表Parameters.WaterFar） */
    @ApiModelProperty(value = "远期水处理规模（历史表Parameters.WaterFar）")
    private String waterFar;

    /** 最大管径（历史表Parameters.PDMax） */
    @ApiModelProperty(value = "最大管径（历史表Parameters.PDMax）")
    private String pdMax;

    /** 景观面积（历史表Parameters.LandscapeArea） */
    @ApiModelProperty(value = "景观面积（历史表Parameters.LandscapeArea）")
    private String landscapeArea;

    /** 垃圾处理规模（历史表Parameters.GarbageDScale） */
    @ApiModelProperty(value = "垃圾处理规模（历史表Parameters.GarbageDScale）")
    private String garbageDScale;

    /** 管廊分类及登记（历史表Parameters.PipeType） */
    @ApiModelProperty(value = "管廊分类及登记（历史表Parameters.PipeType）")
    private String pipeType;

    /** 管廊断面尺寸（历史表Parameters.PipeSize） */
    @ApiModelProperty(value = "管廊断面尺寸（历史表Parameters.PipeSize）")
    private String pipeSize;

    /** 桥梁单跨跨度（历史表Parameters.BridgeSpan） */
    @ApiModelProperty(value = "桥梁单跨跨度（历史表Parameters.BridgeSpan）")
    private String bridgeSpan;

    /** 桥梁总长度（历史表Parameters.BridgeTLength） */
    @ApiModelProperty(value = "桥梁总长度（历史表Parameters.BridgeTLength）")
    private String bridgeTLength;

    /** 道路/公路等级（历史表Parameters.RoadLevels） */
    @ApiModelProperty(value = "道路/公路等级（历史表Parameters.RoadLevels）")
    private String roadLevels;

    /** 道路总长度（历史表Parameters.RoadTLength） */
    @ApiModelProperty(value = "道路总长度（历史表Parameters.RoadTLength）")
    private String roadTLength;

    /** 道路红线宽度（历史表Parameters.RoadRedWidth） */
    @ApiModelProperty(value = "道路红线宽度（历史表Parameters.RoadRedWidth）")
    private String roadRedWidth;

    /** 隧道断面尺寸（历史表Parameters.TunnelSSize） */
    @ApiModelProperty(value = "隧道断面尺寸（历史表Parameters.TunnelSSize）")
    private String tunnelSSize;

    /** 电力容量（历史表Parameters.Power） */
    @ApiModelProperty(value = "电力容量（历史表Parameters.Power）")
    private String power;

    /** 供热面积（历史表Parameters.HeatsupplyArea） */
    @ApiModelProperty(value = "供热面积（历史表Parameters.HeatsupplyArea）")
    private String heatsupplyArea;

    /** 热源规模（历史表Parameters.HeatsorceSize） */
    @ApiModelProperty(value = "热源规模（历史表Parameters.HeatsorceSize）")
    private String heatsorceSize;

    /** 压力等级（历史表Parameters.PressurRate） */
    @ApiModelProperty(value = "压力等级（历史表Parameters.PressurRate）")
    private String pressurRate;

}
