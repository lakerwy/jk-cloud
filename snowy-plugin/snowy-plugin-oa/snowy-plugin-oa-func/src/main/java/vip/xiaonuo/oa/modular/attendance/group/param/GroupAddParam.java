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
package vip.xiaonuo.oa.modular.attendance.group.param;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.oa.modular.attendance.group.entity.AttObject;
import vip.xiaonuo.oa.modular.attendance.group.entity.CorrectRule;
import vip.xiaonuo.oa.modular.attendance.workplace.entity.WorkPlace;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 考勤-考勤组添加参数
 *
 * @author mayike
 * @date  2024/06/05 15:51
 **/
@Getter
@Setter
public class GroupAddParam {

    /** 考勤组名称 */
    @ApiModelProperty(value = "考勤组名称", position = 2, required = true)
    @NotBlank(message = "考勤组名称不能为空")
    private String groupName;

    /** 打卡类型 */
    @ApiModelProperty(value = "打卡类型", position = 3)
    private String checkType;

    /** 班次ID */
    @ApiModelProperty(value = "班次ID", position = 4,required = true)
    @NotBlank(message = "未选择班次")
    private String shiftId;

    /** 管理员，存储为 JSON 字符串 */
    @ApiModelProperty(value = "管理员，存储为 JSON 字符串", position = 5)
    private List<AttObject> assistAdmins;

    /** 考勤打卡对象，存储为 JSON 字符串 */
    @ApiModelProperty(value = "考勤打卡对象，存储为 JSON 字符串", position = 6)
    @NotEmpty(message = "考勤对象范围未选择")
    private List<AttObject> participates;

    /** 无需考勤打卡的人员，存储为 JSON 字符串 */
    @ApiModelProperty(value = "无需考勤打卡的人员，存储为 JSON 字符串", position = 7)
    private List<AttObject> unParticipates;

    /** 真实的考勤打卡的人员列表，存储为 JSON 字符串 */
//    @ApiModelProperty(value = "真实的考勤打卡的人员列表，存储为 JSON 字符串", position = 8)
//    private List<AttObject> trueParticipants;

    /** 考勤工作日设置 1111100 表示 周一到周五为工作日 周六周日为休息日 */
    @ApiModelProperty(value = "考勤工作日设置 1111100 表示 周一到周五为工作日 周六周日为休息日", position = 9)
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
    private List<WorkPlace> workPlaceIds;

    /** 非工作日打卡是否按照正常工作日打卡规则 */
    @ApiModelProperty(value = "非工作日打卡是否按照正常工作日打卡规则", position = 14)
    private Boolean weekendWorkday;

    /** 休息日班次Id */
    @ApiModelProperty(value = "休息日班次Id", position = 15)
    private String weekdayShiftId;

    /** 是否开启补卡规则 */
    @ApiModelProperty(value = "是否开启补卡规则", position = 16)
    private Boolean correctionEnabled;

    /** 补卡规则，存储为 JSON 字符串 */
    @ApiModelProperty(value = "补卡规则，存储为 JSON 字符串", position = 17)
    private CorrectRule correctRule;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 18)
    private String remark;

    public void  isValid(){
        if(!weekendWorkday && StrUtil.isEmpty(weekdayShiftId)){
            throw new CommonException("请设置非工作日的打卡规则");
        }
        if(correctionEnabled && ObjectUtil.isNull(correctRule)){
            throw new CommonException("请设置补卡规则");
        }
    }
}
