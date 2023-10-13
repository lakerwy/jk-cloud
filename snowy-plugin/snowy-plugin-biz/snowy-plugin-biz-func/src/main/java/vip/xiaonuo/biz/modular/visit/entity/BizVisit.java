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
 * 外访表实体
 *
 * @author kaiminglu
 * @date 2023/10/12 10:35
 **/
@Getter
@Setter
@TableName("BIZ_VISIT")
public class BizVisit {

    /**
     * 主键ID
     */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /**
     * 租户Id
     */
    @ApiModelProperty(value = "租户Id", position = 2)
    private String tenantId;

    /**
     * 外访目的
     */
    @ApiModelProperty(value = "外访目的", position = 3)
    private String purpose;

    /**
     * 外访人员
     */
    @ApiModelProperty(value = "外访人员", position = 4)
    private String person;

    /**
     * 外访单位及主要领导情况
     */
    @ApiModelProperty(value = "外访单位及主要领导情况", position = 5)
    private String unit;

    /**
     * 出发时间
     */
    @ApiModelProperty(value = "出发时间", position = 10)
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date goTime;

    /**
     * 出发地点
     */
    @ApiModelProperty(value = "出发地点", position = 11)
    private String goLocation;

    /**
     * 出发交通工具
     */
    @ApiModelProperty(value = "出发交通工具", position = 12)
    private String goTransportation;

    /**
     * 出发航班号/车次
     */
    @ApiModelProperty(value = "出发航班号/车次", position = 13)
    private String goNo;

    /**
     * 飞机火车起始时间
     */
    @ApiModelProperty(value = "飞机火车起始时间", position = 14)
    private String goStart;

    /**
     * 入住酒店(房型、数量)
     */
    @ApiModelProperty(value = "入住酒店(房型、数量)", position = 15)
    private String goHotel;

    /**
     * 天气
     */
    @ApiModelProperty(value = "天气", position = 16)
    private String goWeather;

    /**
     * 出发备注
     */
    @ApiModelProperty(value = "出发备注", position = 17)
    private String goMemo;

    /**
     * 出发时间
     */
    @ApiModelProperty(value = "返回时间", position = 18)
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date backTime;

    /**
     * 出发地点
     */
    @ApiModelProperty(value = "返回地点", position = 19)
    private String backLocation;

    /**
     * 出发交通工具
     */
    @ApiModelProperty(value = "返回交通工具", position = 20)
    private String backTransportation;

    /**
     * 出发航班号/车次
     */
    @ApiModelProperty(value = "返回航班号/车次", position = 21)
    private String backNo;

    /**
     * 飞机火车起始时间
     */
    @ApiModelProperty(value = "返回飞机火车起始时间", position = 22)
    private String backStart;

    /**
     * 入住酒店(房型、数量)
     */
    @ApiModelProperty(value = "入住酒店(房型、数量)", position = 23)
    private String backHotel;

    /**
     * 天气
     */
    @ApiModelProperty(value = "天气", position = 24)
    private String backWeather;

    /**
     * 返回备注
     */
    @ApiModelProperty(value = "返回备注", position = 25)
    private String backMemo;

    /**
     * 出差材料
     */
    @ApiModelProperty(value = "出差材料", position = 26)
    private String material;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", position = 27)
    private String memo;

    /**
     * 总结
     */
    @ApiModelProperty(value = "总结", position = 28)
    private String summarize;

    /**
     * 删除状态标记
     */
    @ApiModelProperty(value = "删除状态标记", position = 29)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", position = 30)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", position = 31)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", position = 32)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人", position = 33)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;
}
