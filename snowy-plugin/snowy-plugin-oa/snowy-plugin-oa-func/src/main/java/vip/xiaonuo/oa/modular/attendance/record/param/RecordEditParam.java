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
package vip.xiaonuo.oa.modular.attendance.record.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 考勤记录表编辑参数
 *
 * @author mayike
 * @date  2024/06/12 11:03
 **/
@Getter
@Setter
public class RecordEditParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;


    /** 打卡记录日期 */
    @ApiModelProperty(value = "打卡记录时间（后台自动切分成日期 和 时分秒 ）", position = 5)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date checkinDate;

    /** 打卡记录时间 */
    @ApiModelProperty(value = "打卡记录时间", position = 5)
    private Date checkinTime;

    /** 打卡数据来源（用户打卡、系统自动打卡等） */
    @ApiModelProperty(value = "打卡数据来源（用户打卡、系统自动打卡等）", position = 6)
    private String sourceType;

    /** 打卡结果（正常、迟到、早退等） */
    @ApiModelProperty(value = "打卡结果（正常、迟到、早退等）", position = 7)
    private String checkinResult;

    /** 考勤类型（上班、下班） */
    @ApiModelProperty(value = "考勤类型（上班、下班）", position = 8)
    private String checkinType;

    /** 来源设备（Mac、Windows等） */
    @ApiModelProperty(value = "来源设备（Mac、Windows等）", position = 9)
    private String sourceDevice;

    /** 其他说明备注 */
    @ApiModelProperty(value = "其他说明备注", position = 10)
    private String description;

    /** 打卡地点描述 */
    @ApiModelProperty(value = "打卡地点描述", position = 11)
    private String recordAddress;

    /** 经度 */
    @ApiModelProperty(value = "经度", position = 12)
    private Double longitude;

    /** 纬度 */
    @ApiModelProperty(value = "纬度", position = 13)
    private Double latitude;

    /** 外勤打卡说明 */
    @ApiModelProperty(value = "外勤打卡说明", position = 14)
    private String signDescription;

    /** 是否外勤打卡 */
    @ApiModelProperty(value = "是否外勤打卡", position = 15)
    private Boolean fieldWork;

    /** 考勤组 ID */
    @ApiModelProperty(value = "考勤组 ID", position = 16)
    private String groupId;

    /** 班次 ID */
    @ApiModelProperty(value = "班次 ID", position = 17)
    private String shiftId;

    /** 工作场所 ID */
    @ApiModelProperty(value = "工作场所 ID", position = 18)
    private String workPlaceId;

    /** 下班打卡是否次日 */
    @ApiModelProperty(value = "下班打卡是否次日", position = 19)
    private Boolean offDutyNextDay;

    /** 考勤申诉信息记录 ID */
    @ApiModelProperty(value = "考勤申诉信息记录 ID", position = 20)
    private String appealId;

    /** 请假数据 ID */
    @ApiModelProperty(value = "请假数据 ID", position = 21)
    private String leaveDataId;

    /** 备注文件地址 */
    @ApiModelProperty(value = "备注文件地址", position = 22)
    private String attachMent;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 23)
    private String extJson;

}
