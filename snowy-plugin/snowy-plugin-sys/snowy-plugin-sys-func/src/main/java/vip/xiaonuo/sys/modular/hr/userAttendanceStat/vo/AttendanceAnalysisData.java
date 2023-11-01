package vip.xiaonuo.sys.modular.hr.userAttendanceStat.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AttendanceAnalysisData {

    /** 时间列表 */
    @ApiModelProperty(value = "时间列表")
    private List<String> dayList;

    /** 应到人数 */
    @ApiModelProperty(value = "应到人数")
    private Integer shouldAtNum;

    /** 应到人数 */
    @ApiModelProperty(value = "应到人数列表")
    private List<Integer> shouldAtNumList;

    /** 打卡人数 */
    @ApiModelProperty(value = "打卡人数")
    private Integer attendanceNum;

    /** 打卡人数列表 */
    @ApiModelProperty(value = "打卡人数列表")
    private List<Integer> attendanceNumList;

    /** 出勤率 */
    @ApiModelProperty(value = "出勤率")
    private String attendanceRate;

    /** 出勤率列表 */
    @ApiModelProperty(value = "出勤率列表")
    private List<String> attendanceRateList;

    /** 平均工时 */
    @ApiModelProperty(value = "平均工时")
    private Double averageWkHour;

    /** 平均工时列表 */
    @ApiModelProperty(value = "平均工时列表")
    private List<Double> averageWkHourList;

    /** 请假人数 */
    @ApiModelProperty(value = "请假人数")
    private Integer leaveNum;

    /** 请假人数列表 */
    @ApiModelProperty(value = "请假人数列表")
    private List<Integer> leaveNumList;

    /** 加班人数 */
    @ApiModelProperty(value = "加班人数")
    private Integer workOtNum;

    /** 加班人数列表 */
    @ApiModelProperty(value = "加班人数列表")
    private List<Integer> workOtNumList;

    /** 迟到人数 */
    @ApiModelProperty(value = "迟到人数")
    private Integer lateNum;

    /** 迟到人数列表 */
    @ApiModelProperty(value = "迟到人数列表")
    private List<Integer> lateNumList;
}
