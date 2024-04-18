/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.biz.modular.teambition.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 任务主表查询参数
 *
 * @author ljy
 * @date  2023/12/08 09:03
 **/
@Getter
@Setter
public class BizTaskInfoPageParam {

    /** 当前页 */
    @ApiModelProperty(value = "当前页码")
    private Integer current;

    /** 每页条数 */
    @ApiModelProperty(value = "每页条数")
    private Integer size;

    /** 排序字段 */
    @ApiModelProperty(value = "排序字段，字段驼峰名称，如：userName")
    private String sortField;

    /** 排序方式 */
    @ApiModelProperty(value = "排序方式，升序：ASCEND；降序：DESCEND")
    private String sortOrder;

    /** 关键词 */
    @ApiModelProperty(value = "关键词")
    private String searchKey;

    /** 父任务ID */
    @ApiModelProperty(value = "父任务ID")
    private String parentId;

    /** 任务名称 */
    @ApiModelProperty(value = "任务名称")
    private String taskName;

    @ApiModelProperty(value = "任务实际开始时间", position = 9)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String taskStartTime;

    @ApiModelProperty(value = "任务实际结束时间", position = 10)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String taskEndTime;

    //@ApiModelProperty(value = "任务类型：0-返回所有能查看任务，1 - 我创建的，2 - 我执行的，3 - 我参与的",required = true)
    @ApiModelProperty(value = "任务类型：0-分页返回所有任务，1 - 我创建的，2 - 我负责的，3 - 我执行的",required = true)
    @NotNull(message = "任务类型不能为空")
    private Integer infoType;

    /** 任务状态 */
    @ApiModelProperty(value = "任务状态，0 未确认，1 已确认，2 已取消，3 进行中，4 已完成")
    private String taskStatus;

    /** 任务来源类别 */
    @ApiModelProperty(value = "任务来源类别")
    private String proType;

    /** 任务来源Id */
    @ApiModelProperty(value = "任务来源Id")
    private String proId;


    @ApiModelProperty(value = "任务预期开始时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String expectStartTime;

    @ApiModelProperty(value = "任务预期结束时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String expectEndTime;
}
