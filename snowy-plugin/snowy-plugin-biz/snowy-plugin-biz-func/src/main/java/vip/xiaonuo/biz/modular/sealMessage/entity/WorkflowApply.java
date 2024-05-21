package vip.xiaonuo.biz.modular.sealMessage.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

/**
 *
 *@Author hxw
 *@Date 2024/5/17 15:15
 */
@Getter
@Setter
public class WorkflowApply {

    /** 申请授权必填参数集合 */
    @ApiModelProperty(value = "申请授权必填参数集合", position = 2)
    @NotBlank(message = "申请授权必填参数集合不能为空")
    private String workflowApplyParam;

    /** 流程申请附件 可同时上传多个附件，附件格式可以通过配置来控制，通常为pdf，doc等类型 */
    @ApiModelProperty(value = "流程申请附件", position = 3)
    private MultipartFile[] file;
}
