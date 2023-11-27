package vip.xiaonuo.sys.modular.hr.userAttendanceStat.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class MonthPreDayStatParam {
    /** 当前页 */
    @ApiModelProperty(value = "当前页码")
    private Integer current;

    /** 每页条数 */
    @ApiModelProperty(value = "每页条数")
    private Integer size;

    /** 排序字段 */
    @ApiModelProperty(value = "排序字段，字段驼峰名称，如：userName")
    private String sortField;

    /** 排序方式 */
    @ApiModelProperty(value = "排序方式，升序：ASCEND；降序：DESCEND")
    private String sortOrder;

    /** 关键词 */
    @ApiModelProperty(value = "关键词")
    private String searchKey;

    @ApiModelProperty(value = "开始时间:yyyy-MM-dd HH:mm:ss", required = true,position = 14)
    @NotNull(message = "beginTime不能为空")
    private String beginTime;

    @ApiModelProperty(value = "结束时间:yyyy-MM-dd HH:mm:ss", required = true,position = 15)
    @NotNull(message = "endTime不能为空")
    private String endTime;
}
