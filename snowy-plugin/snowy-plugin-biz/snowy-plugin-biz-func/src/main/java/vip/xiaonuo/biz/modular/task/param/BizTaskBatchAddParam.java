package vip.xiaonuo.biz.modular.task.param;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author yong.ye
 * @date 2023/8/4 14:27
 */

@Getter
@Setter
public class BizTaskBatchAddParam {

    /** 组织Id */
    @ApiModelProperty(value = "组织Id", position = 2)
    private String orgId;

    /** 责任人Id|部门负责人 */
    @ApiModelProperty(value = "责任人Id|部门负责人", position = 3)
    private String ower;

    /** 年度任务集合 */
    @ApiModelProperty(value = "年度任务集合", position = 4)
    private List<BizTaskSaveParam> batchSaveParam;
}
