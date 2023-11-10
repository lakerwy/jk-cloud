/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.sys.modular.hr.userAttendanceConfig.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.vo.HrUserAttendanceConfigEntity;

import java.sql.Time;
import java.util.List;

/**
 * 打卡班次配置表编辑参数
 *
 * @author ljy
 * @date  2023/09/07 13:07
 **/
@Getter
@Setter
public class HrUserAttendanceConfigEditParam {

    /** 主键id */
    @ApiModelProperty(value = "主键id", required = true, position = 1)
    private String id;

    /** 排班顺序 */
    @ApiModelProperty(value = "排班顺序", position = 3)
    private Integer orderNumber;

    /** 考勤班次类别名 */
    @ApiModelProperty(value = "考勤班次类别名", position = 4)
    private String attendanceTypeName;

    /** 班次打卡类别 */
    @ApiModelProperty(value = "班次打卡类别 (上班OnDuty，下班 OffDuty，休息 REST)", position = 4)
    private String dutyType;

    /** 允许最早多少时间打卡 */
    @ApiModelProperty(value = "允许最早多少时间打卡", position = 5)
    private String BeginMinTime;

    /** 打卡时间 */
    @ApiModelProperty(value = "打卡时间", position = 6)
    private Time checkTime;

    /** 允许最晚多少时间打卡 */
    @ApiModelProperty(value = "允许最晚多少时间打卡", position = 7)
    private String endMinTime;

    /** 作用范围区域  */
    @ApiModelProperty(value = "作用范围区域 ", position = 8)
    private String actionRange;

    /** 区域范围是否有效 */
    @ApiModelProperty(value = "区域范围是否有效", position = 9)
    private Integer isValidRange;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 12)
    private String extJson;

    /** 班次工时，单位：小时 */
    @ApiModelProperty(value = "班次工时，单位：小时", position = 14)
    private String workHours;

    /** 考勤班次类别组ID */
    @ApiModelProperty(value = "考勤班次类别组ID", position = 17)
    private String attendanceTypeId;

    /** 考勤班次类别组名称 */
    @ApiModelProperty(value = "考勤班次类别组名称", position = 18)
    private String attendanceTypName;

    /** 负责人ID */
    @ApiModelProperty(value = "负责人ID", position = 19)
    private String leadId;

    /** 负责人名称 */
    @ApiModelProperty(value = "负责人名称", position = 20)
    private String leadName;

    @ApiModelProperty(value = "考勤配置list", position = 21)
    private List<HrUserAttendanceConfigEntity>  userAttendanceConfigEntityList;

    /** 班次应用人数 */
    @ApiModelProperty(value = "班次应用人数", position = 5)
    private Integer attendanceUserNum;


    /** 严重迟到分钟数 */
    @ApiModelProperty(value = "严重迟到分钟数", position = 15)
    private Integer seriousLateMinute;

    /** 严重早退分钟数 */
    @ApiModelProperty(value = "严重早退分钟数", position = 15)
    private Integer seriousLeaveMinute;
}
