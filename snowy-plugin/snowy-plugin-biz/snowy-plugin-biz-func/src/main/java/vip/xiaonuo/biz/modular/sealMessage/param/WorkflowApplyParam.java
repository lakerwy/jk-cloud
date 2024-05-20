package vip.xiaonuo.biz.modular.sealMessage.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.sealMessage.entity.option;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 *
 *@Author hxw
 *@Date 2024/5/17 15:19
 */
@Getter
@Setter
public class WorkflowApplyParam {
    /** 流程申请单标题 */
    @ApiModelProperty(value = "流程申请单标题", position = 2)
    @NotBlank(message = "流程申请单标题不能为空")
    private String title;

    /** 回调url路径 */
    @ApiModelProperty(value = "回调url路径", position = 3)
    private String callBackUrl;

    /** 申请人账号 */
    @ApiModelProperty(value = "申请人账号", position = 4)
    @NotBlank(message = "申请人账号不能为空")
    private String applyerUserName;

    /** 申请明细信息 */
    @ApiModelProperty(value = "申请明细信息", position = 5)
    private List<UsedSealParam> usedSealParamList;

    /** 开关配置项对象 */
    @ApiModelProperty(value = "开关配置项对象", position = 6)
    private option option;


    /** 明文水印内 */
    @ApiModelProperty(value = "明文水印内", position = 7)
    private String printBillNo;

    /** 第三方流程编号 */
    @ApiModelProperty(value = "第三方流程编号", position = 8)
    private String billNo;
}
