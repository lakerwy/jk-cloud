package vip.xiaonuo.sys.modular.order.param;

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
 * 事件处理单添加参数
 *
 * @author yong.ye
 * @date  2025/01/13 15:34
 **/
@Getter
@Setter
public class WorkOrderAddParam {

    /** 事件单号 */
    @ApiModelProperty(value = "事件单号", position = 2)
    private String eventNo;

    /** 事件分类 */
    @ApiModelProperty(value = "事件分类", position = 3)
    private String eventType;

    /** 事件影响度 */
    @ApiModelProperty(value = "事件影响度", position = 4)
    private String eventAffect;

    /** 客户名称 */
    @ApiModelProperty(value = "客户名称", position = 5)
    private String ctName;

    /** 事件优先级 */
    @ApiModelProperty(value = "事件优先级", position = 6)
    private String eventPriority;

    /** 事件紧急度 */
    @ApiModelProperty(value = "事件紧急度", position = 7)
    private String eventLevel;

    /** 所属项目 */
    @ApiModelProperty(value = "所属项目", position = 8)
    private String prjName;

    /** 事件请求人 */
    @ApiModelProperty(value = "事件请求人", position = 9)
    private String eventSender;

    /** 事件处理人 */
    @ApiModelProperty(value = "事件处理人", position = 10)
    private String eventDealer;

    /** 事件响应时间 */
    @ApiModelProperty(value = "事件响应时间", position = 11)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date eventTime;

    /** 事件标题 */
    @ApiModelProperty(value = "事件标题", position = 12)
    private String eventTitle;

    /** 事件内容 */
    @ApiModelProperty(value = "事件内容", position = 13)
    private String eventContent;

    /** 事件处理过程 */
    @ApiModelProperty(value = "事件处理过程", position = 14)
    private String eventJson;

    /** 回馈：事件处理人 */
    @ApiModelProperty(value = "回馈：事件处理人", position = 15)
    private String dealer;

    /** 回馈：事件解决事件 */
    @ApiModelProperty(value = "回馈：事件解决时间", position = 16)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dealTime;

    /** 回馈：客户满意度 */
    @ApiModelProperty(value = "回馈：客户满意度", position = 17)
    private String ctScore;

    /** 回馈：客户签字 */
    @ApiModelProperty(value = "回馈：客户签字", position = 18)
    private String ctSign;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 19)
    private String attach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 20)
    private String memo;

}
