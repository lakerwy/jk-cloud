package vip.xiaonuo.biz.modular.task.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author yong.ye
 * @date 2023/8/4 14:23
 */

@Getter
@Setter
public class BizTaskSaveParam {


//    /** 组织Id */
//    @ApiModelProperty(value = "组织Id", position = 2)
//    private String orgId;
//
//    /** 责任人Id */
//    @ApiModelProperty(value = "责任人Id", position = 3)
//    private String ower;

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

    /** 完成进度 */
    @ApiModelProperty(value = "完成进度", position = 9)
    private String status;

    /** 证明附件 */
    @ApiModelProperty(value = "证明附件", position = 10)
    private String attach;

//    /** 自评分 */
//    @ApiModelProperty(value = "自评分", position = 11)
//    private Double selfScore;
//
//    /** 人事评分 */
//    @ApiModelProperty(value = "人事评分", position = 12)
//    private Double hrScore;
//
//    /** 董事长评分 */
//    @ApiModelProperty(value = "董事长评分", position = 13)
//    private Double boosScore;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 14)
    private String memo;
}
