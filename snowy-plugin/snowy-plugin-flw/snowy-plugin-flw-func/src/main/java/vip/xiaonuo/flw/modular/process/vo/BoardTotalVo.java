package vip.xiaonuo.flw.modular.process.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 流程耗时面板 统计结果vo
 */


@Getter
@Setter
public class BoardTotalVo {

    /** 耗时明细 - 趋势分析  */
    @ApiModelProperty(value = "耗时明细 - 趋势分析 ")
    private LineChart lineChart ;

    /** 耗时明细 - 部门明细  */
    @ApiModelProperty(value = "耗时明细 - 部门明细 ")
    private List<BoardDept> deptList ;

    /** 人员诊断 - 待办排行  */
    @ApiModelProperty(value = "人员诊断 - 待办排行 ")
    private List<BoardTodoNum> todoNumList ;

    /** 人员诊断 - 耗时排行  */
    @ApiModelProperty(value = "人员诊断 - 耗时排行 ")
    private List<BoardTodoHour> todoHourList ;

}
