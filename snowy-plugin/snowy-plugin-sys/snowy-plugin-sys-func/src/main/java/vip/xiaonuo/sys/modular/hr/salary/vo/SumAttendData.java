package vip.xiaonuo.sys.modular.hr.salary.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryAttend;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 考勤 - 病假|全勤|迟到|矿工|产假|事假
 */

@Getter
@Setter
public class SumAttendData {

    /** 附加工资-全勤奖 */
    @ApiModelProperty(value = "附加工资-全勤奖", position = 1)
    private BigDecimal fullAmount;

    /** 考勤扣款-病假 */
    @ApiModelProperty(value = "考勤扣款-病假", position = 2)
    private BigDecimal sickAmount;

    /** 考勤扣款-事假 */
    @ApiModelProperty(value = "考勤扣款-事假", position = 3)
    private BigDecimal workAmount;

    /** 考勤扣款-迟到 */
    @ApiModelProperty(value = "考勤扣款-迟到", position = 4)
    private BigDecimal lateAmount;

    /** 考勤扣款-矿工 */
    @ApiModelProperty(value = "考勤扣款-矿工", position = 5)
    private BigDecimal minerAmount;

    /** 考勤扣款-产假 */
    @ApiModelProperty(value = "考勤扣款-产假", position = 6)
    private BigDecimal materAmount;

    /** 当月入离职未满勤扣款 */
    @ApiModelProperty(value = "当月入离职未满勤扣款", position = 7)
    private BigDecimal inOutAmount;

    /** 实习生工资 */
    @ApiModelProperty(value = "实习生工资", position = 8)
    private BigDecimal tryAmount;
}
