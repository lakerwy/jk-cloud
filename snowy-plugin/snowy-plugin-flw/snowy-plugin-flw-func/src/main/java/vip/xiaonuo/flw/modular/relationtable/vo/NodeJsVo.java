package vip.xiaonuo.flw.modular.relationtable.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class NodeJsVo {

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id",position = 1)
    private String processInstanceId;

    /** 业务主键Id */
    @ApiModelProperty(value = "业务主键Id",position = 2)
    private String id;

    /** 流程modelId */
    @ApiModelProperty(value = "流程modelId",position = 3)
    private String modelId;

    /** 流程SN */
    @ApiModelProperty(value = "流程SN",position = 4)
    private String sn;

}
