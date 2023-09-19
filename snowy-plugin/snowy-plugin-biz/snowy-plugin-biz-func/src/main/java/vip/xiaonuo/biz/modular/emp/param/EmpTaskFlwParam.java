package vip.xiaonuo.biz.modular.emp.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.task.param.FlwParam;

import java.util.List;

/**
 * @author yong.ye
 * @date 2023/9/19 9:07
 *
 * 流程中添加员工考核任务记录
 */

@Getter
@Setter
public class EmpTaskFlwParam {

    /** 第一部分： 流程参数 **/

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 1)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 1)
    private String processInstanceId;




    /** 第二部分： 公共参数 **/

    /** 用户Id */
    @ApiModelProperty(value = "用户Id", position = 2)
    private String userId;

    /** 用户名 */
    @ApiModelProperty(value = "用户名", position = 3)
    private String userName;

    /** 组织Id */
    @ApiModelProperty(value = "组织Id", position = 4)
    private String orgId;

    /** 年度(如：2023) */
    @ApiModelProperty(value = "年度(如：2023)", position = 5)
    private String year;

    /** 季度(如:1) */
    @ApiModelProperty(value = "季度(如:1)", position = 6)
    private String season;



    /** 第三部分： 任务参数 **/

    /** 员工当前季度任务集合 */
    @ApiModelProperty(value = "员工当前季度任务集合", position = 10)
    private List<EmpTaskParam> paramList;
}
