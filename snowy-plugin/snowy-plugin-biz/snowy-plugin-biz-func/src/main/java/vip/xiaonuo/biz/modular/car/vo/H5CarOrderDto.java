package vip.xiaonuo.biz.modular.car.vo;


import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.car.entity.VehicleApply;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class H5CarOrderDto {


    /** 用车申请单id 主键ID */
    @ApiModelProperty(value = "用车申请单id 主键ID", position = 1)
    private String id;

    /** SN */
    @ApiModelProperty(value = "SN", position = 3)
    private String sn;

    /** 申请人 */
    @ApiModelProperty(value = "申请人", position = 4)
    private String opt;

    /** 申请日期 */
    @ApiModelProperty(value = "申请日期", position = 5)
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
    private Date predictTransportTime;

    /** 预计还车时间 */
    @ApiModelProperty(value = "预计还车时间", position = 10)
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
    private Date startTime;

    /** 出发里程 */
    @ApiModelProperty(value = "出发里程", position = 16)
    private BigDecimal startMileage;

    /** 实际还车时间 */
    @ApiModelProperty(value = "实际还车时间", position = 17)
    private Date endTime;

    /** 还车里程 */
    @ApiModelProperty(value = "还车里程", position = 18)
    private BigDecimal endMileage;

    /** 里程小计 */
    @ApiModelProperty(value = "里程小计", position = 19)
    private BigDecimal subTotal;

    /** 司机userid */
    @ApiModelProperty(value = "司机userid", position = 21)
    private String driverUserId;

    /** 用车人userid */
    @ApiModelProperty(value = "用车人userid", position = 22)
    private String userId;

    /** 用车申请单状态（1-待审核 2-待派车 3-已派车 4-已完成 5-已取消|已拒绝） */
    @ApiModelProperty(value = "用车申请单状态（1-待审核 2-待派车 3-已派车 4-已完成 5-已取消|已拒绝）", position = 23)
    private String status;

    /** 车辆Id */
    @ApiModelProperty(value = "车辆Id", position = 24)
    private String vehicleId;

    /** 出发地 */
    @ApiModelProperty(value = "出发地", position = 25)
    private String startFrom;

    /** 用车人电话 */
    @ApiModelProperty(value = "用车人电话", position = 28)
    private String userMobile;

    /** 司机备注特殊情况 */
    @ApiModelProperty(value = "司机备注特殊情况", position = 29)
    private String driverMemo;

}
