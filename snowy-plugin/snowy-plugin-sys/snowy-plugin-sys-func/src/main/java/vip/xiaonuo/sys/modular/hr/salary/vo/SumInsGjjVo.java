package vip.xiaonuo.sys.modular.hr.salary.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 统计 某个人 的社保|公积金相关数据
 */

@Getter
@Setter
public class SumInsGjjVo {
    /** 【外键】发放工资员工Id：HR_SALARY_STANDARD.ID */
    @ApiModelProperty(value = "【外键】发放工资员工Id：HR_SALARY_STANDARD.ID", position = 1)
    private String standardId;

    /** 个人社保-小计 */
    @ApiModelProperty(value = "个人社保-小计", position = 2)
    private BigDecimal insPerAmount;

    /** 个人公积金-小计 */
    @ApiModelProperty(value = "个人公积金-小计", position = 3)
    private BigDecimal gjjPerAmount;

    /** 个人公积金差额-小计 */
    @ApiModelProperty(value = "个人公积金差额-小计", position = 4)
    private BigDecimal gjjChaAmount;
}
