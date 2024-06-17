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
package vip.xiaonuo.oa.modular.attendance.group.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.oa.modular.attendance.workplace.entity.WorkPlace;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 考勤-考勤组实体
 *
 * @author mayike
 * @date  2024/06/05 15:51
 **/
@Getter
@Setter
@TableName(value = "OA_ATTN_GROUP",autoResultMap = true)
public class Group {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 考勤组名称 */
    @ApiModelProperty(value = "考勤组名称", position = 2)
    private String groupName;

    /** 打卡类型 */
    @ApiModelProperty(value = "打卡类型", position = 3)
    private String checkType;

    /** 班次ID */
    @ApiModelProperty(value = "班次ID", position = 4)
    private String shiftId;

    /** 管理员，存储为 JSON 字符串 */
    @ApiModelProperty(value = "管理员，存储为 JSON 字符串", position = 5)
    @TableField(value = "ASSIST_ADMINS", typeHandler = FastjsonTypeHandler.class)
    private List<AttObject> assistAdmins;

    /** 考勤打卡对象，存储为 JSON 字符串 */
    @ApiModelProperty(value = "考勤打卡对象，存储为 JSON 字符串", position = 6)
    @TableField(value = "PARTICIPATES", typeHandler = FastjsonTypeHandler.class)
    private List<AttObject> participates;

    /** 无需考勤打卡的人员，存储为 JSON 字符串 */
    @ApiModelProperty(value = "无需考勤打卡的人员，存储为 JSON 字符串", position = 7)
    @TableField(value = "UN_PARTICIPATES", typeHandler = FastjsonTypeHandler.class)
    private List<AttObject> unParticipates;

    /** 真实的考勤打卡的人员列表，存储为 JSON 字符串 */
    @ApiModelProperty(value = "真实的考勤打卡的人员列表，存储为 JSON 字符串", position = 8)
    @TableField(value = "TRUE_PARTICIPANTS", typeHandler = FastjsonTypeHandler.class)
    private List<AttObject> trueParticipants;

    /** 考勤工作日设置，打卡类型为自由打卡时配置，如 1,2,3,4,5 */
    @ApiModelProperty(value = "考勤工作日设置 0111110 表示 周一到周五为工作日  周六周日为休息日", position = 9)
    private String workDates;

    /** 是否允许外勤打卡，1表示允许，0表示不允许，默认为允许 */
    @ApiModelProperty(value = "是否允许外勤打卡，1表示允许，0表示不允许，默认为允许", position = 10)
    private Boolean allowFieldWork;

    /** 外勤打卡备注是否必填，1表示必填，0表示非必填，默认为必填 */
    @ApiModelProperty(value = "外勤打卡备注是否必填，1表示必填，0表示非必填，默认为必填", position = 11)
    private Boolean requiredFieldWorkRemarks;

    /** 外勤打卡是否作为异常数据处理，1表示作为异常处理，0表示不作为异常处理，默认为作为异常处理 */
    @ApiModelProperty(value = "外勤打卡是否作为异常数据处理，1表示作为异常处理，0表示不作为异常处理，默认为作为异常处理", position = 12)
    private Boolean fieldWorkMarkError;

    /** 工作场所列表，存储为 JSON 字符串 */
    @ApiModelProperty(value = "工作场所列表，存储为 JSON 字符串", position = 13)
    @TableField(value = "WORK_PLACE_IDS", typeHandler = FastjsonTypeHandler.class)
    private List<WorkPlace> workPlaceIds;

    @ApiModelProperty(value = "非工作日打卡是否按照正常工作日打卡规则", position = 14)
    private Boolean weekendWorkday;

    @ApiModelProperty(value = "休息日班次Id", position = 15)
    private String weekDayShiftId;

    @ApiModelProperty(value = "是否开启补卡规则", position = 16)
    private Boolean  correctionEnabled;

    @ApiModelProperty(value = "补卡规则", position = 7)
    @TableField(value = "CORRECT_RULE", typeHandler = FastjsonTypeHandler.class)
    private CorrectRule correctRule;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 16)
    private String remark;

    /** 租户ID */
    @ApiModelProperty(value = "租户ID", position = 17)
    private String tenantId;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 18)
    private String extJson;

    /** 删除标志，默认为0 */
    @ApiModelProperty(value = "删除标志，默认为0", position = 19)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 20)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 21)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 22)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 23)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;
}
