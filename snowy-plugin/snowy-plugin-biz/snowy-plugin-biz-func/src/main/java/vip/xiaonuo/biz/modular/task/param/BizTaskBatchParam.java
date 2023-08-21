package vip.xiaonuo.biz.modular.task.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author yong.ye
 * @date 2023/8/21 9:06
 */

@Getter
@Setter
public class BizTaskBatchParam {

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

    /** 状态 */
    @ApiModelProperty(value = "状态", position = 9)
    private String status;

    /** 证明附件 */
    @ApiModelProperty(value = "证明附件", position = 10)
    private String attach;

    /** 董事长评分 */
    @ApiModelProperty(value = "董事长评分", position = 13)
    private Double boosScore;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 14)
    private String memo;

    /** 完成进度 百分比 100% 存 100 */
    @ApiModelProperty(value = "完成进度 百分比 100% 存 100", position = 15)
    private Double finishStatus;

    /** 实际完成时间 */
    @ApiModelProperty(value = "实际完成时间", position = 16)
    private Date finishDate;

    /** 董事长意见 */
    @ApiModelProperty(value = "董事长意见", position = 17)
    private String boosMemo;

    /** 权重（存小数） */
    @ApiModelProperty(value = "权重（存小数）", position = 18)
    private Double rate;

    /** 进度分系数 */
    @ApiModelProperty(value = "进度分系数", position = 19)
    private Double progressRate;

    /** 人工调整分 */
    @ApiModelProperty(value = "人工调整分", position = 20)
    private Double adjScore;
}
