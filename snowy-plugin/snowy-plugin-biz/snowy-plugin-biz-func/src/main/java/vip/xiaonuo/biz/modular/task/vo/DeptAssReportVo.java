package vip.xiaonuo.biz.modular.task.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yong.ye
 * @date 2023/8/9 14:30
 */

@Getter
@Setter
public class DeptAssReportVo {
    /** 考核年月 */
    @ApiModelProperty(value = "考核年月", position = 2)
    private String assYear;

    /** 组织Id */
    @ApiModelProperty(value = "组织Id", position = 3)
    private String orgId;

    /** 组织名称 */
    @ApiModelProperty(value = "组织名称", position = 4)
    private String orgName;

    /** 集团经营业绩绩点分(15分) */
    @ApiModelProperty(value = "集团经营业绩绩点分(15分)", position = 5)
    private Double jtScore;

    /** 年度任务分(80分) */
    @ApiModelProperty(value = "年度任务分(80分)", position = 6)
    private Double ndScore;

    /** 部门协同分(5分) */
    @ApiModelProperty(value = "部门协同分(5分)", position = 7)
    private Double xtScore;

    /** 部门考核分(无限制) */
    @ApiModelProperty(value = "部门考核分(无限制)", position = 8)
    private Double khScore;

    /** 总分 */
    @ApiModelProperty(value = "总分", position = 9)
    private Double totalScore;
}
