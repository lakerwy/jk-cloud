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
 * 项目产值台账 - 项目管理编辑参数
 *
 * @author yy
 * @date  2025/06/03 09:39
 **/
@Getter
@Setter
public class PorProjectEditParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 立项Id */
    @ApiModelProperty(value = "立项Id", position = 2)
    private String projectId;

    /** 计划开工日期 */
    @ApiModelProperty(value = "计划开工日期", position = 3)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date planStartDate;

    /** 计划完工日期 */
    @ApiModelProperty(value = "计划完工日期", position = 4)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date planEndDate;

    /** 计划工期总日历天数 */
    @ApiModelProperty(value = "计划工期总日历天数", position = 5)
    private Integer planDates;

    /** 项目状态 */
    @ApiModelProperty(value = "项目状态", position = 6)
    private String projectStatus;

    /** 开工日期 */
    @ApiModelProperty(value = "开工日期", position = 7)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date realStartDate;

    /** 实际完工日期 */
    @ApiModelProperty(value = "实际完工日期", position = 8)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date realEndDate;

    /** 实际工期总日历天数 */
    @ApiModelProperty(value = "实际工期总日历天数", position = 9)
    private Integer realDates;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 10)
    private String projectMemo;

    /** 预留字段-备注 */
    @ApiModelProperty(value = "预留字段-备注", position = 15)
    private String memo;

    /** 预留字段-附件 */
    @ApiModelProperty(value = "预留字段-附件", position = 16)
    private String attach;

    /** 责任部门Id */
    @ApiModelProperty(value = "责任部门Id", position = 21)
    private String dutyDeptId;

    /** 责任部门 */
    @ApiModelProperty(value = "责任部门", position = 22)
    private String dutyDeptName;

    /** 责任人Id */
    @ApiModelProperty(value = "责任人Id", position = 23)
    private String dutyId;

    /** 责任人 */
    @ApiModelProperty(value = "责任人", position = 24)
    private String dutyName;
}
