package vip.xiaonuo.sys.modular.order.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 运维工单实体
 *
 * @author yong.ye
 * @date  2025/01/13 16:13
 **/
@Getter
@Setter
@TableName("OM_ORDER")
public class OmOrder {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 工单号 */
    @ApiModelProperty(value = "工单号", position = 2)
    private String omNo;

    /** 需求部门 */
    @ApiModelProperty(value = "需求部门", position = 3)
    private String dept;

    /** 系统故障 */
    @ApiModelProperty(value = "系统故障", position = 4)
    private String omType;

    /** 需求提出人 */
    @ApiModelProperty(value = "需求提出人", position = 5)
    private String sender;

    /** 联系人 */
    @ApiModelProperty(value = "联系人", position = 6)
    private String contact;

    /** 工单故障 */
    @ApiModelProperty(value = "工单故障", position = 7)
    private String content;

    /** 故障现场原因 */
    @ApiModelProperty(value = "故障现场原因", position = 8)
    private String reason;

    /** 故障处理过程 */
    @ApiModelProperty(value = "故障处理过程", position = 9)
    private String process;

    /** 主管领导意见 */
    @ApiModelProperty(value = "主管领导意见", position = 10)
    private String managJson;

    /** 工程师处理 */
    @ApiModelProperty(value = "工程师处理", position = 11)
    private String dealJson;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 12)
    private String memo;

    /** 事件响应时间 */
    @ApiModelProperty(value = "事件响应时间", position = 13)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date omTime;

    /** 事件标题 */
    @ApiModelProperty(value = "事件标题", position = 14)
    private String title;

    /** 回馈：事件解决时间 */
    @ApiModelProperty(value = "回馈：事件解决时间", position = 15)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dealTime;

    /** 回馈：客户满意度 */
    @ApiModelProperty(value = "回馈：客户满意度", position = 16)
    private String ctScore;

    /** 回馈：客户签字 */
    @ApiModelProperty(value = "回馈：客户签字", position = 17)
    private String ctSign;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 18)
    private String attach;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 19)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 20)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 21)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 22)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 23)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 24)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 25)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;
}
