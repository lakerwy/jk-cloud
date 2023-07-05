package vip.xiaonuo.biz.modular.mapBiz.Chartreviews.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class BizChartReviewsVo {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 流程单号 */
    @ApiModelProperty(value = "流程单号", position = 2)
    private Integer taskId;

    /** SN值 */
    @ApiModelProperty(value = "SN值", position = 3)
    private String sn;

    /** 受理人 */
    @ApiModelProperty(value = "受理人", position = 4)
    private String opt;

    /** 项目(合同)编号 */
    @ApiModelProperty(value = "项目(合同)编号", position = 6)
    private String projectNo;

    /** 送审人 */
    @ApiModelProperty(value = "送审人", position = 7)
    private String sender;

    /** 项目来源 */
    @ApiModelProperty(value = "项目来源", position = 8)
    private String companyOrDepartment;

    /** 项目负责人 */
    @ApiModelProperty(value = "项目负责人", position = 9)
    private String projectLeader;

    /** 设计阶段 */
    @ApiModelProperty(value = "设计阶段", position = 10)
    private String mapStage;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 14)
    private String note;

    /** 受理状态 */
    @ApiModelProperty(value = "受理状态", position = 22)
    private String status;

    /** 合同名称（历史表Hetong.ProjectName） */
    @ApiModelProperty(value = "合同名称（历史表Hetong.ProjectName）", position = 27)
    private String projectName;

    /** 工程类别（历史表Hetong.SecondLevel） */
    @ApiModelProperty(value = "工程类别（历史表Hetong.SecondLevel）", position = 25)
    private String secondLevel;

    /** 受理时间 */
    @ApiModelProperty(value = "受理时间", position = 30)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date optDate;
}
