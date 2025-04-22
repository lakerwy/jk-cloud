package vip.xiaonuo.sys.modular.hr.icManage.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 工商信息新增|变更|注销--流程申请实体
 *
 * @author yy
 * @date  2025/04/21 15:58
 **/
@Getter
@Setter
@TableName("HR_IC_MANAGE_APPLY")
public class IcManageApply {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 事项类别 */
    @ApiModelProperty(value = "事项类别", position = 2)
    private String applyType;

    /** 公司类别 */
    @ApiModelProperty(value = "公司类别", position = 3)
    private String companyType;

    /** 经办部门 */
    @ApiModelProperty(value = "经办部门", position = 4)
    private String applyDeptName;

    /** 经办部门Id */
    @ApiModelProperty(value = "经办部门Id", position = 5)
    private String applyDeptId;

    /** 经办人 */
    @ApiModelProperty(value = "经办人", position = 6)
    private String applyName;

    /** 经办人Id */
    @ApiModelProperty(value = "经办人Id", position = 7)
    private String applyId;

    /** 公司名称 */
    @ApiModelProperty(value = "公司名称", position = 8)
    private String companyName;

    /** 类型 */
    @ApiModelProperty(value = "类型", position = 9)
    private String type;

    /** 统一社会信用代码 */
    @ApiModelProperty(value = "统一社会信用代码", position = 10)
    private String corpCode;

    /** 注册资本(元) */
    @ApiModelProperty(value = "注册资本(元)", position = 11)
    private BigDecimal registeredCapital;

    /** 经营范围 */
    @ApiModelProperty(value = "经营范围", position = 12)
    private String businessScope;

    /** 实缴资本(元) */
    @ApiModelProperty(value = "实缴资本(元)", position = 13)
    private BigDecimal paidinCapital;

    /** 住所 */
    @ApiModelProperty(value = "住所", position = 14)
    private String address;

    /** 成立日期 */
    @ApiModelProperty(value = "成立日期", position = 15)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date establishTime;

    /** 法定代表人 */
    @ApiModelProperty(value = "法定代表人", position = 16)
    private String legalRepresentative;

    /** 法定代表人身份信息 */
    @ApiModelProperty(value = "法定代表人身份信息", position = 17)
    private String legalRepresentativeId;

    /** 经营期限类型（长期|有期限时间） */
    @ApiModelProperty(value = "经营期限类型（长期|有期限时间）", position = 18)
    private String businessTermType;

    /** 经营期限-开始时间 */
    @ApiModelProperty(value = "经营期限-开始时间", position = 19)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date businessTermStart;

    /** 经营期限-结束时间 */
    @ApiModelProperty(value = "经营期限-结束时间", position = 20)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date businessTermEnd;

    /** 股东信息JSON */
    @ApiModelProperty(value = "股东信息JSON", position = 21)
    private String shareholderInfoJson;

    /** 主要人员JSON */
    @ApiModelProperty(value = "主要人员JSON", position = 22)
    private String mainPersonnelJson;

    /** 工商变更记录JSON */
    @ApiModelProperty(value = "工商变更记录JSON", position = 23)
    private String icUpdateRecordJson;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 24)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 25)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 26)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 27)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 28)
    private String memo;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 29)
    private String attachments;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 30)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 31)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 32)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;

    /** SN */
    @ApiModelProperty(value = "SN", position = 33)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 34)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 35)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 36)
    private String status;




    /** 变更|注销的公司id（HR_IC_MANAGE.ID） */
    @ApiModelProperty(value = "变更|注销的公司id（HR_IC_MANAGE.ID）", position = 41)
    private String icManageId;

    /** 变更|注销的公司（HR_IC_MANAGE.COMPANY_NAME） */
    @ApiModelProperty(value = "变更|注销的公司（HR_IC_MANAGE.COMPANY_NAME）", position = 42)
    private String icManageCompanyName;

    /** 注销时间 */
    @ApiModelProperty(value = "注销时间", position = 43)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyDate;

    /** 附件与备注-备注|注销说明 */
    @ApiModelProperty(value = "附件与备注-备注|注销说明", position = 44)
    private String applyMemo;

    /** 附件与备注-附件|注销说明-附件 */
    @ApiModelProperty(value = "附件与备注-附件|注销说明-附件", position = 45)
    private String applyAttach;
}
