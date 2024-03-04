package vip.xiaonuo.sys.modular.hr.salary.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 工龄工资：计算结果实体类型
 */


@Getter
@Setter
public class SalarySeniorityVo {

    /** 转正工龄 */
    @ApiModelProperty(value = "转正工龄", position = 1)
    private String positiveAge;

    /** 本月工龄工资|生日福利（元） */
    @ApiModelProperty(value = "本月工龄工资|生日福利（元）", position = 2)
    private BigDecimal amount;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 3)
    private String memo;
}
