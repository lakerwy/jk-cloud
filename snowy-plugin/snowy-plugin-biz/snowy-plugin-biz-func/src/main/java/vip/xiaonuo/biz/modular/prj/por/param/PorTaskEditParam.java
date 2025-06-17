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
 * 项目产值台账 - 项目管理 - 项目计划管理编辑参数
 *
 * @author yy
 * @date  2025/06/03 09:42
 **/
@Getter
@Setter
public class PorTaskEditParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 项目产值台账 - 项目管理 - 主键Id */
    @ApiModelProperty(value = "项目产值台账 - 项目管理 - 主键Id", position = 2)
    private String porProjectId;

    /** 任务名称 */
    @ApiModelProperty(value = "任务名称", position = 3)
    private String taskName;

    /** 责任人id */
    @ApiModelProperty(value = "责任人id", position = 4)
    private String taskLeaderId;

    /** 责任人 */
    @ApiModelProperty(value = "责任人", position = 5)
    private String taskLeaderName;

    /** 计划开始日期 */
    @ApiModelProperty(value = "计划开始日期", position = 6)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date taskPlanStartDate;

    /** 计划结束日期 */
    @ApiModelProperty(value = "计划结束日期", position = 7)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date taskPlanEndDate;

    /** 实际开始日期 */
    @ApiModelProperty(value = "实际开始日期", position = 8)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date taskRealStartDate;

    /** 实际结束日期 */
    @ApiModelProperty(value = "实际结束日期", position = 9)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date taskRealEndDate;

    /** 实际工期天数 */
    @ApiModelProperty(value = "实际工期天数", position = 10)
    private Integer taskRealDates;

    /** 任务完成进度百分比 */
    @ApiModelProperty(value = "任务完成进度百分比", position = 11)
    private Double taskFinishRate;

    /** 任务描述 */
    @ApiModelProperty(value = "任务描述", position = 12)
    private String taskDesc;

    /** 预留字段-备注 */
    @ApiModelProperty(value = "预留字段-备注", position = 17)
    private String memo;

    /** 预留字段-附件 */
    @ApiModelProperty(value = "预留字段-附件", position = 18)
    private String attach;

    /** 任务状态 */
    @ApiModelProperty(value = "任务状态", position = 22)
    private String taskStatus;
}
