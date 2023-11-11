package vip.xiaonuo.biz.modular.car.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @author yong.ye
 * @date 2023/11/10 9:22
 */

@Getter
@Setter
public class VehicleChartParam {

    /** 区间开始 */
    @ApiModelProperty(value = "区间开始")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;

    /** 区间结束 */
    @ApiModelProperty(value = "区间结束")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    /** 所属部门 */
    @ApiModelProperty(value = "所属部门")
    private String depts;

    /** 指派车辆 */
    @ApiModelProperty(value = "指派车辆")
    private String vehicleId;

    /** 指派司机 */
    @ApiModelProperty(value = "指派司机")
    private String driverUserId;
}
