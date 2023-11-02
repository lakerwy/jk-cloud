package vip.xiaonuo.sys.modular.hr.userBusinessTrip.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class BusinessTripFlwAddParam {

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 1)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position =2)
    private String processInstanceId;

    /** 第二部分： 公共参数 **/

    /** 用户Id */
    @ApiModelProperty(value = "用户Id", position = 3)
    private String userId;

    /** 用户名 */
    @ApiModelProperty(value = "用户名", position = 4)
    private String userName;

    /** 组织Id */
    @ApiModelProperty(value = "组织Id", position = 5)
    private String orgId;

    /** 申请出差总天数 */
    @ApiModelProperty(value = "申请出差总天数", position = 8)
    private Double totalDays;


    /** 第三部分： 任务参数 **/

    /** 员工申请出差的行程列表 */
    @ApiModelProperty(value = "员工申请出差的行程列表", position = 6)
    private List<HrUserBusinessTripAddParam> businessTripAddParamList;
}
