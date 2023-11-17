package vip.xiaonuo.sys.modular.hr.userAttendanceStat.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
public class OriginalAtdParam {
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

    @ApiModelProperty(value = "用户ID", position = 1)
    private List<String> userIds;

    @ApiModelProperty(value = "部门ID", position = 2)
    private List<String> orgIds;

    @ApiModelProperty(value = "考勤组ID", position = 3)
    private List<String> attendanceGroupIds;

    @ApiModelProperty(value = "开始时间-年月日-时分秒", required = true,position = 4)
    @NotNull(message = "开始时间不为空")
    private String startTime;

    @ApiModelProperty(value = "结束时间-年月日-时分秒", required = true,position = 5)
    @NotNull(message = "结束时间不为空")
    private String endTime;
}
