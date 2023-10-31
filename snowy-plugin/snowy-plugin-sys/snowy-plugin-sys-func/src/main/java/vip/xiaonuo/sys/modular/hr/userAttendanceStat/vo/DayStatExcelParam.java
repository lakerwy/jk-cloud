package vip.xiaonuo.sys.modular.hr.userAttendanceStat.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.entity.HrUserAttendanceStat;

import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
public class DayStatExcelParam {
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

    @ApiModelProperty(value = "用户ID--非参数", position = 1)
    private String userId;

    @ApiModelProperty(value = "用户名称", position = 2)
    private String userName;

    @ApiModelProperty(value = "部门ID--非参数", position = 3)
    private String orgId;

    @ApiModelProperty(value = "部门名称", position = 4)
    private String orgName;

    @ApiModelProperty(value = "员工编号", position = 5)
    private String empNo;

    @ApiModelProperty(value = "考勤组ID", position = 6)
    private String attendanceTypeId;

    @ApiModelProperty(value = "考勤组名称", position = 7)
    private String attendanceTypeName;

    @ApiModelProperty(value = "日期，年月日-星期几", position = 8)
    private String date;

    @ApiModelProperty(value = "班次详情", position = 9)
    private String attendanceTypeDetails;

    @ApiModelProperty(value = "职位", position = 10)
    private String position;

    @ApiModelProperty(value = "打卡记录", position = 11)
    private List<HrUserAttendanceStat> userAttendanceStatList;

    @ApiModelProperty(value = "出勤天数", position = 12)
    private Double attendanceDays;

    @ApiModelProperty(value = "休息天数", position = 13)
    private Double restDays;

    @ApiModelProperty(value = "开始时间:yyyy-MM-dd HH:mm:ss", required = true,position = 14)
    @NotNull(message = "beginTime不能为空")
    private String beginTime;

    @ApiModelProperty(value = "结束时间:yyyy-MM-dd HH:mm:ss", required = true,position = 15)
    @NotNull(message = "endTime不能为空")
    private String endTime;

    @ApiModelProperty(value = "人员ID列表", position = 16)
    private List<String> userList;

    @ApiModelProperty(value = "部门ID列表", position = 17)
    private List<String> orgList;

    @ApiModelProperty(value = "是否为工作日", position = 18)
    private Boolean isWorkDay;

    @ApiModelProperty(value = "工作时长", position = 19)
    private Integer workMinute;

    @ApiModelProperty(value = "迟到次数", position = 20)
    private Integer lateNum;

    @ApiModelProperty(value = "迟到时长", position = 21)
    private Integer lateMinute;

    @ApiModelProperty(value = "严重迟到次数", position = 22)
    private Integer seriousLateNum;

    @ApiModelProperty(value = "严重迟到时长", position = 23)
    private Integer seriousLateMinute;

    @ApiModelProperty(value = "早退次数", position = 24)
    private Integer leaveEarlyNum;

    @ApiModelProperty(value = "早退时长", position = 25)
    private Integer leaveEarlyMinute;

    @ApiModelProperty(value = "缺卡次数", position = 25)
    private Integer lackAttendance;

    /** 旷工数据，出差数据，外出数据*/

    /** 事假 */
    @ApiModelProperty(value = "事假", position = 26)
    private String personalLeave;

    /** 病假 */
    @ApiModelProperty(value = "病假", position = 27)
    private String sickLeave;

    /** 婚假 */
    @ApiModelProperty(value = "婚假", position = 28)
    private String marriageLeave;

    /** 产假 */
    @ApiModelProperty(value = "产假", position = 29)
    private String maternityLeave;

    /** 丧假 */
    @ApiModelProperty(value = "丧假", position = 30)
    private String bereavementLeave;

    /** 年假 */
    @ApiModelProperty(value = "年假", position = 31)
    private String annualLeave;

    /** 调休 */
    @ApiModelProperty(value = "调休", position = 32)
    private String compensatoryLeave;

    @ApiModelProperty(value = "加班总时长", position = 33)
    private Double workOtHours;

    @ApiModelProperty(value = "关联的审批单", position = 34)
    private String processorIdentifier;

    @ApiModelProperty(value = "具体日期-年月日", position = 8)
    private String day;
}
