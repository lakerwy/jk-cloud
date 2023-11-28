package vip.xiaonuo.common.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 业务流程发起，组合接口，公共参数类（发起参数）
 */
@Getter
@Setter
public class CommonFlwEntity {
    /** 模型id */
    @ApiModelProperty(value = "模型id", required = true, position = 1)
    @NotBlank(message = "modelId不能为空")
    private String modelId;

    /** 填写的数据 */
    @ApiModelProperty(value = "填写的数据", position = 2)
    private String dataJson;

    /** 发起人id */
    @ApiModelProperty(value = "发起人id", position = 3)
    private String initiator;

    /** 发起人姓名 */
    @ApiModelProperty(value = "发起人姓名", position = 4)
    private String initiatorName;

    /** 发起人组织id */
    @ApiModelProperty(value = "发起人组织id", position = 5)
    private String initiatorOrgId;

    /** 发起人组织名称 */
    @ApiModelProperty(value = "发起人组织名称", position = 6)
    private String initiatorOrgName;

    /** 发起人职位id */
    @ApiModelProperty(value = "发起人职位id", position = 7)
    private String initiatorPositionId;

    /** 发起人职位名称 */
    @ApiModelProperty(value = "发起人职位名称", position = 8)
    private String initiatorPositionName;

    @ApiModelProperty(value = "业务主键id",position = 9)
    private String id;
}
