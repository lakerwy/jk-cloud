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
package vip.xiaonuo.sys.modular.hr.jkAttendance.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 统计-员工考勤原始记录统计表编辑参数
 *
 * @author ljy
 * @date  2023/11/17 11:36
 **/
@Getter
@Setter
public class OriginalAtdDataEditParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 考勤统计ID */
    @ApiModelProperty(value = "考勤统计ID", position = 2)
    private String atdStatId;

    /** 考勤ID */
    @ApiModelProperty(value = "考勤ID", position = 3)
    private String atdId;

    /** 人员ID */
    @ApiModelProperty(value = "人员ID", position = 4)
    private String userId;

    /** 人员名称 */
    @ApiModelProperty(value = "人员名称", position = 5)
    private String userName;

    /** 考勤组ID */
    @ApiModelProperty(value = "考勤组ID", position = 6)
    private String atdGroupId;

    /** 考勤组名称 */
    @ApiModelProperty(value = "考勤组名称", position = 7)
    private String atdGroupName;

    /** 部门ID */
    @ApiModelProperty(value = "部门ID", position = 8)
    private String orgId;

    /** 部门名称 */
    @ApiModelProperty(value = "部门名称", position = 9)
    private String orgName;

    /** 工号,编号 */
    @ApiModelProperty(value = "工号,编号", position = 10)
    private String empNo;

    /** 职位 */
    @ApiModelProperty(value = "职位", position = 11)
    private String postion;

    /** 考勤日期：年月日-周几 */
    @ApiModelProperty(value = "考勤日期：年月日-周几", position = 12)
    private String atdDate;

    /** 打卡时间 */
    @ApiModelProperty(value = "打卡时间", position = 13)
    private Date checkTime;

    /** 打卡结果 */
    @ApiModelProperty(value = "打卡结果", position = 14)
    private String atdResult;

    /** 打卡是否异常：-1 缺卡，0 管理员修改,1 正常,3 迟到，4 早退，5 请假，6 外出，7 出差 */
    @ApiModelProperty(value = "打卡是否异常：-1 缺卡，0 管理员修改,1 正常,3 迟到，4 早退，5 请假，6 外出，7 出差", position = 15)
    private Integer attendanceFlag;

    /** 打卡位置 */
    @ApiModelProperty(value = "打卡位置", position = 16)
    private String atdLocation;

    /** 打卡备注 */
    @ApiModelProperty(value = "打卡备注", position = 17)
    private String atdRemark;

    /** 异常打卡原因 */
    @ApiModelProperty(value = "异常打卡原因", position = 18)
    private String errorReason;

    /** 附件ID */
    @ApiModelProperty(value = "附件ID", position = 19)
    private String fileId;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 20)
    private String extJson;

}
