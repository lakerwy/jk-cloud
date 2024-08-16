package vip.xiaonuo.biz.modular.zizhi.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AdjSortParam {

    /** 需要调整的行元素 - 主键  被拖拽的行主键id */
    @ApiModelProperty(value = "需要调整的行元素 - 主键  被拖拽的行主键id", required = true, position = 1)
    @NotBlank(message = "拖拽的元素主键id不能为空")
    private String sourceId;

    /** 目标行元素 - 主键  拖拽至目标行主键id 【目标位置上一行】 */
    @ApiModelProperty(value = "目标行元素 - 主键  拖拽至目标行主键id【目标位置上一行】", position = 2)
    private String beforeTargetId;

    /** 目标行元素 - 主键  拖拽至目标行主键id 【目标位置下一行】 */
    @ApiModelProperty(value = "目标行元素 - 主键  拖拽至目标行主键id【目标位置下一行】", position = 3)
    private String nextTargetId;

}
