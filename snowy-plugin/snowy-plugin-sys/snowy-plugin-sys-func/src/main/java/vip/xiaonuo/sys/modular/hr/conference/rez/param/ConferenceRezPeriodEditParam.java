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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 会议模块-周期预约会议编辑参数
 *
 * @author ljy
 * @date  2023/12/02 10:18
 **/
@Getter
@Setter
public class ConferenceRezPeriodEditParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 会议室ID */
    @ApiModelProperty(value = "会议室ID", position = 2)
    private String conferenceConfigId;

    /** 预约主题 */
    @ApiModelProperty(value = "预约主题", position = 3)
    private String theme;

    /** 开始时间 */
    @ApiModelProperty(value = "开始时间", position = 4)
    private Date beginTime;

    /** 结束时间 */
    @ApiModelProperty(value = "结束时间", position = 5)
    private Date endTime;

    /** 参与者 */
    @ApiModelProperty(value = "参与者", position = 6)
    private String joinUser;

    /** 参与人数 */
    @ApiModelProperty(value = "参与人数", position = 7)
    private Integer joinSize;

    /** 预约人 */
    @ApiModelProperty(value = "预约人", position = 8)
    private String rezUser;

    /** 会议记录人 */
    @ApiModelProperty(value = "会议记录人", position = 9)
    private String recordUser;

    /** 主持人 */
    @ApiModelProperty(value = "主持人", position = 10)
    private String presideUser;

    /** 会务人员：多个 */
    @ApiModelProperty(value = "会务人员：多个", position = 11)
    private String conAffUsers;

    /** 会议室位置 */
    @ApiModelProperty(value = "会议室位置", position = 12)
    private String location;

    /** 外部人员 */
    @ApiModelProperty(value = "外部人员", position = 13)
    private String outsideUser;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 14)
    private String remark;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 15)
    private String fileId;

    /** 对应周几：1 周一，2 周二，3 周三 ... */
    @ApiModelProperty(value = "对应周几：1 周一，2 周二，3 周三 ...", position = 16)
    private Integer weekTime;

    /** 对应月的哪一天 */
    @ApiModelProperty(value = "对应月的哪一天", position = 17)
    private Integer dayTime;

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

    /** 组织部门 */
    @ApiModelProperty(value = "组织部门", position = 22)
    private String orgDept;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 23)
    private String extJson;

}
