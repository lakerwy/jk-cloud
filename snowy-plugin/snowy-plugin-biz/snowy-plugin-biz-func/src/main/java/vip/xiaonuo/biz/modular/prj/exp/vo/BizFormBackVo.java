package vip.xiaonuo.biz.modular.prj.exp.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.prj.exp.entity.BizFormBack;

import java.math.BigDecimal;


@Getter
@Setter
public class BizFormBackVo extends BizFormBack {

    /** 借支单 - 流程实例Id */
    @ApiModelProperty(value = "借支单 - 流程实例Id", position = 101)
    private String cashProcessInstanceId;

    /** 借支单 - 流程状态 */
    @ApiModelProperty(value = "借支单 - 流程状态", position = 102)
    private String cashStatus;

    /** 借支单 - 借支金额（元） */
    @ApiModelProperty(value = "借支金额（元）", position = 103)
    private BigDecimal cashAdvanceAmount;

}
