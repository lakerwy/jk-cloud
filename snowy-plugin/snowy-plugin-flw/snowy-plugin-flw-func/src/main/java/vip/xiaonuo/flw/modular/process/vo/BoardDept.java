package vip.xiaonuo.flw.modular.process.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 部门明细
 */


@Getter
@Setter
public class BoardDept {

    /** 部门名称 */
    @ApiModelProperty(value = "部门名称", position = 1)
    private String deptName;

    /** 流程平均耗时（h） */
    @ApiModelProperty(value = "流程平均耗时（h）", position = 2)
    private Double avgHours;

    /** 审批单量 */
    @ApiModelProperty(value = "审批单量", position = 3)
    private Long flwNums;
}
