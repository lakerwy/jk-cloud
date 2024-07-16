package vip.xiaonuo.oa.modular.attendance.apply.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.oa.modular.attendance.apply.entity.ApplyJson;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 考勤申请管理表编辑参数
 *
 * @author mayike
 * @date  2024/07/15 09:56
 **/
@Getter
@Setter
public class ApplyEditParam<T extends ApplyJson> {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** SN单号 */
    @ApiModelProperty(value = "SN单号", position = 3)
    private String sn;

    /** 申请人ID */
    @ApiModelProperty(value = "申请人ID", position = 4)
    private String applyUserId;

    /** 申请人名称 */
    @ApiModelProperty(value = "申请人名称", position = 5)
    private String applyUserName;

    /** 申请类型：1 补卡 2 请假 3 加班 4 外出 5 出差 6 签到 */
    @ApiModelProperty(value = "申请类型：1 补卡 2 请假 3 加班 4 外出 5 出差 6 签到", position = 6)
    private int applyType;

    /** 申请开始时间 */
    @ApiModelProperty(value = "申请开始时间", position = 7)
    private Date applyStartTime;

    /** 申请结束时间 */
    @ApiModelProperty(value = "申请结束时间", position = 8)
    private Date applyEndTime;

    /** 申请内容JSON */
    @ApiModelProperty(value = "申请内容JSON", position = 9)
    private ApplyJson applyJson;

    /** 模型ID */
    @ApiModelProperty(value = "模型ID", position = 10)
    private String modelId;

    /** 流程实例id */
    @ApiModelProperty(value = "流程实例id", position = 11)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 12)
    private String status;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 13)
    private String extJson;

}
