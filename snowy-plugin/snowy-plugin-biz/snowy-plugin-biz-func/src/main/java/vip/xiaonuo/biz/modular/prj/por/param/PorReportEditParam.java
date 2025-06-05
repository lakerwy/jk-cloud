package vip.xiaonuo.biz.modular.prj.por.param;

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
 * 项目产值台账 - 项目管理 - 项目周报|月报编辑参数
 *
 * @author yy
 * @date  2025/06/03 09:44
 **/
@Getter
@Setter
public class PorReportEditParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 项目产值台账 - 项目管理 - 主键Id */
    @ApiModelProperty(value = "项目产值台账 - 项目管理 - 主键Id", position = 2)
    private String porProjectId;

    /** 月报|周报 */
    @ApiModelProperty(value = "月报|周报", position = 3)
    private String reportType;

    /** 项目整体进度 */
    @ApiModelProperty(value = "项目整体进度", position = 4)
    private Double reportRate;

    /** 预计完工时间 */
    @ApiModelProperty(value = "预计完工时间", position = 5)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date reportPreEndDate;

    /** 项目经理id */
    @ApiModelProperty(value = "项目经理id", position = 6)
    private String reportLeaderId;

    /** 项目经理 */
    @ApiModelProperty(value = "项目经理", position = 7)
    private String reportLeaderName;

    /** 填报时间 */
    @ApiModelProperty(value = "填报时间", position = 8)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reportFillTime;

    /** 填报人id */
    @ApiModelProperty(value = "填报人id", position = 9)
    private String reportOptId;

    /** 填报人 */
    @ApiModelProperty(value = "填报人", position = 10)
    private String reportOptName;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 11)
    private String reportMemo;

    /** 本周|本月工作 */
    @ApiModelProperty(value = "本周|本月工作", position = 12)
    private String reportWork;

    /** 未按计划完成的工作、原因阐述及计划 */
    @ApiModelProperty(value = "未按计划完成的工作、原因阐述及计划", position = 13)
    private String reportNotPlan;

    /** 下周|下月计划 */
    @ApiModelProperty(value = "下周|下月计划", position = 14)
    private String reportPlan;

    /** 图片 */
    @ApiModelProperty(value = "图片", position = 15)
    private String reportImage;

    /** 文件 */
    @ApiModelProperty(value = "文件", position = 16)
    private String reportAttach;

    /** 预留字段-备注 */
    @ApiModelProperty(value = "预留字段-备注", position = 21)
    private String memo;

    /** 预留字段-附件 */
    @ApiModelProperty(value = "预留字段-附件", position = 22)
    private String attach;

}
