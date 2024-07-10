package vip.xiaonuo.oa.modular.attendance.apply.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 请假管理表添加参数
 *
 * @author mayike
 * @date  2024/07/09 12:00
 **/
@Getter
@Setter
public class HolidayApplyAddParam {

    /** 申请人ID */
    @ApiModelProperty(value = "申请人ID", position = 3)
    private String applyUserId;

    /** 申请人名称 */
    @ApiModelProperty(value = "申请人名称", position = 4)
    private String applyUserName;

    /** 申请类型：事假 1，病假 2，婚假 3，产假 4，丧假 5，年假 6，调休 7，出差 8 */
    @ApiModelProperty(value = "申请类型：事假 1，病假 2，婚假 3，产假 4，丧假 5，年假 6，调休 7，出差 8", position = 5)
    private String applyType;

    /** 申请开始时间 */
    @ApiModelProperty(value = "申请开始时间", position = 6)
    private Date applyBeginTime;

    /** 申请结束时间 */
    @ApiModelProperty(value = "申请结束时间", position = 7)
    private Date applyEndTime;

    /** 申请理由 */
    @ApiModelProperty(value = "申请理由", position = 8)
    private String applyReason;

    /** 请假天数 */
    @ApiModelProperty(value = "请假天数", position = 9)
    private Double leaveDays;

    /** 模型ID */
    @ApiModelProperty(value = "模型ID", position = 10)
    private String modelId;

    /** 流程实例id */
    @ApiModelProperty(value = "流程实例id", position = 11)
    private String processInstanceId;

    /** SN单号 */
    @ApiModelProperty(value = "SN单号", position = 12)
    private String sn;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 13)
    private String status;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 14)
    private String extJson;

}
