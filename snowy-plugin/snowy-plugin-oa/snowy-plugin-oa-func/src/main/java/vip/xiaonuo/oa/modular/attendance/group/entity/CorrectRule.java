package vip.xiaonuo.oa.modular.attendance.group.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/6/11 15:15
 * 补卡规则
 */
@Setter
@Getter
public class CorrectRule {
    @ApiModelProperty(value = "补卡次数", position = 1)
    private int correctNum;

    @ApiModelProperty(value = "补卡起算日期", position = 2)
    private int correctStartTime;

    @ApiModelProperty(value = "补卡类型", position = 3)
    private String[] correctTypes;

    @ApiModelProperty(value = "补卡时效，如补最近一天的")
    private int correctApplyTime;
}
