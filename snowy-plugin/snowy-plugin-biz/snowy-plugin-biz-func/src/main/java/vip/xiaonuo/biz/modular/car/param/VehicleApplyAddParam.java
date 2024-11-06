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
package vip.xiaonuo.biz.modular.car.param;

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
 * CAR-用车申请单添加参数
 *
 * @author yong.ye
 * @date  2023/11/08 16:04
 **/
@Getter
@Setter
public class VehicleApplyAddParam {

    /** SN */
    @ApiModelProperty(value = "SN", position = 3)
    private String sn;

    /** 申请人 */
    @ApiModelProperty(value = "申请人", position = 4)
    private String opt;

    /** 申请日期 */
    @ApiModelProperty(value = "申请日期", position = 5)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date optDate;

    /** 用车人 */
    @ApiModelProperty(value = "用车人", position = 6)
    private String users;

    /** 所属部门 */
    @ApiModelProperty(value = "所属部门", position = 7)
    private String dept;

    /** 目的地 */
    @ApiModelProperty(value = "目的地", position = 8)
    private String destination;

    /** 预计用车时间 */
    @ApiModelProperty(value = "预计用车时间", position = 9)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date predictTransportTime;

    /** 预计还车时间 */
    @ApiModelProperty(value = "预计还车时间", position = 10)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date predictCarTime;

    /** 用车事由 */
    @ApiModelProperty(value = "用车事由", position = 11)
    private String carFor;

    /** 随行人员 */
    @ApiModelProperty(value = "随行人员", position = 12)
    private String retinue;

    /** 指派车辆 */
    @ApiModelProperty(value = "指派车辆", position = 13)
    private String vehicle;

    /** 指派司机 */
    @ApiModelProperty(value = "指派司机", position = 14)
    private String driver;

    /** 实际出发时间 */
    @ApiModelProperty(value = "实际出发时间", position = 15)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /** 出发里程 */
    @ApiModelProperty(value = "出发里程", position = 16)
    private BigDecimal startMileage;

    /** 实际还车时间 */
    @ApiModelProperty(value = "实际还车时间", position = 17)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /** 还车里程 */
    @ApiModelProperty(value = "还车里程", position = 18)
    private BigDecimal endMileage;

    /** 里程小计 */
    @ApiModelProperty(value = "里程小计", position = 19)
    private BigDecimal subTotal;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 20)
    private String notes;

    /** 司机userid */
    @ApiModelProperty(value = "司机userid", position = 21)
    private String driverUserId;

    /** 用车人userid */
    @ApiModelProperty(value = "用车人userid", position = 22)
    private String userId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 23)
    private String status;

    /** 用车申请单状态（1-待审核 2-待派车 3-已派车 4-已完成 5-已取消 6-已拒绝 */
    @ApiModelProperty(value = "用车申请单状态（1-待审核 2-待派车 3-已派车 4-已完成 5-已取消 6-已拒绝）", position = 23)
    private String applyStatus;

    /** 车辆Id */
    @ApiModelProperty(value = "车辆Id", position = 24)
    private String vehicleId;

    /** 出发地 */
    @ApiModelProperty(value = "出发地", position = 25)
    private String startFrom;

    /** 车辆调度员（派车单-车辆调配管理员） */
    @ApiModelProperty(value = "车辆调度员（派车单-车辆调配管理员）", position = 26)
    private String carAdminUserId;

    /** 审批主管（用户的直属主管） */
    @ApiModelProperty(value = "审批主管（用户的直属主管）", position = 27)
    private String seniorManagerUserId;

    /** 用车人电话 */
    @ApiModelProperty(value = "用车人电话", position = 28)
    private String userMobile;

    /** 司机备注特殊情况 */
    @ApiModelProperty(value = "司机备注特殊情况", position = 29)
    private String driverMemo;

    /** 审批主管（用户的直属主管）-钉钉系统中的UserId */
    @ApiModelProperty(value = "审批主管（用户的直属主管）-钉钉系统中的UserId", position = 30)
    private String seniorManagerDingDingUserId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 40)
    private String processInstanceId;

}
