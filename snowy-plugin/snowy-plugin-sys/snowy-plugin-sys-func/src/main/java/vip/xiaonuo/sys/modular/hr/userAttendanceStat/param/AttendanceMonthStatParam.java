package vip.xiaonuo.sys.modular.hr.userAttendanceStat.param;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.vo.AttendanceMonthStatData;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class AttendanceMonthStatParam {
    /** 当前页 */
    @ApiModelProperty(value = "当前页码", position = 1)
    private Integer current;

    /** 每页条数 */
    @ApiModelProperty(value = "每页条数", position = 2)
    private Integer size;

    /** 排序字段 */
    @ApiModelProperty(value = "排序字段，字段驼峰名称，如：userName", position = 3)
    private String sortField;

    /** 排序方式 */
    @ApiModelProperty(value = "排序方式，升序：ASCEND；降序：DESCEND", position = 4)
    private String sortOrder;

    /** 关键词 */
    @ApiModelProperty(value = "关键词", position = 5)
    private String searchKey;

    @ApiModelProperty(value = "用户ID", position = 6)
    private String userId;

    @ApiModelProperty(value = "用户名称", position = 7)
    private String userName;

    @ApiModelProperty(value = "部门ID", position = 8)
    private String orgId;

    @ApiModelProperty(value = "部门名称", position = 9)
    private String orgName;

    @ApiModelProperty(value = "考勤组ID", position = 10)
    private String attendanceTypeId;

    @ApiModelProperty(value = "考勤组名称", position = 11)
    private String attendanceTypeName;

    @ApiModelProperty(value = "职位", position = 12)
    private String position;

    @ApiModelProperty(value = "开始时间", required = true,position = 13)
    @NotNull(message = "beginTime不能为空")
    private String beginTime;

    @ApiModelProperty(value = "结束时间", required = true,position = 14)
    @NotNull(message = "endTime不能为空")
    private String endTime;

    @ApiModelProperty(value = "无需统计人员ID列表", position = 16)
    private List<String> userList;

    @ApiModelProperty(value = "出勤天数", position = 17)
    private Integer workDays;

    @ApiModelProperty(value = "迟到次数", position = 18)
    private Integer lateNum;

    @ApiModelProperty(value = "迟到时长-单位：分钟", position = 19)
    private Long lateTime;

    @ApiModelProperty(value = "上班缺卡次数", position = 20)
    private Integer onDutyLackNum;

    @ApiModelProperty(value = "下班缺卡次数", position = 21)
    private Integer offDutyLackNum;

    @ApiModelProperty(value = "旷工天数", position = 22)
    private Integer absenteeismNum;

    @ApiModelProperty(value = "事假-单位；半天", position = 23)
    private Double personalLeaveNum;

    @ApiModelProperty(value = "病假", position = 24)
    private Double sickLeaveNum;

    @ApiModelProperty(value = "婚假", position = 25)
    private Double marriageLeaveNum;

    @ApiModelProperty(value = "产假", position = 26)
    private Double maternityLeaveNum;

    @ApiModelProperty(value = "丧假", position = 27)
    private Double bereavementLeave;

    @ApiModelProperty(value = "年假", position = 28)
    private Double annualLeave;

    @ApiModelProperty(value = "调休", position = 29)
    private Double compensatoryLeave;

    @ApiModelProperty(value = "无需统计部门ID列表", position = 30)
    private List<String> orgList;

    @ApiModelProperty(value = "考勤结果", position = 31)
    private List<Map<Integer, AttendanceMonthStatData>> monthStatList;

    @ApiModelProperty(value = "是否为工作日", position = 32)
    private Boolean isWorkDay;

    @ApiModelProperty(value = "加班时长-单位：小时", position = 33)
    private Double workOvertime;

    @ApiModelProperty(value = "员工编号", position = 34)
    private String empNo;
}
