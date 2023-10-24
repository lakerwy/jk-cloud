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
package vip.xiaonuo.biz.modular.visit.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 董事办-外访表实体
 *
 * @author yong.ye
 * @date  2023/10/24 09:20
 **/
@Getter
@Setter
@TableName("BIZ_VISIT")
public class BizVisit {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 租户Id */
    @ApiModelProperty(value = "租户Id", position = 2)
    private String tenantId;

    /** 外访目的 */
    @ApiModelProperty(value = "外访目的", position = 3)
    private String purpose;

    /** 外访人员 */
    @ApiModelProperty(value = "外访人员", position = 4)
    private String person;

    /** 外访单位及主要领导情况 */
    @ApiModelProperty(value = "外访单位及主要领导情况", position = 5)
    private String unit;

    /** 出行计划：出差地点 */
    @ApiModelProperty(value = "出行计划：出差地点", position = 6)
    private String tripLocation;

    /** 出行计划：出差人员 */
    @ApiModelProperty(value = "出行计划：出差人员", position = 7)
    private String tripPerson;

    /** 出行计划：出行 */
    @ApiModelProperty(value = "出行计划：出行", position = 8)
    private String tripDescrip;

    /** 出行计划：出发时间 */
    @ApiModelProperty(value = "出行计划：出发时间", position = 9)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date goTime;

    /** 出行计划：出发地点 */
    @ApiModelProperty(value = "出行计划：出发地点", position = 10)
    private String goLocation;

    /** 出行计划：市内交通工具 */
    @ApiModelProperty(value = "出行计划：市内交通工具", position = 11)
    private String cityVehicle;

    /** 出行计划：出差乘坐交通工具 */
    @ApiModelProperty(value = "出行计划：出差乘坐交通工具", position = 12)
    private String tripVehicle;

    /** 出行计划：航班号/车次 */
    @ApiModelProperty(value = "出行计划：航班号/车次", position = 13)
    private String goNo;

    /** 出行计划：飞机/火车(起始时间) */
    @ApiModelProperty(value = "出行计划：飞机/火车(起始时间)", position = 14)
    private String goStart;

    /** 出行计划：抵达目的地交通 */
    @ApiModelProperty(value = "出行计划：抵达目的地交通", position = 15)
    private String arriveVehicle;

    /** 出行计划：入住酒店(房型、数量) */
    @ApiModelProperty(value = "出行计划：入住酒店(房型、数量)", position = 16)
    private String goHotel;

    /** 出行计划：当地天气及气温 */
    @ApiModelProperty(value = "出行计划：当地天气及气温", position = 17)
    private String goWeather;

    /** 出行计划：备注 */
    @ApiModelProperty(value = "出行计划：备注", position = 18)
    private String goMemo;

    /** 返程计划：出发地点 */
    @ApiModelProperty(value = "返程计划：出发地点", position = 19)
    private String backLocation;

    /** 返程计划：返程人员 */
    @ApiModelProperty(value = "返程计划：返程人员", position = 20)
    private String backPerson;

    /** 返程计划：返程 */
    @ApiModelProperty(value = "返程计划：返程", position = 21)
    private String backDescrip;

    /** 返程计划：出发时间 */
    @ApiModelProperty(value = "返程计划：出发时间", position = 22)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date backTime;

    /** 返程计划：接送人及电话 */
    @ApiModelProperty(value = "返程计划：接送人及电话", position = 23)
    private String pickPerson;

    /** 返程计划：接送交通工具及车牌号 */
    @ApiModelProperty(value = "返程计划：接送交通工具及车牌号", position = 24)
    private String pickVehicle;

    /** 返程计划：返程乘坐交通工具 */
    @ApiModelProperty(value = "返程计划：返程乘坐交通工具", position = 25)
    private String backVehicle;

    /** 返程计划：航班号/车次 */
    @ApiModelProperty(value = "返程计划：航班号/车次", position = 26)
    private String backNo;

    /** 返程计划：飞机/火车(起始时间) */
    @ApiModelProperty(value = "返程计划：飞机/火车(起始时间)", position = 27)
    private String backStart;

    /** 返程计划：返程后接送人及电话 */
    @ApiModelProperty(value = "返程计划：返程后接送人及电话", position = 28)
    private String backPickPerson;

    /** 返程计划：返程后接送车牌号 */
    @ApiModelProperty(value = "返程计划：返程后接送车牌号", position = 29)
    private String backPickVehicle;

    /** 返程计划：当地天气及气温 */
    @ApiModelProperty(value = "返程计划：当地天气及气温", position = 30)
    private String backWeather;

    /** 返程计划：备注 */
    @ApiModelProperty(value = "返程计划：备注", position = 31)
    private String backMemo;

    /** 总结：外访目的 */
    @ApiModelProperty(value = "总结：外访目的", position = 32)
    private String sumPurpose;

    /** 总结：工作计划完成情况 */
    @ApiModelProperty(value = "总结：工作计划完成情况", position = 33)
    private String sumPlanComplete;

    /** 总结：工作开展情况 */
    @ApiModelProperty(value = "总结：工作开展情况", position = 34)
    private String sumImplComplete;

    /** 总结：工作总结 */
    @ApiModelProperty(value = "总结：工作总结", position = 35)
    private String sumSummary;

    /** 出差物料领用单JSON */
    @ApiModelProperty(value = "出差物料领用单JSON", position = 36)
    private String material;

    /** 删除状态标记 */
    @ApiModelProperty(value = "删除状态标记", position = 37)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 38)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 39)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 40)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 41)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** SN */
    @ApiModelProperty(value = "SN", position = 50)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 50)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 50)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 50)
    private String status;

    /** 入参信息 */
    @ApiModelProperty(value = "入参信息", position = 50)
    private String extJson;
}
