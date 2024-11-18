package vip.xiaonuo.sys.modular.hr.honorAward.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 项目成果编辑参数
 *
 * @author pzh
 * @date  2024/10/16 15:18
 **/
@Getter
@Setter
public class HonorAwardEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 荣誉奖项类型（项目成果|科技奖|数字化成果） */
    @ApiModelProperty(value = "荣誉奖项类型（项目成果|科技奖|数字化成果）",position = 2)
    private String type;

    /** 项目名称 */
    @ApiModelProperty(value = "项目名称", position = 3)
    private String prjName;

    /** 活动名称 */
    @ApiModelProperty(value = "活动名称", position = 4)
    private String activityName;

    /** 发奖单位 */
    @ApiModelProperty(value = "发奖单位", position = 5)
    private String awardingOrg;

    /** 专业 */
    @ApiModelProperty(value = "专业", position = 6)
    private String profession;

    /** 获奖等级 */
    @ApiModelProperty(value = "获奖等级", position = 7)
    private String awardLevel;

    /** 级别 */
    @ApiModelProperty(value = "级别", position = 8)
    private String level;

    /** 完成人员 */
    @ApiModelProperty(value = "完成人员", position = 9)
    private String completedPersonnel;

    /** 完成人员 */
    @ApiModelProperty(value = "完成人员Id", position = 10)
    private String completedPersonnelId;

    /** 申报时间 */
    @ApiModelProperty(value = "申报时间", position = 11)
    private Date declareDate;

    /** 证书时间 */
    @ApiModelProperty(value = "证书时间", position = 12)
    private Date certDate;

    /** 证书编号 */
    @ApiModelProperty(value = "证书编号", position = 13)
    private String certNo;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 14)
    private String note;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 15)
    private String attachments;

    /** SN流水号 */
    @ApiModelProperty(value = "SN流水号", position = 21)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 22)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 23)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 24)
    private String status;

    /** 入参信息 */
    @ApiModelProperty(value = "入参信息", position = 25)
    private String extJson;

}
