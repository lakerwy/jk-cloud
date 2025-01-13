package vip.xiaonuo.sys.modular.order.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 事件处理单查询参数
 *
 * @author yong.ye
 * @date  2025/01/13 15:34
 **/
@Getter
@Setter
public class WorkOrderPageParam {

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

    /** 事件单号 */
    @ApiModelProperty(value = "事件单号")
    private String eventNo;

    /** 事件分类 */
    @ApiModelProperty(value = "事件分类")
    private String eventType;

    /** 事件影响度 */
    @ApiModelProperty(value = "事件影响度")
    private String eventAffect;

    /** 客户名称 */
    @ApiModelProperty(value = "客户名称")
    private String ctName;

    /** 事件优先级 */
    @ApiModelProperty(value = "事件优先级")
    private String eventPriority;

    /** 事件紧急度 */
    @ApiModelProperty(value = "事件紧急度")
    private String eventLevel;

    /** 所属项目 */
    @ApiModelProperty(value = "所属项目")
    private String prjName;

    /** 事件请求人 */
    @ApiModelProperty(value = "事件请求人")
    private String eventSender;

    /** 事件处理人 */
    @ApiModelProperty(value = "事件处理人")
    private String eventDealer;

    /** 事件响应时间开始 */
    @ApiModelProperty(value = "事件响应时间开始")
    private String startEventTime;

    /** 事件响应时间结束 */
    @ApiModelProperty(value = "事件响应时间结束")
    private String endEventTime;

    /** 事件标题 */
    @ApiModelProperty(value = "事件标题")
    private String eventTitle;

    /** 回馈：事件解决事件开始 */
    @ApiModelProperty(value = "回馈：事件解决时间开始")
    private String startDealTime;

    /** 回馈：事件解决事件结束 */
    @ApiModelProperty(value = "回馈：事件解决时间结束")
    private String endDealTime;

}
