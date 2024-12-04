package vip.xiaonuo.flw.modular.process.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardTodo {

    /** 用户id */
    @ApiModelProperty(value = "用户id", position = 1)
    private String userId;

    /** 用户姓名 */
    @ApiModelProperty(value = "用户姓名", position = 2)
    private String userName;

    /** 流程耗时（h） */
    @ApiModelProperty(value = "流程耗时（h）", position = 3)
    private Double dealHours;

    /** 流程耗时（h） - 趋势 1 上升 0 不变 -1 下降 */
    @ApiModelProperty(value = "流程耗时（h） - 趋势 1 上升 0 不变 -1 下降", position = 3)
    private Integer dealHoursSate;

    /** 待办单量 */
    @ApiModelProperty(value = "待办单量", position = 4)
    private Long toDoNums;

    /** 待办单量 - 趋势 1 上升 0 不变 -1 下降 */
    @ApiModelProperty(value = "待办单量 - 趋势 1 上升 0 不变 -1 下降", position = 3)
    private Integer toDoNumSate;

}
