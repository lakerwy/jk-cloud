package vip.xiaonuo.biz.modular.task.param;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 任务中心：流程中提交的任务数据（部门年度重点任务记录）
 * @author yong.ye
 * @date 2023/8/4 14:23
 */

@Getter
@Setter
public class FlwParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 年度重点/专项工作任务 */
    @ApiModelProperty(value = "年度重点/专项工作任务", position = 4)
    private String taskName;

    /** 开始时间 */
    @ApiModelProperty(value = "开始时间", position = 5)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    /** 完成时间 */
    @ApiModelProperty(value = "完成时间", position = 6)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    /** 过程节点 */
    @ApiModelProperty(value = "过程节点", position = 7)
    private String node;

    /** 标志成果 */
    @ApiModelProperty(value = "标志成果", position = 8)
    private String markResult;

    /** 权重（存小数） */
    @ApiModelProperty(value = "权重（存小数）", position = 18)
    private Double rate;

    /** 状态 */
    @ApiModelProperty(value = "状态", position = 19)
    private String status;

    /** =================组织绩效需求-新增的字段================= */

    /** 序号 */
    @ApiModelProperty(value = "序号", position = 200)
    private BigDecimal indexNum;

    /** 职能模块 */
    @ApiModelProperty(value = "职能模块", position = 201)
    private String funModule;

    /** 级别 */
    @ApiModelProperty(value = "级别", position = 201)
    private String level;

    /** 计划说明及完成标准 */
    @ApiModelProperty(value = "计划说明及完成标准", position = 202)
    private String taskDesc;

    /** 计划说明及完成标准 */
    @ApiModelProperty(value = "计划前置条件", position = 203)
    private String taskCondition;

    /** 配合部门 */
    @ApiModelProperty(value = "配合部门", position = 203)
    private String coopDept;

    /** 配合部门 */
    @ApiModelProperty(value = "配合部门ID", position = 204)
    private String coopDeptId;

    /** 实际完成日期 */
    @ApiModelProperty(value = "实际完成日期（格式：yyyy-MM-dd）", position = 205)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date actualFinishDate;


}
