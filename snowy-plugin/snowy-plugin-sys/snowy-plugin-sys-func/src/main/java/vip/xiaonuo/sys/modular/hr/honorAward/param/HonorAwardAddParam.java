package vip.xiaonuo.sys.modular.hr.honorAward.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 项目成果添加参数
 *
 * @author pzh
 * @date  2024/10/16 15:18
 **/
@Getter
@Setter
public class HonorAwardAddParam {

    /** 荣誉奖项类型（项目成果|科技奖|数字化成果） */
    @ApiModelProperty(value = "荣誉奖项类型（项目成果|科技奖|数字化成果）",position = 1)
    private String type;

    /** 项目名称 */
    @ApiModelProperty(value = "项目名称", position = 2)
    private String prjName;

    /** 活动名称 */
    @ApiModelProperty(value = "活动名称", position = 3)
    private String activityName;

    /** 发奖单位 */
    @ApiModelProperty(value = "发奖单位", position = 4)
    private String awardingOrg;

    /** 专业 */
    @ApiModelProperty(value = "专业", position = 5)
    private String profession;

    /** 获奖等级 */
    @ApiModelProperty(value = "获奖等级", position = 6)
    private String awardLevel;

    /** 级别 */
    @ApiModelProperty(value = "级别", position = 7)
    private String level;

    /** 完成人员 */
    @ApiModelProperty(value = "完成人员", position = 8)
    private String completedPersonnel;

    /** 完成人员 */
    @ApiModelProperty(value = "完成人员Id", position = 9)
    private String completedPersonnelId;

    /** 申报时间 */
    @ApiModelProperty(value = "申报时间", position = 10)
    private Date declareDate;

    /** 证书时间 */
    @ApiModelProperty(value = "证书时间", position = 11)
    private Date certDate;

    /** 证书编号 */
    @ApiModelProperty(value = "证书编号", position = 12)
    private String certNo;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 13)
    private String note;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 14)
    private String attachments;

}
