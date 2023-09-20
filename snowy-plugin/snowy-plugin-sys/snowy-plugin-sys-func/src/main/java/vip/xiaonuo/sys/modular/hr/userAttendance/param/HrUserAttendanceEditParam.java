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
package vip.xiaonuo.sys.modular.hr.userAttendance.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 打卡记录表编辑参数
 *
 * @author ljy
 * @date  2023/09/07 14:24
 **/
@Getter
@Setter
public class HrUserAttendanceEditParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 人员信息ID */
    @ApiModelProperty(value = "人员信息ID", position = 2)
    private String userId;

    /** 打卡时间 */
    @ApiModelProperty(value = "打卡时间", position = 4)
    private Date checkTime;

    /** 打卡点位(机位) */
    @ApiModelProperty(value = "打卡点位(机位)", position = 5)
    private String machineId;

    /** 打卡是否异常：1 异常，0 正常 */
    @ApiModelProperty(value = "打卡是否异常：1 异常，0 正常", position = 6)
    private Integer abnormalFlag;

    /** 上下班标识；OnDuty：上班；OffDuty：下班 */
    @ApiModelProperty(value = "上下班标识；OnDuty：上班；OffDuty：下班", position = 7)
    private String checkType;

    /** 打卡地址对应的纬度 */
    @ApiModelProperty(value = "打卡地址对应的纬度", position = 8)
    private String latitude;

    /** 打卡地址对应的经度 */
    @ApiModelProperty(value = "打卡地址对应的经度", position = 9)
    private String longitude;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 10)
    private String extJson;

    /** 是否补卡标识 */
    @ApiModelProperty(value = "是否补卡标识", position = 17)
    private Integer isResubmitAttendanceFlag;

    /** 申请补卡ID */
    @ApiModelProperty(value = "申请补卡ID", position = 18)
    private String applyResubmitAttendanceId;
}
