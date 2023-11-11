package vip.xiaonuo.biz.modular.car.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author yong.ye
 * @date 2023/11/10 10:18
 */

@Getter
@Setter
public class FeeChartParam {

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

    /** 费用类型 */
    @ApiModelProperty(value = "费用类型")
    private String feeTypes ;

    /** 指派车辆Id */
    @ApiModelProperty(value = "指派车辆Id")
    private String vehicleId ;
}
