package vip.xiaonuo.sys.modular.hr.conference.record.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class RezRecordVo {

    /** 会议主键 */
    @ApiModelProperty(value = "会议主键", position = 1)
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

    /** 会议室位置 */
    @ApiModelProperty(value = "会议室位置", position = 9)
    private String location;

    /** 外部人员 */
    @ApiModelProperty(value = "外部人员", position = 10)
    private String outsideUser;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 11)
    private String remark;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 12)
    private String fileId;

    /** 主持人 */
    @ApiModelProperty(value = "主持人", position = 22)
    private String presideUser;

    /** 会议记录人 */
    @ApiModelProperty(value = "会议记录人", position = 23)
    private String recordUser;

    /** 会务人员：多个 */
    @ApiModelProperty(value = "会务人员：多个", position = 24)
    private String conAffUsers;

    /** 在线会议号 */
    @ApiModelProperty(value = "在线会议号", position = 21)
    private String onLineCode;

    /** 在线会议号平台名称 */
    @ApiModelProperty(value = "在线会议号平台名称", position = 22)
    private String onLineName;

    /** 会议链接 */
    @ApiModelProperty(value = "会议链接", position = 23)
    private String onLineUrl;

    /** 会议议程 */
    @ApiModelProperty(value = "会议议程", position = 24)
    private String rezAgenda;

    /** 组织部门 */
    @ApiModelProperty(value = "组织部门",position = 25)
    private String orgDept;

}
