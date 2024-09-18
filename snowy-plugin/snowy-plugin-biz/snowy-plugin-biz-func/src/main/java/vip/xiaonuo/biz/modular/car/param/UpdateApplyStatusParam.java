package vip.xiaonuo.biz.modular.car.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yong.ye
 * @date 2023/11/9 14:41
 */

@Getter
@Setter
public class UpdateApplyStatusParam {

    /** 派车单（用车申请单）id */
    @ApiModelProperty(value = "派车单（用车申请单）id", position = 1)
    private String vehicleApplyId;

    /** 派车单状态：（1-待审核 2-待派车 3-已派车 4-已完成 5-已取消|已拒绝） */
    @ApiModelProperty(value = "派车单状态：（1-待审核 2-待派车 3-已派车 4-已完成 5-已取消|已拒绝）", position = 1)
    private String status;

    /** 车辆调度员（派车单-车辆调配管理员）--- 钉钉系统中的UserId */
    @ApiModelProperty(value = "车辆调度员（派车单-车辆调配管理员）--- 钉钉系统中的UserId", position = 1)
    private String carAdminDingDingUserId;
}
