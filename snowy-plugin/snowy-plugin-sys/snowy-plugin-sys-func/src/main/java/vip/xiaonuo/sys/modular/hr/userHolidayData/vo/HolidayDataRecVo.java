package vip.xiaonuo.sys.modular.hr.userHolidayData.vo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.sys.modular.hr.userHolidayData.entity.HrUserHolidayData;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class HolidayDataRecVo {
    /**假期增减数据*/
    @ApiModelProperty(value = "假期增减数据", position = 1)
    private List<HolidayDataRec> holidayDataRecList;

    /**假期数据*/
    @ApiModelProperty(value = "假期数据", position = 2)
    private HrUserHolidayData holidayData;
}
