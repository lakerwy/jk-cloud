package vip.xiaonuo.flw.modular.process.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class FlwProcessParamUpdate {

    @ApiModelProperty(value = "模型Id",  required = true, position = 1)
    private String modelId;

    @ApiModelProperty(value = "流程实例Id集合", required = true, position = 2)
    private List<String> processInstanceIdList;
}
