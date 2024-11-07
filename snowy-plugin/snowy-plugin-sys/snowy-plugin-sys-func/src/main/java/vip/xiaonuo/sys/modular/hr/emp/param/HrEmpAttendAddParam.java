package vip.xiaonuo.sys.modular.hr.emp.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 员工月度考勤表 - 单独 - 非工资系统添加参数
 *
 * @author yong.ye
 * @date  2024/11/06 16:53
 **/
@Getter
@Setter
public class HrEmpAttendAddParam {

    /** 所属年月 */
    @ApiModelProperty(value = "所属年月", position = 2)
    private String yearMonth;

    /** 员工类型：总部|分公司|外部 */
    @ApiModelProperty(value = "员工类型：总部|分公司|外部", position = 3)
    private String userType;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号", position = 4)
    private String idCardNumber;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 5)
    private String userName;

    /** 所属公司 */
    @ApiModelProperty(value = "所属公司", position = 6)
    private String company;

    /** 建科云用户Id */
    @ApiModelProperty(value = "建科云用户Id", position = 7)
    private String userId;

    /** 总部+分公司-花名册Id */
    @ApiModelProperty(value = "总部+分公司-花名册Id", position = 8)
    private String personnelId;

    /** 一级部门 */
    @ApiModelProperty(value = "一级部门", position = 9)
    private String firstOrgName;

    /** 二级部门 */
    @ApiModelProperty(value = "二级部门", position = 10)
    private String orgName;

    /** 职位 */
    @ApiModelProperty(value = "职位", position = 11)
    private String position;

    /** 未打卡（上班） */
    @ApiModelProperty(value = "未打卡（上班）", position = 12)
    private Integer upMissNum;

    /** 未打卡（下班） */
    @ApiModelProperty(value = "未打卡（下班）", position = 13)
    private Integer downMissNum;

    /** 迟到次数-A类 */
    @ApiModelProperty(value = "迟到次数-A类", position = 14)
    private Integer lateNumA;

    /** 迟到次数-B类 */
    @ApiModelProperty(value = "迟到次数-B类", position = 15)
    private Integer lateNumB;

    /** 迟到次数-C类 */
    @ApiModelProperty(value = "迟到次数-C类", position = 16)
    private Integer lateNumC;

    /** 病假天数 */
    @ApiModelProperty(value = "病假天数", position = 17)
    private Float sickDay;

    /** 事假天数 */
    @ApiModelProperty(value = "事假天数", position = 18)
    private Float workDay;

    /** 矿工天数 */
    @ApiModelProperty(value = "矿工天数", position = 19)
    private Float minerDay;

    /** 年假天数 */
    @ApiModelProperty(value = "年假天数", position = 20)
    private Float yearDay;

    /** 产假天数 */
    @ApiModelProperty(value = "产假天数", position = 21)
    private Float materDay;

    /** 婚假天数 */
    @ApiModelProperty(value = "婚假天数", position = 22)
    private Float marryDay;

    /** 丧假天数 */
    @ApiModelProperty(value = "丧假天数", position = 23)
    private Float funDay;

    /** 是否全勤 */
    @ApiModelProperty(value = "是否全勤", position = 24)
    private String isFull;

    /** 应计薪天数 */
    @ApiModelProperty(value = "应计薪天数", position = 25)
    private Float requiredDay;

    /** 实勤天数 */
    @ApiModelProperty(value = "实勤天数", position = 26)
    private Float realDay;

    /** 调休（h） */
    @ApiModelProperty(value = "调休（h）", position = 27)
    private Float adjustHour;

    /** 实扣天数-当月病假（一个月以上病假） */
    @ApiModelProperty(value = "实扣天数-当月病假（一个月以上病假）", position = 28)
    private Float realMonthSickDay;

    /** 实扣天数-病假（一个月以内） */
    @ApiModelProperty(value = "实扣天数-病假（一个月以内）", position = 29)
    private Float realUnMonthSickDay;

    /** 实扣天数-产假 */
    @ApiModelProperty(value = "实扣天数-产假", position = 30)
    private Float realMaterDay;

    /** 实扣天数-未打卡（上班） */
    @ApiModelProperty(value = "实扣天数-未打卡（上班）", position = 31)
    private Integer realUpMissNum;

    /** 实扣天数-未打卡（下班） */
    @ApiModelProperty(value = "实扣天数-未打卡（下班）", position = 32)
    private Integer realDownMissNum;

    /** 加班时长（h） */
    @ApiModelProperty(value = "加班时长（h）", position = 33)
    private Float overtimeHour;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 34)
    private String memo;

}
