package vip.xiaonuo.biz.modular.teambition.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.teambition.vo.ThirdBizTaskInfoVo;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class ThirdBizTaskInfoParam {


    /** 第三方主ID */
    @ApiModelProperty(value = "第三方主ID",position = 1)
    @NotNull(message = "第三方主ID")
    private String proId;

    /** 第三方类别 */
    @ApiModelProperty(value = "第三方类别",position = 1)
    @NotNull(message = "第三方类别")
    private String proType;

    @ApiModelProperty(value = "任务批量修改列表", position = 2)
    @NotNull(message = "任务批量修改列表")
    private List<ThirdBizTaskInfoVo> thirdBizTaskInfoVoList;
}
