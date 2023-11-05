package vip.xiaonuo.sys.modular.hr.userAttendanceStat.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
public class AttendanceAnalysisData {

    /** 时间列表 */
    @ApiModelProperty(value = "时间列表-下标")
    private List<String> dayList;

    /** 应到人数 */
    @ApiModelProperty(value = "应到人数")
    private Long shouldAtNum;
    /** 应到人数 */
    /*@ApiModelProperty(value = "应到人数列表") // key -时间，value - 值
    private Map<String,String> shouldAtNumMap;*/
    /** 应到人数和昨日对比结果 */
    @ApiModelProperty(value = "应到人数和之前对比结果")
    private String shouldAtCompare;

    /** 打卡人数 */
    @ApiModelProperty(value = "打卡人数")
    private Long attendanceNum;
    /** 打卡人数列表 */
    /*@ApiModelProperty(value = "打卡人数列表")
    private Map<String,String> attendanceNumMap;*/
    /** 打卡人数和昨日对比结果 */
    @ApiModelProperty(value = "打卡人数和之前对比结果")
    private String attendanceCompare;


    /** 出勤率 */
    @ApiModelProperty(value = "出勤率")
    private String attendanceRate;
    /** 出勤率列表 */
    /*@ApiModelProperty(value = "出勤率列表")
    private Map<String,String> attendanceRateMap;*/
    /** 出勤率和昨日对比结果 */
    @ApiModelProperty(value = "出勤率和之前对比结果")
    private String attendanceRateCompare;

    /** 平均工时 */
    @ApiModelProperty(value = "平均工时")
    private Double averageWkHour;
    /** 平均工时列表 */
    /*@ApiModelProperty(value = "平均工时列表")
    private Map<String,String> averageWkHourMap;*/
    /** 平均工时列表和之前对比结果 */
    @ApiModelProperty(value = "平均工时列表和之前对比结果")
    private String averageWkHourCompare;

    /** 请假人数 */
    @ApiModelProperty(value = "请假人数")
    private Integer leaveNum;
    /** 请假人数列表 */
    /*@ApiModelProperty(value = "请假人数列表")
    private Map<String,String> leaveNumMap;*/
    /** 请假人数和之前对比结果 */
    @ApiModelProperty(value = "请假人数和之前对比结果")
    private String leaveNumCompare;


    /** 加班人数 */
    @ApiModelProperty(value = "加班人数")
    private Integer workOtNum;
    /** 加班人数列表 */
   /* @ApiModelProperty(value = "加班人数列表")
    private Map<String,String> workOtNumMap;*/
    /** 加班人数和之前对比结果 */
    @ApiModelProperty(value = "加班人数和之前对比结果")
    private String workOtNumCompare;


    /** 迟到人数 */
    @ApiModelProperty(value = "迟到人数")
    private Integer lateNum;
    /** 迟到人数列表 */
    /*@ApiModelProperty(value = "迟到人数列表")
    private Map<String,String> lateNumMap;*/
    /** 迟到人数和之前对比结果 */
    @ApiModelProperty(value = "迟到人数和之前对比结果")
    private String lateNumCompare;

    /** 部门明细:部门列表 */
    @ApiModelProperty(value = "部门列表")
    private List<String> orgNameList;
    /** 部门明细:部门数据 */
    @ApiModelProperty(value = "部门数据:key代表是返回的哪种类型的数据")
    private List<Map<String,Object>> orgDataMap;


    @ApiModelProperty(value = "横坐标数据")
    private String[] xAxis;

    @ApiModelProperty(value = "具体数据1")
    private String[] firstData;

    @ApiModelProperty(value = "具体数据1")
    private String[] secondData;

}
