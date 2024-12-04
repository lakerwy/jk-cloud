package vip.xiaonuo.flw.modular.process.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.flw.modular.process.result.FlwProcessResult;

import java.util.Date;

@Getter
@Setter
public class FlwBoardResult extends FlwProcessResult {

    /** 所属日期 年-月-日 */
    @ApiModelProperty(value = "所属日期 年-月-日 ", position = 66)
    private Date boardDate;

    /** 流程耗时(h) */
    @ApiModelProperty(value = "流程耗时(h)", position = 66)
    private Double boardTime;
}
