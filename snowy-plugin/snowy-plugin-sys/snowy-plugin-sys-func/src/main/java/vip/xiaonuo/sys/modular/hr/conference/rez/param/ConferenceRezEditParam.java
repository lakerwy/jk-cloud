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
package vip.xiaonuo.sys.modular.hr.conference.rez.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.sys.modular.hr.conference.userSign.entity.ConferenceUserSign;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

/**
 * 会议模块-预约会议编辑参数
 *
 * @author ljy
 * @date  2023/12/01 23:35
 **/
@Getter
@Setter
public class ConferenceRezEditParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 周期会议ID */
    @ApiModelProperty(value = "周期会议ID,修改周期预约需传ID和周,月时间", position = 2)
    private String rezPeriodId;

    /** 会议室ID */
    @ApiModelProperty(value = "会议室ID", position = 3)
    private String conferenceConfigId;

    /** 预约主题 */
    @ApiModelProperty(value = "预约主题", position = 4)
    private String theme;

    /** 开始时间 */
    @ApiModelProperty(value = "开始时间", position = 5)
    private Date beginTime;

    /** 结束时间 */
    @ApiModelProperty(value = "结束时间", position = 6)
    private Date endTime;

    /** 参与者 */
    @ApiModelProperty(value = "参与者", position = 7)
    private String joinUser;

    /** 组织部门 */
    @ApiModelProperty(value = "组织部门", position = 8)
    private String joinOrg;

    /** 参与人数 */
    @ApiModelProperty(value = "参与人数", position = 9)
    private Integer joinSize;

    /** 预约人 */
    @ApiModelProperty(value = "预约人", position = 10)
    private String rezUser;

    /** 主持人 */
    @ApiModelProperty(value = "主持人", position = 11)
    private String presideUser;

    /** 会议记录人 */
    @ApiModelProperty(value = "会议记录人", position = 12)
    private String recordUser;

    /** 会务人员：多个 */
    @ApiModelProperty(value = "会务人员：多个", position = 13)
    private String conAffUsers;

    /** 会议室位置 */
    @ApiModelProperty(value = "会议室位置", position = 14)
    private String location;

    /** 外部人员 */
    @ApiModelProperty(value = "外部人员", position = 15)
    private String outsideUser;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 16)
    private String remark;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 17)
    private String fileId;

    /** 会议号 */
    @ApiModelProperty(value = "会议号", position = 18)
    private String onLineCode;

    /** 会议平台名称 */
    @ApiModelProperty(value = "会议平台名称", position = 19)
    private String onLineName;

    /** 会议URL */
    @ApiModelProperty(value = "会议URL", position = 20)
    private String onLineUrl;

    /** 会议议程 */
    @ApiModelProperty(value = "会议议程", position = 21)
    private String rezAgenda;

    /** 会议记录 */
    @ApiModelProperty(value = "会议记录", position = 22)
    private String record;

    /** 审核人 */
    @ApiModelProperty(value = "审核人", position = 23)
    private String verifyUserId;

    /** 审核人名称 */
    @ApiModelProperty(value = "审核人名称", position = 24)
    private String verifyUserName;

    /** 审核状态：0 审核中，1 审核通过，2 审核未通过 */
    @ApiModelProperty(value = "审核状态：0 审核中，1 审核通过，2 审核未通过", position = 25)
    private Integer verifyStatus;

    /** 组织部门 */
    @ApiModelProperty(value = "组织部门", position = 26)
    private String orgDept;

    /** 缺席人员 */
    @ApiModelProperty(value = "缺席人员", position = 27)
    private String absents;

    /** 缺席原因 */
    @ApiModelProperty(value = "缺席原因", position = 28)
    private String absentReason;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 29)
    private String extJson;

    /** 周期预约参数 */
    /** 对应周几：1 周一，2 周二，3 周三 ... */
    @ApiModelProperty(value = "周期预约参数：对应周几：1 周一，2 周二，3 周三 ...", position = 30)
    private Integer weekTime;

    /** 每月的哪一天 */
    @ApiModelProperty(value = "周期预约参数：每月的哪一天", position = 31)
    private Integer dayTime;

    /** 会议纪要*/
    @ApiModelProperty(value = "会议纪要", position = 32)
    private String confSummary;

    /** 外部签到人列表 */
    @ApiModelProperty(value = "外部签到人列表", position = 33)
    private List<ConferenceUserSign> outsideSignList;

    /** 签到人列表 */
    @ApiModelProperty(value = "签到人列表", position = 34)
    private List<ConferenceUserSign> conferenceUserSignList;
}
