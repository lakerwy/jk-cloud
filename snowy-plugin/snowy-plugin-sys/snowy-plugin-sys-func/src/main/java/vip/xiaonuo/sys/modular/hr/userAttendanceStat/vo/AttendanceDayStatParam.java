package vip.xiaonuo.sys.modular.hr.userAttendanceStat.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class AttendanceDayStatParam {
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
    private String userId;

    @ApiModelProperty(value = "用户名称", position = 2)
    private String userName;

    @ApiModelProperty(value = "部门ID", position = 3)
    private String orgId;

    @ApiModelProperty(value = "部门名称", position = 4)
    private String orgName;

    @ApiModelProperty(value = "考勤组ID", position = 5)
    private String attendanceTypeId;

    @ApiModelProperty(value = "考勤组名称", position = 6)
    private String attendanceTypeName;

    @ApiModelProperty(value = "职位", position = 7)
    private String position;

    @ApiModelProperty(value = "打卡记录和状态", position = 8)
    private List<Map<String,List<AttendanceDayStatData>>> dayCheckTimeMapList;

    @ApiModelProperty(value = "开始时间", required = true,position = 9)
    @NotNull(message = "beginTime不能为空")
    private String beginTime;

    @ApiModelProperty(value = "结束时间", required = true,position = 10)
    @NotNull(message = "endTime不能为空")
    private String endTime;

    @ApiModelProperty(value = "无需统计人员ID列表", position = 11)
    private List<String> userList;

    @ApiModelProperty(value = "无需统计部门ID列表", position = 12)
    private List<String> orgList;

    @ApiModelProperty(value = "是否为工作日", position = 13)
    private Boolean isWorkDay;
}
