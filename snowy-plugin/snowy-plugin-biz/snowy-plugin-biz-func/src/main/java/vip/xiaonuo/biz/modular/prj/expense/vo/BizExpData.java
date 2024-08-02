package vip.xiaonuo.biz.modular.prj.expense.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 报销单|借支单 统计报表
 */

@Getter
@Setter
public class BizExpData {

    /** 单位 */
    @ApiModelProperty(value = "单位", position = 1)
    private String unit;

    /** 部门 */
    @ApiModelProperty(value = "部门", position = 1)
    private String dept;

    /** 报销人 */
    @ApiModelProperty(value = "报销人", position = 2)
    private String exptor;

    /** 日期 */
    @ApiModelProperty(value = "日期", position = 3)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expDate;

    /** 费用明细-总计金额（小写） */
    @ApiModelProperty(value = "费用明细-总计金额（小写）", position = 4)
    private String lowFeeTotal;

    /** 本月员工合计 */
    @ApiModelProperty(value = "本月员工合计", position = 5)
    private String empTotal;

    /** 本月单位合计 */
    @ApiModelProperty(value = "本月单位合计", position = 6)
    private String unitTotal;

}
