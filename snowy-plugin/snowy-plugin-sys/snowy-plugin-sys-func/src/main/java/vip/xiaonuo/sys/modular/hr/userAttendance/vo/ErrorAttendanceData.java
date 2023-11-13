package vip.xiaonuo.sys.modular.hr.userAttendance.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ErrorAttendanceData {

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

    /** 人员ID */
    @ApiModelProperty(value = "人员ID", position = 1)
    private String userId;

    /** 用户姓名 */
    @ApiModelProperty(value = "用户姓名", position = 2)
    private String userName;

    /** 打卡时间 */
    @ApiModelProperty(value = "实际打卡时间", position = 3)
    private String checkTime;

    @ApiModelProperty(value = "考勤组ID", position = 4)
    private String attendanceGroupId;

    /**考勤组*/
    @ApiModelProperty(value = "考勤组", position = 5)
    private String attendanceGroupName;

    /** 异常状态*/
    @ApiModelProperty(value = "异常状态", position = 6)
    private String errorStatus;

    /** 处理结果*/
    @ApiModelProperty(value = "处理结果", position = 7)
    private String dealResult;

    /** 上下班时间*/
    @ApiModelProperty(value = "上下班时间", position = 8)
    private String time;

    /** 处理时间*/
    @ApiModelProperty(value = "处理时间", position = 9)
    private String dealTime;

    /** 处理人*/
    @ApiModelProperty(value = "处理人", position = 10)
    private String dealUser;

    /** 打卡ID*/
    @ApiModelProperty(value = "打卡ID", position = 11)
    private String attendanceId;

    /** 处理人*/
    @ApiModelProperty(value = "处理人", position = 12)
    private Date updateTime;

    /** 处理时间*/
    @ApiModelProperty(value = "处理时间", position = 13)
    private String updateUser;
}
