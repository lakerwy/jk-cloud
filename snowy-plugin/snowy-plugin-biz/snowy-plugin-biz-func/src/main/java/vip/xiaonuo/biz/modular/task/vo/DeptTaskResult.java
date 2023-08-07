package vip.xiaonuo.biz.modular.task.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.task.entity.BizTask;

import java.util.List;

/**
 * 描述：部门任务实体
 *
 * @author yong.ye
 * @date 2023/8/7 15:15
 */

@Getter
@Setter
public class DeptTaskResult {

    /** 组织Id */
    @ApiModelProperty(value = "组织Id", position = 1)
    private String orgId;

    /** 组织名称 */
    @ApiModelProperty(value = "组织名称", position = 2)
    private String orgName;

//    /** 年度 */
//    @ApiModelProperty(value = "年度", position = 3)
//    private String year;

    /** 部门任务集合 */
    @ApiModelProperty(value = "部门任务集合", position = 3)
    private List<BizTask> taskList;

}
