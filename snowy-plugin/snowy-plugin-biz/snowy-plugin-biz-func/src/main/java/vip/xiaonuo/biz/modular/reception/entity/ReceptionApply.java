package vip.xiaonuo.biz.modular.reception.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 接待申请实体
 *
 * @author pzh
 * @date  2025/05/28 10:15
 **/
@Getter
@Setter
@TableName("BIZ_RECEPTION_APPLY")
public class ReceptionApply {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 申请人 */
    @ApiModelProperty(value = "申请人", position = 2)
    private String applicant;

    /** 来访单位 */
    @ApiModelProperty(value = "来访单位", position = 3)
    private String visitUnit;

    /** 接待事由 */
    @ApiModelProperty(value = "接待事由", position = 4)
    private String reasons;

    /** 来访日期 */
    @ApiModelProperty(value = "来访日期（格式：yyyy-MM-dd HH:mm:ss）", position = 5)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date visitDate;

    /** 交通需求 */
    @ApiModelProperty(value = "交通需求", position = 6)
    private String trafficDemand;

    /** 来访人员停车需求 */
    @ApiModelProperty(value = "来访人员停车需求", position = 7)
    private String parkDemand;

    /** 用餐需求 */
    @ApiModelProperty(value = "用餐需求", position = 8)
    private String dinersDemand;

    /** 用餐人数 */
    @ApiModelProperty(value = "用餐人数", position = 9)
    private String dinersNums;

    /** 住宿需求 */
    @ApiModelProperty(value = "住宿需求", position = 10)
    private String stayDemand;

    /** 入住时间 */
    @ApiModelProperty(value = "入住时间", position = 11)
    private String stayTimes;

    /** 欢迎图文字 */
    @ApiModelProperty(value = "欢迎图文字", position = 12)
    private String welcomeText;

    /** 拍照需求 */
    @ApiModelProperty(value = "拍照需求", position = 13)
    private String isPhoto;

    /** 是否需要上公众号宣传 */
    @ApiModelProperty(value = "是否需要上公众号宣传", position = 14)
    private String isAccounts;

    /** 会议室使用需求 */
    @ApiModelProperty(value = "会议室使用需求", position = 15)
    private String isMeeting;

    /** 会议室可选资源 */
    @ApiModelProperty(value = "会议室可选资源", position = 16)
    private String meetResource;

    /** 接待安排 */
    @ApiModelProperty(value = "接待安排", position = 17)
    private String arrangement;

    /** 宣传环节 */
    @ApiModelProperty(value = "宣传环节", position = 18)
    private String publicity;

    /** 宣传环节附件 */
    @ApiModelProperty(value = "宣传环节附件", position = 19)
    private String publicityAttach;

    /** 技术支持环节 */
    @ApiModelProperty(value = "技术支持环节", position = 20)
    private String support;

    /** 来访人员信息JSON */
    @ApiModelProperty(value = "来访人员信息JSON", position = 21)
    private String visitList;

    /** 我司参会人员JSON */
    @ApiModelProperty(value = "我司参会人员JSON", position = 22)
    private String ourUserList;

    /** 会议日期 */
    @ApiModelProperty(value = "会议日期（格式：yyyy-MM-dd）", position = 23)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date meetDate;

    /** 会议时间 */
    @ApiModelProperty(value = "会议时间", position = 24)
    private String meetTime;

    /** 会议地点 */
    @ApiModelProperty(value = "会议地点", position = 25)
    private String meetPlace;

    /** 会议主持 */
    @ApiModelProperty(value = "会议主持", position = 26)
    private String meetDirect;

    /** 会议流程 */
    @ApiModelProperty(value = "会议流程", position = 27)
    private String meetFlow;

    /** 行政及数字院人员安排JSON */
    @ApiModelProperty(value = "行政及数字院人员安排JSON", position = 28)
    private String divisionList;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 29)
    private String memo;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 30)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 31)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 32)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 33)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 34)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 35)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 36)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 37)
    private String status;

    /** SN流水号 */
    @ApiModelProperty(value = "SN流水号", position = 38)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 39)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 40)
    private String processInstanceId;
}
