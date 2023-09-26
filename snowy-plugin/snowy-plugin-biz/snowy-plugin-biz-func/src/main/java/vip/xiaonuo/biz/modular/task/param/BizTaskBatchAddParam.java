package vip.xiaonuo.biz.modular.task.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author yong.ye
 * @date 2023/8/21 9:04
 * 描述：批量新增部门任务
 */

@Getter
@Setter
public class BizTaskBatchAddParam {
    /** 组织Id */
    @ApiModelProperty(value = "组织Id", position = 2)
    private String orgId;

    /** 责任人Id */
    @ApiModelProperty(value = "责任人Id", position = 3)
    private String ower;

    /** 责任人Id */
    @ApiModelProperty(value = "责任人Id", position = 4)
    private String owerId;

    /** 年度 */
    @ApiModelProperty(value = "年度", position = 5)
    private String year;

    /** 年度任务集合 */
    @ApiModelProperty(value = "年度任务集合", position = 6)
    private List<BizTaskBatchParam> batchParamList;
}
